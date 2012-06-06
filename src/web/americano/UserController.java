package americano;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import americano.model.Book;
import americano.model.User;
import americano.service.UserService;

@RequestMapping( "/user" )
@Controller
public class
UserController
extends AbstractController
{
	@Autowired
	protected UserService userService;

	@RequestMapping( value = "/{id}", method = RequestMethod.GET )
	public @ResponseBody User
	getUser(
		@PathVariable( "id" ) String id
	)
	{
		final User user = userService.getUser( id );
		
		return user;
	}

	@RequestMapping( value = "/{user}/books.html", method = RequestMethod.GET )
	public ModelAndView
	formBooks(
		@PathVariable( "user" ) final String username
	)
	{
		final ModelAndView ret = new ModelAndView( "user/books" );
		
		return ret;
	}

	@RequestMapping( value = "/{user}/books", method = RequestMethod.GET )
	public
	@ResponseBody Collection<Book>
	getBooks(
		@PathVariable( "user" ) final String username
	)
	{
		return userService.getBooks( username );
	}
	

}
