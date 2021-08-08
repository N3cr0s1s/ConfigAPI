package me.necrosis.configapi;

import me.necrosis.configapi.config.ConfigProvider;
import me.necrosis.configapi.reflect.AnnotationScan;
import me.necrosis.configapi.reflect.MainPluginGetter;
import me.necrosis.configapi.reflect.SaveProvider;
import me.necrosis.configapi.test.TestClass;
import me.necrosis.configapi.test.TestClass2;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigAPI extends JavaPlugin {

    private static ConfigAPI configAPI;

    private final ConfigProvider configProvider = new ConfigProvider(this);
    private final AnnotationScan annotationScan = new AnnotationScan(this);
    private final SaveProvider   saveProvider   = new SaveProvider(this);
    private final MainPluginGetter mainPluginGetter = new MainPluginGetter();
    private TestClass testClass;

    @Override
    public void onEnable() {
        this.configAPI = this;

        testClass = new TestClass();
        getLogger().info( testClass.getFields());
        testClass.mentsTeKutya();
        getLogger().info( testClass.getFields());

        new TestClass2().saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ConfigAPI getConfigAPI() {
        return configAPI;
    }

    private static ConfigAPI getInstance(){
        return configAPI;
    }

    public ConfigProvider getConfigProvider() {
        return configProvider;
    }

    public AnnotationScan getAnnotationScan() {
        return annotationScan;
    }

    public SaveProvider getSaveProvider() {
        return saveProvider;
    }

    public MainPluginGetter getMainPluginGetter() {
        return mainPluginGetter;
    }
}
