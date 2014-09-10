/**
 * 
 */
package se.pedcat.framework.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import se.pedcat.framework.common.context.ApplicationContext;
import se.pedcat.framework.common.context.ApplicationContextManager;
import se.pedcat.framework.common.factory.FrameworkServiceLocator;
import se.pedcat.framework.common.model.RemoteResponse;
import se.pedcat.framework.common.service.RemoteCallService;


// TODO: Auto-generated Javadoc
/**
 * The Class RemoteCallHandler.
 *
 * @author laha
 */
/**
 * Hjälpklass till proxy
 * 
 * 
 * @author laha  
 *
 */
public  class RemoteCallHandler implements InvocationHandler
{
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(RemoteCallHandler.class);

	
	/** The service name. */
	private String serviceName;
	
	/** The service. */
	private RemoteCallService service = null;
	
	/**
	 * Instantiates a new remote call handler.
	 *
	 * @param serviceLocator the service locator
	 * @param serviceName the service name
	 */
	public RemoteCallHandler(FrameworkServiceLocator serviceLocator,String serviceName)
	{
		service = (RemoteCallService) serviceLocator.getService();
		this.serviceName = serviceName;
	}

	/**
	 * Instantiates a new remote call handler.
	 *
	 * @param service the service
	 * @param serviceName the service name
	 */
	public RemoteCallHandler(RemoteCallService service, String serviceName) {
		this.service = service;
		this.serviceName = serviceName;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable 
	{
		long start = System.currentTimeMillis(); 
		ApplicationContext ac  = ApplicationContextManager.getInstance().getCurrentContext();
		logger.trace("ApplicationContext:" + ac.getUserName());
		try
		{
			logger.trace("PING:" + service.ping()+ " " + method.getName());
			
			Object o = service.call(ac,this.serviceName, method.getName(), method.getParameterTypes(), args);
			if (o instanceof RemoteResponse  )
			{
				if (((RemoteResponse)o).getReturnedException()==null)
				{
					RemoteResponse remoteResponse = (RemoteResponse) o;
					ApplicationContextManager.getInstance().setCurrentContext(remoteResponse.getApplicationContext());
					logger.trace(remoteResponse.getApplicationContext().getMessageInfo("connectionPoolName") + " " + remoteResponse.getApplicationContext().getMessageInfo("hibernateSessionFactory") );
					return remoteResponse.getReturnedObject();
				}
				else
				{
					throw new FrameworkException(((RemoteResponse)o).getReturnedException());
				}
			}
			else
			{
				return o;
			}
		}
		catch (FrameworkException e)
		{
			throw e;
		}
		catch (Throwable e)
		{
			throw CommonUtility.getNestedException(e);
		}
		finally
		{

			try
			{
				ApplicationContextManager.getInstance().addResponseStatistics(service.getClass().getName() + "." + method.getName(),System.currentTimeMillis()-start);
			}
			catch (Throwable t)
			{
				logger.error(t.getMessage());
			}
		}
		
	}
}
