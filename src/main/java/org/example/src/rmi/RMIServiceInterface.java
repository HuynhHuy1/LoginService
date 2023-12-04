package org.example.src.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.example.src.Model.User;

public interface RMIServiceInterface extends Remote {
    public String getToken() throws RemoteException;

    public String getUserNameFromToken(String token) throws RemoteException;

    public User getUserById(int id) throws RemoteException;

    public List<User> getListUserByListId(List<Integer> listId) throws RemoteException;
}
