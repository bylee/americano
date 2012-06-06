package americano.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book
{
	@Id
	protected String id;
	
	protected String title;
	
	protected String subtitle;
	
	protected String author;
	
	protected String edition;
	
	protected String description;
	
	protected String keyword;
	
	protected String tag;
	
	protected String theme;
	
	protected String createdDate;
	
	protected String owner;

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

	/**
	 * @return the edition
	 */
	public String getEdition()
	{
		return edition;
	}

	/**
	 * @param edition the edition to set
	 */
	public void setEdition( String edition )
	{
		this.edition = edition;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription( String description )
	{
		this.description = description;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword()
	{
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword( String keyword )
	{
		this.keyword = keyword;
	}

	/**
	 * @return the tag
	 */
	public String getTag()
	{
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag( String tag )
	{
		this.tag = tag;
	}

	/**
	 * @return the theme
	 */
	public String getTheme()
	{
		return theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme( String theme )
	{
		this.theme = theme;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate( String createdDate )
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return the owner
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner( String owner )
	{
		this.owner = owner;
	}

	
}
