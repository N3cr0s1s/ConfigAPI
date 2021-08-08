package me.necrosis.configapi.reflect;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainPluginGetter {

    public MainPluginGetter(){

    }

    public JavaPlugin getMainPlugin(Class<?> clazz_) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int times = 0;
        for(int i = 0; i < clazz_.getPackage().getName().length(); i++){
            if(clazz_.getPackage().getName().charAt(i) == '.'){
                times++;
            }
        }
        Object javaPlugin = null;
        for(int i = 0; i < times; i++){
            try {
                Reflections reflections = new Reflections(clazz_.getPackage().getName().split(".", 10)[times], new SubTypesScanner(false), this.getClass().getClassLoader());
                for (Class<?> clazz : reflections.getSubTypesOf(JavaPlugin.class)) {
                    Method method = clazz.getDeclaredMethod("getInstance");
                    method.setAccessible(true);
                    javaPlugin = method.invoke(null);
                    break;
                }
            }catch (Exception e){
                times= times -1;
                continue;
            }
        }
        return (JavaPlugin) javaPlugin;
    }
}
