# Config API
## First of all:
To use it, you need to create a static method that returns the plugin as a value.
```java
public final class ExamplePlugin extends JavaPlugin {

    private static ExamplePlugin examplePlugin;

    @Override
    public void onEnable() {
        this.examplePlugin = this;
    }

    private static ExamplePlugin getInstance(){
        return this.examplePlugin;
    }
}
```
And that's it, you ready to use.

## Config class
You need to extend class with `Configuration` to use annotations.
```java
public class TestClass extends Configuration{
}
```
or you can simple copy-paste `Configuration` methods to your class
```java
public class TestClass{

    public TestClass(){
        try {
            ConfigAPI.getConfigAPI().getAnnotationScan().scan(this.getClass(),this);
        } catch (FieldValueNotExistException | FileNotExistException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(){
        ConfigAPI.getConfigAPI().getSaveProvider().saveConfig(this);
    }
}
```

## Annotations

## AutoLoadFromConfig
This annotation will automatically look up the field name in `config.yml`, and replace the field value with founded value.

### Example
Code
```java
@AutoLoadFromConfig
private String example;
```
Yml ( .../plugins/`pluginName`/config.yml )
example yml: ( .../plugins/`ExampePlugin`/config.yml )
```yml
example-section:
	example: "Test string!"
```
Output
String `example` write to console
```
Test string!
```

## AutoLoadFromCustomConfig

| params | description |
|--------|-------------|
| filePath | This is the path in plugin data folder |
| fileName | This is the `.yml` name in the filePath |

This annotation will automatically look up the field name in `plugin.dataFolder()/filepath/fileName.yml`, and replace the field value with founded value.
### Example
Code
```java
@AutoLoadFromCustomConfig(filePath = "exampleFolder",fileName = "example")
private String example;
```
Yml ( .../plugins/`pluginName`/`filePath`/`fileName`.yml )
example yml: ( .../plugins/`ExampePlugin`/`exampleFolder`/`example`.yml )
```yml
example-section:
	example: "Test string!"
```
Output
String `example` write to console
```
Test string!
```

## LoadFromConfig
| params | description |
|--------|-------------|
| fieldPath | This is the value path in config.yml |
This annotation is get the fieldPath value from `config.yml`

### Example
Code
```java
@LoadFromConfig( fieldPath = "example-section.example")
public String exampleString;
```
Yml ( .../plugins/`pluginName`/config.yml )
example yml: ( .../plugins/`ExampePlugin`/config.yml )
```yml
example-section:
	example: "Test string!"
```
Output
String `exampleString` write to console
```
Test string!
```
## LoadFromCustomConfig

| params | description |
|--------|-------------|
| filePath | This is the path in plugin data folder |
| fileName | This is the `.yml` name in the filePath |
| fieldPath| This is the value path in custom config file |

This annotation will automatically look up the field name in `plugin.dataFolder()/filepath/fileName.yml`, and replace the field value with founded value.
### Example
Code
```java
@LoadFromCustomConfig(filePath = "exampleFolder",fileName = "example",fieldPath = "example-section.example")
private String exampleString;
```
Yml ( .../plugins/`pluginName`/`filePath`/`fileName`.yml )
example yml: ( .../plugins/`ExampePlugin`/`exampleFolder`/`example`.yml )
```yml
example-section:
	example: "Test string!"
```
Output
String `exampleString` write to console
```
Test string!
```

## All annotation
```java
public class TestClass extends Configuration{

	@AutoLoadFromConfig
	private String testString;

	@AutoLoadFromCustomConfig(filePath = "",fileName = "test")
	private int testInt;
	
	@LoadFromConfig(fieldPath = "tests.testDouble")
	private double testDouble;
	
	@LoadFromCustomConfig(filePath = "testFolder",fileName = "testFile",fieldPath = "tests.testFloat")
	private float testFloat;
}
```

## Object mapper

```java
@MapObject(filePath = "testObjects",fileName = "mappedObject")
public class TestClass extends Configuration{
    private String testString;
    private double testDouble;
}
```

## Save
If you use `Configuration` extend
```java
public class TestClass extends Configuration{
    {
        saveConfig();
    }
}
```

without

```java
public class TestClass{

    public TestClass(){
        try {
            ConfigAPI.getConfigAPI().getAnnotationScan().scan(this.getClass(),this);
        } catch (FieldValueNotExistException | FileNotExistException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(){
        ConfigAPI.getConfigAPI().getSaveProvider().saveConfig(this);
    }

    {
        saveConfig();
    }
}
```