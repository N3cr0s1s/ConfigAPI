package me.necrosis.configapi.test;

import me.necrosis.configapi.Configuration;
import me.necrosis.configapi.annotations.load.LoadFromConfig;
import me.necrosis.configapi.annotations.object.MapObject;

@MapObject(filePath = "uha",fileName = "mappedObject")
public class TestClass2 extends Configuration {

    private String teszt = "";

    private int haha = 26;

    private int asde = 2;

}
