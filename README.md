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