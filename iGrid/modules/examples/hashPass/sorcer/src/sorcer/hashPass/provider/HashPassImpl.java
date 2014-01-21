package sorcer.hashPass.provider;

import java.rmi.RemoteException;

public class HashPassImpl implements HashPass {
	private HashPassEntity hashPass;
	
	public HashPassImpl(HashPassEntity hashPass) throws RemoteException {
		this.hashPass = hashPass;
	}
	public HashPassEntity initializeInputFields(){
		return hashPass;
	}
	
	public void hashPassword(HashPassEntity hashPass){
		this.hashPass.setPass(hashPass.getPass());
		this.hashPass.hashPassword();
		return;	
	}
}
