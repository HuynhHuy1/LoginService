package org.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.example.src.rmi.RMIServiceImpl;
import org.example.src.rmi.RMIServiceInterface;

public class Server {

    public static void main(String[] args) throws IOException {
        Thread thread = new Thread() {
            public void run() {
                try {
                    LocateRegistry.createRegistry(1099);
                    RMIServiceInterface rmiService = new RMIServiceImpl();
                    Naming.rebind("RMIService", rmiService);
                    System.out.println("Server running...");
                    System.out.println("Thread Running");
                } catch (MalformedURLException | RemoteException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        thread.start();
        try {
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("Server is running");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connect....");
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // try {
        //     RMIServiceInterface rmiService = new RMIServiceImpl();
        //     LocateRegistry.createRegistry(1100);

        //     Naming.rebind("rmi://localhost:1100/RMIService", rmiService);
        //     System.out.println("Server running...");
        //     System.out.println("Thread Running");
        // } catch (MalformedURLException | RemoteException e) {
        //     System.out.println(e.getMessage());
        // }

    }
}
