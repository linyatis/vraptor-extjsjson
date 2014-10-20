#VRaptor-ExtJSJson

##Installing

You have to clone or download this and run `mvn install`.

Then, add to your pom:

```
<dependency>
<groupId>br.com.caelum.vraptor</groupId>
<artifactId>vraptor-extjsjson</artifactId>
<version>1.0.0-beta</version>
</dependency>

```

##How it works
It works similarly to JSONSerialization `result.use(Results.json())`.  

With `result.use(ExtJsJson.class)` you can use:

 - `success(boolean success)`: manually sets success value
 - `total()`: include "total" param with your collection size (your object has to be a collection);
 - `total(int size)`: include "total" param with given size (your object has to be a collection);
 - `message(String msg)`: include "msg" param with given text;
 - **`from(Object object, String alias)`**: include your object in the `Serializer`, with the given alias. From this method, it uses the `br.com.caelum.vraptor.serialization.Serializer`, so it is the same as the as the `result.use(Results.json()).from()` (i.e., for now, you can use just recursive());
 - `from(Object object)`: the same as above, but with default alias "data" (ExtJS default alias).
 
 Then, you must finish with `serialize()`, as well as `Results.json()` does.

###Examples

Examples here use the entity "User", as follows.   
  
When mentioned "users", means `List<User> users`.

```
public class User {
    private int id;
    private String name;

    //Getters and setters
}
```

####Default (not-collection)

```
@Controller
public class MyController{
    //...

    public void get() {
        result.use(ExtJSJson.class).from(user).serialize();
    }
}

```
Results in:

```
{
    "data" : {
        "id" : 1,
        "name" : "John"
    },
    "success" : true
}
```

####Default (collection)

```
@Controller
public class MyController{
    //...

    public void list() {
    result.use(ExtJSJson.class).from(users).serialize();
    }
}

```
Results in:

```
{
    "data" : [{
        "id" : 1,
        "name" : "John"
    }, {
        "id" : 2,
        "name" : "Mary"
    }],
    "success" : true
}
```

####With total param (collection)

```
@Controller
public class MyController{
    //...

    public void list() {
        result.use(ExtJSJson.class).total().from(users).serialize();
    }
}

```
Results in:

```
{
    "data" : [{
        "id" : 1,
        "name" : "John"
    }, {
        "id" : 2,
        "name" : "Mary"
        }],
    "success" : true,
    "total" : 2
}
```
