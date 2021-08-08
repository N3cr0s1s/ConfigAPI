package me.necrosis.configapi.reflect;

import me.necrosis.configapi.ConfigAPI;
import me.necrosis.configapi.annotations.load.AutoLoadFromConfig;
import me.necrosis.configapi.annotations.load.AutoLoadFromCustomConfig;
import me.necrosis.configapi.annotations.load.LoadFromConfig;
import me.necrosis.configapi.annotations.load.LoadFromCustomConfig;
import me.necrosis.configapi.annotations.object.MapObject;
import me.necrosis.configapi.exceptions.FieldValueNotExistException;
import me.necrosis.configapi.exceptions.FileNotExistException;
import me.necrosis.configapi.exceptions.ObjectCannotMappedException;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class AnnotationScan {

    private final ConfigAPI configAPI;

    public AnnotationScan(ConfigAPI configAPI){
        this.configAPI  =   configAPI;
    }

    public void scan(Object object) throws FileNotExistException, FieldValueNotExistException {
        this.scan(object.getClass(),object);
    }

    public void scan(Class<?> clazz,Object object) throws FieldValueNotExistException, FileNotExistException {
        this.objectMap(clazz,object);
        try {
            this.fieldMap(clazz,object,this.configAPI.getMainPluginGetter().getMainPlugin(clazz));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scan class and replace field values
     * @param clazz     Class to scan
     * @param object    Object
     */
    private void objectMap(Class<?> clazz,Object object) throws FileNotExistException, FieldValueNotExistException {
        if(!clazz.isAnnotationPresent(MapObject.class)) return;
        MapObject annotation = clazz.getAnnotation(MapObject.class);
        try {
            new ObjectMapperYml(object)
                    .setFieldValues(annotation.filePath(),annotation.fileName());
        } catch (ObjectCannotMappedException e) {
            e.getMessage();
        }
    }

    /**
     * Scan class and replace annotated fields
     * @param clazz     Class to scan
     * @param object    Object
     */
    private void fieldMap(Class<?> clazz, Object object, JavaPlugin plugin) throws FieldValueNotExistException, FileNotExistException {
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);

            this.overrideField(clazz,object,field,plugin);

        }
    }

    /**
     * Get all annotated field,and change value to saved value
     * @param clazz     Class to scan
     * @param object    Object
     * @param field     Annotated field
     * @return          Annotated field replaced with config value
     */
    private boolean overrideField(Class<?> clazz,Object object,Field field,JavaPlugin plugin) throws FieldValueNotExistException, FileNotExistException {
        if(field.isAnnotationPresent(AutoLoadFromConfig.class)){

            String name = field.getName();
            //  Get value
            Object value = this.configAPI.getConfigProvider().autoLoadFromConfig(name,plugin);
            try {
                field.set(object,value);
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }

        }else if(field.isAnnotationPresent(AutoLoadFromCustomConfig.class)){

            AutoLoadFromCustomConfig annotation = field.getAnnotation(AutoLoadFromCustomConfig.class);
            String name = field.getName();
            String path = annotation.filePath();
            String fileName = annotation.fileName();
            //  Get value
            Object value = this.configAPI.getConfigProvider().autoLoadFromCustomConfig(path,fileName,name,plugin);
            try{
                field.set(object,value);
                return true;
            }catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }

        }else if(field.isAnnotationPresent(LoadFromConfig.class)){

            LoadFromConfig annotation = field.getAnnotation(LoadFromConfig.class);
            String path = annotation.fieldPath();
            //  Get value
            Object value = this.configAPI.getConfigProvider().loadFromConfig(path,plugin);
            try{
                field.set(object,value);
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }

        }else if(field.isAnnotationPresent(LoadFromCustomConfig.class)){

            LoadFromCustomConfig annotation = field.getAnnotation(LoadFromCustomConfig.class);
            String filePath = annotation.filePath();
            String fileName = annotation.fileName();
            String path = annotation.fieldPath();
            //  Get value
            Object value = this.configAPI.getConfigProvider().loadFromCustomConfig(filePath,fileName,path,plugin);
            try{
                field.set(object,value);
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }
}
