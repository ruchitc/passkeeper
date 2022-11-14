package com.nuvcse.passkeeper;

public class account_details {
    String accName;
    String email;
    String password;

    public account_details() {

    }

    public account_details(String accName, String email, String password) {
        this.accName = accName;
        this.email = email;
        this.password = password;
    }

    public String getAccName() {
        return accName;
    }

    public void setName(String name) {
        this.accName = accName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


