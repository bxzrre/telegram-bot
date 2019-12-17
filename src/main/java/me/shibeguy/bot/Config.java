package me.shibeguy.bot;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {

    private final File configFile = new File("config.json");
    private JSONObject configObject;

    Config() {
        if (!configFile.exists()) {
            create();
            System.out.println("Couldn't find config so we created one for you, please configure the bot and rerun.");
            System.exit(0);
        }

        JSONObject object = read(configFile);

        if (object.has("bot-token") && object.has("urban-token")) {
            configObject = object;
        }
        else {
            create();
            System.err.println("A value was missing in the config file, regenerating...");
            System.exit(1);
        }
    }

    String getValue(String key) {
        return configObject == null ? null : configObject.get(key).toString();
    }

    void create() {
        try {
            Files.write(Paths.get(configFile.getPath()), new JSONObject()
                    .put("bot-token", "")
                    .put("urban-token", "")
                    .toString(4).getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private JSONObject read(File file) {
        JSONObject object = null;

        try {
            object = new JSONObject(new String(Files.readAllBytes(Paths.get(file.getPath())), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return object;
    }
}
