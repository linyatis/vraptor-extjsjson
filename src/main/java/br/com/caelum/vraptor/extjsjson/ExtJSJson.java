package br.com.caelum.vraptor.extjsjson;

import javax.enterprise.inject.Specializes;

import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.Serializer;

/**
 * An ExtJS Json serializer. It works similarly to the {@link JSONSerialization}
 * 
 * @author Jose Filipe Lyra
 * 
 */
@Specializes
public interface ExtJSJson extends View {

	/**
	 * Serializes this object to the clients writer, with alias "data" and
	 * success true
	 * 
	 * @param object
	 * @return {@link Serializer}
	 */
	<T> Serializer from(T object);

	/**
	 * Serializes this object to the clients writer, with a given alias and
	 * success true
	 * 
	 * @param object
	 * @param alias
	 * @return {@link Serializer}
	 */
	<T> Serializer from(T object, String alias);

	/**
	 * Serializes the message to the clients writer, with the given success
	 * 
	 * @param msg
	 * @param success
	 * @return {@link Serializer}
	 */
	Serializer fromMsg(String msg, boolean success);

	/**
	 * Serializes the message to the clients writer, with the success true
	 * 
	 * @param msg
	 * @return {@link Serializer}
	 */
	Serializer fromMsg(String msg);

	/**
	 * Serializes the message to the clients writer, with the success false
	 * 
	 * @param msg
	 * @return {@link Serializer}
	 */
	Serializer fromError(String msg);

	/**
	 * Manually sets success value
	 * 
	 * @param success
	 * @return {@link ExtJSJson}
	 */
	ExtJSJson success(boolean success);

	/**
	 * Enables use of the total value and sets automatically by the size of the
	 * object provided. If the given object is not a Collection, not serializes
	 * total.
	 * 
	 * @return {@link ExtJSJson}
	 */
	ExtJSJson total();

	/**
	 * Enables use of the total value and sets the given size. If the object is
	 * not a Collection, not serializes total.
	 * 
	 * @param size
	 * 
	 * @return {@link ExtJSJson}
	 */
	ExtJSJson total(int size);

	/**
	 * Include message into the serialized object.
	 * 
	 * @param msg
	 * 
	 * @return {@link ExtJSJson}
	 */
	ExtJSJson message(String msg);

}
