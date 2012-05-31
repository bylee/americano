package americano;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import americano.model.Crud;
import americano.model.CrudField;
import americano.model.CrudObject;
import americano.service.CrudService;
import americano.util.ClassUtils;
import americano.util.CrudUtils;
import escode.util.StringUtils;

@Controller
@RequestMapping( "/crud" )
public class
CrudController
extends AbstractController
{
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	@Autowired
	protected CrudService svc;
	
	protected
	String
	getPath(
		final String name
	)
	{
		return "crud/" + name;
	}
	

	@RequestMapping(  value = { "/index.html", "/", "" } )
	public
	ModelAndView
	index()
	{
		final ModelAndView ret = new ModelAndView( getPath( "index" ) );
		
		final ArrayList<Crud> models = new ArrayList<Crud>();
		
		for ( final String modelName : svc.getAllModels() )
		{
			final Crud crud = new Crud( modelName, getPath( modelName ) );
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
		
		final List<CrudObject> models = svc.listModel( modelName );
		
		ret.addObject( "models", models );
		ret.addObject( "model", new Crud( modelName, getPath( modelName ) ) );
		return ret;
	}
	

	@RequestMapping( value = "/{object}", method = RequestMethod.POST )
	public
	ModelAndView
	create(
		@PathVariable( "object" ) final String modelName,
		HttpServletRequest req
	)
	throws InstantiationException, IllegalAccessException
	{
		Object obj = svc.create( modelName, req.getParameterMap() );
		
		if ( !StringUtils.isEmpty( req.getParameter( "save" ) ) )
		{
			return list( modelName );
		}
		else if ( !StringUtils.isEmpty( req.getParameter( "saveAndContinue" ) ) )
		{
			final Class<?> modelType = svc.getModel( modelName );
			final Field idField = CrudUtils.getIdField( modelType );
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
		final Class<?> modelType = svc.getModel( modelName );
		
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
		final Object modelObj = svc.getModel( modelName, id );
		
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
