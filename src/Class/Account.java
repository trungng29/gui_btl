/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;
//import java.util.*;
/**
 *
 * @author huyle
 */
public class Account {
    static int cnt = 1;
    private String username;
    private String password;
    
    public Account(String username){
        this.username = username;
        this.password = String.format("abc@%02d", Integer.parseInt(username.substring(2)));
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}

