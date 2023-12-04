package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.controller.UserController;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    private ClientHandler clientHandler;
    private static final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final HashMap<String, String> users = new HashMap<>();

    public ClientHandler(Socket clientSocket, ClientHandler clientHandler) {
        this.clientSocket = clientSocket;
        this.clientHandler = clientHandler;
    }

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public ClientHandler() {
        this.clientSocket = new Socket();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            UserController userController = new UserController(clientSocket);

            while (true) {
                String request;
                if ((request = in.readLine()) != null) {
                    System.out.println(request);
                    Gson gson = new Gson();

                    Type type = new TypeToken<Map<String, Object>>() {
                    }.getType();
                    Map<String, Object> jsonMap = gson.fromJson(request, type);
                    String method = (String) jsonMap.get("method");
                    System.out.println(method);
                    switch (method) {
                        case "signUpUser":
                            Map<String, String> dataSignUp = (Map<String, String>) jsonMap.get("data");
                            String email = dataSignUp.get("email");
                            String username = dataSignUp.get("username");
                            String password = dataSignUp.get("password");
                            String phone = dataSignUp.get("phone");

                            userController.signUpUser(email, username, password, phone);

                            break;
                        case "logInUser":
                            System.out.println("hello 22");
                            Map<String, String> dataLogin = (Map<String, String>) jsonMap.get("data");
                            String user_name = dataLogin.get("username");
                            String pass = dataLogin.get("password");
                            if (authenticate(user_name, pass)) {
                                System.out.println("Failed");
                            } else {
                                userController.logInUser(user_name, pass);
                                String token = generateToken(user_name);
                                System.out.println("Token: " + token);
                            }
                            break;
                    }
                }
                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String username) {
        // String rs = generateToken(username, new HashMap<>());
        return generateToken(username, new HashMap<>());
    }

    public static String generateToken(String username, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        final String username = extractUsername(token);
        return (username.equals(users.get(username)));
    }
}
