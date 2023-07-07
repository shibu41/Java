//Utility class to generate sql statements
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import annotations.Column;
import annotations.PrimaryKey;

public class MetaModel {
	
	private Class<?> clss;
	
	public MetaModel(Class<?> clss) {
		this.clss = clss;
	}

	public static MetaModel of(Class<?> clss) {
		return new MetaModel(clss);
	}
	
	//Method to get primary field of the entity class
	public PrimaryKeyField getPrimaryField() {
		//get all fields of the entity class
		java.lang.reflect.Field[] fields = clss.getDeclaredFields();
		/*looping through all the fields to check
		 * which field is marked with primary key annotation*/
		for(java.lang.reflect.Field field: fields) {
			PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
			if(primaryKey != null) {
				PrimaryKeyField primaryKeyField = new PrimaryKeyField(field);
				return primaryKeyField;
			}
		}
		throw new IllegalArgumentException("No Primary Key found in class");
	}
	
	//method to get non-primary columns
	public List<ColumnField> getColumns() {
		//1st create a list of column fields and get list of column names
		List<ColumnField> columnFields = new ArrayList<>();
		java.lang.reflect.Field[] fields = clss.getDeclaredFields();
		
		//looping through all the fields to get column fields
		for(java.lang.reflect.Field field: fields) {
			Column column = field.getAnnotation(Column.class);
			if(column != null) {
				ColumnField columnField = new ColumnField(field);
				columnFields.add(columnField);
			}
		}
		return columnFields;
	}

	//insert into Person(id, firstName, lastName) value (?, ?, ?)
	public String buildInsertRequest() {
		String columnElement = buildColumnNames();
		
		//becoz indexing starts from 0
		int numberOfColumns = getColumns().size() + 1;
		//creating a string of question mark elements
		String questionMarkElement = IntStream.range(0, numberOfColumns)
												.mapToObj(index->"?")
												.collect(Collectors.joining(", "));
		return "insert into " + this.clss.getSimpleName()
				+ " (" + columnElement + ") values (" + questionMarkElement + ")";
		
	}

	private String buildColumnNames() {
		//getting primary key name
		String primaryKeyColumnName = getPrimaryField().getName();
		//getting other column names
		List<String> columnNames = getColumns().stream().map(c->c.getName()).collect(Collectors.toList());
		//adding primary key name to the list of column names
		columnNames.add(0, primaryKeyColumnName);
		//creating a string of column names separated by comma
		String columnElement = String.join(", ",columnNames);
		return columnElement;
	}

	//select id, firstName, lastName from Person where id = ?
	public String buildSelectRequest() {
		String columnElement = buildColumnNames();
		return "select " + columnElement + " from " + this.clss.getSimpleName() +
				" where " + getPrimaryField().getName() + " = ?";
	}
}








