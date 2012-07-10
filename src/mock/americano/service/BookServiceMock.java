package americano.service;

import java.util.ArrayList;
import java.util.Collection;

import escode.util.ObjectUtils;

import americano.model.Book;
import americano.model.Chapter;

public class BookServiceMock
extends BookService
{
	public Collection<Chapter> getChapters( String bookId )
	{
		final ArrayList<Chapter> chapters = new ArrayList<Chapter>();
		if ( "aaa".equals( bookId ) )
		{
			final Chapter chapter1 = new Chapter();
			chapter1.setId( "ch1" );
			chapter1.setTitle( "aaa-Chapter1" );
			chapters.add( chapter1 );
			final Chapter chapter2 = new Chapter();
			chapter2.setId( "ch2" );
			chapter2.setTitle( "aaa-Chapter2" );
			chapters.add( chapter2 );
		}
		else if ( "bbb".equals( bookId ) )
		{
			final Chapter chapter1 = new Chapter();
			chapter1.setId( "ch1" );
			chapter1.setTitle( "bbb-Chapter1" );
			chapters.add( chapter1 );
			final Chapter chapter2 = new Chapter();
			chapter2.setId( "ch2" );
			chapter2.setTitle( "bbb-Chapter2" );
			chapters.add( chapter2 );
		}
		else if ( "testbook".equals( bookId ) )
		{
			final Chapter chapter1 = new Chapter();
			chapter1.setId( "ch1" );
			chapter1.setTitle( "bbb-Chapter1" );
			chapters.add( chapter1 );
			final Chapter chapter2 = new Chapter();
			chapter2.setId( "ch2" );
			chapter2.setTitle( "bbb-Chapter2" );
			chapters.add( chapter2 );
		}
		return chapters;
	}

	public Book
	createBook( final Book book )
	{
		book.setId( ObjectUtils.generateGUID( book ) );
		return book;
	}

}
