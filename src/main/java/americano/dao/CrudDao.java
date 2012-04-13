package americano.dao;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CrudDao
extends HibernateDaoSupport
{
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	
	public
	void
	insert(
		final Object obj
	)
	{
		logger.trace( "Insert {}", obj );
		getHibernateTemplate().save( obj );
	}
	
	public List<?>
	list(
		final String modelName
	)
	{
		logger.trace( "Select {}", modelName );
		final List<?> result = getHibernateTemplate().find(
			MessageFormat.format( "from {0}", modelName )
		);
		
		logger.debug( "Result for {} :{}", modelName, result );
		
		return result;
	}
	
	public Object
	get(
		final Class<?> modelType,
		final String id
	)
	{
		logger.trace( "Get {} from {}", id, modelType );
		final Object ret = getHibernateTemplate().get( modelType, id );
		
		logger.debug( "Result for {} :{}", id, ret );
		
		return ret;
	}
	
	public void update( Object obj )
	{
		getHibernateTemplate().update( obj );
	}
	
	public void delete( Object obj )
	{
		getHibernateTemplate().delete( obj );
	}

}
