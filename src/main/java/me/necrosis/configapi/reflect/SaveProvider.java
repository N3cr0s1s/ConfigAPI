package me.necrosis.configapi.reflect;

import me.necrosis.configapi.ConfigAPI;
import me.necrosis.configapi.annotations.load.AutoLoadFromConfig;
import me.necrosis.configapi.annotations.load.AutoLoadFromCustomConfig;
import me.necrosis.configapi.annotations.load.LoadFromConfig;
import me.necrosis.configapi.annotations.load.LoadFromCustomConfig;
import me.necrosis.configapi.annotations.object.ExcludeMapper;
import me.necrosis.configapi.annotations.object.MapObject;
import me.necrosis.configapi.exceptions.FileNotExistException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SaveProvider {

    private ConfigAPI configAPI;

    public SaveProvider(ConfigAPI configAPI){
        this.configAPI  =   configAPI;
    }

    /**
     * Save class to config
     * @param object    Class to save
     */
    public void saveConfig(Object object) {
        File file = null;
        FileConfiguration config = null;

        /**
         * If object annotated with MapObject
         */
        if(object.getClass().isAnnotationPresent(MapObject.class)){
            MapObject annotation = object.getClass().getAnnotation(MapObject.class);
            file    =   new File(getPlugin(object).getDataFolder() + "/" + annotation.filePath() + "/" + annotation.fileName() + ".yml");
            config  =   YamlConfiguration.loadConfiguration(file);

            for(Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.isAnnotationPresent(ExcludeMapper.class)) continue;
                try {
                    config.set(object.getClass().getSimpleName() + "." + field.getName(),field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            this.saveCustomYml(file,config);
        }

        for(Field field : object.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(field.isAnnotationPresent(ExcludeMapper.class)) continue;
            if(field.isAnnotationPresent(AutoLoadFromConfig.class)){

                String name = field.getName();
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                /**
                 *      SAVE
                 */
                file    =   new File(getPlugin(object).getDataFolder() + "/" +"config.yml");
                config  =   YamlConfiguration.loadConfiguration(file);
                for(String configurationSection : config.getConfigurationSection("").getKeys(true)){
                    if(configurationSection.endsWith(name)){
                        config.set(configurationSection,value);
                        this.saveCustomYml(file,config);
                    }
                }

            }else if(field.isAnnotationPresent(AutoLoadFromCustomConfig.class)){

                AutoLoadFromCustomConfig annotation = field.getAnnotation(AutoLoadFromCustomConfig.class);
                String name = field.getName();
                String path = annotation.filePath();
                String fileName = annotation.fileName();
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                /**
                 *      SAVE
                 */
                file    =   new File(getPlugin(object).getDataFolder() + "/" + annotation.filePath() + "/" + annotation.fileName() + ".yml");
                config  =   YamlConfiguration.loadConfiguration(file);
                for(String configurationSection : config.getConfigurationSection("").getKeys(true)){
                    if(configurationSection.endsWith(name)){
                        config.set(configurationSection,value);
                        this.saveCustomYml(file,config);
                    }
                }

            }else if(field.isAnnotationPresent(LoadFromConfig.class)){

                LoadFromConfig annotation = field.getAnnotation(LoadFromConfig.class);
                String path = annotation.fieldPath();
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                /**
                 *      SAVE
                 */
                file    =   new File(getPlugin(object).getDataFolder() + "/" +"config.yml");
                config  =   YamlConfiguration.loadConfiguration(file);
                config.set(path,value);
                this.saveCustomYml(file,config);

            }else if(field.isAnnotationPresent(LoadFromCustomConfig.class)){

                LoadFromCustomConfig annotation = field.getAnnotation(LoadFromCustomConfig.class);
                String filePath = annotation.filePath();
                String fileName = annotation.fileName();
                String path = annotation.fieldPath();
                Object value = null;
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                /**
                 *      SAVE
                 */
                file    =   new File(getPlugin(object).getDataFolder() + "/" + annotation.filePath() + "/" + annotation.fileName() + ".yml");
                config  =   YamlConfiguration.loadConfiguration(file);
                config.set(path,value);
                this.saveCustomYml(file,config);

            }
        }
    }

    public void saveCustomYml(File ymlFile,FileConfiguration ymlConfig) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JavaPlugin getPlugin(Object object){
        try {
            return this.configAPI.getMainPluginGetter().getMainPlugin(object.getClass());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
