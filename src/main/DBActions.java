package main;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DBActions<T> {

	public String addEntry(T t) {
		if (checkIfMappable(t)) {
			HashMap<String, String> objectProperties = new HashMap<String, String>();
			try {
				objectProperties = readObject(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String allKeys = objectProperties.keySet().toString().replace('[', ' ').replace(']', ' ');
			String allVvalues = objectProperties.values().toString().replace('[', ' ').replace(']', ' ');
			String statement = "Update " + t.getClass().getSimpleName() + " ( " + allKeys + ") values ( " + allVvalues
					+ ");";
			return statement;
		} else {
			throw new UnsupportedOperationException("Object does not implement Mappable interface");
		}
	}

	public String deleteEntry(T t) {
		if (checkIfMappable(t)) {
			String id = "";
			try {
				id = readObject(t).get("id");
			} catch (Exception e) {
				e.printStackTrace();
			}
			String statement = "Delete from " + t.getClass().getSimpleName() + "where id = " + id + ";";
			return statement;
		} else {
			throw new UnsupportedOperationException("Object does not implement Mappable interface");
		}
	}

	// TO DO - figure out based on what to update entry
	public String updateEntry(T t) {
		if (checkIfMappable(t)) {
			HashMap<String, String> objectProperties = new HashMap<String, String>();
			try {
				objectProperties = readObject(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String allKeys = objectProperties.keySet().toString().replace('[', ' ').replace(']', ' ');
			String allValues = objectProperties.values().toString().replace('[', ' ').replace(']', ' ');
			String statement = "Update " + t.getClass().getSimpleName() + " ( " + allKeys + ") values ( " + allValues
					+ ") where id="
					+ objectProperties.get("id") + ";";
			return statement;
		} else {
			throw new UnsupportedOperationException("Object does not implement Mappable interface");
		}
	}

	public String createTable(T t) {
		if (checkIfMappable(t)) {
			// HashMap<String, String> objectProperties = new HashMap<String, String>();
			//HashMap<String, String> modifiedEntry = new HashMap<String, String>();
			Set<String> keys = new HashSet<String>();
			Set<String> modifiedKeys = new HashSet<String>();
			try {
				keys = readObject(t).keySet();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (String key : keys) {
				modifiedKeys.add(key + " varchar(255)");
			}
			String allKeys = modifiedKeys.toString().replace('[', ' ').replace(']', ' ');
			String statement = "Create table " + t.getClass().getSimpleName() + " (" + allKeys + ");";
			return statement;
		} else {
			throw new UnsupportedOperationException("Object does not implement Mappable interface");
		}
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

	private Boolean checkIfMappable(T t) {
		if (DBMappable.class.isAssignableFrom(t.getClass())) {
			return true;
		} else {
			return false;
		}
	}

}
