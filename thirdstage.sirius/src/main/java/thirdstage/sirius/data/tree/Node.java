package thirdstage.sirius.data.tree;

import java.util.SortedSet;

/**
 * <p>
 * This class is immutable.
 * 
 * @author 3rdstage
 *
 * @param <T>
 * @since 2012-04-20
 */
public interface Node<T> {
	
	T getData();
	
	Node<T> getParent();
	
	SortedSet<? extends Node<T>> getAllChildren();
	
	int getNumberOfChildren();
	
	/**
	 * @param index 0 base
	 * @return
	 * @throws IllegalArgumentException if the specified <code>index</code> is less than 0.
	 */
	Node<T> getChildAt(int index) throws IllegalArgumentException;
	
	boolean hasChild(Node<T> node);
	
	boolean hasChild(T data);
	
	/**
	 * Returns 0 for root.
	 * 
	 * @return
	 */
	int getDepth();
	

}
