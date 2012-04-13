package americano;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import americano.dao.CrudDao;
import americano.model.Book;
import americano.model.Crud;
import americano.model.CrudField;
import americano.model.User;
import americano.util.ClassUtils;
import americano.util.StringUtils;

@Controller
@RequestMapping( "/crud" )
public class
CrudController
{
	// TODO Change to HashMap
	protected static ArrayList<Class<?>> dm = new ArrayList<Class<?>>(
		Arrays.asList(
			User.class,
			Book.class
		)
	);
	
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	@Autowired
	protected CrudDao dao;
	
	protected
	Class<?>
	getModel(
		final String name
	)
	{
		if ( null == name )
		{
			throw new NullPointerException();
		}
		
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
	
	protected
	String
	getPath(
		final String name
	)
	{
		return "crud/" + name;
	}
	
	protected
	Field
	getIdField( Class<?> clazz )
	{
		final Field[] fields = clazz.getFields();
		
		final Field idField = getIdField( fields );
		if ( null != idField )
		{
			return idField;
		}
		
		return getIdField( clazz.getDeclaredFields() );
	}
	
	protected
	Field
	getIdField(
		final Field[] fields
	)
	{
		for ( int i = 0, n = fields.length ; i < n ; ++i )
		{
			final Annotation[] annotations = fields[i].getAnnotations();
			for ( int j = 0, m = annotations.length ; j < m ; ++j )
			{
				if ( annotations[j] instanceof Id )
				{
					return fields[i];
				}
			}
		}
		
		return null;
	}
	

	@RequestMapping(  value = { "/index.html", "/", "" } )
	public
	ModelAndView
	index()
	{
		final ModelAndView ret = new ModelAndView( getPath( "index" ) );
		
		final ArrayList<Crud> models = new ArrayList<Crud>();
		
		for ( final Class<?> modelType : dm )
		{
			final String modelName = modelType.getSimpleName();
			Crud crud = new Crud( modelName, getPath( modelName ) );
			models.add( crud );
		}
		
		ret.addObject( "models", models );
		
		return ret;
	}
	
	@RequestMapping( value = "/{object}", method = RequestMethod.GET )
	public
	ModelAndView 
	list(
		@PathVariable( "object" ) final String modelName
	)
	{
		final ModelAndView ret = new ModelAndView( getPath( "list" ) );
		
		final HashMap<Object, Object> id2name = new HashMap<Object, Object>();
		
		final Class<?> modelType = getModel( modelName );
		
		final Field idField = getIdField( modelType );
		final String fieldName = idField.getName();
		
		for ( final Object obj : dao.list( modelName ) )
		{
			final Object id = ClassUtils.getFieldValue( obj, fieldName );
			id2name.put( id, obj );
		}
		
		ret.addObject( "models", id2name );
		ret.addObject( "model", new Crud( modelName, getPath( modelName ) ) );
		return ret;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping( value = "/{object}", method = RequestMethod.POST )
	public
	ModelAndView
	create(
		@PathVariable( "object" ) final String modelName,
		HttpServletRequest req
	)
	throws InstantiationException, IllegalAccessException
	{
		final Class<?> modelType = getModel( modelName );
		final Object obj = modelType.newInstance();
		final Enumeration<String> parameterKeys = req.getParameterNames();
		while ( parameterKeys.hasMoreElements() )
		{
			final String fieldKey = parameterKeys.nextElement();
			final String fieldValue = (String) req.getParameter( fieldKey );
			logger.debug( "Field :{}, Value :{}", fieldKey, fieldValue );

			ClassUtils.setFieldValue( obj, fieldKey, fieldValue );
		}
		
		dao.insert( obj );
		
		if ( !StringUtils.isEmpty( req.getParameter( "save" ) ) )
		{
			return list( modelName );
		}
		else if ( !StringUtils.isEmpty( req.getParameter( "saveAndContinue" ) ) )
		{
			final Field idField = getIdField( modelType );
			final Object idValue = ClassUtils.getFieldValue( obj, idField.getName() );
			
			return updateForm( modelName, idValue.toString() );
		}
		else if ( !StringUtils.isEmpty( req.getParameter( "saveAndAddAnother" ) ) )
		{
			return createForm( modelName );
		}
		
		return list( modelName );
		
	}

	@RequestMapping( "/{object}/new" )
	public
	ModelAndView
	createForm(
		@PathVariable( "object" ) final String modelName
	)
	throws InstantiationException, IllegalAccessException
	{
		final Class<?> modelType = getModel( modelName );
		
		final ModelAndView ret = new ModelAndView( getPath( "new" ) );
		ret.addObject( "fields", CrudField.create( modelType.newInstance() ) );
		ret.addObject( "model", new Crud( modelName, getPath( modelName ) ) );
		return ret;
	}
	
	public
	ModelAndView
	update()
	{
		final ModelAndView ret = new ModelAndView();
		return ret;
	}

	@RequestMapping( value = "/{object}/{id}" )
	public
	ModelAndView
	updateForm(
		@PathVariable( "object" ) final String modelName,
		@PathVariable( "id" ) final String id
	)
	throws InstantiationException, IllegalAccessException
	{
		final Class<?> modelType = getModel( modelName );
		final Object modelObj = dao.get( modelType, id );
		
		final ModelAndView ret = new ModelAndView( getPath( "edit" ) );
		ret.addObject( "fields", CrudField.create( modelObj ) );
		ret.addObject( "model", new Crud( modelName, getPath( modelName ) ) );
		ret.addObject( "id", id );
		return ret;
	}
	
	@RequestMapping( value = "/{object}/{id}", method = RequestMethod.DELETE )
	public
	ModelAndView
	delete(
		@PathVariable( "object" ) final String modelName,
		@PathVariable( "id" ) final String id
	)
	{
		logger.trace( "DELETE request" );
		
		final ModelAndView ret = new ModelAndView();
		return ret;
	}
}
