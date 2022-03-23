package com.revature.controller;

import com.revature.model.Client;
import com.revature.model.ClientAccount;
import com.revature.service.ClientAccountService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class ClientAccountController implements Controller{

    private ClientAccountService clientAccountService;

    public ClientAccountController() {
        this.clientAccountService = new ClientAccountService();
    }


    //REVIEW THIS ONE FOR THE SELECT AMOUNT HIGHER THAN 200 AND LOWER THAN
    private Handler getAllClientAccounts = (ctx) ->{

        String clientId = ctx.pathParam("client_id");
        String qLess = ctx.queryParam("amountLessThan");
        String qGreater = ctx.queryParam("amountGreaterThan");




        List<ClientAccount> clientAccounts = clientAccountService.getAllClientAccounts(clientId, qLess, qGreater);

        ctx.status(200);
        ctx.json(clientAccounts);
    };


    private Handler getClientAccountsById = (ctx) -> {

        String clientId = ctx.pathParam("client_id");
        String accountId = ctx.pathParam("account_id");

        ClientAccount clientAccount = clientAccountService.getClientAccountById(clientId, accountId);
        ctx.status(200);
        ctx.json(clientAccount);

    };

    private Handler addClientAccount = (ctx) -> {
        String clientId = ctx.pathParam("client_id");
        ClientAccount clientAccountToAdd = ctx.bodyAsClass(ClientAccount.class);

        ClientAccount clientAccount = clientAccountService.addClientAccount(clientId, clientAccountToAdd);
        ctx.status(201);
        ctx.json(clientAccount);

    };

    private Handler updateClientAccount = (ctx) -> {
        String clientId = ctx.pathParam("client_id");
        String accountId = ctx.pathParam("account_id");


        ClientAccount clientAccountToUpdate = ctx.bodyAsClass(ClientAccount.class);

        ClientAccount clientAccount = clientAccountService.updateClientAccount(clientId, accountId, clientAccountToUpdate);
        ctx.status(200);
        ctx.json(clientAccount);

    };

    private Handler deleteClientAccount = (ctx) -> {
        String clientId = ctx.pathParam("client_id");
        String accountId = ctx.pathParam("account_id");

        boolean clientAccount = clientAccountService.deleteClientAccount(clientId, accountId);
        ctx.status(410);
        ctx.result("For the client: " + clientId + " the account: " + accountId + " was deleted");
    };


    @Override
    public void mapEndPoints(Javalin app) {

        app.get("/clients/{client_id}/accounts", getAllClientAccounts);
        app.get("/clients/{client_id}/accounts/{account_id}", getClientAccountsById);
        app.post("/clients/{client_id}/accounts", addClientAccount);
        app.put("/clients/{client_id}/accounts/{account_id}", updateClientAccount);
        app.delete("clients/{client_id}/accounts/{account_id}", deleteClientAccount);


    }
}
