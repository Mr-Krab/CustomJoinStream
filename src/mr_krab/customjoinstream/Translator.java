package mr_krab.customjoinstream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Translator {
	Plugin p;
	 
    Map<String,String> tran = new HashMap<String, String>();
 
    public Translator(Plugin plugin, String locale){
        if(locale.contains(File.separator)) throw new RuntimeException("locale name is not valid");
        if (locale.equals("system")) locale = System.getProperty("user.language");
        InputStream is = null;
        File file = new File(plugin.getLangFolder(),locale+".yml");
        if(!file.exists()){
            is = plugin.getResource(locale+".yml");
            if (is==null){
                is = plugin.getResource("en.yml");
            }
        } else {
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException ignored) {}
        }
        if(is!=null){
            try {
                Yaml yaml = new Yaml();
                Map<String, Object> map = (Map<String, Object>) yaml.load(is);
                Map<String,Object> vals = (Map<String, Object>) map.get("locale");
                for(Map.Entry<String,Object> e:vals.entrySet()){
                    tran.put(e.getKey(),e.getValue().toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    public String translate(String key, Object... values){
        if(!tran.containsKey(key))return key;
        return String.format(tran.get(key),values);
    }
 
    public String translate(String key){
        if(!tran.containsKey(key))return key;
        return tran.get(key);
    }
}