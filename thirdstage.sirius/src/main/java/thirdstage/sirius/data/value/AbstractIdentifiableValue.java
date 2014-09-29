package thirdstage.sirius.data.value;

import thirdstage.sirius.data.tree.CategoryValue;

public abstract class AbstractIdentifiableValue<T> implements java.io.Serializable, Identifiable<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2325961115213244276L;
	
	private T id = null;
	
	public T getId(){ return this.id; }
	
	public AbstractIdentifiableValue(T id){ this.id = id; }
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		Class<?> clazz1 = this.getClass();
		Class<?> clazz2 = obj.getClass();
		if (clazz1 != clazz2)
			return false;
		AbstractIdentifiableValue<T> other = (AbstractIdentifiableValue<T>) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}	
	
}
