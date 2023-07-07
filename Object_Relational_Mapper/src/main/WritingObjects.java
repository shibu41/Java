package main;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import entity.Person;
import orm.EntityManager;

public class WritingObjects {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		EntityManager<Person> em = EntityManager.of(Person.class);
		 Person tony = new Person("Tony", "Stark");
		 Person steven = new Person("Stephen", "Strange");
		 Person peter = new Person("Peter", "Parker");
		 
		 em.persist(tony);
		 em.persist(steven);
		 em.persist(peter);
	}
}
