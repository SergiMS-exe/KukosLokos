package com.example.kukoslokos.util.bodies;

public class RegisterBody {
    private String nombre;
    private String email;
    private String nickName;
    private String password;

    public RegisterBody(String nombre, String email, String nickName, String password) {
        this.nombre = nombre;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
}
