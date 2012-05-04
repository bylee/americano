package americano.dao;

import java.text.MessageFormat;
import java.util.List;

public class CrudDao
extends AbstractDao
{
	public
	void
	insert(
		final Object obj
	)
	{
		save( obj );
	}
	
	public List<?>
	list(
		final String modelName
	)
	{
		logger.trace( "Select {}", modelName );
		final List<?> result = super.find(
			MessageFormat.format( "from {0}", modelName )
		);
		logger.debug( "Result for {} :{}", modelName, result );
		
		return result;
	}
	
	public Object
	get(
		final Class<?> modelType,
		final String id
	)
	{
		logger.trace( "Get {} from {}", id, modelType );
		final Object ret = getSession().get( modelType, id );
		
		logger.debug( "Result for {} :{}", id, ret );
		
		return ret;
	}
	
}
