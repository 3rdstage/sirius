package thirdstage.dv.widget.meta;

import javax.annotation.Nonnull;

public enum AttributeType {
	
	BOOLEAN,
	INTEGER,
	NUMBER,
	STRING,
	UNKNOWN;
	
	public static AttributeType valueFromJsonType(@Nonnull String jsonType){
		if(jsonType == null) throw new IllegalArgumentException("The parameter should not null");
		
		return AttributeType.valueOf(jsonType.toUpperCase());
	}
	
	
}
