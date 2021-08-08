package me.necrosis.configapi.reflect;

import me.necrosis.configapi.ConfigAPI;
import me.necrosis.configapi.annotations.object.ExcludeMapper;
import me.necrosis.configapi.exceptions.FieldValueNotExistException;
import me.necrosis.configapi.exceptions.FileNotExistException;
import me.necrosis.configapi.exceptions.ObjectCannotMappedException;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapperYml {

    private Object mapObject;

    public ObjectMapperYml(Object mapObject){
        this.mapObject  =   mapObject;
    }

    /**
     * Create yml config section with object field values
     * @return  Yml section as List[String]
     */
    public List<String> getMappedObject() throws IllegalAccessException {
        List<String> mappedObject = new ArrayList<>();
        mappedObject.add(this.mapObject.getClass().getSimpleName()+":");
        mappedObject.add("  __class: " + this.mapObject.getClass().getPackage());
        for(Field field : this.mapObject.getClass().getDeclaredFields()){
            field.setAccessible(true);
            mappedObject.add("  " + field.getName() + ": " + field.get(this.mapObject));
        }
        return mappedObject;
    }

    /**
     * Set object field values from FileConfiguration
     * @param filePath  Custom config file path
     * @param fileName  Custom config file name
     * @return          Mapped object
     */
    public ObjectMapperYml setFieldValues(String filePath,String fileName) throws ObjectCannotMappedException, FileNotExistException {
        for(Field field : this.mapObject.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(field.isAnnotationPresent(ExcludeMapper.class)) continue;

            try {
                field.set(
                        this.mapObject,
                        ConfigAPI.getConfigAPI().getConfigProvider()
                                .autoLoadFromCustomConfig(filePath, fileName, field.getName(),ConfigAPI.getConfigAPI().getMainPluginGetter().getMainPlugin(this.mapObject.getClass()))
                );
            }catch (FieldValueNotExistException e) {
                throw new ObjectCannotMappedException("Object cannot mapped,because file does not exist,-or config file is corrupted.");
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return this;
    }
}
