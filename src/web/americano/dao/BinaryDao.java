package americano.dao;

import org.springframework.stereotype.Repository;

import americano.model.Binary;

@Repository
public class
BinaryDao
extends AbstractDao
{
	public Binary getBinary( final String id )
	{
		return get( Binary.class, id );
	}

}
