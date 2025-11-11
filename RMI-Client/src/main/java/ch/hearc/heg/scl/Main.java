package ch.hearc.heg.scl;

import ch.hearc.heg.scl.rmiObj.Hello;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        String host = "157.26.104.116";
        int port = 1099;

        try {
            Registry reg = LocateRegistry.getRegistry(host, port);

            /* Récupération de l'objet distant */
            Hello obj = (Hello) reg.lookup("HelloService");

            /* Appel de la méthode distante */
            System.out.println(obj.sayHello());
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}