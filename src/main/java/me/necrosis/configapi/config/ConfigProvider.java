package me.necrosis.configapi.config;

import me.necrosis.configapi.ConfigAPI;
import me.necrosis.configapi.exceptions.FieldValueNotExistException;
import me.necrosis.configapi.exceptions.FileNotExistException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

public class ConfigProvider {

    private final ConfigAPI configAPI;

    public ConfigProvider(ConfigAPI configAPI){
        this.configAPI  =   configAPI;
    }

    /**
     * Return field name value
     * @param name  Field name
     * @return      Field value in config.yml file
     * @throws FieldValueNotExistException
     */
    public Object autoLoadFromConfig(String name,JavaPlugin plugin) throws FieldValueNotExistException {
        JavaPlugin javaPlugin = plugin;
        FileConfiguration config = javaPlugin.getConfig();
        for(String string:config.getConfigurationSection("").getKeys(true)){
            if(string.endsWith(name)){
                return config.get(string);
            }
        }
        throw new FieldValueNotExistException(name + " field is not exist in config.yml");
    }

    /**
     * Return field name value
     * @param filePath  Config file path
     * @param fileName  Config file name
     * @param name      Field name
     * @return          Field value in {pluginDataFolder}/filepath/fileName.yml
     * @throws FieldValueNotExistException
     * @throws FileNotExistException
     */
    public Object autoLoadFromCustomConfig(String filePath,String fileName,String name,JavaPlugin plugin) throws FieldValueNotExistException, FileNotExistException {
        JavaPlugin javaPlugin = plugin;
        FileConfiguration config = null;
        try {
            config = YamlConfiguration.loadConfiguration(new File(javaPlugin.getDataFolder() + "/" + filePath + "/" + fileName + ".yml"));
        }catch (Exception e){
            throw new FileNotExistException("File not exist. File location: " + javaPlugin.getDataFolder() + "/" + filePath + "/" + fileName + ".yml" );
        }
        for(String string:config.getConfigurationSection("").getKeys(true)){
            if(string.endsWith(name)){
                return config.get(string);
            }
        }
        throw new FieldValueNotExistException(name + " field is not exist in " +  javaPlugin.getDataFolder() + "/" + filePath + "/" + fileName + ".yml");
    }

    /**
     * Get field value in config.yml with path
     * @param path  Path to value
     * @return      Annotated field value
     * @throws FieldValueNotExistException
     */
    public Object loadFromConfig(String path,JavaPlugin plugin) throws FieldValueNotExistException {
        JavaPlugin javaPlugin = plugin;
        try{
            return javaPlugin.getConfig().get(path);
        }catch (Exception e){
            throw new FieldValueNotExistException(path + " not exist in config.yml");
        }
    }

    /**
     * Get field value in custom config file
     * @param filePath  Config file path
     * @param fileName  Config file name
     * @param path      Object path in custom yml
     * @return          Annotated field value
     * @throws FileNotExistException
     * @throws FieldValueNotExistException
     */
    public Object loadFromCustomConfig(String filePath,String fileName,String path,JavaPlugin plugin) throws FileNotExistException, FieldValueNotExistException {
        JavaPlugin javaPlugin = plugin;
        FileConfiguration config = null;
        try {
            config = YamlConfiguration.loadConfiguration(new File(javaPlugin.getDataFolder() + "/" + filePath + "/" + fileName + ".yml"));
        }catch (Exception e){
            throw new FileNotExistException("File not exist. File location: " + javaPlugin.getDataFolder() + "/" + filePath + "/" + fileName + ".yml" );
        }
        try{
            return config.get(path);
        } catch (Exception e){
            throw new FieldValueNotExistException(path + " not exist in /" + filePath + "/" + fileName + ".yml");
        }
    }

    /**
     * Get custom config file
     * @param filePath  Config file path
     * @param fileName  Config file name
     * @return          Custom config file FileConfiguration
     */
    public FileConfiguration getCustomConfig(String filePath,String fileName,JavaPlugin plugin){
        JavaPlugin javaPlugin = plugin;
        FileConfiguration config = null;
        try {
            config = YamlConfiguration.loadConfiguration(new File(javaPlugin.getDataFolder() + "/" + filePath + "/" + fileName + ".yml"));
        }catch (Exception e){
            File file = new File(javaPlugin.getDataFolder() + "/" + filePath + "/" + fileName + ".yml");
            config  = YamlConfiguration.loadConfiguration(file);
        }
        return config;
    }

}
