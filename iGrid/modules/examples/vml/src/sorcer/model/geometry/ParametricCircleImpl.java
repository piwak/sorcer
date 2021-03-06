/*
 * Copyright 2009 the original author or authors.
 * Copyright 2009 SorcerSoft.org.
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

package sorcer.model.geometry;

import java.rmi.RemoteException;

/**
 * @author Mike Sobolewski
 */
public class ParametricCircleImpl implements ParametricCircle {

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.model.geometry.ParametricCircle#area(double)
	 */
	@Override
	public double area(double radius) throws RemoteException {
		return Math.PI * Math.pow(radius, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.model.geometry.ParametricCircle#circumference(double, double)
	 */
	@Override
	public double circumference(double radius) throws RemoteException {
		return 2 * Math.PI * radius;
	}

}
