package main;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DBActions<T> {

	public String addEntry(T t) {
		HashMap<String, String> objectProperties = new HashMap<String, String>();
		try {
			objectProperties = readObject(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String allKeys = objectProperties.keySet().toString().replace('[', ' ').replace(']', ' ');
		String allVvalues = objectProperties.values().toString().replace('[', ' ').replace(']', ' ');
		String statement = "Update " + t.getClass() + " ( " + allKeys + ") values ( " + allVvalues + ")";
		return statement;
	}

	// TO DO - figure out based on what to update entry
	public String deleteEntry(T t) {
		String statement = "";
		try {
			readObject(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statement;
	}

	public String updateEntry(T t, int id) {
		HashMap<String, String> objectProperties = new HashMap<String, String>();
		try {
			objectProperties = readObject(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String allKeys = objectProperties.keySet().toString().replace('[', ' ').replace(']', ' ');
		String allVvalues = objectProperties.values().toString().replace('[', ' ').replace(']', ' ');
		String statement = "Update " + t.getClass() + " ( " + allKeys + ") values ( " + allVvalues + ") where id="
				+ objectProperties.get(id);
		return statement;
	}

	public String createTable(T t) {
		try {
			readObject(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String statement = "Create table " + t.getClass() + "";
		return statement;
	}

	protected HashMap<String, String> readObject(T t) throws Exception {
		HashMap<String, String> objectProperties = new HashMap<String, String>();
		try {
			for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(t.getClass(), Object.class)
					.getPropertyDescriptors()) {
				objectProperties.put(propertyDescriptor.getName(),
						propertyDescriptor.getReadMethod().invoke(t, null).toString());
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return objectProperties;
	}

	protected Boolean checkIfMapper(T t) {
		if (DBMappable.class.isAssignableFrom(t.getClass())) {
			return true;
		} else {
			return false;
		}
	}

}
