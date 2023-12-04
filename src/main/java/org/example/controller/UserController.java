package org.example.controller;

import org.example.ClientHandler;
import org.example.model.ResponseObject;
import org.example.repository.UserRepository;
import org.example.src.Model.User;
import org.example.utils.ResponseUtils;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    UserRepository userRepository;

    ResponseUtils responseUtils;
    private Socket socket;

    public UserController(Socket socket) {
        this.socket = socket;
        userRepository = new UserRepository();
        responseUtils = new ResponseUtils(socket);
    }
    public UserController() {
        userRepository = new UserRepository();
        responseUtils = new ResponseUtils(socket);
    }

    ClientHandler clientHandler = new ClientHandler();

    public void logInUser(String username, String password) {
        User user = userRepository.findUserByNameAndPassword(username, password);
        ResponseObject responseObject = new ResponseObject();

        if (user != null) {
            if (!user.getUsername().isEmpty()) {
                System.out.println(user.getUsername());
                responseObject.setStatus(200);
                responseObject.setData(user.getId() + "");
                responseUtils.responseClient(responseObject);
            } else {
                responseObject.setStatus(401);
                responseObject.setData("Login Failed");
                responseUtils.responseClient(responseObject);
            }
        }else {
            responseObject.setStatus(401);
            responseObject.setData("User not found in Database");
            responseUtils.responseClient(responseObject);
        }
    }

    public boolean signUpUser(String email, String username, String password, String phone) {
        User newUser = userRepository.addUser(username, email, password, phone);
        ResponseObject responseObject = new ResponseObject();

        if (!newUser.getUsername().isEmpty()) {
            responseObject.setStatus(200);
            responseObject.setData("Sign Up Success");
            responseUtils.responseClient(responseObject);
            return true;
        } else {
            responseObject.setStatus(401);
            responseObject.setData("Sign up Failed");
            responseUtils.responseClient(responseObject);
            return false;
        }
    }

    // Handle RMI
    public List<User> getUserChatedById(List<Integer> listUserId){
        List<User> listUser = new ArrayList<>();
        for (Integer userId : listUserId) {
            System.out.println(userId);
            User user = this.userRepository.getUserById(userId);
            System.out.println(user.getUsername());
            listUser.add(user);
        }
        return listUser;
    }
}
