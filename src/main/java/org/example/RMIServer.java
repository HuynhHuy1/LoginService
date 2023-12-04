package org.example;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.example.src.rmi.RMIServiceImpl;
import org.example.src.rmi.RMIServiceInterface;

public class RMIServer {
    public static void main(String[] args) {
        try {
            RMIServiceInterface testRMI = new RMIServiceImpl();
            LocateRegistry.createRegistry(1000);

            Naming.rebind("rmi://192.168.1.12:1000/Token", testRMI);
            System.out.println("Server running...");
        } catch (RemoteException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
