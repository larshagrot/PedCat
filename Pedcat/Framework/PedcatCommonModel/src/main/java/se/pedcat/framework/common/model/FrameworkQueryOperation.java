/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import se.pedcat.framework.common.context.ApplicationContext;
import se.pedcat.framework.common.model.FrameworkOperation;
import se.pedcat.framework.common.model.ModelQuery;


// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkQueryOperation.
 *
 * @param <QueryType> the generic type
 */
public class FrameworkQueryOperation<QueryType> extends FrameworkOperation {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new framework query operation.
	 *
	 * @param ac the ac
	 */
	public FrameworkQueryOperation(ApplicationContext ac) {
		super(ac);
	}
	
	/** The query. */
	private QueryType query;

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public QueryType getQuery() {
		return query;
	}
	
	/**
	 * Sets the query.
	 *
	 * @param query the new query
	 */
	public void setQuery(QueryType query) {
		this.query = query;
	}
}
