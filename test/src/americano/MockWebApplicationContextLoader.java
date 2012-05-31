package americano;

import static org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class
MockWebApplicationContextLoader
extends AbstractContextLoader
{
	/**
	 * The configuration defined in the {@link MockWebApplication} annotation.
	 */
	private MockWebApplication configuration;

	protected MockServletContext createContext()
	{
		final MockServletContext context =
			new MockServletContext( configuration.webapp(), new FileSystemResourceLoader() );
		return context;
	}

	protected MockServletConfig createServletConfig( ServletContext servletContext )
	{
		MockServletConfig config = new MockServletConfig( servletContext, configuration.name() );
		return config;
	}

	protected XmlWebApplicationContext configureContext()
	{
		return new XmlWebApplicationContext();
	}

	public ApplicationContext loadContext(
		MergedContextConfiguration contextConfig
	)
	throws Exception
	{
		// Establish the servlet context and config based on the test class's MockWebApplication annotation.
		final MockServletContext servletContext = createContext();;
		final MockServletConfig servletConfig = createServletConfig( servletContext );

		// Create a WebApplicationContext and initialize it with the xml and servlet configuration.
		final XmlWebApplicationContext webAppContext = configureContext();
		servletContext.setAttribute( ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webAppContext );
		webAppContext.setServletConfig( servletConfig );
		webAppContext.setConfigLocations( contextConfig.getLocations() );

		// Create a DispatcherServlet that uses the previously established WebApplicationContext.
		final DispatcherServlet dispatcherServlet = createServlet();

		// Add the DispatcherServlet (and anything else you want) to the context.
		// Note: this doesn't happen until refresh is called below.
		webAppContext.addBeanFactoryPostProcessor( new BeanFactoryPostProcessor() {
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
				beanFactory.registerResolvableDependency( DispatcherServlet.class, dispatcherServlet );
				// Register any other beans here, including a ViewResolver if you are using JSPs.
			}
		});

		// Have the context notify the servlet every time it is refreshed.
		webAppContext.addApplicationListener(new SourceFilteringListener(webAppContext, new ApplicationListener<ContextRefreshedEvent>() {
			public void onApplicationEvent(ContextRefreshedEvent event) {
				dispatcherServlet.onApplicationEvent(event);
			}
		}));

		// Prepare the context.
		webAppContext.refresh();
		webAppContext.registerShutdownHook();

		// Initialize the servlet.
		dispatcherServlet.setContextConfigLocation("");
		dispatcherServlet.init(servletConfig);

		return webAppContext;
	}

	public ApplicationContext loadContext(
		final String... locations
	)
	throws Exception
	{
		// Establish the servlet context and config based on the test class's MockWebApplication annotation.
		final MockServletContext servletContext = createContext();;
		final MockServletConfig servletConfig = createServletConfig( servletContext );

		// Create a WebApplicationContext and initialize it with the xml and servlet configuration.
		final XmlWebApplicationContext webAppContext = configureContext();
		servletContext.setAttribute( ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webAppContext );
		webAppContext.setServletConfig( servletConfig );
		webAppContext.setConfigLocations( locations );

		// Create a DispatcherServlet that uses the previously established WebApplicationContext.
		final DispatcherServlet dispatcherServlet = createServlet();

		// Add the DispatcherServlet (and anything else you want) to the context.
		// Note: this doesn't happen until refresh is called below.
		webAppContext.addBeanFactoryPostProcessor( new BeanFactoryPostProcessor() {
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
				beanFactory.registerResolvableDependency( DispatcherServlet.class, dispatcherServlet );
				// Register any other beans here, including a ViewResolver if you are using JSPs.
			}
		});

		// Have the context notify the servlet every time it is refreshed.
		webAppContext.addApplicationListener(new SourceFilteringListener(webAppContext, new ApplicationListener<ContextRefreshedEvent>() {
			public void onApplicationEvent(ContextRefreshedEvent event) {
				dispatcherServlet.onApplicationEvent(event);
			}
		}));

		// Prepare the context.
		webAppContext.refresh();
		webAppContext.registerShutdownHook();

		// Initialize the servlet.
		dispatcherServlet.setContextConfigLocation("");
		dispatcherServlet.init(servletConfig);

		return webAppContext;
	}

	protected DispatcherServlet createServlet()
	{
		DispatcherServlet servlet = new DispatcherServlet();
		
		return servlet;
	}

	/**
	 * One of these two methods will get called before {@link #loadContext(String...)}.
	 * We just use this chance to extract the configuration.
	 */
	@Override
	protected String[] generateDefaultLocations(Class<?> clazz) {
		extractConfiguration(clazz);            
		return super.generateDefaultLocations(clazz);
	}

	/**
	 * One of these two methods will get called before {@link #loadContext(String...)}.
	 * We just use this chance to extract the configuration.
	 */
	@Override
	protected String[] modifyLocations(Class<?> clazz, String... locations) {
		extractConfiguration(clazz);
		return super.modifyLocations(clazz, locations);
	}

	private void extractConfiguration(Class<?> clazz) {
		configuration = AnnotationUtils.findAnnotation( clazz, MockWebApplication.class );
		if ( null == configuration )
			throw new IllegalArgumentException("Test class " + clazz.getName() + " must be annotated @MockWebApplication.");
	}

	protected String getResourceSuffix()
	{
		return "-context.xml";
	}

}
