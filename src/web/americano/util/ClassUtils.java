package americano.util;

import java.lang.reflect.Field;

public class
ClassUtils
extends escode.util.ClassUtils
{
	protected ClassUtils() {}
	
	public static
	Field
	getField(
		final Object obj,
		final String fieldName
	)
	{
		final Class<?> clazz = obj.getClass();
		
		Field field = null;
		try
		{
			field = clazz.getField( fieldName );
		}
		catch ( SecurityException e )
		{
			e.printStackTrace();
		}
		catch ( NoSuchFieldException e )
		{
			try
			{
				field = clazz.getDeclaredField( fieldName );
			}
			catch ( SecurityException e1 )
			{
				e1.printStackTrace();
			}
			catch ( NoSuchFieldException e1 )
			{
			}
		}
		
		return field;
	}
	
	public static
	Object
	getFieldValue(
		final Object obj,
		final String fieldName
	)
	{
		final Field field = getField( obj, fieldName );
		
		if ( null == field )
		{
			return null;
		}
		
		final boolean bAccessible = field.isAccessible();
		try
		{
			field.setAccessible( true );
			return field.get( obj );
		}
		catch ( IllegalArgumentException e )
		{
		}
		catch ( IllegalAccessException e )
		{
		}
		finally
		{
			field.setAccessible( bAccessible );
		}
		
		return null;
		
	}
	public static
	void
	setFieldValue(
		final Object obj,
		final String fieldName,
		final Object fieldValue
	)
	{
		
		final Field field = getField( obj, fieldName );

		if ( null == field )
		{
			return ;
		}
		
		final boolean bAccessible = field.isAccessible();
		try
		{
			field.setAccessible( true );
			field.set( obj, fieldValue );
		}
		catch ( IllegalArgumentException e )
		{
		}
		catch ( IllegalAccessException e )
		{
		}
		finally
		{
			field.setAccessible( bAccessible );
		}
	}
	

}
