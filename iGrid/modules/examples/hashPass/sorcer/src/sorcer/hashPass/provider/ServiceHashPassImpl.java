package sorcer.hashPass.provider;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import net.jini.admin.Administrable;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import sorcer.core.SorcerConstants;
import sorcer.core.provider.Provider;
import sorcer.service.Context;
import sorcer.service.Exertion;
import sorcer.service.ExertionException;
import sorcer.hashPass.provider.HashPassEntity;
import sorcer.hashPass.provider.ServiceHashPass;

public class ServiceHashPassImpl implements HashPass, ServiceHashPass, SorcerConstants{
	
	private HashPassEntity hashPassEntity;
	
	public Context initializeInputFields(Context context) throws RemoteException {
		return process(context, ServiceHashPass.HASHER);
	}
	
	@Override
	public Context hashPassword(Context context) throws RemoteException {
		// TODO Auto-generated method stub
		return process(context, ServiceHashPass.HASHPASSWORD);
	}

	private Context process(Context context, String selector)
			throws RemoteException {
		try {

			HashPassEntity result = null, input=null;
			if (selector.equals(ServiceHashPass.HASHER)) {
				result = initializeInputFields();
			} else if (selector.equals(ServiceHashPass.HASHPASSWORD)) {
				input = (HashPassEntity) context.getValue(ServiceHashPass.HASHPASSWORD);
				hashPassword(input);
				result = initializeInputFields();
			}

			String outputMessage = "processed by " + getHostname();
			context.putValue(
					ServiceHashPass.HASHER, result);
		//	context.putValue(ServiceAccount.COMMENT, outputMessage);
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}
		} catch (Exception ex) {
			// ContextException, UnknownHostException
			throw new RemoteException(selector + " process execption", ex);
		}
		return context;
	}
	
	public ServiceHashPassImpl(HashPassEntity hashPass) throws RemoteException {
		hashPassEntity = hashPass;
	}

	public HashPassEntity initializeInputFields() throws RemoteException {
		return hashPassEntity;
	}
	
	private Provider partner;

	private Administrable admin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.core.provider.proxy.Partnership#getPartner()
	 */
	public Remote getInner() throws RemoteException {
		return (Remote) partner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.base.Service#service(sorcer.base.Exertion)
	 */
	public Exertion service(Exertion exertion, Transaction transaction)
			throws RemoteException, ExertionException, TransactionException {
		return partner.service(exertion, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jini.admin.Administrable#getAdmin()
	 */
	public Object getAdmin() throws RemoteException {
		return admin;
	}

	public void setInner(Object provider) {
		partner = (Provider) provider;
	}

	public void setAdmin(Object admin) {
		this.admin = (Administrable) admin;
	}

	/**
	 * Returns name of the local host.
	 * 
	 * @return local host name
	 * @throws UnknownHostException
	 */
	private String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	@Override
	public void hashPassword(HashPassEntity hashPass) throws RemoteException {
		this.hashPassEntity.setPass(hashPass.getPass());
		this.hashPassEntity.hashPassword();
		return;
		
	}
}
