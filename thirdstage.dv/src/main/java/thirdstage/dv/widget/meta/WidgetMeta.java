package thirdstage.dv.widget.meta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class WidgetMeta {
	
	private String name;
	
	private String title;
	
	private List<PartMeta> parts = new ArrayList<PartMeta>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PartMeta> getPartMetas() {
		return parts;
	}

	public void addPartMeta(@Nonnull PartMeta meta){
		this.parts.add(meta);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder().append("{\n")
			.append("  name : ").append(this.getName()).append("\n")
			.append("  title : ").append(this.getTitle()).append("\n");
		
		for(PartMeta pm : this.parts){
			sb.append(pm.toString()).append("\n");
		}
		
		return sb.append("}").toString();
	}	
}
