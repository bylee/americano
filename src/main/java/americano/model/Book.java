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

}
