package americano.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import americano.dao.CrudDao;
import americano.model.CrudObject;
import americano.util.ClassUtils;
import americano.util.CrudUtils;
import escode.util.CollectionUtils;
import escode.util.StreamUtils;
import escode.util.StringUtils;

@Component
public class CrudService
{

	// TODO Change to HashMap
	protected ArrayList<Class<?>> dm = new ArrayList<Class<?>>();
	
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	@Autowired
	protected CrudDao dao;

	private static final String FILE_NAME = "crud.txt";

	public CrudService()
	{
		final ClassLoader cl =
			Thread.currentThread().getContextClassLoader();
		
		// Gaurantee no hangul( only english )
		final BufferedReader in =
			new BufferedReader( new InputStreamReader( cl.getResourceAsStream( FILE_NAME ) ) );
		try
		{
			String line = null;
			
			for ( int iLine = 1 ; null != ( line = in.readLine() ) ; ++iLine )
			{
				if ( StringUtils.isEmpty( line ) )
				{
					logger.debug( "'{}' th line ignored", iLine );
					continue;
				}
				
				final String className = StringUtils.trim( line );
				try
				{
					final Class<?> clazz = Class.forName( className );
					
					dm.add( clazz );
				}
				catch ( final ClassNotFoundException e )
				{
					logger.warn( "{} is not found", className );
				}
			}
			
		}
		catch ( final IOException e )
		{
			logger.error( "Crud list not loaded", e );
		}
		finally
		{
			logger.info( "{} model(s) loaded", dm.size() );
			StreamUtils.tryClose( in );
		}
		
	}
	
	public
	Class<?>
	getModel(
		final String name
	)
	{
		logger.trace( "model name :{}", name );
		
		if ( null == name )
		{
			throw new NullPointerException();
		}

		logger.debug( "registered model :{}", dm );
		for ( final Class<?> modelType : dm )
		{
			final String modelName = modelType.getSimpleName();
			
			if ( !modelName.equals( name ) )
			{
				continue;
			}
			
			return modelType;
		}
		throw new IllegalArgumentException();
	}
	
	public
	Object
	getModel(
		final String modelName,
		final String id
	)
	{
		Class<?> clazz = getModel( modelName );
		return dao.get( clazz, id );
	}
	
	public
	List<String>
	getAllModels()
	{
		final ArrayList<String> models = new ArrayList<String>();
		
		for ( final Class<?> modelType : dm )
		{
			final String modelName = modelType.getSimpleName();
			models.add( modelName );
		}
		
		return models;
	}
	
	
	public
	List<CrudObject>
	listModel( final String modelName )
	{
		final Class<?> modelType = getModel( modelName );
		
		final Field idField = CrudUtils.getIdField( modelType );
		final String fieldName = idField.getName();

		ArrayList<CrudObject> ret = new ArrayList<CrudObject>();
		for ( final Object obj : dao.list( modelName ) )
		{
			final Object id = ClassUtils.getFieldValue( obj, fieldName );
			ret.add( new CrudObject( id, obj ) );
		}
		
		return ret;
	}
	
	public
	Object
	create(
		final String modelName,
		final Map<String, String[]> parameters
	)
	throws InstantiationException, IllegalAccessException
	{
		final Class<?> modelType = getModel( modelName );
		final Object obj = modelType.newInstance();
		for ( final String fieldKey : parameters.keySet() )
		{
			final String fieldValue = CollectionUtils.pickupFirst( parameters.get( fieldKey ) );
			logger.debug( "Field :{}, Value :{}", fieldKey, fieldValue );

			ClassUtils.setFieldValue( obj, fieldKey, fieldValue );
		}
		
		dao.insert( obj );
		
		return obj;

	}


}
