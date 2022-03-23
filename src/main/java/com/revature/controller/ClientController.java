package com.revature.controller;
//TODO 26: Create a client controller class and implement the interface Controller

import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class ClientController implements Controller{
    //TODO 27: create the client Service class
    private ClientService clientService;

    //TODO 28: create the constructor for the controller class
    public ClientController(){
        this.clientService = new ClientService();
    }
    //TODO 29: create the Handlers

    private Handler getAllClients = (ctx) ->{
        //TODO 30: create the method to return all clients on the service class
        List<Client> clients = clientService.getAllClients();

        ctx.status(200);
        ctx.json(clients);
    };
    //TODO 32: create the method to return a client by ID (don't convert from String to int here, because the controller layer only needs to capture the path string)
    private Handler getClientById =(ctx) ->{

        //pathParam to get the String inside the brackets
        String clientId = ctx.pathParam("clientId");
        ctx.status(200);
        Client client = clientService.getClientByID(clientId);
        ctx.json(client);
    };

    private Handler addClient = (ctx) -> {
        Client clientToAdd = ctx.bodyAsClass(Client.class);

        Client addedClient = clientService.addClient(clientToAdd);
        ctx.status(201); //Created
        ctx.json(addedClient);
    };

    private Handler updateClient = (ctx) -> {
        String id = ctx.pathParam("clientId");
        Client clientToUpdate = ctx.bodyAsClass(Client.class);

        Client clientUpdated = clientService.updateClient(id, clientToUpdate);
        ctx.status(200);
        ctx.json(clientUpdated);
    };

    private Handler deleteClientById = (ctx) -> {
        String id = ctx.pathParam("clientId");

        boolean client = clientService.deleteClientById(id);
        ctx.status(410); //deleted
        ctx.result("Client: " + id + " deleted");
    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.get("/clients", getAllClients);
        app.get("/clients/{clientId}", getClientById); //{}you are going to save the content inside the brackets
        app.post("/clients", addClient);
        app.put("/clients/{clientId}", updateClient);
        app.delete("/clients/{clientId}", deleteClientById);

    }
}
