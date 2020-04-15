package business;

/**
 * Abstract class with abstract method computePrice()
 * @author vladg
 * @see BaseProduct
 * @see CompositeProduct
 */
public abstract class MenuItem implements java.io.Serializable {
	public abstract int computePrice();
}
