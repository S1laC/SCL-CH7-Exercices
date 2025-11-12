package ch.hearc.heg.scl;

import ch.hearc.heg.scl.rmiObj.Hello;
import ch.hearc.heg.scl.rmiObj.IOpenWeather;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String host = "157.26.104.116";
        int port = 1099;

        try {
            Registry reg = LocateRegistry.getRegistry(host, port);

            /* Récupération de l'objet distant */
            IOpenWeather owm = (IOpenWeather) reg.lookup("OpenWeatherMap");
            Scanner sc = new Scanner(System.in);
            sc.useLocale(Locale.US); // pour accepter le point décimal par défaut

            System.out.println("Bonjour! Veuillez entrer des coordonnées GPS.");
            double latitude  = readDouble(sc, "Latitude  (-90..90)  : ", -90.0, 90.0);
            owm.setLatitude(latitude);
            double longitude = readDouble(sc, "Longitude (-180..180): ", -180.0, 180.0);
            owm.setLongitude(longitude);

            System.out.println(owm.getWeather());

        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static double readDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim().replace(',', '.'); // tolère la virgule
            try {
                double v = Double.parseDouble(raw);
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) {}
            System.out.printf("** Valeur invalide. Entrez un nombre dans l'intervalle [%.2f .. %.2f].%n", min, max);
        }
    }

}