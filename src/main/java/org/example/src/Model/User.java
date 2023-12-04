package org.example.src.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private int id;
    private String email;
    private String username;
    private String password;
    private String phone;
    private Timestamp created_at;
}
