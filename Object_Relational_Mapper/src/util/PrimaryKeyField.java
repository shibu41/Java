//class to store primary key details 
package util;

import java.lang.reflect.Field;

public class PrimaryKeyField {
	
	private Field field;

	public PrimaryKeyField(Field field) {
		this.field = field;
	}

	public String getName() {
		return field.getName();
	}
	
	public Class<?> getType() {
		return field.getType();
	}

	public Field getField() {
		return this.field;
	}

}
