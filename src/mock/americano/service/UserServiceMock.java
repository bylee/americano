package americano.service;

import java.util.ArrayList;
import java.util.List;

import americano.model.Book;

public class UserServiceMock
extends UserService
{
	public List<Book> getBooks( String username )
	{
		final ArrayList<Book> books = new ArrayList<Book>();
		
		final Book book1 = new Book();
		book1.setId( "aaa" );
		book1.setTitle( "Hello, World" );
		books.add( book1 );

		final Book book2 = new Book();
		book2.setId( "bbb" );
		book2.setTitle( "Getting started" );
		books.add( book2 );

		return books;
	}

}
