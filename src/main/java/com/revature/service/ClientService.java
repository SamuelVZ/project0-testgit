package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    //TODO 42: create a logger in the service layer

    public static Logger logger = LoggerFactory.getLogger(ClientService.class);


    private ClientDao clientDao;

    //TODO 31: create the constructor for the Service class(communicates with the Dao layer)
    public ClientService(){
        this.clientDao = new ClientDao();
    }


    //TODO 39: create a constructor for testing mock clients so we don't access to the real database
    public ClientService(ClientDao mockDao){
        this.clientDao = mockDao;
    }

    public List<Client> getAllClients() throws SQLException {
        logger.info("Service layer - get all the clients");
        return this.clientDao.getAllClients();
    }



    public Client getClientByID(String id) throws SQLException, ClientNotFoundException {
        logger.info("Service layer to get the client with id: " + id);

        //TODO 34: check if the input is valid, if not throw a IllegalArgumentException
        try {
            int clientId = Integer.parseInt(id);
            Client c = clientDao.getClientById(clientId);

            //TODO 33: check if the client exist, if not send a message (create a personal exception class)
            if (c == null){
                logger.warn("Service layer - the client with id: " + id + " doesn't exist");
                throw new ClientNotFoundException("The client with the id " + id + " doesn't exist");
            }
            logger.info("Service layer - Successful return of the client with id: " + id);
            return c;
        }catch (NumberFormatException e) {
            logger.warn("Service layer - the client id: " + id + " is invalid");
            throw new IllegalArgumentException("Id provided is invalid: " + id);
        }
    }

    public Client addClient(Client client) throws SQLException {
        logger.info("Service layer - add the client" + client);

        validateClientInfo(client);

        Client addedClient = clientDao.addClient(client);
        return addedClient;
    }

    public Client updateClient(String id, Client clientToUpdate) throws SQLException, ClientNotFoundException {
        logger.info("Service layer to update the client with id: " + id);

        try {
            int clientId = Integer.parseInt(id);
            Client checkClient = clientDao.getClientById(clientId);

            if (checkClient == null){
                logger.warn("Service layer - The client attempted to be updated with the id: " + id + " doesn't exist");
                throw new ClientNotFoundException("The client attempted to be updated with the id: " + id + " doesn't exist");
            }

            validateClientInfo(clientToUpdate);

            clientToUpdate.setId(clientId);

            Client clientUpdated = clientDao.updateClient(clientToUpdate);
            logger.info("Service layer - Successful return of the client with id: " + id);
            return clientUpdated;


        }catch (NumberFormatException e) {
            logger.warn("Service layer - the client id: " + id + " is invalid");
            throw new IllegalArgumentException("Id provided is invalid: " + id);
        }


    }


    public boolean deleteClientById(String id) throws ClientNotFoundException, SQLException {
        logger.info("Service layer - delete the client with id: " + id);
        try {
            int clientId = Integer.parseInt(id);
            Client c = clientDao.getClientById(clientId);

            if (c == null){
                logger.warn("Service layer - the client with id: " + id + " doesn't exist");
                throw new ClientNotFoundException("The client with the id " + id + " doesn't exist");
            }


            boolean d = clientDao.deleteClientById(clientId);
            if (d == false){
                logger.warn("Service layer - No records deleted of the client with id: " + id);
                throw new ClientNotFoundException("The client with the id " + id + " doesn't exist");
            }

            logger.info("Service layer - Successful delete of the client with id: " + id);
            return true;

        }catch (NumberFormatException e) {
            logger.warn("Service layer - the client id: " + id + " is invalid");
            throw new IllegalArgumentException("Id provided is invalid: " + id);
        }
    }



    private void validateClientInfo(Client client){

        client.setFirstName(client.getFirstName().trim());
        client.setLastName(client.getLastName().trim());
        client.setPhoneNumber(client.getPhoneNumber().trim());

        if(!client.getFirstName().matches("[a-zA-z]+") || !client.getLastName().matches("[a-zA-z]+")){
            logger.warn("Service layer - the client to have a firstname or lastname with no alphabet letters. Firstname: " +
                    client.getFirstName() + " Lastname: " + client.getLastName());
            throw new IllegalArgumentException("First and last names need to only have alphabet letters. Firstname: "+ client.getFirstName() + " Lastname: " + client.getLastName());
        }
        if(!client.getPhoneNumber().matches("\\d+")){
            logger.warn("Service layer - The phone number provided have values different than numbers. Phone number: " + client.getPhoneNumber());
            throw new IllegalArgumentException("Phone number need to have only numbers. Phone number: " + client.getPhoneNumber());
        }

        if(client.getAge() < 0){
            throw new IllegalArgumentException("A client with age < 0 is not valid");
        }

    }


}
