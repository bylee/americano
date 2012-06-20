package americano.dao;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import americano.model.Chapter;

@Repository
public class
ChapterDao
extends AbstractDao
{
	@SuppressWarnings("unchecked")
	public Collection<Chapter> getChapterIn( final String bookId )
	{
		return (List<Chapter>) find( MessageFormat.format( "from Chapter chapter where chapter.bookId = '{}", bookId ) );
	}

}
