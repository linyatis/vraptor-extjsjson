package br.com.caelum.vraptor.extjsjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manage {@link Map} that represents the object to be serialized as an ExtJS
 * Json
 * 
 * @author José Filipe Lyra
 * 
 */
public class ExtJSMapper {

	private Integer total;
	private boolean useTotal;
	private boolean success;
	private String msg;

	public ExtJSMapper() {
		this.success = true;
	}

	public void setTotal(int total) {
		this.total = total;
		this.useTotal = true;
	}

	public void total() {
		this.useTotal = true;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public <T> Map<String, Object> map(T object, String alias) {

		Map<String, Object> modelMap = new HashMap<String, Object>(4);

		if (object instanceof Collection) {
			List<Object> list = new ArrayList<Object>((Collection<?>) object);
			modelMap.put(alias, list);

			if (this.useTotal) {
				if (this.total == null)
					this.total = list.size();
				modelMap.put("total", this.total);
			}

		} else {
			modelMap.put(alias, object);
		}

		if (this.msg != null) {
			modelMap.put("msg", this.msg);
		}
		modelMap.put("success", this.success);

		return modelMap;
	}

	public Map<String, Object> mapMsg(String msg, boolean success) {

		this.msg = msg;
		this.success = success;

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("msg", this.msg);
		modelMap.put("success", this.success);

		return modelMap;
	}

}
