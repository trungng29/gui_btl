/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.util.ArrayList;

/**
 *
 * @author huyle
 */
public class DSAccount {
    private ArrayList<Account> dsAccount;
    
    public DSAccount(){
        this.dsAccount = new ArrayList<>();
    }
    
    public ArrayList<Account> getDSAccount(){
        return dsAccount;
    }
    
    public void addAccount(Account account){
        dsAccount.add(account);
    }
    
    public void removeAccount(Account account){
        dsAccount.remove(account);
    }
}
