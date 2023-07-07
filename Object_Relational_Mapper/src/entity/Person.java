//Entity Class
package entity;

import annotations.Column;
import annotations.PrimaryKey;

public class Person {

	@PrimaryKey
	private long id;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	public Person() {
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public static Person of(String firstName, String lastName) {
		return new Person(firstName, lastName);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
