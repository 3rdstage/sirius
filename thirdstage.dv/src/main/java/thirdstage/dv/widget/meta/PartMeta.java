package thirdstage.dv.widget.meta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class PartMeta {
	
	private String name;
	
	private String title;

	private String description;
	
	private List<AttributeMeta> attributes = new ArrayList<AttributeMeta>();

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
	
	public String getTitle(){ return this.title; }
	
	public void setTitle(String title){ this.title = title; }
	
	public String getDescription(){ return this.description; }
	
	public void setDescription(String desc){ this.description = desc; }

	public List<AttributeMeta> getAttributeMetas() {
		return attributes;
	}

	public void addAttributeMeta(@Nonnull AttributeMeta meta){
		this.attributes.add(meta);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder().append("   {\n")
			.append("     name : ").append(this.getName()).append("\n")
			.append("     title : ").append(this.getTitle()).append("\n");
		
		for(AttributeMeta am : this.attributes){
			sb.append("     ").append(am.toString()).append("\n");
		}
		
		return sb.append("   }").toString();
	}
}
