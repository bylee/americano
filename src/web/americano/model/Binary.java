package americano.model;

public class Binary
{
	protected String id;
	
	protected String owner;
	
	protected String type;
	
	protected byte[] contents;
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId( final String id )
	{
		this.id = id;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public void setOwner( final String owner )
	{
		this.owner = owner;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType( final String type )
	{
		this.type = type;
	}
	
	public byte[] getContents()
	{
		return this.contents;
	}
	
	public void setContents( final byte[] contents )
	{
		this.contents = contents;
	}
	
}
