package ch.hearc.ig;

import ch.hearc.ig.business.Weather;
import ch.hearc.ig.business.WeatherStation;
import ch.hearc.ig.service.OWMManager;
import ch.hearc.ig.tools.HibernateUtil;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static OWMManager manager;

    public static void main(String[] args) {
        manager = new OWMManager();
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US); // pour accepter le point décimal par défaut

        boolean running = true;
        while (running) {
            printMenu();
            int action = readInt(sc, "Votre choix : ", 0, 2);

            switch (action) {
                case 1 -> {
                    double latitude  = readDouble(sc, "Latitude  (-90..90)  : ", -90.0, 90.0);
                    double longitude = readDouble(sc, "Longitude (-180..180): ", -180.0, 180.0);

                    // appeler la couche de service et récupérer l'objet
                    try {
                        WeatherStation station = manager.call(latitude, longitude);

                        // Affichage de la station et de la mesure qu'on vient d'appeler
                        showStation(station);
                        showWeather(station.getWeather().getLast());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    pressEnter(sc);
                }
                case 2 -> {
                    // Récupérer toutes les stations de la DB et les lister
                    List<WeatherStation> allWS = manager.getAllWeatherStations();
                    listAllStations(allWS);

                    // en choisir une (Scanner)
                    System.out.println("choisir une ville");
                    int choice = readInt(sc, "Indice [1.." + allWS.size() + "] : ", 1, allWS.size());

                    // Afficher la ville choisie et les mesures
                    showStation(allWS.get(choice - 1));
                    for (Weather weather: allWS.get(choice - 1).getWeather())
                        showWeather(weather);
                    pressEnter(sc);
                }
                case 0 -> running = false;
            }
        }

        System.out.println("Au revoir!");
        sc.close();
        HibernateUtil.shutdown();
    }

    // ----- helpers UI -----
    private static void printMenu() {
        System.out.println();
        System.out.println("============== OpenWeather (console) ==============");
        System.out.println("1) Entrer des coordonnées GPS et récupérer la météo");
        System.out.println("2) Choisir une station météo depuis la base de données");
        System.out.println("0) Quitter");
    }

    private static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) {}
            System.out.printf("** Valeur invalide. Entrez un entier entre %d et %d.%n", min, max);
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

    private static void pressEnter(Scanner sc) {
        System.out.print("Appuyez sur Entrée pour continuer...");
        sc.nextLine();
        System.out.println();
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
    private static void listAllStations(List<WeatherStation> stations) {
        System.out.println("\n==============================================");
        System.out.println("Stations météo disponibles");
        System.out.println("==============================================");

        if (stations == null || stations.isEmpty()) {
            System.out.println("  Aucune station trouvée en base. Utilisez d'abord l'option 1.");
            System.out.println("==============================================");
        } else {
        // Counter for the list index (1, 2, 3...)
        int index = 1;
        for (WeatherStation station : stations) {
            System.out.printf("%d - %s (%s)\n",
                    index,
                    station.getName(),
                    station.getCountry().getCountryCode().toUpperCase()
            );
            index++;
        }
        System.out.println("==============================================");
        }
    }
}