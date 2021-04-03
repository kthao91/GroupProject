package business.entities.iterator;

import business.entities.iterator.SafeIterator.Type;
import business.facade.*;
import bussiness.entites.*;

/**
 * SafeProduct class allows the Product object to use the SafeIterator
 * 
 * @author Chris
 *
 */
public class SafeProduct extends Type {

	public void copy(Result result, Object object) {
		Product pro = (Product) object;
		result.setProductFields(pro);
	}

}
