package com.optimizer;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {
    private static Map<String, Object> config;

    public static void loadConfig() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config/application.yml")) {
            if (inputStream != null) {
                config = yaml.load(inputStream);
                System.out.println("Configurações carregadas com sucesso!");
            } else {
                System.err.println("Arquivo application.yml não encontrado!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getConfig(String key) {
        if (config != null) {
            String[] keys = key.split("\\.");
            Map<String, Object> currentMap = config;
            for (int i = 0; i < keys.length - 1; i++) {
                Object value = currentMap.get(keys[i]);
                if (value instanceof Map) {
                    currentMap = (Map<String, Object>) value;
                } else {
                    return null;
                }
            }
            return currentMap.get(keys[keys.length - 1]);
        }
        return null;
    }
    
}
