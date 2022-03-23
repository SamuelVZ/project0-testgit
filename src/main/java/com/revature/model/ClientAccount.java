package com.revature.model;

import java.util.Objects;

public class ClientAccount {
    private int client_id;
    private String firstName;
    private String lastName;
    private int account_id;
    private String accountName;
    private int balance;

    public ClientAccount() {
    }

    public ClientAccount(int client_id, int account_id, int balance) {
        this.client_id = client_id;
        this.account_id = account_id;
        this.balance = balance;
    }

    public ClientAccount(int client_id, String firstName, String lastName, int account_id, String accountName, int balance) {
        this.client_id = client_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account_id = account_id;
        this.accountName = accountName;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ClientAccount{" +
                "client_id=" + client_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", account_id=" + account_id +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientAccount that = (ClientAccount) o;
        return client_id == that.client_id && account_id == that.account_id && balance == that.balance && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(accountName, that.accountName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id, firstName, lastName, account_id, accountName, balance);
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
