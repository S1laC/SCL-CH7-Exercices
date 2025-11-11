package ch.hearc.heg.scl.rmiObj;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    /* Le throws RemoteException est obligatoire et permet de gérer les exceptions liées à la communication réseau */
    String sayHello() throws RemoteException;

    String giveDate() throws RemoteException;

    String countDate() throws RemoteException;

}
