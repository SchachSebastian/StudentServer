## Jackson

Converts JSON to objects, and objects to JSON.

#### @JsonProperty

used to define the name of the property in the JSON

```java
class Person {
	// can be used on fields or getters
	@JsonProperty("name_of_person")
	private String name;
	@JsonProperty("name_of_person")
	private String getName() {
		return name;
	}
}
```

#### @JsonAnySetter/JsonAnyGetter

used to define a map of properties that are not defined in the class

```java
class Person {
	private Map<String, Object> properties = new HashMap<>();
	@JsonAnySetter
	public void set(String name, Object value) {
		properties.put(name, value);
	}
	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return properties;
	}
}
```

#### @JsonSerializer/JsonDeserializer

used to define a custom serializer/deserializer for a class, especially useful for enums and dates

```java
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

class Person {
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private Date date;
}

// with format dd.MM.yyyy
class LocalDateSerializer extends StdSerializer<Date> {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(dateFormat.format(value));
	}
}

class LocalDateDeserializer extends StdDeserializer<Date> {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	public LocalDateDeserializer() {
		this(Date.class);
	}
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String date = p.getText();
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
```

#### ObjectMapper

object used to convert objects to JSON and vice versa

```java
import com.fasterxml.jackson.databind.ObjectMapper;

class Jackson {
	// from json to object
	void deserialize() {
		ObjectMapper objectMapper = new ObjectMapper();
		School school = objectMapper
				.readValue(getClass().getClassLoader()
				                     .getResource("school.json"),
				           School.class);
//	getClass().getClassLoader().getResource("school.json")
//      specifies the path to the file in resources directory
	}
	// with string
	void deserializeWithString(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		School school = objectMapper.readerFor(School.class).readValue(json);
	}
	// serialize object to json
	void serialize() {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(school);
	}
}
```

## JakartaEE

Resource and Application class required, probably predefined in the revision project

```java

@ApplicationPath("/api_or_something_else")
public class AppApplication extends Application {
	// This class is empty because it is only used to define the path
}
```

```java

@Path("/anything")
public class AnyResource {
	// write GET, POST, PUT, DELETE methods here
}
```

#### HTTP methods

```java
@GET/@POST/@PUT/@DELETE -defines the HTTP method
@Produces("application/json") -optional
@Consumes("application/json") -optional
@Path("/{id}") -optional path parameter
public Response something(@PathParam("id") String id /*the path param*/,
		Object  /*object in fetch body*/){
		// do something
		return Response.ok().build();
		}
```

##### Response

Response can be built with a body, status code, headers, etc.
Builder pattern is used to build the response.

```java
class Example {
	void example() {
		Response.ok(); //status 200 response
		Response.status(anyStatus); //any status response
		Response.ok().entity(anyObject); //response with body
		Response.ok().build(); //build response
	}
}
```

#### JavaScript Fetch

```javascript
fetch("http://localhost:8080/api_or_something_else/anything", {
    method: "GET", // defines the HTTP method, default is GET
    headers: { // defines the headers
        "Content-Type": "application/json" // required if body is present
    },
    body: JSON.stringify({ // defines the body, can be any object
        "id": 1
    })
}).then(response => response.json())
    .then(data => console.log(data));
```

## Junit

Library for testing

#### @BeforeEach

annotation that defines a method that is executed before each test

```java
@BeforeEach
void setUp(){
		// do something
		}
```

#### @Test

annotation for test methods

```java
@Test
public void test(){
		// test something
		assertTrue(true);
		}
```

#### assert

used to test if the result is as expected, in @Test methods

```java
assertEquals(expected,actual);
		assertTrue(condition); // hat schreiber nie verwendet, use instead of assertEquals(true, condition)
// a fail message can be added to the assert
		assertEquals(expected,actual,"fail message");
```

## JWT

used to authenticate users
required classes

- JWTManager - used to create and verify tokens
- JWTResource - sends the token to the client
- Token - the token itself

#### JWTManager

```java
public class JWTManager {
	private static final Algorithm algorithm = Algorithm.HMAC256(
			"student-server is a nice " + "example"); // algoritm
	// to sign the token, secret can be any string, but preferably a long one
	public static Token createJWT() {
		return new Token(JWT.create().withIssuedAt(new Date()).withIssuer("Student-Server")
		                    .sign(algorithm)); // create a token with the issuer Student-Server
		// and the issue date
	}
	public static boolean verifyToken(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("Student-Server").build();
			// JWTVerifier tests if the token is valid, based on secret and the added extra
			// information, like issuer
			verifier.verify(token.replaceFirst("Bearer ", "")); // remove Bearer before
			// verifying, as it is not part of the token but stands for the type of the token
			return true;
		} catch (JWTVerificationException ex) { // exception is thrown if the token is not valid
			return false;
		}
	}
}
```

#### Token

```java
public class Token {
	private String token;
	public Token(String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}
}
```

#### JWTResource

```java
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;

@Path("/token")
public class JWTResource {
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	public Response getToken(LoginData loginData /*can be any class representing login data*/) {
		if (loginData.isCorrect()) { // method to check login data
			return Response.ok(JWTManager.createJWT()).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
}
```

#### Server

Every method that requires authentication needs to add a JWTManager.verifyToken(token) call
and a new parameter annotated with @HeaderParam("Authorization") to automatically get the token sent from client

```java
class ExampleResource {
	@GET
	@Path("/example")
	public Response example(@HeaderParam("Authorization") String token) {
		if (JWTManager.verifyToken(token)) {
			// do something
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
```

#### Client

the client needs to fetch the token from the server and send it with every request
by adding an extra header to the fetch call

```json
headers: {
"Authorization": "Bearer " + JWT_TOKEN // <--- add bearer because its standard
}
```

## Streams

#### map

```java
List<String> list=Arrays.asList("a","b","c");
		List<String> result=list.stream().map(String::toUpperCase).toList();
// result = ["A", "B", "C"]
```

#### filter

```java
List<String> list=Arrays.asList("a","b","c");
		List<String> result=list.stream().filter(s->s.contains("a")).collect(Collectors.toList());
		result=["a"]
```

#### flatMap

```java
List<List<String>>list=Arrays.asList(Arrays.asList("a","b"),Arrays.asList("c","d"));
		List<String> result=list.stream().flatMap(Collection::stream).collect(Collectors.toList());
		result=["a","b","c","d"]
```

### collect

```java
// toList
List<String> list=Arrays.asList("a","b","c");
		List<String> result=list.stream().collect(Collectors.toList());
		result=["a","b","c"]
// maxBy - gets max element based on comparator
		List.of(12,12,13).stream().collect(Collectors.maxBy(Integer::compareTo));
		result=Optional[13]
```

... and many more different stream functions and comparators