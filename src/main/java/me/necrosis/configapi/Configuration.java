package me.necrosis.configapi;

import me.necrosis.configapi.exceptions.FieldValueNotExistException;
import me.necrosis.configapi.exceptions.FileNotExistException;

import java.lang.reflect.InvocationTargetException;

public abstract class Configuration {

    public Configuration(){
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
