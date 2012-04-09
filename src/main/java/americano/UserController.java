package americano;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class
UserController
{
	protected String
	list(
		Model model
	)
	{
		return "list_users";
	}
	
	@RequestMapping( value = "/users", method = RequestMethod.GET )
	public String
	createForm(
		@RequestParam( value = "form", required = false ) final String bForm,
		Model model
	)
	{
		if ( null != bForm )
		{
			return list( model );
		}
		return "create_user";
	}
	
	@RequestMapping( value = "/users", method = RequestMethod.POST )
	public ModelAndView
	create(
	)
	{
		return null;
	}
	
	@RequestMapping( value = "/users1", method = RequestMethod.GET )
	public ModelAndView
	show()
	{
		final ModelAndView ret = new ModelAndView();
		return ret;
	}

}
