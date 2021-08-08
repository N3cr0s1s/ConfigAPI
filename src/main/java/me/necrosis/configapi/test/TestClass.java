package me.necrosis.configapi.test;

import me.necrosis.configapi.Configuration;
import me.necrosis.configapi.annotations.load.AutoLoadFromConfig;
import me.necrosis.configapi.annotations.load.AutoLoadFromCustomConfig;
import me.necrosis.configapi.annotations.load.LoadFromConfig;
import me.necrosis.configapi.annotations.load.LoadFromCustomConfig;
import me.necrosis.configapi.annotations.object.ExcludeMapper;
import me.necrosis.configapi.annotations.object.MapObject;

@MapObject(fileName = "sanyi")
public class TestClass extends Configuration{

    @LoadFromCustomConfig(filePath = "aha",fileName = "lajos",fieldPath = "asd.freak")
    public String teszt;

    public int intteszt;
    public double doubleis;

    @ExcludeMapper
    public String eztKihagyom = "Marad az erteke";

    public void mentsTeKutya(){
        teszt       =   "ez a mentett ertek!!!!!!";
        intteszt    =   28;
        doubleis    =   6.32;
        eztKihagyom =   "ezt meg ne mentes";

        saveConfig();


    }

    public String getFields(){
        return "teszt: " + teszt + ", intteszt: " + intteszt + ", doubleis: " + doubleis + ", eztKihagyom: " + eztKihagyom;
    }
}
