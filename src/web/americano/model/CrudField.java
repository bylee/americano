package americano.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import americano.util.ClassUtils;
import americano.util.IStringConstants;
import americano.util.StringUtils;

public class CrudField
{
	public static List<CrudField> create( Object obj ) throws IllegalArgumentException, IllegalAccessException
	{
		final Class<?> clazz = obj.getClass();
		final Field[] fields = clazz.getDeclaredFields();
		
		final ArrayList<CrudField> fieldDefs = new ArrayList<CrudField>();
		
		for ( final Field field : fields )
		{
			final Annotation[] annotations = field.getAnnotations();
			final CrudField fieldDef = new CrudField();
			fieldDef.setName( field.getName() );
			final Object value = ClassUtils.getFieldValue( obj, field.getName() );
			if ( null != value )
			{
				fieldDef.setValue( value.toString() );
			}
			for ( final Annotation annotation : annotations )
			{
				if ( annotation instanceof Id )
				{
					fieldDef.setRequired( true );
					// Key
				} else if ( annotation instanceof Column )
				{
					// 
				}
			}
			fieldDefs.add( fieldDef );
		}

		return fieldDefs;
		
	}
	protected String name;
	
	protected boolean bRequired;
	
	protected String type;
	
	protected int length;
	
	protected String value = IStringConstants.EMPTY_STRING;

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
	 * @return the bRequired
	 */
	public boolean isRequired()
	{
		return bRequired;
	}

	/**
	 * @param bRequired the bRequired to set
	 */
	public void setRequired( boolean bRequired )
	{
		this.bRequired = bRequired;
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
	 * @return the length
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength( int length )
	{
		this.length = length;
	}

	public String getValue()
	{
		return value;
	}
	
	public void setValue( final String value )
	{
		this.value = StringUtils.nvl( value );
	}
	
}
