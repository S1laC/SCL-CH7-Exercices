package ch.hearc.heg.scl.rmiObj;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/* UnicastRemoteObject est une classe abstraite qui permet de créer des objets distants */
public class HelloImpl extends UnicastRemoteObject implements Hello {
    public HelloImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Matthieu tu me brises les ovaires 🤌🤌🤌🤌🤌🤌🤌🤌🤌!";
    }

    @Override
    public String giveDate() throws RemoteException {
        return "";
    }

    @Override
    public String countDate() throws RemoteException {
        return "";
    }
}
