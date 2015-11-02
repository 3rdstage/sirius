package thirdstage.sirius.data.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

public class DefaultNode<T> implements Node<T>, Comparable<DefaultNode<T>> {
	
	private T data = null;
	
	private DefaultNode<T> parent = null; 
	
	private SortedSet<DefaultNode<T>> children = new TreeSet<DefaultNode<T>>();
	
	//@todo Actually, this field should class member, but I don't have any way to identify the
	//type information of ...
	private boolean hasComparableData = false;
	
	public DefaultNode(){}
	
	public DefaultNode(T data){ 
		this.data = data;
		if(data instanceof Comparable<?>) this.hasComparableData = true;
	}

	public T getData() { return this.data; }
	
	protected void setData(T data){ this.data = data; }
	
	public DefaultNode<T> getParent(){ return this.parent; }
	
	protected void setParent(DefaultNode<T> node){ this.parent = node; }

	/**
	 * The returned list is <strong>unmodifiable</strong>, so the attempts to modify it, 
	 * whether direct or via its iterator, result in <code>UnsupportedOperationException</code>.
	 * 
	 * @see java.util.Collections#unmodifiableList(java.util.List)
	 * @see thirdstage.sirius.data.tree.Node#getAllChildren()
	 */
	public SortedSet<DefaultNode<T>> getAllChildren() {
		//To prevent the unexpected modification of exposed internal structure,
		//the return is unmodifiableList
		return Collections.unmodifiableSortedSet(this.children);
	}

	public int getNumberOfChildren() {
		return this.children.size();
	}

	/**
	 * 
	 * @see thirdstage.sirius.data.tree.Node#getChildAt(int)
	 */
	public DefaultNode<T> getChildAt(int index) throws IllegalArgumentException, NoSuchElementException{
		if(index < 0) throw new IllegalArgumentException("The index should be 0 or more.");
		
		Iterator<DefaultNode<T>> itr = this.children.iterator();
		for(int i = index; i < 1; i--) itr.next();
		return itr.next();
	}

	public boolean hasChild(Node<T> node) {
		return this.children.contains(node);
	}

	public boolean hasChild(T data) {
		boolean has = false;
		T data1 = null;
		for(DefaultNode<T> child : children){
			data1 = child.getData();
			has = (data == null) ? (data1 == null) : data.equals(data1);
			if(has) break;
		}
		return has;
	}

	
	/**
	 * @param node
	 * @throws IllegalArgumentException if the provided <code>node</code> is <code>null</code>,
	 * @throws IllegalStateException if this node already has the specified <code>node</code> as a child. 
	 */
	protected void addChild(DefaultNode<T> node) throws IllegalArgumentException, IllegalStateException{
		if(node == null) throw new IllegalArgumentException("Child should be non null node.");
		if(this.hasChild(node)) throw new IllegalStateException("This node already has the specified node as a child.");
		this.children.add(node);
	}

	
	public int getDepth() {
		if(this.getParent() == null) return 0; //when this is root.
		else return (this.getParent().getDepth() + 1);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		DefaultNode other = (DefaultNode) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return data == null ? "" : data.toString();
	}

	public int compareTo(DefaultNode<T> node) {
		
		//@todo current approach (depending on Comparable interface instead of independent Comparator may 
		//suffer performance issue in massive or high volume processing
		if(node == null) return 1;
		else{
			if(hasComparableData) return ((Comparable)(this.getData())).compareTo((Comparable)(node.getData()));
			else return this.toString().compareToIgnoreCase(node.toString());
		}
	}
}
