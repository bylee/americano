package americano.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Id;

public class CrudUtils
{
	
	protected static
	Field
	getIdField(
		final Field[] fields
	)
	{
		for ( int i = 0, n = fields.length ; i < n ; ++i )
		{
			final Annotation[] annotations = fields[i].getAnnotations();
			for ( int j = 0, m = annotations.length ; j < m ; ++j )
			{
				if ( annotations[j] instanceof Id )
				{
					return fields[i];
				}
			}
		}
		
		return null;
	}
	

	public static
	Field
	getIdField( Class<?> clazz )
	{
		final Field[] fields = clazz.getFields();
		
		final Field idField = getIdField( fields );
		if ( null != idField )
		{
			return idField;
		}
		
		return getIdField( clazz.getDeclaredFields() );
	}
	

}
