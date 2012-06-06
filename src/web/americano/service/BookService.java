package americano.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import escode.util.ObjectUtils;

import americano.dao.BookDao;
import americano.model.Book;
import americano.model.Chapter;


public class
BookService
{
	@Autowired
	protected BookDao bookDao;
	
	
	public Collection<Chapter>
	getChapters(
		final String bookId
	)
	{
		return null;
	}
	
	public Book
	createBook( final Book book )
	{
		book.setId( ObjectUtils.generateGUID( book ) );
		bookDao.insert( book );
		
		return book;
	}
}
