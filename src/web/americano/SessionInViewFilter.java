package americano;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import escode.util.MapUtils;

public class
SessionInViewFilter
extends OpenSessionInViewFilter
{
	protected FlushMode flushMode = getDefaultFlushMode();
	
	protected Map<String, FlushMode> str2flushMode = MapUtils.convert( new Object[][] {
		new Object[] { "manual", FlushMode.MANUAL },
		new Object[] { "commit", FlushMode.COMMIT },
		new Object[] { "auto", FlushMode.AUTO },
		new Object[] { "always", FlushMode.ALWAYS },
	} );
	
	protected
	FlushMode
	getDefaultFlushMode()
	{
		return FlushMode.AUTO;
	}

	public
	void
	setFlushMode(
		final String mode
	)
	{
		if ( null == mode )
		{
			this.flushMode  = getDefaultFlushMode();
			logger.info( "change to default flush mode :" + this.flushMode );
			return ;
		}
		
		final FlushMode flushMode = str2flushMode.get( mode.toLowerCase() );
		if ( null == flushMode )
		{
			logger.warn( "unknown flush mode :" + mode );
			return ;
		}
		logger.info( "Change flush mode :" + this.flushMode + " -> " + flushMode );
		this.flushMode = flushMode;
	}

	protected
	Session
	openSession(
		final SessionFactory sessionFactory
	)
	throws DataAccessResourceFailureException
	{
		try
		{
			final Session session = SessionFactoryUtils.openSession( sessionFactory );
			configureSession( session );
			return session;
		}
		catch (
			final HibernateException ex
		)
		{
			throw new DataAccessResourceFailureException( "Could not open Hibernate Session", ex );
		}

	}

	protected void
	configureSession(
		final Session session
	)
	{
		session.setFlushMode( flushMode );
	}
	
	protected boolean createSession()
	{
		final SessionFactory sessionFactory = lookupSessionFactory();

		if ( TransactionSynchronizationManager.hasResource( sessionFactory ) )
		{
			return false;
		}
		
		logger.debug("Opening Hibernate Session in SessionInViewFilter");
		Session session = openSession( sessionFactory );
		TransactionSynchronizationManager.bindResource( sessionFactory, new SessionHolder( session ) );
		return true;
	}
	
	protected void destroySession()
	{
		final SessionFactory sessionFactory = lookupSessionFactory();
		final SessionHolder sessionHolder = 
			(SessionHolder) TransactionSynchronizationManager.unbindResource( sessionFactory );
		logger.debug("Closing Hibernate Session in SessionInViewFilter");
		final Session session = sessionHolder.getSession();
		session.flush();
		SessionFactoryUtils.closeSession( session );

	}

	protected void doFilterInternal( HttpServletRequest request,
		HttpServletResponse response, FilterChain filterChain )
	throws ServletException, IOException
	{
		boolean bParticipate = createSession();
		if ( logger.isTraceEnabled() )
		{
			logger.trace( "session's participation :" + bParticipate );
		}

		try
		{
			filterChain.doFilter( request, response );
		}
		finally
		{
			if ( bParticipate )
			{
				destroySession();
			}
		}
	}
}
