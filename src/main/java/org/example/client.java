package org.example;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost",9090);
    }
}
