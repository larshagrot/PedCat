/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.service.FrameworkService;
import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.common.util.Handler;


// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkServiceLocator.
 */
public abstract class FrameworkServiceLocator implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Cache med serviceobject. */
	private List<FrameworkService> serviceCache = new ArrayList<FrameworkService>();

	/**
	 * Instantiates a new framework service locator.
	 */
	protected FrameworkServiceLocator() {

	}

	/** Hittar och instantierar services. @author laha */

	private static Logger logger = LoggerFactory
			.getLogger(FrameworkServiceLocator.class);

	/**
	 * Ska returnera en instans som körs lokalt.
	 *
	 * @return the framework service
	 */
	public abstract FrameworkService createLocalService();

	/**
	 * Ska returnera Class för local home.
	 *
	 * @return the local home class
	 */
	public abstract Class<?> getLocalHomeClass();

	/**
	 * Ska returnera jndi namn för local.
	 *
	 * @return the jndi local name
	 */
	public abstract String getJndiLocalName();

	/**
	 * Ska returnera jndi namn.
	 *
	 * @return the jndi name
	 */
	public abstract String getJndiName();

	/**
	 * Namn pö InitialContextFactory.
	 *
	 * @return the initial context factory
	 */
	public abstract String getInitialContextFactory();

	/**
	 * Anger om vi ska anropa som local EJB.
	 *
	 * @return true, if is use local ejb
	 */
	public abstract boolean isUseLocalEjb();

	/**
	 * Anger om vi ska anropa som remote EJB.
	 *
	 * @return true, if is use remote ejb
	 */

	public abstract boolean isUseRemoteEjb();

	/**
	 * Returnera Class för ifc.
	 *
	 * @return the service class
	 */
	public abstract Class<?> getServiceClass();

	/**
	 * Returnera Class för remote home.
	 *
	 * @return the remote home class
	 */
	public abstract Class<?> getRemoteHomeClass();

	/**
	 * url till server.
	 *
	 * @return the provider url
	 */
	public abstract String getProviderUrl();


	/**
	 * Hanterar anrop mot proxy.
	 *
	 * @author laha
	 */
	protected static class RemoteServiceHandler implements InvocationHandler {
		
		/** The remote ifc. */
		private Object remoteIfc;

		/**
		 * Instantiates a new remote service handler.
		 *
		 * @param integration the integration
		 */
		public RemoteServiceHandler(Object integration) {
			this.remoteIfc = integration;
		}

		/* (non-Javadoc)
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
		 */
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			
			try {
				Method m = remoteIfc.getClass().getMethod(method.getName(),
						method.getParameterTypes());
				return m.invoke(this.remoteIfc, args);
			}
			catch (InvocationTargetException e) {
				logger.error("Got exception when calling EJB", e);
				e.printStackTrace();
				Exception e2  = CommonUtility.getNestedException(e);
				CommonUtility.ensureFrameworkException("Got exception when calling EJB",e2,null);
				throw e2;
				
			}
			catch (Exception e) {
				logger.error("Got exception when calling EJB", e);
				Exception e2  = CommonUtility.getNestedException(e);
				CommonUtility.ensureFrameworkException("Got exception when calling EJB",e2,null);
				throw e2;
			}
		}

	}

	/**
	 * Lögger tillbaka service för öteranvöndning.
	 *
	 * @param service the service
	 */
	public void releaseService(FrameworkService service) {
		synchronized (serviceCache) {
			serviceCache.add(service);
		}

	}

	/**
	 * Skapar/returnerar service Anropet ska görna lömna tillbaka med
	 * releaseService.
	 *
	 * @return the service
	 */
	public FrameworkService getService() {

		FrameworkService returnedService = null;

		synchronized (serviceCache) {
			if (serviceCache.size() > 0) {
				returnedService = (FrameworkService) serviceCache.remove(0);
			}
		}

		if (returnedService != null) {
			try {
				returnedService.ping();
			} catch (Throwable t) {
				logger.info("Could not reuse service " + t.getMessage());
				returnedService = null;
			}
		}

		if (returnedService == null) {
			int retries = 0;
			while (true)
			{
				try
				{
					if (isUseRemoteEjb()) {
						logger.info("Using remote EJB service " + this.getClass().getName());
						returnedService = createRemoteEjbService();
					} else if (isUseLocalEjb()) {
						logger.info("Using local EJB service " + this.getClass().getName());

						returnedService = createLocalEjbService();
					} else {
						logger.info("Using local service " + this.getClass().getName());
						returnedService = this.createLocalService();
					}
					returnedService.ping();
					serviceCache.add(returnedService);
					break;
				}
				catch  (Throwable t)
				{
					retries++;
					if (retries<5)
					{
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						throw new FrameworkException(t);
					}
				}
			}
		}
		return returnedService;
	}

	/**
	 * Creates the local ejb service.
	 *
	 * @return the framework service
	 */
	public FrameworkService createLocalEjbService() {
		// TODO Auto-generated method stub
		throw new FrameworkException(
				"createLocalEjbService() ej implementerad!");
	}

	/**
	 * Creates the remote ejb service.
	 *
	 * @return the framework service
	 */
	public FrameworkService createRemoteEjbService() {
		// TODO Auto-generated method stub
		throw new FrameworkException(
				"createRemoteEjbService() ej implementerad!");
	}

	/**
	 * Gets the service classes.
	 *
	 * @return the service classes
	 */
	protected Class<?>[] getServiceClasses() {
		// TODO Auto-generated method stub
		return new Class[] { this.getServiceClass() };
	}
	
	/**
	 * Creates the proxy.
	 *
	 * @param service the service
	 * @param serviceInterfaceClass the service interface class
	 * @return the framework service
	 */
	public FrameworkService createProxy(FrameworkService service,
			Class<?>[] serviceInterfaceClass) {
		return (FrameworkService) java.lang.reflect.Proxy.newProxyInstance(this.getClass().getClassLoader(), serviceInterfaceClass, new Handler(service));
	}
	

}
