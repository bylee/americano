package americano.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

@Entity
public class
User
{
	@Id
	protected String username;
	
	@Column
	protected String password;
	
	@Column
	protected String iconId;
	
	@Column
	protected String pictureId;
	
	public User()
	{
	}
	
	public User( final String username )
	{
		this( username, null );
	}
	
	public User( final String username, final String password )
	{
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername( String username )
	{
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword( String password )
	{
		this.password = password;
	}
	
	public String getIcon()
	{
		return this.iconId;
	}
	
	public void setIcon( final String iconId )
	{
		this.iconId = iconId;
	}
	
	public String getPicture()
	{
		return this.pictureId;
	}
	
	public void setPicture( final String pictureId )
	{
		this.pictureId = pictureId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "User(" + username + ")";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public
	boolean
	equals(
		final Object obj
	)
	{
		if ( !( obj instanceof User ) )
		{
			return false;
		}
		
		System.out.println( "a" );
		final User other = (User) obj;
		
		System.out.println( other.username );
		System.out.println( this.username );
		return new EqualsBuilder()
		.append( this.username, other.username )
		.isEquals();
	}
}
