package com.amos.server.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/*
 * 
 * object %5B 0 %5D %5B a %5D: 1
   object %5B 0 %5D %5B w %5D: 8
   %5D=]
   %5B=[
   
   obj %5B qwer %5D : 123
 */
public class JSONObject {

	private ArrayList<Mapp> al = new ArrayList<>();
	private String name;

	public void put(String key, Object val) {
		// al.add(new Mapp(key, val));
		map.put(key, val);
	}

	private static Map<String, Object> map = new LinkedHashMap<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		Iterator<Entry<String, Object>> itr=map.entrySet().iterator();
		Object val = null;
		while (itr.hasNext()) {
			Map.Entry<String, Object> entr=(Entry<String, Object>) itr.next();
			
			if (!(entr.getValue() instanceof JsonArray))
				val = entr.getValue() instanceof String ? "\"" + entr.getValue() + "\"" :  entr.getValue();
			sb.append("\"" + entr.getKey() + "\":" + val + ",");
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("}");
		map.clear();
		return sb.toString();
	}

	class Mapp {

		String key;
		private Object val;

		public Mapp(String key, Object val) {
			this.key = key;
			this.val = val;
		}

		public String getKey() {
			return key;
		}

		public Object getVal() {
			return val;
		}

		@Override
		public String toString() {
			if (!(val instanceof JsonArray))
				val = val instanceof String ? "\"" + val + "\"" : val;

			return "\"" + key + "\":" + val;
		}

	}

	static JSONObject getJsonObject(String name, String urlParam) {
		JSONObject js = new JSONObject();
		String beg = name + "%5B";
		boolean keys = true;
		int len = beg.length();
		urlParam = urlParam.substring(urlParam.indexOf(beg) + len);
		js.setName(name);

		while (keys) {
			String key = urlParam.substring(0, urlParam.indexOf("%5D"));
			String value = urlParam.substring(urlParam.indexOf("=") + 1,urlParam.indexOf("&"));
			map.put(key, value);
			int upers=urlParam.indexOf("&");
				if(upers == -1)
				keys = false;
				else
			urlParam = urlParam.substring(urlParam.indexOf("&") + 1);
			
		
			 key = urlParam.substring(0, urlParam.indexOf("%5B"));
			 value = urlParam.substring(urlParam.indexOf("=") + 1);
			map.put(key, value);

		}
		return js;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
