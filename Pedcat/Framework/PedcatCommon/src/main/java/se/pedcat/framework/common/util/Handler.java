/**
 * 
 */
package se.pedcat.framework.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.context.ApplicationContextManager;
import se.pedcat.framework.common.service.FrameworkService;


// TODO: Auto-generated Javadoc
/**
 * The Class Handler.
 *
 * @author laha
 */
public  class Handler implements InvocationHandler
{
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(Handler.class);

	/** The service. */
	FrameworkService service ;

	/** The reader. */
	private boolean reader;
	
	/**
	 * Instantiates a new handler.
	 *
	 * @param service the service
	 */
	public Handler(FrameworkService service)
	{
		this.service = service;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable 
	{
		
		long start = System.currentTimeMillis(); 
		Method methodCalled = null;
		try
		{
			methodCalled = service.getClass().getMethod(method.getName(), method.getParameterTypes());
			return methodCalled.invoke(service, args);
		}
		catch (Throwable e)
		{
			e = CommonUtility.getNestedException(e);
			logger.error("Could not call :" + service.getClass().getName());
			logger.error("Method         :" + method.getName());
			logger.error("Exception      :",e);
			
			throw new FrameworkException(e);
			
		}
		finally
		{
			ApplicationContextManager.getInstance().addResponseStatistics(service.getClass().getName() + "." + method.getName(),System.currentTimeMillis()-start);
		}
		
	}
}
