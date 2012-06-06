package americano.model;

import javax.persistence.Id;

public class Chapter
{
	@Id
	protected String id;
	
	protected String bookId;
	
	protected Chapter previousChapter;
	
	protected String title;
	
	protected String type;
	
	protected String subtitle;
	
	protected String author;

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * @return the bookId
	 */
	public String getBookId()
	{
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId( String bookId )
	{
		this.bookId = bookId;
	}

	/**
	 * @return the previousChapter
	 */
	public Chapter getPreviousChapter()
	{
		return previousChapter;
	}

	/**
	 * @param previousChapter the previousChapter to set
	 */
	public void setPreviousChapter( Chapter previousChapter )
	{
		this.previousChapter = previousChapter;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle( String title )
	{
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType( String type )
	{
		this.type = type;
	}

	/**
	 * @return the subtitle
	 */
	public String getSubtitle()
	{
		return subtitle;
	}

	/**
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle( String subtitle )
	{
		this.subtitle = subtitle;
	}

	/**
	 * @return the author
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor( String author )
	{
		this.author = author;
	}
	
	

}
