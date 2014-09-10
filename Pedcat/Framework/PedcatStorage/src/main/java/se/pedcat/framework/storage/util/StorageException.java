package se.pedcat.framework.storage.util;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.util.ModelException;

public class StorageException extends ModelException {

	public StorageException(String msg, String messageKey, String thrownBy,
			Throwable nestedException, Carrier carrier, Carrier[] carriers,
			Key key, ModelQuery query) {
		super(msg, messageKey, thrownBy, nestedException, carrier, carriers, key, query);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String msg, String messageKey, String thrownBy,
			Throwable nestedException, Carrier carrier, Key key) {
		super(msg, messageKey, thrownBy, nestedException, carrier, key);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String msg, String messageKey, String thrownBy,
			Throwable nestedException, Carrier carrier) {
		super(msg, messageKey, thrownBy, nestedException, carrier);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String msg, String messageKey, String thrownBy,
			Throwable nestedException, Key key) {
		super(msg, messageKey, thrownBy, nestedException, key);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String msg, String messageKey, String thrownBy,
			Throwable nestedException) {
		super(msg, messageKey, thrownBy, nestedException);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String msg, String messageKey, String thrownBy) {
		super(msg, messageKey, thrownBy);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String message, Throwable throwable) {
		super(message, throwable);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public StorageException(Exception e) {
		super("",e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
