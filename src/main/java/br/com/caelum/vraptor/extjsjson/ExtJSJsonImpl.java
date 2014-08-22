package br.com.caelum.vraptor.extjsjson;

import javax.inject.Inject;

import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.Serializer;

/**
 * {@link ExtJsJson} implementation. <br>
 * 
 * @author José Filipe Lyra
 * 
 */
public class ExtJSJsonImpl implements ExtJSJson {

	@Inject
	private ExtJSMapper mapper;
	@Inject
	private JSONSerialization json;

	public ExtJSJsonImpl() {
	}

	@Override
	public <T> Serializer from(T object) {
		return this.from(object, "data");
	}

	@Override
	public <T> Serializer from(T object, String alias) {
		return this.json.withoutRoot().from(this.mapper.map(object, alias));
	}

	@Override
	public Serializer fromMsg(String msg, boolean success) {
		return this.json.withoutRoot().from(this.mapper.mapMsg(msg, success));
	}

	@Override
	public Serializer fromMsg(String msg) {
		return this.fromMsg(msg, true);
	}

	@Override
	public Serializer fromError(String msg) {
		return this.fromMsg(msg, false);
	}

	@Override
	public ExtJSJson success(boolean success) {
		this.mapper.setSuccess(success);
		return this;
	}

	@Override
	public ExtJSJson total() {
		this.mapper.total();
		return this;
	}

	@Override
	public ExtJSJson total(int size) {
		this.mapper.setTotal(size);
		return this;
	}

	@Override
	public ExtJSJson message(String msg) {
		this.mapper.setMsg(msg);
		return this;
	}

}
