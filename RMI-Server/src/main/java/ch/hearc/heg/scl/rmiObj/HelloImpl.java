package ch.hearc.heg.scl.rmiObj;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/* UnicastRemoteObject est une classe abstraite qui permet de créer des objets distants */
public class HelloImpl extends UnicastRemoteObject implements Hello {
    private int counter;
    public HelloImpl() throws RemoteException {
        counter = 0;
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Matthieu tu me brises les ovaires 🤌🤌🤌🤌🤌🤌🤌🤌🤌!";
    }

    @Override
    public String giveDate() throws RemoteException {

        this.counter++;
        return "";
    }

    @Override
    public String countDate() throws RemoteException {
        return "La date a été demandée " + counter + " fois.";
    }
}
