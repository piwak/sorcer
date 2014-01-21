package sorcer.hashPass.provider;

import java.rmi.RemoteException;
import sorcer.core.SorcerConstants;
import sorcer.core.provider.ServiceTasker;
import sorcer.service.Context;
import com.sun.jini.start.LifeCycle;

@SuppressWarnings("rawtypes")
public class HashPassProvider extends ServiceTasker implements HashPass,
		ServiceHashPass, SorcerConstants {

	private HashPassEntity hashPassEntity;

	public HashPassProvider(String[] args, LifeCycle lifeCycle)
			throws Exception {
		super(args, lifeCycle);

		String pass = getProperty("provider.pass");
		if (pass != null) {
			hashPassEntity = new HashPassEntity(pass, null);
		} else {
			hashPassEntity = new HashPassEntity();
		}
	}

	@Override
	public Context initializeInputFields(Context context)
			throws RemoteException {
		return process(context, ServiceHashPass.HASHER);
	}

	public Context getPass(Context context) throws RemoteException {
		return process(context, ServiceHashPass.PASSWORD);
	}

	public Context getHash(Context context) throws RemoteException {
		return process(context, ServiceHashPass.HASH);
	}

	public Context hashPassword(Context context) throws RemoteException {
		return process(context, ServiceHashPass.HASHPASSWORD);
	}

	private Context process(Context context, String selector)
			throws RemoteException {
		try {

			HashPassEntity result = null, input = null;
			if (selector.equals(ServiceHashPass.HASHER)) {
				result = initializeInputFields();
			} else if (selector.equals(ServiceHashPass.HASHPASSWORD)) {
				input = (HashPassEntity) context
						.getValue(ServiceHashPass.HASHPASSWORD);
				hashPassword(input);
				result = initializeInputFields();
			}
			context.putValue(ServiceHashPass.HASHER, result);
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}

		} catch (Exception ex) {
			// ContextException, UnknownHostException
			throw new RemoteException(selector + " process execption", ex);
		}
		return context;
	}

	public HashPassEntity initializeInputFields() {
		return hashPassEntity;
	}

	public HashPassEntity getHashPass() {
		return hashPassEntity;
	}


	@Override
	public void hashPassword(HashPassEntity hashPassEntity)
			throws RemoteException {
		this.hashPassEntity.setPass(hashPassEntity.getPass());
		this.hashPassEntity.hashPassword();
		return;

	}
}
