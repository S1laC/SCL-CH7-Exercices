package ch.hearc.ig.TCP_Object;

import ch.hearc.ig.business.WeatherStation;
import ch.hearc.ig.service.OWMManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ObjectServer {
    public static OWMManager manager;

    public static void main(String[] args) {
        try {
            manager = new OWMManager();
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true) {
                System.out.println("Serveur en attente de connexion...");

                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion établie avec le client.");

                InputStream is = clientSocket.getInputStream();
                ObjectInputStream in = new ObjectInputStream(is);

                OutputStream os = clientSocket.getOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                Coordinates receivedObject = (Coordinates) in.readObject();
                System.out.println("Objet reçu du client : " + receivedObject);

                // appeler la couche de service et récupérer l'objet
                WeatherStation station = manager.call(receivedObject.getLatitude(), receivedObject.getLongitude());
                out.writeObject(station);
                System.out.println("Données météo envoyées au client. Fermeture de la connexion.");
                clientSocket.close();
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
