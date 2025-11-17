package ch.hearc.heg.scl.rmiObj;

import ch.hearc.ig.business.WeatherStation;
import ch.hearc.ig.service.OWMManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class OpenWeather extends UnicastRemoteObject implements IOpenWeather {

    private Double latitude;
    private Double longitude;
    private static OWMManager manager;

    public OpenWeather() throws RemoteException {
        super();
    }

    @Override
    public void setLatitude(Double latitude) throws RemoteException {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(Double longitude) throws RemoteException {
        this.longitude = longitude;
    }

    @Override
    public String getWeather() throws RemoteException {
        if (latitude == null || longitude == null) {
            return "Vous devez d'abord définir des coordonnées GPS pour obtenir des données météo";
        }

        if (manager == null) {
            manager = new OWMManager();
        }

        try {
            WeatherStation station = manager.call(latitude, longitude);
            return station.getName() + ":" + station.getWeather();
        } catch (Exception e) {
            throw new RemoteException("Erreur lors de l'appel OpenWeather", e);
        }
    }

    @Override
    public List<String> getStations() throws RemoteException {
        return List.of();
    }
}
