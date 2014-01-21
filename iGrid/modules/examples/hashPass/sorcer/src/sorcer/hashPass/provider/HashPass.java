package sorcer.hashPass.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sorcer.hashPass.provider.HashPassEntity;

public interface HashPass extends Remote {
	public HashPassEntity initializeInputFields() throws RemoteException;

	public void hashPassword(HashPassEntity password) throws RemoteException;
}
