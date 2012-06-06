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
	
	

}
