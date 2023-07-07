//class responsible for sql operations
package orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import util.ColumnField;
import util.MetaModel;

public class EntityManagerImpl<T> implements EntityManager<T> {
	
	//used to generate unique ids
	private AtomicLong idGen = new AtomicLong(0L);

	//method to write objects to sql database
	@Override
	public void persist(T t) throws SQLException, IllegalArgumentException, IllegalAccessException {

		//build sql statements
		MetaModel model = MetaModel.of(t.getClass());
		String sql = model.buildInsertRequest();
		//create a ps object to actually write objects to sql database
		PreparedStatement statement = prepareStatementWith(sql).andParameters(t);
		statement.executeUpdate();
	}

	private PreparedStatementWrapper prepareStatementWith(String sql) throws SQLException {
		Connection conn = DriverManager.getConnection
				("jdbc:h2:~/eclipse-workspace/ORM/db-files/db-file", "sa", "");
		PreparedStatement ps = conn.prepareStatement(sql);
		return new PreparedStatementWrapper(ps);
	}
	
	private class PreparedStatementWrapper{
		private PreparedStatement statement;
		
		public PreparedStatementWrapper(PreparedStatement ps) {
			statement = ps;
		}
		
		public PreparedStatement andParameters(T t) throws SQLException, IllegalArgumentException, IllegalAccessException {
			MetaModel model = MetaModel.of(t.getClass());
			Class<?> primaryKeyType = model.getPrimaryField().getType();
			if(primaryKeyType == long.class) {
				long id = idGen.incrementAndGet();
				statement.setLong(1, id);
				Field field = model.getPrimaryField().getField();
				field.setAccessible(true);
				field.set(t, id);
			}
			for(int col=0; col<model.getColumns().size(); col++){
				ColumnField cf = model.getColumns().get(col);
				Field field = cf.getField();
				field.setAccessible(true);
				Object value = field.get(t);
				statement.setString(col + 2, (String)value);
			}
			return statement;
		}

		public PreparedStatement andPrimaryKey(Object primaryKey) throws SQLException {
			if(primaryKey.getClass() == Long.class) {
				statement.setLong(1, (Long)primaryKey);
			}
			return statement;
		}
	}

	@Override
	public T find(Class<T> clss, Object primaryKey) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//build sql statements
		MetaModel model = MetaModel.of(clss);
		String sql = model.buildSelectRequest();
		//create a ps object to actually read objects from sql database
		PreparedStatement statement = prepareStatementWith(sql).andPrimaryKey(primaryKey);
		ResultSet resultSet = statement.executeQuery();
		return buildInstanceFrom(clss, resultSet);
	}

	private T buildInstanceFrom(Class<T> clss, ResultSet resultSet) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		MetaModel metamodel = MetaModel.of(clss);
		T t = clss.getConstructor().newInstance();
		Field primaryKeyField = metamodel.getPrimaryField().getField();
		String primaryKeyColumnName = metamodel.getPrimaryField().getName();
		Class<?> primaryKeyType = primaryKeyField.getType();
		
		resultSet.next();
		if(primaryKeyType == long.class) {
			long primaryKey = resultSet.getInt(primaryKeyColumnName);
			primaryKeyField.setAccessible(true);
			primaryKeyField.set(t, primaryKey);
		}
		
		for(ColumnField columnField: metamodel.getColumns()) {
			Field field = columnField.getField();
			field.setAccessible(true);
			Class<?> columnType = columnField.getType();
			String columnName =columnField.getName();
			if(columnType == int.class) {
				int value = resultSet.getInt(columnName);
				field.set(t, value);
			}
			else if(columnType == String.class) {
				String value = resultSet.getString(columnName);
				field.set(t,  value);
			}
		}
		return t;
	}

}







