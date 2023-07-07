package main;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import entity.Person;
import orm.EntityManager;

public class ReadingObjects {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		EntityManager<Person> em = EntityManager.of(Person.class);
		
		Person tony = em.find(Person.class, 1L);

		 System.out.println(tony.toString());
	}

}
