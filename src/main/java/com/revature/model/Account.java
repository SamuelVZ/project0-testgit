package com.revature.model;

import java.util.Objects;

public class Account {
private int id;
private String accountName;
private String accountDescription;

    public Account() {
    }

    public Account(int id, String accountName, String accountDescription) {
        this.id = id;
        this.accountName = accountName;
        this.accountDescription = accountDescription;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", accountDescription='" + accountDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(accountName, account.accountName) && Objects.equals(accountDescription, account.accountDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountName, accountDescription);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }
}
