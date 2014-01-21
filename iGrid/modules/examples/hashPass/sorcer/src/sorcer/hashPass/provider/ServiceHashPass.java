package sorcer.hashPass.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sorcer.service.Context;

@SuppressWarnings("rawtypes")
public interface ServiceHashPass extends Remote {
	public Context initializeInputFields(Context fields)
			throws RemoteException;

	public Context hashPassword(Context password) throws RemoteException;

	public final static String HASHER = "hasher";
	public final static String HASHPASS = "hashPass";
	public final static String PASSWORD = "password";
	public final static String HASH = "hash";
	public final static String HASHPASSWORD = "hashPassword";
}
