package americano.dao;

import americano.model.User;

public class UserDao
extends AbstractDao
{
	public
	void
	insertUser(
		final User user
	)
	{
		save( user );
	}

}
