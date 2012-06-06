package americano.service;

import java.util.ArrayList;
import java.util.Collection;

import americano.model.Chapter;

public class BookServiceMock
extends BookService
{
	public Collection<Chapter> getChapters( String bookId )
	{
		final ArrayList<Chapter> chpaters = new ArrayList<Chapter>();
		if ( "aaa".equals( bookId ) )
		{
		}
		else if ( "bbb".equals( bookId ) )
		{
			
		}
		return chpaters;
	}

}
