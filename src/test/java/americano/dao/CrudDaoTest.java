package americano.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import americano.model.User;


@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class CrudDaoTest
{
	
	@Autowired
	protected CrudDao dao;
	
	
	@Test
	public void test_insert() throws Exception
	{
		final User user = new User( "abc", "aaa" );
		
		dao.insert( user );
	}

	@Test
	public void test_list() throws Exception
	{
		List<?> result = dao.list( User.class.getSimpleName() );
		assertEquals( 1, result.size() );
		assertEquals( new User( "abc" ), result.get( 0 ) );
	}
}
