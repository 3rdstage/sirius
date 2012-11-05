package thirdstage.sirius.data.value;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AbstractIdentifiableValueTest {

  @Test
  public void equals() {
	  
	  EmployeeValue emp1 = new EmployeeValue(1, "Peter", "Manager");
	  EmployeeValue emp2 = new EmployeeValue(2, "Jennifer", "Asistant");
	  
	  PartValue prt1 = new PartValue(1, "Accounting", 5);
	  PartValue prt2 = new PartValue(2, "Sales", 6);
	  
	  Assert.assertFalse(emp1.equals(emp2));
	  Assert.assertTrue(emp1.equals(emp1));
	  Assert.assertFalse(emp1.equals(prt1));
	  
  }
}

class EmployeeValue extends AbstractIdentifiableValue<Integer>{
	
	private String name = null;
	
	private String title = null;
	
	public EmployeeValue(int id, String name, String title){
		super(id);
		
		this.name = name;
		this.title = title;
	}
	
	public String getName(){ return this.name; }
	
	public String getTitle(){ return this.title; }
	
}

class PartValue extends AbstractIdentifiableValue<Integer>{
	
	private String name = null;
	
	private int floor = 0;
	
	public PartValue(int id, String name, int floor){
		super(id);
		this.name = name;
		this.floor = floor;
	}
	
	public String getName(){ return this.name; }
	
	public int floor(){ return this.floor;}
	
}


