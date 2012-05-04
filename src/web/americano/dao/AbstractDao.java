package americano.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDao
{
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
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
	
	protected Serializable save( Object obj )
	{
		logger.trace( "Save {}", obj );
		return getSession().save( obj );
	}
	
	protected void update( Object obj )
	{
		getSession().update( obj );
	}
	
	protected void delete( Object obj )
	{
		getSession().delete( obj );
	}
	
	protected List<?> find(
		final String queryStr
	)
	{
		Query query = getSession().createQuery( queryStr );
		
		return query.list();

	}

}
