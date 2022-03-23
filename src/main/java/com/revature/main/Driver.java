package com.revature.main;

import com.revature.controller.*;
import com.revature.dao.ClientAccountDao;
import com.revature.model.ClientAccount;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


public class Driver {

    //TODO 40: create a logger using the slf4j library
    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) throws SQLException {


        //TODO 28: create an instance of javalin
        Javalin app = Javalin.create();

        //TODO 41: to log before every request
        app.before((ctx) -> {
                logger.info(ctx.method() + " request received for " + ctx.path());
        });

        mapControllers (app, new TestController(), new ClientController(), new ExceptionController(), new ClientAccountController());

        app.start();





        //TODO 23: test the dao layer
       /* ClientDao clientDao = new ClientDao();
        System.out.println(clientDao.getClientById(1));

        System.out.println(clientDao.getAllClients());
        System.out.println(clientDao.deleteClientById(1));
        System.out.println(clientDao.getAllClients());

        System.out.println(clientDao.updateClient(new Client(2,"HugoUPd", "laicona", 55,"3692581477")));
        System.out.println(clientDao.getAllClients());

        System.out.println(clientDao.addClient(new Client(0,"newNew", "added", 22,"3692581477")));
        System.out.println(clientDao.getAllClients());




        ClientAccountDao clientAccountDao = new ClientAccountDao();
        System.out.println(clientAccountDao.addClientAccount(new ClientAccount(4,2,200)));

        ClientAccountDao clientAccountDao = new ClientAccountDao();
        System.out.println(clientAccountDao.getClientAccountById(1,5));

        ClientAccountDao clientAccountDao = new ClientAccountDao();
        System.out.println(clientAccountDao.getAllClientAccountsByClientId(1));

        ClientAccountDao clientAccountDao = new ClientAccountDao();
        System.out.println(clientAccountDao.updateClientAccounts(new ClientAccount(1,1,5)));

        ClientAccountDao clientAccountDao = new ClientAccountDao();
        System.out.println(clientAccountDao.getAllClientAccountsByClientId(1));
        System.out.println(clientAccountDao.deleteClientAccountById(1,1));
        System.out.println(clientAccountDao.getAllClientAccountsByClientId(1));

        */
    }

    private static void mapControllers(Javalin app, Controller... controllers) {
        for (Controller c: controllers){
            c.mapEndPoints(app);
        }

    }
}
