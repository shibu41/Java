package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface EntityManager<T> {

	static <T> EntityManager<T> of(Class<T> clss) {
		return new EntityManagerImpl<T>();
	}
	
	void persist(T t) throws SQLException, IllegalArgumentException, IllegalAccessException;
	
	T find(Class<T> clss, Object primaryKey) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}