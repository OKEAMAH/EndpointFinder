package ca.zhack.endpointfinder.shim;

import java.util.HashMap;
import java.util.Map.Entry;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.annotations.JSFunction;

public class Map extends ScriptableObject {

	private java.util.Map<Object, Object> internalMap = new HashMap<Object, Object>();
	
	@Override
	public String getClassName() {
		return "Map";
	}

	@JSFunction
	public void set(Object key, Object value) {
		internalMap.put(key, value);
	}
	
	@JSFunction
	public boolean has(Object key) {
		return internalMap.containsKey(key);
	}
	
	@JSFunction
	public Object get(Object key) {
		Object res = internalMap.get(key);
		
		if (res == null) {
			return Undefined.instance;
		}
		
		return res;
	}
	
	@JSFunction
	public void forEach(Object param) {
		Callable callback = (Callable) param;
		
		for(Entry<Object, Object> v : internalMap.entrySet()) {
			callback.call(Context.getCurrentContext(), (Scriptable) callback, this, new Object[] { v.getValue(), v.getKey(), this });
		}
	}
	
}