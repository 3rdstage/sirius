package thirdstage.sirius.data.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import thirdstage.sirius.data.tree.FastPreorderTree;
import thirdstage.sirius.data.tree.Node;
import thirdstage.sirius.data.tree.Tree;

public class FastPreorderTreeTest {
	
	protected static final Map<String, CategoryValue> categories = new HashMap<String, CategoryValue>();
	
	@BeforeClass
	public void beforeClass() {
		
		categories.put("CAT0001", new CategoryValue("CAT0001","Electronics",null,1,null));
		categories.put("CAT0002", new CategoryValue("CAT0002","Cell Phones","CAT0001",1,null));
		categories.put("CAT0003", new CategoryValue("CAT0003","Smartphones","CAT0001",2,null));
		categories.put("CAT0004", new CategoryValue("CAT0004","Digital Camera","CAT0001",3,null));
		categories.put("CAT0005", new CategoryValue("CAT0005","Fashion", null,2,null));
		categories.put("CAT0006", new CategoryValue("CAT0006","Women's Clothing","CAT0005",1,null));
		categories.put("CAT0007", new CategoryValue("CAT0007","Women's Handbags","CAT0005",2,null));
		categories.put("CAT0008", new CategoryValue("CAT0008","Women's Shoes","CAT0005",3,null));
		categories.put("CAT0009", new CategoryValue("CAT0009","Women's Accessories","CAT0005",4,null));
		categories.put("CAT0010", new CategoryValue("CAT0010","Men's Clothing","CAT0005",5,null));
		categories.put("CAT0011", new CategoryValue("CAT0011","Men's Shoes","CAT0005",6,null));
		categories.put("CAT0012", new CategoryValue("CAT0012","Men's Accessories","CAT0005",7,null));
		categories.put("CAT0013", new CategoryValue("CAT0013","Jacket","CAT0006",1,null));
		categories.put("CAT0014", new CategoryValue("CAT0014","Coat","CAT0006",2,null));
		categories.put("CAT0015", new CategoryValue("CAT0015","Canon","CAT0004",1,null));
		categories.put("CAT0016", new CategoryValue("CAT0016","Sony","CAT0004",3,null));
		categories.put("CAT0017", new CategoryValue("CAT0017","Nikon","CAT0004",2,null));
		categories.put("CAT0018", new CategoryValue("CAT0018","Canon EOS","CAT0015",1,null));
		categories.put("CAT0019", new CategoryValue("CAT0019","Canon IXUS","CAT0015",3,null));
		categories.put("CAT0020", new CategoryValue("CAT0020","Canon PowerShot","CAT0015",2,null));
	}

	@AfterClass
	public void afterClass() {
		categories.clear();
	}

	@Test(expectedExceptions={IllegalStateException.class})
	public void testAddChild(){
		
		Tree<CategoryValue> tree = new FastPreorderTree<CategoryValue>();

		CategoryValue root = new CategoryValue("CAT0000", "Root", null, 1, null);
		tree.setRoot(root);
		
		for(CategoryValue cat : categories.values()){
			if(cat.getParentId() == null){
				tree.addChild(root, cat);
			}else{
				tree.addChild(categories.get(cat.getParentId()), cat);
			}
		}
		
		CategoryValue child = categories.get("CAT0018");
		CategoryValue parent = categories.get(child.getParentId());
		tree.addChild(parent, child);
		
		List<Node<CategoryValue>> preOrderSeq = tree.preOrderTraversalSequence();
		
		System.out.println("The pre-order traversal sequence of the tree is : ");
		this.printCategoryValueNodeSequence(preOrderSeq, System.out);
	}

	@Test
	public void testPreOrderTraversalSequence() {
		
		Tree<CategoryValue> tree = new FastPreorderTree<CategoryValue>();
		
		CategoryValue root = new CategoryValue("CAT0000", "Root", null, 1, null);
		tree.setRoot(root);
		
		for(CategoryValue cat : categories.values()){
			if(cat.getParentId() == null){
				tree.addChild(root, cat);
			}else{
				tree.addChild(categories.get(cat.getParentId()), cat);
			}
		}
		
		List<Node<CategoryValue>> preOrderSeq = tree.preOrderTraversalSequence();
		
		System.out.println("The pre-order traversal sequence of the tree is : ");
		this.printCategoryValueNodeSequence(preOrderSeq, System.out);
		
		int cnt = 0;
		Assert.assertEquals("CAT0000", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0001", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0002", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0003", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0004", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0015", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0018", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0020", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0019", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0017", preOrderSeq.get(cnt++).getData().getId());
		Assert.assertEquals("CAT0016", preOrderSeq.get(cnt++).getData().getId());
	}
	
	
	protected void printCategoryValueNodeSequence(List<Node<CategoryValue>> seq, java.io.PrintStream out){

		int d = 0;
		for(Node<CategoryValue> node : seq){
			d = node.getDepth();
			while(d-- > 0) System.out.print("  ");
			out.printf("  %1$s (id: %2$s, parent: %3$s, depth: %4$d, order: %5$d )\n", 
					node.getData().getName(), node.getData().getId(), node.getData().getParentId(), 
					node.getDepth(), node.getData().getOrder());
		}
	}
}


