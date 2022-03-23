package com.revature.dao;
//TODO 6: create the Dao layer(where we use the queries) implementing the CRUD


import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {


    //Create
    public Client addClient(Client client) throws SQLException {
        //TODO 18: do the insert method
        try (Connection con = ConnectionUtility.getConnection()) {

            String sql = "INSERT INTO clients(first_name, last_name, age, phone_number) VALUES (?, ?, ?, ?)";

            //TODO 19: we use the "Statement.RETURN_GENERATED_KEYS" to get the id
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setInt(3, client.getAge());
            pstmt.setString(4, client.getPhoneNumber());

            //TODO 20: executeUpdate to do a insert, update or delete
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();

            int clientNewId = rs.getInt("id");

            return new Client(clientNewId, client.getFirstName(), client.getLastName(), client.getAge(), client.getPhoneNumber());

        }
    }

    //Read
    public Client getClientById(int id) throws SQLException {
        //TODO 7: create a connection object and do a try catch with arguments "try(){}"
        try (Connection con = ConnectionUtility.getConnection()){

            //Todo 8: create the query we need and use "?" for the value(s) we want to provide
            String sql = "SELECT * FROM clients WHERE id = ?";

            //TODO 9: use PrepareStatement to work with the connection object
            PreparedStatement pstmt = con.prepareStatement(sql);

            //TODO 10: set the values for the statement
            pstmt.setInt(1, id);

            //TODO 11: do the executeQuery for a SELECT query and save the output of the query in a resultSet
            ResultSet rs = pstmt.executeQuery();

            //TODO 12: Iterate through the result set
            if(rs.next()){
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");
                String phoneNumber = rs.getString("phone_number");

                //TODO 13: return a new client object wit the data from the resultSet
                return new Client(id, firstName,lastName, age, phoneNumber);
            }
        }
        //TODO 14: return null if no results
            return null;
    }

        public List<Client> getAllClients() throws SQLException {

            //TODO 15: do the select * method
            //TODO 16: create a list of clients to save the results
            List<Client> clients = new ArrayList<>();

            try (Connection con = ConnectionUtility.getConnection()){

                String sql = "SELECT * FROM clients";

                PreparedStatement pstmt = con.prepareStatement(sql);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    int clientId = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    int age = rs.getInt("age");
                    String phoneNumber = rs.getString("phone_number");

                    //TODO 17 add each client to the list
                    clients.add(new Client(clientId, firstName, lastName, age, phoneNumber));
                }
            }
            return clients;
        }




    //Update

    public Client updateClient(Client client) throws SQLException {
//TODO 22: do the delete method
        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "UPDATE clients " +
                    "SET first_name = ?, " +
                    "last_name = ?, " +
                    "age = ? ," +
                    "phone_number = ? " +
                    "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setInt(3, client.getAge());
            pstmt.setString(4, client.getPhoneNumber());
            pstmt.setInt(5, client.getId());

            pstmt.executeUpdate();

        }
            return client;
    }
    //Delete

    public boolean deleteClientById(int id) throws SQLException {
        //TODO 21: do the delete method
        try(Connection con = ConnectionUtility.getConnection()){

            String sql = "DELETE FROM clients WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);

            int recordsDeleted = pstmt.executeUpdate();

            if (recordsDeleted ==1){
                return true;
            }
        }
        return false;
    }
}

