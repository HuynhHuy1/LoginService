package org.example.src.rmi;

import org.example.ClientHandler;
import org.example.controller.UserController;
import org.example.src.Model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServiceImpl extends UnicastRemoteObject implements RMIServiceInterface {
    ClientHandler clientHandler;

    public RMIServiceImpl() throws RemoteException {
        this.clientHandler = new ClientHandler();
    }

    @Override
    public String getToken()  throws RemoteException {
        // String rs = clientHandler.generateToken(username);
        System.out.println("Hello");
        // System.out.println(rs);
        return "";
    }

    @Override
    public String getUserNameFromToken(String token) throws RemoteException {
        return clientHandler.extractUsername(token);
    }

    @Override
    public User getUserById(int id) throws RemoteException {
        return null;
    }

    @Override
    public List<User> getListUserByListId(List<Integer> listId) throws RemoteException {
        UserController userController = new UserController();
        System.out.println("hello");
        List<User> listUser = userController.getUserChatedById(listId);
        System.out.println("User Name:" + listUser.get(0).getUsername());
        return listUser;
    }
}
