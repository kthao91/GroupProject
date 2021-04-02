import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Store implements Serializable {
	private static Store store;
	private MemberList members = new MemberList();
	private OrderList orders = new OrderList();

	private Store() {
	}

	public static Store instance() {
		if (store == null) {
			return store = new Store();
		} else {
			return store;
		}
	}

	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhone());
		if (members.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Iterator<Member> getMembers(Request request) {
		LinkedList<Member> returnMembers = new LinkedList<Member>();
		for (Member currentMember : members) {
			System.out.println("currentMember is: " + currentMember);
			if (currentMember.getName().contains(request.getMemberName())) {
				returnMembers.add(currentMember);
			}
		}
		return returnMembers.iterator();
	}

	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Member> members = new LinkedList<Member>();

		public Member search(String memberId) {
			for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
				Member member = iterator.next();
				if (member.getId().equals(memberId)) {
					return member;
				}
			}
			return null;
		}

		public boolean insertMember(Member member) {
			members.add(member);
			System.out.println(member.getId());
			return true;
		}

		@Override
		public Iterator<Member> iterator() {
			// TODO Auto-generated method stub
			return members.iterator();
		}
	}

	private class OrderList implements Iterable<Order>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Order> orders = new LinkedList<Order>();

		/**
		 * Searches for an order with a certain orderNumber
		 * 
		 * @param orderNumber the orderNumber of the order to be found
		 * @return order if the correct order is found
		 */
		public Order search(String orderNumber) {
			for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
				Order order = iterator.next();
				if (order.getOrderNumber().equals(orderNumber)) {
					return order;
				}
			}
			return null;
		}

		@Override
		public Iterator<Order> iterator() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
