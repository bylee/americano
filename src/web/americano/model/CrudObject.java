package americano.model;

public class CrudObject
{
	protected Object id;
	
	protected Object model;
	
	public CrudObject()
	{
	}
	
	public CrudObject(
		final Object id,
		final Object model
	)
	{
		setId( id );
		setModel( model );
	}
	
	public Object getId()
	{
		return this.id;
	}
	
	public void setId( final Object id )
	{
		this.id = id;
	}
	
	public Object getModel()
	{
		return this.model;
	}
	
	public void setModel( final Object model )
	{
		this.model = model;
	}

}
