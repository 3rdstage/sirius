package thirdstage.sirius.data.tree;

import java.util.List;

public interface Tree<T> {
	
	Node<T> getRoot();
	
	void setRoot(T data);
	
	int getSize();
	
	Node<T> find(T data);
	
	void add(T data);
	
	void remove(T data);
	
	void addChild(T parent, T child)
			throws IllegalArgumentException, IllegalStateException;
	
	void removeChildAt(T parent, int index)
			throws IllegalArgumentException, IllegalStateException;
	
	void removeChildren(T parent)
			throws IllegalArgumentException, IllegalStateException;
	
	<N extends Node<T>> List<N> preOrderTraversalSequence();
	
	<N extends Node<T>> List<N> inOrderTraversalSequence();
	
	<N extends Node<T>> List<N> postOrderTraversalSequence();

}
