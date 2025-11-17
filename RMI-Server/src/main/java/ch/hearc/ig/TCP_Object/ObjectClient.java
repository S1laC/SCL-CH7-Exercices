package ch.hearc.ig.TCP_Object;

import ch.hearc.ig.business.Weather;
import ch.hearc.ig.business.WeatherStation;

import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class ObjectClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);

            InputStream is = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);

            Scanner sc = new Scanner(System.in);
            sc.useLocale(Locale.US); // pour accepter le point décimal par défaut

            System.out.println("Bonjour! Veuillez entrer des coordonnées GPS.");
            double latitude  = readDouble(sc, "Latitude  (-90..90)  : ", -90.0, 90.0);
            double longitude = readDouble(sc, "Longitude (-180..180): ", -180.0, 180.0);

            Coordinates coordinates = new Coordinates(latitude, longitude);
            out.writeObject(coordinates);

            WeatherStation station = (WeatherStation) in.readObject();
            System.out.println("========= Réponse du serveur =========");
            // Affichage de la station et de la mesure qu'on vient d'appeler
            showStation(station);
            showWeather(station.getWeather().getLast());

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
    // ----- affichages longs  -----
    private static void showStation(WeatherStation station) {
        System.out.println("\n==============================================");
        System.out.printf("  %-15s : %s\n", "Nom", station.getName());
        System.out.printf("  %-15s : %d\n", "ID OpenWeatherMap", station.getIdOWM());
        System.out.println("==============================================");

        String countryInfo = station.getCountry().getCountryName() +
                " (" + station.getCountry().getCountryCode().toUpperCase() + ")";
        System.out.printf("  %-15s : %s\n", "Pays", countryInfo);
        System.out.printf("  %-15s : %.4f\n", "Latitude", station.getLatitude());
        System.out.printf("  %-15s : %.4f\n", "Longitude", station.getLongitude());

        System.out.println("==============================================");
    }
    private static void showWeather(Weather weather) {

        System.out.println("\n==============================================");
        System.out.println("Relevé météo " + weather.getDt());
        System.out.println("==============================================");

        // Core Conditions
        System.out.printf("  %-15s : %s\n", "Description", weather.getDescription());
        System.out.printf("  %-15s : %.1f °C\n", "Température", weather.getTemp());

        // Atmospheric Details
        System.out.println("----------------------------------------------");
        System.out.printf("  %-15s : %d hPa\n", "Pression", weather.getPressure());
        System.out.printf("  %-15s : %d %%\n", "Humidité", weather.getHumidity());
        System.out.printf("  %-15s : %d %%\n", "Couverture nuageuse", weather.getCloudiness());

        // Wind and Visibility
        System.out.println("----------------------------------------------");
        System.out.printf("  %-15s : %.1f m/s\n", "Vitesse du vent", weather.getWindSpeed());
        System.out.printf("  %-15s : %d degrees\n", "Direction du vent", weather.getWindDirection());
        System.out.printf("  %-15s : %d meters\n", "Visibilité", weather.getVisibility());

        System.out.println("----------------------------------------------");
        System.out.printf("  %-15s : %.2f mm\n", "Pluie (dernière heure)", weather.getRain());

        System.out.println("==============================================");
    }


}
