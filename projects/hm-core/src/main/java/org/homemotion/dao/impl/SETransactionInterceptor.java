package org.homemotion.dao.impl;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.homemotion.dao.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Transactional
public class SETransactionInterceptor implements Serializable {

	private static final long serialVersionUID = 5978356159603989323L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SETransactionInterceptor.class);

	@AroundInvoke
	public Object aroundInvoke(InvocationContext invocationContext)
			throws Exception {
//		boolean taStarted = false;
//		EntityManager entityManager;
//		Object target = invocationContext.getTarget();
//		if(target instanceof AbstractJPAItemManagerImpl){
//			entityManager = ((AbstractJPAItemManagerImpl)target).getEntityManager();
//			// begin transaction
//			if (!entityManager.getTransaction().isActive()) {
//				if(!entityManager.getTransaction().isActive()){
//					LOGGER.debug("Starting transaction...");
//					entityManager.getTransaction().begin();
//					taStarted = true;
//					LOGGER.debug("Transaction started.");
//				}
//			}
//			try {
//				// call transactional code block
				return invocationContext.proceed();
//			} catch (Exception e) {
//				if(entityManager.getTransaction().isActive()){
//					LOGGER.debug("Unrecoverable exception occurred during transaction, rolling back...", e);
//					entityManager.getTransaction().rollback();
//					LOGGER.debug("Transaction rolled back.");
//				}
//				throw e;
//			} finally {
//				if(taStarted){
//					if(entityManager.getTransaction().isActive()){
//						LOGGER.debug("Committing transaction...");
//						entityManager.getTransaction().commit();
//						LOGGER.debug("Transaction committed.");
//					}
//				}
//			}
//		}
//		else{
//			LOGGER.error("Transaction component must implement EMAware, executing call without transaction...");
//			return invocationContext.proceed();
//		}
	}

}