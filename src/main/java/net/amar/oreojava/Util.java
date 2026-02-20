package net.amar.oreojava;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {
    private static final String JSON_PATH = "src/main/resources/b.json";
    private static final Dotenv dotenv = Dotenv
            .configure()
            .directory("src/main/resources")
            .load();

    public static boolean addToModsBlacklist(String mod) {
        try {
            String json = Files.readString(Path.of(JSON_PATH));
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("mods");

            for (int i = 0; i < arr.length(); i++)
                if (arr.get(i).equals(mod))
                    return false;
            arr.put(mod);
            try (BufferedWriter r = new BufferedWriter(new FileWriter(JSON_PATH))) {
                r.write(obj.toString(4));
                Log.info("Wrote [%s] to mod json".formatted(mod));
                return true;
            }
        } catch (IOException e) {
            Log.error("Failed to write to mod json",e);
            return false;
        }
    }

    public static boolean removeFromModBlacklist(String mod) {
        try {
            String json = Files.readString(Path.of(JSON_PATH));
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("mods");
            boolean change = false;

            for (int i = 0; i < arr.length(); i++)
                if (arr.get(i).equals(mod)) {
                    arr.remove(i);
                    change = true;
                }
            if (change)
                try (BufferedWriter r = new BufferedWriter(new FileWriter(JSON_PATH))) {
                    r.write(obj.toString());
                    Log.info("Removed [%s] from mod json".formatted(mod));
                    return true;
                }
            else return false;
        } catch (IOException e) {
            Log.error("Failed to remove from mod json",e);
            return false;
        }
    }

    public static JSONArray getBlacklistedMods() {
        try {
            String json = Files.readString(Path.of(JSON_PATH));
            return new JSONObject(json).getJSONArray("mods");
        } catch (IOException e) {
            Log.error("Failed to get mods json array",e);
            return null;
        }
    }

    public static String botToken() {
        return dotenv.get("TOKEN");
    }

    public static String serverId() {
        return dotenv.get("GID");
    }

    public static String serverId2() {
        return dotenv.get("GID2");
    }

    public static String ownerId() {
        return dotenv.get("OWNER_ID");
    }
}
