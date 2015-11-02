package thirdstage.sirius.data.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultTree<T> implements Tree<T> {

	private DefaultNode<T> root = null;
	private List<DefaultNode<T>> nodes = new ArrayList<DefaultNode<T>>();
	
	private final Lock l1 = new ReentrantLock(); //this lock is only for root.
	private final ReadWriteLock l2 = new ReentrantReadWriteLock(); //this lock is only for nodes.
	
	/**
	 * @param clazz
	 */
	public DefaultTree(){
	}

	public Node<T> getRoot() {
		return this.root;
	}
	
	/**
	 * @see thirdstage.sirius.data.tree.Tree#setRoot(java.lang.Object)
	 * @throws IllegalArgumentException if <code>null</code> is provided for the <code>data</code>
	 * @throws IllegalStateException when the root is specified before.
	 */
	public void setRoot(T data) throws IllegalArgumentException, IllegalStateException{
		if(data == null) throw new IllegalArgumentException("Data can't be null.");
		
		l1.lock();
		try{
			if(this.root == null){ 
				this.root = new DefaultNode<T>(data);
				this.nodes.add(this.root);
			}
			else throw new IllegalStateException("Root is already specified and can't be modified.");
		}finally{
			l1.unlock();
		}
	}
	
	public int getSize() {
		return this.nodes.size();
	}

	public Node<T> find(T data) throws IllegalArgumentException{
		DefaultNode<T> found = null;
		l2.readLock().lock(); //@todo Is this necessary ? It is read lock.
		try{
			for(DefaultNode<T> node : nodes){
				if(node.getData().equals(data)){
					found = node;
					break;
				}
			}
		}finally{
			l2.readLock().unlock();
		}
		return found;
	}
	
	public List<Node<T>> findBySpEL(String express){
		return null;
	}
	
	public void add(T data) throws IllegalArgumentException, IllegalStateException{
		if(data == null) throw new IllegalArgumentException("Data can't be null.");
		
		l2.writeLock().lock();
		try{
			if(this.find(data) != null) throw new IllegalStateException("Provided data is already added to this tree.");
			this.nodes.add(new DefaultNode<T>(data));
		}finally{
			l2.writeLock().unlock();
		}
	}
	
	/**
	 * not yet implemented.
	 * 
	 * @see thirdstage.sirius.data.tree.Tree#remove(java.lang.Object)
	 */
	public void remove(T data) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	/**
	 * Add new node or nodes if this tree doesn't yet contain node for parent or child and
	 * set the relationship between the two nodes.
	 * 
	 * Throws exception if this tree contains nodes for parent and child and the relationship 
	 * is already established before. 
	 * 
	 * @see thirdstage.sirius.data.tree.Tree#addChild(java.lang.Object, java.lang.Object)
	 */
	public void addChild(T parent, T child) throws IllegalArgumentException,
			IllegalStateException {
		if(parent == null || child == null) throw new IllegalArgumentException("Data can't be null.");
		
		this.l2.writeLock().lock();
		try{
			DefaultNode<T> parentNode = (DefaultNode<T>)this.find(parent);
			DefaultNode<T> childNode = (DefaultNode<T>)this.find(child);
			
			if(parentNode == null){
				parentNode = new DefaultNode<T>(parent);
				this.nodes.add(parentNode);
			}
			if(childNode == null){
				childNode = new DefaultNode<T>(child);
				this.nodes.add(parentNode);
			}
			if(parentNode.hasChild(childNode)) throw new IllegalStateException("Provided parent and child are already established in this tree.");
			else{
				parentNode.addChild(childNode);
				childNode.setParent(parentNode);
			}
			
		}finally{
			this.l2.writeLock().unlock();
		}
	}
	
	
	/**
	 * Not yet implemented.
	 * 
	 * @see thirdstage.sirius.data.tree.Tree#removeChildAt(java.lang.Object, int)
	 */
	public void removeChildAt(T parent, int index)
			throws IllegalArgumentException, IllegalStateException {
		throw new UnsupportedOperationException("Not yet implemented");

	
	}
	
	/**
	 * Not yet implemented.
	 * 
	 * @see thirdstage.sirius.data.tree.Tree#removeChildren(java.lang.Object)
	 */
	public void removeChildren(T parent) throws IllegalArgumentException,
			IllegalStateException {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public List<Node<T>> preOrderTraversalSequence() {

		List<Node<T>> seq = new ArrayList<Node<T>>(this.nodes.size() + 5);
		this.preOrderTraversal(this.root, seq);
		
		return seq;
	}
	
	/**
	 * The <code>bag</code> should not be <code>null</code>.
	 * If the specified <code>node</code> is <code>null</code>, nothing happen.
	 * 
	 * @param node
	 * @param bag
	 */
	private void preOrderTraversal(Node<T> node, List<Node<T>> bag){
		
		if(bag == null) throw new IllegalArgumentException("You shold specify non-null container.");
		if(node == null) return;
		
		bag.add(node);
		for(Node<T> child : node.getAllChildren()){
			this.preOrderTraversal(child, bag);
		}
		
	}
	
	public List<Node<T>> inOrderTraversalSequence() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Node<T>> postOrderTraversalSequence() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
