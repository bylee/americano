package americano;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.PassThroughFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.DispatcherServlet;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( loader=MockWebApplicationContextLoader.class, locations= { "classpath:americano/ControllerTest-context.xml" } )
@MockWebApplication( name="sample" )
public class
ControllerTest
{
	protected Logger logger = LoggerFactory.getLogger( getClass() );
	
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
		final Object[][] TEST_CASES = new Object[][] {
			new Object[] { "/user/", "POST", "\"username\":" },
			new Object[] { "/user/testuser", "GET", "\"username\":" },	// 사용자 정보
			new Object[] { "/user/testuser/books", "GET", "\"id\":" },	// 사용자가 소유한 책
			
			new Object[] { "/book/testbook/chapters", "GET", "\"id\":" },	// testbokk( 책 )의 챕터들
			new Object[] { "/book/", "POST", "\"id\":" },	// 책을 생성
			new Object[] { "/html/books.html", "GET", "<html>" },	// 책 정보에 대한 Form
			
			new Object[] { "/html/chapters.html", "GET", "<html>" },	// 챕터에 대한 Form
		};
		
		for ( final Object[] TEST_CASE : TEST_CASES )
		{
			final String path = (String) TEST_CASE[0];
			final String method = (String) TEST_CASE[1];
			final String containing = (String) TEST_CASE[2];
			logger.trace( "Path :{}, method :{}", path, method );
			final MockHttpServletRequest req = new MockHttpServletRequest( method, path );
			final MockHttpServletResponse res = new MockHttpServletResponse();
			
			final PassThroughFilterChain chain =
				new PassThroughFilterChain(
					filter,
					new PassThroughFilterChain( servlet )
				);
			
			chain.doFilter( req, res );
			final String contents = res.getContentAsString();
			logger.info( "Contents :{}", contents );
			assertNotNull( contents );
			assertTrue(
				path + "(" + method + ") failed",
				contents.contains( containing )
			);
			
		}
		
	}

}
