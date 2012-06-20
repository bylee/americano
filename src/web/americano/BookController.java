package americano;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import americano.model.Book;
import americano.model.Chapter;
import americano.service.BookService;

@Controller
@RequestMapping( "/book" )
public class BookController
{
	@Autowired
	protected BookService bookService;
	
	@RequestMapping( value = "/{id}/chapters", method = RequestMethod.GET )
	public
	@ResponseBody Collection<Chapter>
	getChpaters(
		@PathVariable( "id" ) final String bookId
	)
	{
		return bookService.getChapters( bookId );
	}
	
	@RequestMapping( value = "/", method = RequestMethod.POST )
	public
	@ResponseBody Book
	create(
		Book book
	)
	{
		return bookService.createBook( book );
	}

}
