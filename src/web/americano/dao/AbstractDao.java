package americano.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao
{
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public
	void
	setSessionFactory(
		final SessionFactory sessionFactory
	)
	{
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	public void insert( Object obj )
	{
		logger.trace( "Insert {}", obj );
		getSession().persist( obj );
		getSession().save( obj );
	}
	
	public void update( Object obj )
	{
		getSession().update( obj );
		getSession().save( obj );
	}
	
	public void delete( Object obj )
	{
		getSession().delete( obj );
		getSession().save( obj );
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get( Class<T> clazz, Serializable id )
	{
		logger.trace( "Get {} from {}", id, clazz );
		getSession().flush();
		T ret = (T) getSession().get( clazz, id );
		logger.debug( "Result for {} :{}", id, ret );
		
		return ret;
	}
	
	protected List<?> find(
		final String queryStr
	)
	{
		getSession().flush();
		Query query = getSession().createQuery( queryStr );
		
		return query.list();

	}

}
