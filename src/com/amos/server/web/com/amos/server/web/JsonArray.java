package com.amos.server.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonArray<T> {

	private List<T> data;

	
	public JsonArray() {
		data = new ArrayList<>();
	}

	public JsonArray(T...o){
		this();
		add(o);
	}
	
	public void add( T o) {
		data.add(o);
	}

	public void add(T... o) {
		data.addAll(Arrays.asList(o));
	}

	public T get(int index) {
		return data.get(index);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (get(0) instanceof String || get(0) instanceof Character) {

			for (T o : data) {
				sb.append("\"" + o + "\"" + ",");
			}

		} else
			for (T o : data) {
				sb.append(o+",");
			}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]");

		return sb.toString();
	}
	

}
