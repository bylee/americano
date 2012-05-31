package americano.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import americano.model.User;

@Repository
public class UserDao
extends AbstractDao
{
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	public
	void
	insertUser(
		final User user
	)
	{
		insert( user );
	}
	
	public
	User
	getUser( final String username )
	{
		return get( User.class, username );
	}

}
