package thirdstage.sirius.data.tree;

public class MutableNode<T> extends DefaultNode<T> {
	
	
	public MutableNode(T data){
		super(data);
	}
	
	public void setData(T data){
		super.setData(data);
	}

}
