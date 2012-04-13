package americano.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import americano.model.User;

public class UserDao
extends HibernateDaoSupport
{
	public void insertUser( User user )
	{
		getHibernateTemplate().save( user );
	}

}
