package ch.hearc.heg.scl;

import ch.hearc.heg.scl.rmiObj.HelloImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            /* Création du registre RMI sur le port 1099 */
            Registry reg = LocateRegistry.createRegistry(1099);

            /* Création de l'objet distant */
            HelloImpl obj = new HelloImpl();

            /* Enregistrement de l'objet distant dans le registre */
            reg.rebind("HelloService", obj);

            // TODO enregistrer l'objet OpenWeather
            System.out.println("Serveur prêt");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}