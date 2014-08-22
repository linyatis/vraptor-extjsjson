#VRaptor-ExtJSJson

##Installing

You have to clone or download this and run `mvn clean install`.

Then, add to your pom:

```
<dependency>
<groupId>br.com.caelum.vraptor</groupId>
<artifactId>vraptor-extjsjson</artifactId>
<version>1.0.0-beta</version>
</dependency>

```

##How it works
It works similarly to JSONSerialization: `result.use(Results.json())`.

###Example

```
@Controller
public class MyController{
    //...
    
    public void list() {
        result.use(ExtJSJson.class).from(service.getListAll()).serialize();
    }
    
    public void anotherOne() {
    result.use(ExtJSJson.class).from(service.getListAll()).recursive().serialize();
    }
}

```
