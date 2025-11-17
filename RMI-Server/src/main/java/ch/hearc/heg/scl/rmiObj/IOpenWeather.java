package ch.hearc.heg.scl.rmiObj;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOpenWeather extends Remote {
    void setLatitude(Double latitude) throws RemoteException;
    void setLongitude(Double longitude)throws RemoteException;
    String getWeather() throws RemoteException;
    List<String> getStations() throws RemoteException;
}
