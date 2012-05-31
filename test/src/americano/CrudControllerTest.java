package americano;

import static org.junit.Assert.assertEquals;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.PassThroughFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.DispatcherServlet;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( loader=MockWebApplicationContextLoader.class, locations= { "classpath:americano/CrudControllerTest-context.xml" } )
@MockWebApplication( name="sample" )
public class
CrudControllerTest
{
	protected Filter filter;
	
	@Autowired
	DispatcherServlet servlet;
	
	@Before
	public void setUpFilter() throws Exception
	{
		final FilterConfig config = new MockFilterConfig( servlet.getServletContext() );
		this.filter = new SessionInViewFilter();
		this.filter.init( config );
	}
	
	@After
	public void tearDownFilter()
	{
		this.filter.destroy();
	}

	@Test
	public void test_create() throws Exception
	{
		final MockHttpServletRequest req = new MockHttpServletRequest( "POST", "/crud/User" );
		req.addParameter( "username", "bylee" );
		req.addParameter( "password", "password" );
		req.addParameter( "save", "yes" );
		final MockHttpServletResponse res = new MockHttpServletResponse();
		
		final PassThroughFilterChain chain =
			new PassThroughFilterChain(
				filter,
				new PassThroughFilterChain( servlet )
			);
		
		chain.doFilter( req, res );
		
		assertEquals( "crud/list", res.getForwardedUrl() );
	}

}
