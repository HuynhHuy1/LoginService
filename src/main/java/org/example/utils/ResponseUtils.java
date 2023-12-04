package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ResponseObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ResponseUtils {
    private Socket socket;
    public ResponseUtils(Socket socket) {
        this.socket = socket;
    }

    public void responseClient(ResponseObject response) {
        try {
            PrintWriter out = new PrintWriter(this.socket.getOutputStream());
            System.out.println(response.getData());
            out.println(this.objectToJson(response));
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String objectToJson(ResponseObject response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
