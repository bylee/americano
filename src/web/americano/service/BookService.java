package americano.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import americano.dao.BookDao;
import americano.dao.ChapterDao;
import americano.model.Book;
import americano.model.Chapter;
import escode.util.ObjectUtils;


public class
BookService
{
	@Autowired
	protected BookDao bookDao;
	
	@Autowired
	protected ChapterDao chapterDao;
	
	public
	Collection<Chapter>
	getChapters(
		final String bookId
	)
	{
		return chapterDao.getChapterIn( bookId );
	}
	
	public Book
	createBook( final Book book )
	{
		book.setId( ObjectUtils.generateGUID( book ) );
		bookDao.insert( book );
		
		return book;
	}
}
