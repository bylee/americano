package americano;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping( "/chapter" )
public class ChapterController {

	@RequestMapping( "/chapter.html")
	public ModelAndView formChapter()
	{
		return new ModelAndView( "chapter/chapter" );
	}
} 
