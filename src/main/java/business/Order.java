package business;

/**
 * Order class which overrides methods hashCode and equals that will be used in Restaurant Class. Also, it contains the attributes for an order.
 * @author vladg
 *
 */
public class Order {
	
	public int orderId;
	public String date;
	public int table;
	
	public Order(int orderId, String date, int table)
	{
		this.orderId=orderId;
		this.date=date;
		this.table=table;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + orderId;
		result = prime * result + table;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (orderId != other.orderId)
			return false;
		if (table != other.table)
			return false;
		return true;
	}
	
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public int getTable() {
		return this.table;
	}

	public void setTable(int table) {
		this.table = table;
	}

}
