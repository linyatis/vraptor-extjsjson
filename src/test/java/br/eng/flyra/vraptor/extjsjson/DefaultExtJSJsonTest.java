package br.eng.flyra.vraptor.extjsjson;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.interceptor.DefaultTypeNameExtractor;
import br.com.caelum.vraptor.serialization.Serializee;
import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;
import br.com.caelum.vraptor.serialization.gson.GsonJSONSerialization;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;
import br.com.caelum.vraptor.util.test.MockInstanceImpl;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public class DefaultExtJSJsonTest {

	private DefaultExtJSJson extjsSerialization;

	private GsonJSONSerialization serialization;
	private ByteArrayOutputStream stream;
	private HttpServletResponse response;
	private DefaultTypeNameExtractor extractor;
	private Environment environment;
	private GsonSerializerBuilder builder;

	@Before
	public void setup() throws Exception {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-0300"));

		stream = new ByteArrayOutputStream();

		response = mock(HttpServletResponse.class);
		when(response.getWriter()).thenReturn(new PrintWriter(stream));
		extractor = new DefaultTypeNameExtractor();
		environment = mock(Environment.class);

		List<JsonSerializer<?>> jsonSerializers = new ArrayList<>();
		List<JsonDeserializer<?>> jsonDeserializers = new ArrayList<>();

		builder = new GsonBuilderWrapper(
				new MockInstanceImpl<>(jsonSerializers),
				new MockInstanceImpl<>(jsonDeserializers), new Serializee());
		serialization = new GsonJSONSerialization(response, extractor, builder,
				environment);

		extjsSerialization = new DefaultExtJSJson(serialization,
				new ExtJSMapper());
	}

	private String result() {
		return new String(stream.toByteArray());
	}

	public static class Client {
		String name;

		Calendar included;

		public Client(String name) {
			this.name = name;
		}
	}

	public static class Order {
		Client client;

		double price;

		String comments;

		public Order(Client client, double price, String comments) {
			this.client = client;
			this.price = price;
			this.comments = comments;
		}

		public String nice() {
			return "nice output";
		}

	}

	@Test
	public void shouldSerializeObject() {
		String expectedResult = "{\"data\":{\"price\":15.0,\"comments\":\"pack it nicely, please\"},\"success\":true}";

		Order order = new Order(new Client("Jose Filipe Lyra"), 15.0,
				"pack it nicely, please");

		extjsSerialization.from(order).serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}

	@Test
	public void shouldSerializeObjectWithAlias() {
		String expectedResult = "{\"order\":{\"price\":15.0,\"comments\":\"pack it nicely, please\"},\"success\":true}";

		Order order = new Order(new Client("Jose Filipe Lyra"), 15.0,
				"pack it nicely, please");

		extjsSerialization.from(order, "order").serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}
	
	@Test
	public void shouldSerializeCollection() {
		String expectedResult = "{\"data\":[{\"price\":15.0,\"comments\":\"pack it nicely, please\"}],\"success\":true}";

		List<Order> orders = new ArrayList<Order>();
		
		Order order = new Order(new Client("Jose Filipe Lyra"), 15.0,
				"pack it nicely, please");
		
		orders.add(order);

		extjsSerialization.from(orders).serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}
	
	@Test
	public void shouldSerializeCollectionRecursively() {
		String expectedResult = "{\"data\":[{\"client\":{\"name\":\"Jose Filipe Lyra\"},\"price\":15.0,\"comments\":\"pack it nicely, please\"}],\"success\":true}";

		List<Order> orders = new ArrayList<Order>();
		
		Order order = new Order(new Client("Jose Filipe Lyra"), 15.0,
				"pack it nicely, please");
		
		orders.add(order);

		extjsSerialization.from(orders).recursive().serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}
	
	@Test
	public void shouldSerializeCollectionWithTotal() {
		String expectedResult = "{\"total\":1,\"data\":[{\"price\":15.0,\"comments\":\"pack it nicely, please\"}],\"success\":true}";
		List<Order> orders = new ArrayList<Order>();
		
		Order order = new Order(new Client("Jose Filipe Lyra"), 15.0,
				"pack it nicely, please");
		
		orders.add(order);

		extjsSerialization.total().from(orders).serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}
	
	@Test
	public void shouldSerializeInfo() {
		String expectedResult = "{\"success\":true,\"msg\":\"Test message.\"}";

		String msg = "Test message.";

		extjsSerialization.fromMsg(msg).serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}
	
	@Test
	public void shouldSerializeError() {
		String expectedResult = "{\"success\":false,\"msg\":\"Test message.\"}";

		String msg = "Test message.";

		extjsSerialization.fromError(msg).serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}
	
	@Test
	public void shouldSerializeMessage() {
		String expectedResult = "{\"success\":false,\"msg\":\"Test message.\"}";

		String msg = "Test message.";

		extjsSerialization.fromMsg(msg, false).serialize();
		assertThat(result(), is(equalTo(expectedResult)));
	}
	
}
