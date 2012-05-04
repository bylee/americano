package americano.model;

public class Crud
{
	protected String name;
	
	protected String path;
	
	public Crud(
		final String name,
		final String path
	)
	{
		this.name = name;
		
		this.path = path;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath( String path )
	{
		this.path = path;
	}
}
