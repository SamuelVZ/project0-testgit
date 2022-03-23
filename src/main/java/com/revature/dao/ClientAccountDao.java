package com.revature.dao;

import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.model.ClientAccount;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientAccountDao {

    public ClientAccount addClientAccount (ClientAccount clientAccount) throws SQLException {

        try (Connection con = ConnectionUtility.getConnection()) {

            String sql = "INSERT INTO clients_accounts(client_id, account_id, balance) VALUES (?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientAccount.getClient_id());
            pstmt.setInt(2, clientAccount.getAccount_id());
            pstmt.setInt(3, clientAccount.getBalance());

            pstmt.executeUpdate();

//            ResultSet rs = pstmt.getGeneratedKeys();
//            rs.next();
//
//            int clientNewId = rs.getInt("id");

            return new ClientAccount(clientAccount.getClient_id(), clientAccount.getAccount_id(), clientAccount.getBalance());
        }
    }

    public ClientAccount getClientAccountById (int clientId, int accountId) throws SQLException {

        try (Connection con = ConnectionUtility.getConnection()){

            //String sql = "SELECT * FROM clients_accounts WHERE client_id = ? AND account_id = ?";

            String sql2 = "SELECT ca.client_id, c.first_name, c.last_name, ca.account_id, a.account_name, ca.balance " +
                    "FROM clients_accounts ca " +
                    "JOIN clients c ON ca.client_id = c.id " +
                    "JOIN accounts a ON ca.account_id = a.id " +
                    "WHERE ca.client_id = ? AND account_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql2);

            pstmt.setInt(1, clientId);
            pstmt.setInt(2, accountId);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                int rClientId = rs.getInt("client_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int rAccountId = rs.getInt("account_id");
                String accountName = rs.getString("account_name");
                int balance = rs.getInt("balance");


                //return new ClientAccount(rClientId, rAccountId, balance);
                return new ClientAccount(rClientId, firstName, lastName, rAccountId, accountName, balance);
            }
        }
        //TODO 14: return null if no results
        return null;

    }


    public List<ClientAccount> getAllClientAccountsByClientId(int clientId) throws SQLException {

        List<ClientAccount> clientAccounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection()){

           // String sql = "SELECT * FROM clients_accounts WHERE client_id = ?";

            String sql2 = "SELECT ca.client_id, c.first_name, c.last_name, ca.account_id, a.account_name, ca.balance " +
                    "FROM clients_accounts ca " +
                    "JOIN clients c ON ca.client_id = c.id " +
                    "JOIN accounts a ON ca.account_id = a.id " +
                    "WHERE ca.client_id = ?";


            PreparedStatement pstmt = con.prepareStatement(sql2);

            pstmt.setInt(1, clientId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int rClientId = rs.getInt("client_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int rAccountId = rs.getInt("account_id");
                String accountName = rs.getString("account_name");
                int balance = rs.getInt("balance");
                clientAccounts.add(new ClientAccount(rClientId, firstName, lastName, rAccountId, accountName, balance));
            }
        }
        return clientAccounts;
    }

    public ClientAccount updateClientAccounts (ClientAccount clientAccount) throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "UPDATE clients_accounts " +
                    "SET client_id = ?, " +
                    "account_id = ?, " +
                    "balance = ? " +
                    "WHERE client_id = ? AND account_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientAccount.getClient_id());
            pstmt.setInt(2, clientAccount.getAccount_id());
            pstmt.setInt(3, clientAccount.getBalance());
            pstmt.setInt(4, clientAccount.getClient_id());
            pstmt.setInt(5, clientAccount.getAccount_id());

            pstmt.executeUpdate();

        }
        return clientAccount;
    }

    public boolean deleteClientAccountById (int clientId, int accountId) throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()){

            String sql = "DELETE FROM clients_accounts WHERE client_id = ? AND account_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setInt(2, accountId);

            int recordsDeleted = pstmt.executeUpdate();

            if (recordsDeleted == 1){
                return true;
            }
        }
        return false;
    }

    public Account getAccountById(int id) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()){


            String sql = "SELECT * FROM accounts WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                String accountName = rs.getString("account_name");
                String accountDescription = rs.getString("account_description");

                return new Account(id, accountName,accountDescription);
            }
        }
        return null;
    }

        public List<ClientAccount> getClientAccountByIdLessGreater(int clientId, int lessThan, int greaterThan) throws SQLException {

            List<ClientAccount> clientAccounts = new ArrayList<>();

            try (Connection con = ConnectionUtility.getConnection()){

                /*String sql = "SELECT * FROM clients_accounts WHERE client_id = ? " +
                        "AND balance < ? " +
                        "AND balance > ?";
                */

                String sql2 = "SELECT ca.client_id, c.first_name, c.last_name, ca.account_id, a.account_name, ca.balance " +
                        "FROM clients_accounts ca " +
                        "JOIN clients c ON ca.client_id = c.id " +
                        "JOIN accounts a ON ca.account_id = a.id " +
                        "WHERE ca.client_id = ? " +
                        "AND balance < ? " +
                        "AND balance > ?";


                PreparedStatement pstmt = con.prepareStatement(sql2);

                pstmt.setInt(1, clientId);
                pstmt.setInt(2, lessThan);
                pstmt.setInt(3, greaterThan);

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()){
                    int rClientId = rs.getInt("client_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    int rAccountId = rs.getInt("account_id");
                    String accountName = rs.getString("account_name");
                    int balance = rs.getInt("balance");
                    clientAccounts.add(new ClientAccount(rClientId, firstName, lastName, rAccountId, accountName, balance));
                }
            }
            return clientAccounts;
        }

}
