package americano.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import americano.dao.BookDao;
import americano.dao.UserDao;
import americano.model.User;

@Component
public class UserService
{
	@Autowired
	protected UserDao userDao;
	
	@Autowired
	protected BookDao bookDao;
	
	public User getUser( final String username )
	{
		return userDao.getUser( username );
		
	}
}
