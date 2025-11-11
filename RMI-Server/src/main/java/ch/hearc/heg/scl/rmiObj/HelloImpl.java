package ch.hearc.heg.scl.rmiObj;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
        return formattedNow;
    }

    @Override
    public String countDate() throws RemoteException {
        return "";
    }

}
