package ch.hearc.heg.scl;

import ch.hearc.heg.scl.rmiObj.Hello;
import ch.hearc.heg.scl.rmiObj.IOpenWeather;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //String host = "157.26.104.116";
        String host = "localhost";
        int port = 1099;

        try {
            Registry reg = LocateRegistry.getRegistry(host, port);

            /* Récupération de l'objet distant */
            IOpenWeather owm = (IOpenWeather) reg.lookup("OpenWeatherMap");
            Scanner sc = new Scanner(System.in);
            sc.useLocale(Locale.US); // pour accepter le point décimal par défaut
            boolean running = true;
            while (running) {
                printMenu();
                int action = readInt(sc, "Votre choix : ", 0, 2);

                switch (action) {
                    case 1 -> {
                        System.out.println("Bonjour! Veuillez entrer des coordonnées GPS.");
                        double latitude = readDouble(sc, "Latitude  (-90..90)  : ", -90.0, 90.0);
                        owm.setLatitude(latitude);
                        double longitude = readDouble(sc, "Longitude (-180..180): ", -180.0, 180.0);
                        owm.setLongitude(longitude);

                        System.out.println(owm.getWeather());
                    }
                    case 2 -> {
                        for(String station : owm.getStations()) {
                            System.out.println(station);
                        }
                    }
                    case 0 -> running = false;
                }
            }

            System.out.println("Au revoir!");
            sc.close();
         } catch(RemoteException | NotBoundException e){
                throw new RuntimeException(e);
            }
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
}