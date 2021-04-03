package business.entities.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import business.facade.*;
import bussiness.entites.*;

//import business.entities.iterators.SafeIterator.Type.SafeBook;

/**
 * This Iterator implementation is tailor-made to supply "read-only" versions of
 * Book and Member objects. It is generic. If the user chooses Book for the
 * generic parameter, they should also supply SafeIterator.BOOK as the second
 * parameter to the constructor. Similarly, if they choose Member instead, they
 * should also choose SafeIterator.MEMBER as the second parameter of
 * constructor.
 * 
 * @author Brahma Dathan
 *
 * @param <T> Either Book or Member
 */
public class SafeIterator<T> implements Iterator<Result> {
	private Iterator<T> iterator;
	private Type type;
	private Result result = new Result();
	public static final Type PRODUCT = new SafeProduct();
	public static final Type MEMBER = new SafeMember();

	/**
	 * This class is designed to ensure that the appropriate object is used to copy
	 * to the Result object.
	 * 
	 * @author Brahma Dathan
	 *
	 */
	public abstract static class Type {
		/**
		 * The copy method is used to copy the object to Result. Object is Book or
		 * Member at present.
		 * 
		 * @param result the Result object
		 * @param object the Product or Member object
		 */
		public abstract void copy(Result result, Object object);

		public static class SafeProduct extends Type {
			@Override
			public void copy(Result result, Object object) {
				Product pro = (Product) object;
				result.setProductFields(pro);
			}
		}
	}

	public static class SafeMember extends Type {
		@Override
		public void copy(Result result, Object object) {
			Member member = (Member) object;
			result.setMemberFields(member);
		}
	}

	/**
	 * The user of SafeIterator must supply an Iterator to Book or Member. If
	 * Iterator<Book> is passed as the first parameter, SafeItearator.BOOK should be
	 * passed as the second parameter. If Iterator<Member> is passed as the first
	 * parameter, SafeItearator.MEMBER should be the second parameter.
	 * 
	 * @param iterator Iterator<Book> or Iterator<Member>
	 * @param type     SafeItearator.BOOK or SafeItearator.MEMBER
	 */
	public SafeIterator(Iterator<T> iterator, Type type) {
		this.iterator = iterator;
		this.type = type;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			type.copy(result, iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}

}