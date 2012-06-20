package americano.dao;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class
CrudDao
extends AbstractDao
{
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
	
}
