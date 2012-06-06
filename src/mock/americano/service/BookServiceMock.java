package americano.service;

import java.util.ArrayList;
import java.util.List;

import americano.model.Book;

public class BookServiceMock
extends BookService
{
	public List<Book> getBooksOf( String username )
	{
		final ArrayList<Book> books = new ArrayList<Book>();
		
		final Book book1 = new Book();
		
		book1.setId( "aaa" );
		book1.setTitle( "Hello, World" );
		books.add( book1 );
		
		return books;
	}

}
