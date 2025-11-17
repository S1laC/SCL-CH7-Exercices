package ch.hearc.ig.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Env {
    private static String filename = ".env";
    private static Properties props = null;
    private static InputStream input = null;

    private Env() {
        // Private constructor to prevent instantiation
    }

    public static void load() {
        if (props == null) {
            props = new Properties();

            try {
                input = Env.class.getClassLoader().getResourceAsStream(filename);
                if (input == null) {
                    Log.warn("Désolé, le fichier " + filename + " est introuvable.");
                    return;
                }

                props.load(input);
            } catch (Exception e) {
                Log.error("Erreur lors du chargement du fichier " + filename, e);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        Log.error("Erreur lors de la fermeture du fichier " + filename, e);
                    }
                }
            }
        }
    }

    public static String get(String key) {
        if (props == null) {
            load();
        }

        if (props.containsKey(key)) {
            return props.getProperty(key);
        } else {
            Log.warn("La clé " + key + " n'existe pas dans le fichier " + filename);
        }

        return null;
    }
}
