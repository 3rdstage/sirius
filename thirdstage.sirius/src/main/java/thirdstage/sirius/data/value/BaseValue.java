package thirdstage.sirius.data.value;

public abstract class BaseValue<T> extends AbstractIdentifiableValue<T> implements Named {
	
	private String name = null;
	
	public BaseValue(T id, String name){
		super(id);
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}
