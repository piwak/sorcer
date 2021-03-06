/*
 * Copyright 2013 the original author or authors.
 * Copyright 2013 SorcerSoft.org.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sorcer.core.exertion;

import java.rmi.RemoteException;

import net.jini.core.transaction.Transaction;
import sorcer.core.context.ServiceContext;
import sorcer.core.invoker.MethodInvoker;
import sorcer.core.provider.jobber.ServiceConcatenator;
import sorcer.core.signature.ObjectSignature;
import sorcer.service.Block;
import sorcer.service.Context;
import sorcer.service.Exertion;
import sorcer.service.ExertionException;
import sorcer.service.SignatureException;

/**
 * The SORCER object block extending the basic block implementation {@link Block}.
 * 
 * @author Mike Sobolewski
 */
public class ObjectBlock extends Block {
	
	private static final long serialVersionUID = -3917210130874207557L;
	
	public ObjectBlock() throws SignatureException {
		super(null, new ObjectSignature("execute", ServiceConcatenator.class));
	}
	
	public ObjectBlock(String name) {
		super(name, new ObjectSignature("execute", ServiceConcatenator.class));
	}
	
	public ObjectBlock(String name, Context context)
			throws SignatureException {
		this(name);
		if (context != null)
			this.dataContext = (ServiceContext) context;
	}
	
	public Block doBlock(Transaction txn) throws ExertionException,
			SignatureException, RemoteException {
		// return (Job) new ServiceJobber().exec(job, txn);
		Block result = null;
		try {
			ObjectSignature os = (ObjectSignature) getProcessSignature();
			MethodInvoker evaluator = ((ObjectSignature) getProcessSignature())
					.getEvaluator();
			if (evaluator == null) {
				evaluator = new MethodInvoker(os.newInstance(),
						os.getSelector());
			}
			evaluator.setParameterTypes(new Class[] { Exertion.class });
			evaluator.setParameters(new Exertion[] { this });
			result = (Block)evaluator.evaluate();
			getControlContext().appendTrace("" + evaluator);
		} catch (Exception e) {
			e.printStackTrace();
			if (controlContext != null)
				controlContext.addException(e);
		}
		return result;
	}
	
}
