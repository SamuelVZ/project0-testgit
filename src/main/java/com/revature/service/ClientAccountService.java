package com.revature.service;

import com.revature.dao.ClientAccountDao;
import com.revature.dao.ClientDao;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.ClientAccountFoundException;
import com.revature.exceptions.ClientAccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.model.ClientAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientAccountService {

    public static Logger logger = LoggerFactory.getLogger(ClientService.class);

    private ClientAccountDao clientAccountDao;
    private ClientDao clientDao;


    public ClientAccountService() {
        this.clientAccountDao = new ClientAccountDao();
        this.clientDao = new ClientDao();
    }

    //for testing
    public ClientAccountService(ClientAccountDao mockAccountDao, ClientDao clientDao) {
        this.clientAccountDao = mockAccountDao;
        this.clientDao = clientDao;
    }


    public List<ClientAccount> getAllClientAccounts(String clientId, String less, String greater) throws SQLException, ClientNotFoundException {
        logger.info("Service layer - get all the accounts of the client " + clientId);


            try {
                int id = Integer.parseInt(clientId);
                Client c = clientDao.getClientById(id);

                if (c == null) {
                    logger.warn("Service layer - the client with id: " + clientId + " doesn't exist");
                    throw new ClientNotFoundException("The client with the id " + clientId + " doesn't exist");
                }

                if(less == null && greater == null) {
                    List<ClientAccount> a = clientAccountDao.getAllClientAccountsByClientId(id);
                    if (a.isEmpty()) {
                        logger.warn("Service layer - the client with id: " + clientId + " doesn't have accounts");
                        throw new ClientNotFoundException("The client with the id " + clientId + " doesn't have accounts");
                    }

                    return a;

                }else if(less != null && greater != null) {

                    try {
                        int qLess = Integer.parseInt(less);
                        int qGreater = Integer.parseInt(greater);

                        List<ClientAccount> a = clientAccountDao.getClientAccountByIdLessGreater(id, qLess, qGreater);

                        if (a.isEmpty()) {
                            logger.warn("Service layer - the client with id: " + clientId + " doesn't have accounts that " +
                                    "meet the criteria: less than: " + less + " and greater than: " + greater);
                            throw new ClientNotFoundException("Service layer - the client with id: " + clientId + " doesn't have accounts that " +
                                    "meet the criteria: less than: " + less + " and greater than: " + greater);
                        }
                        return a;

                    } catch (NumberFormatException e) {
                        logger.warn("Service layer - the query have invalid values: less than: " + less + " greater than: " + greater);
                        throw new IllegalArgumentException("Id provided is invalid: " + clientId);
                    }

                }
            } catch (NumberFormatException e) {
                logger.warn("Service layer - the client id: " + clientId + " is invalid");
                throw new IllegalArgumentException("Id provided is invalid: " + clientId);
            }
            return null;
    }

    public ClientAccount getClientAccountById(String clientId, String accountId) throws SQLException, ClientNotFoundException, ClientAccountNotFoundException, AccountNotFoundException {
        logger.info("Service layer - get the account " + accountId + " for the client " + clientId);

        try {
            int iClientId = Integer.parseInt(clientId);
            Client c = clientDao.getClientById(iClientId);

            if (c == null){
                logger.warn("Service layer - the client with id: " + clientId + " doesn't exist");
                throw new ClientNotFoundException("The client with the id " + clientId + " doesn't exist");
            }
            List<ClientAccount> aC = clientAccountDao.getAllClientAccountsByClientId(iClientId);
            if(aC.isEmpty()){
                logger.warn("Service layer - the client with id: " + clientId + " doesn't have accounts");
                throw new ClientNotFoundException("The client with the id " + clientId + " doesn't have accounts");
            }

            try {
                int iAccountId =Integer.parseInt(accountId);
                Account a = clientAccountDao.getAccountById(iAccountId);

                if (a == null){
                    logger.warn("Service layer - the account with id: " + accountId + " doesn't exist");
                    throw new AccountNotFoundException("The account with the id " + accountId + " doesn't exist");
                }


                ClientAccount cA = clientAccountDao.getClientAccountById(iClientId, iAccountId);

                if (cA == null){
                    logger.warn("Service layer - The client: " + clientId +" doesn't have the account: " + accountId);
                    throw new ClientAccountNotFoundException("The client: " + clientId +" doesn't have the account: " + accountId);
                }

                return  cA;

            }catch (NumberFormatException e) {
                logger.warn("Service layer - the account id: " + accountId + " is invalid");
                throw new IllegalArgumentException("Account Id provided is invalid: " + accountId);
            }


        }catch (NumberFormatException e) {
            logger.warn("Service layer - the client id: " + clientId + " is invalid");
            throw new IllegalArgumentException("Id provided is invalid: " + clientId);
        }

    }


    public ClientAccount addClientAccount(String clientId, ClientAccount clientAccountToAdd) throws ClientNotFoundException, SQLException, AccountNotFoundException, ClientAccountFoundException {
        logger.info("Service layer - add to the client " + clientId + " the account " + clientAccountToAdd);

        try {
            int id = Integer.parseInt(clientId);
            clientAccountToAdd.setClient_id(id);

            Client c = clientDao.getClientById(id);

            if (c == null){
                logger.warn("Service layer - the client with id: " + clientId + " doesn't exist");
                throw new ClientNotFoundException("The client with the id " + clientId + " doesn't exist");
            }

            List<ClientAccount> a = clientAccountDao.getAllClientAccountsByClientId(id);
            if(a.isEmpty()){
                logger.warn("Service layer - the client with id: " + clientId + " doesn't have accounts");
                throw new ClientNotFoundException("The client with the id " + clientId + " doesn't have accounts");
            }

            Account acc = clientAccountDao.getAccountById(clientAccountToAdd.getAccount_id());

            if (acc == null){
                logger.warn("Service layer - the account with id: " + clientAccountToAdd.getAccount_id() + " doesn't exist");
                throw new AccountNotFoundException("The account with the id " + clientAccountToAdd.getAccount_id() + " doesn't exist");
            }

            ClientAccount clientAccount = clientAccountDao.getClientAccountById(id, clientAccountToAdd.getAccount_id());

            if (clientAccount != null) {
                logger.warn("Service layer - the client " + id + " have already the account " + clientAccountToAdd.getAccount_id());
                throw new ClientAccountFoundException("The client with the id " + clientAccount.getClient_id() + " have already the account " + clientAccountToAdd.getAccount_id());
            }

            validateClientAccountInfo(clientAccountToAdd);

            ClientAccount addedClientAccount = clientAccountDao.addClientAccount(clientAccountToAdd);
            ClientAccount fullUpdatedClientAccount = clientAccountDao.getClientAccountById(addedClientAccount.getClient_id(), addedClientAccount.getAccount_id());


            return fullUpdatedClientAccount;
            //return addedClientAccount;


        }catch (NumberFormatException e) {
            logger.warn("Service layer - the client id: " + clientId + " is invalid");
            throw new IllegalArgumentException("the client id provided is invalid: " + clientId);
        }
    }


    public ClientAccount updateClientAccount(String clientId, String accountId, ClientAccount clientAccountToUpdate) throws ClientNotFoundException, SQLException, AccountNotFoundException, ClientAccountNotFoundException {
        try {

            int iClientId = Integer.parseInt(clientId);

            try {
                int iAccountId = Integer.parseInt(accountId);
                clientAccountToUpdate.setClient_id(iClientId);
                clientAccountToUpdate.setAccount_id(iAccountId);

                Client c = clientDao.getClientById(iClientId);

                if (c == null){
                    logger.warn("Service layer - the client with id: " + clientId + " doesn't exist");
                    throw new ClientNotFoundException("The client with the id " + clientId + " doesn't exist");
                }

                List<ClientAccount> a = clientAccountDao.getAllClientAccountsByClientId(iClientId);
                if(a.isEmpty()){
                    logger.warn("Service layer - the client with id: " + clientId + " doesn't have accounts");
                    throw new ClientNotFoundException("The client with the id " + clientId + " doesn't have accounts");
                }

                Account acc = clientAccountDao.getAccountById(iAccountId);

                if (acc == null){
                    logger.warn("Service layer - the account with id: " + accountId + " doesn't exist");
                    throw new AccountNotFoundException("The account with the id " + accountId + " doesn't exist");
                }

                ClientAccount cA = clientAccountDao.getClientAccountById(iClientId, iAccountId);
                if (cA == null) {
                    logger.warn("Service layer - Can't update, the client: " + clientId + " doesn't have the account: " + accountId);
                    throw new ClientAccountNotFoundException("The client: " + clientId + " doesn't have the account: " + accountId);
                }

                validateClientAccountInfo(clientAccountToUpdate);

                ClientAccount updatedClientAccount = clientAccountDao.updateClientAccounts(clientAccountToUpdate);

                ClientAccount fullUpdatedClientAccount = clientAccountDao.getClientAccountById(updatedClientAccount.getClient_id(), updatedClientAccount.getAccount_id());
                return fullUpdatedClientAccount;
                //return updatedClientAccount;

            }catch (NumberFormatException e) {
                logger.warn("Service layer - the account id: " + accountId + " is invalid");
                throw new IllegalArgumentException("Account Id provided is invalid: " + accountId);
            }

        }catch (NumberFormatException e) {
            logger.warn("Service layer - the client id: " + clientId + " is invalid");
            throw new IllegalArgumentException("the client id provided is invalid: " + clientId);
        }

    }


    public boolean deleteClientAccount(String clientId, String accountId) throws ClientNotFoundException, SQLException, AccountNotFoundException {

        try{
            int iClientId = Integer.parseInt(clientId);

            try{
                int iAccountId = Integer.parseInt(accountId);

                Client c = clientDao.getClientById(iClientId);

                if (c == null){
                    logger.warn("Service layer - the client with id: " + clientId + " doesn't exist");
                    throw new ClientNotFoundException("The client with the id " + clientId + " doesn't exist");
                }

                List<ClientAccount> a = clientAccountDao.getAllClientAccountsByClientId(iClientId);
                if(a.isEmpty()){
                    logger.warn("Service layer - the client with id: " + clientId + " doesn't have accounts");
                    throw new ClientNotFoundException("The client with the id " + clientId + " doesn't have accounts");
                }

                Account acc = clientAccountDao.getAccountById(iAccountId);

                if (acc == null){
                    logger.warn("Service layer - the account with id: " + accountId + " doesn't exist");
                    throw new AccountNotFoundException("The account with the id " + accountId + " doesn't exist");
                }


                boolean d = clientAccountDao.deleteClientAccountById(iClientId, iAccountId);
                if (d == false){
                    logger.warn("Service layer - No records deleted for the client "+ clientId + " with account id: " + accountId);
                    throw new ClientNotFoundException("No records deleted for the client "+ clientId + " with account id: " + accountId);
                }

                logger.info("Service layer - Successful delete of the client "+ clientId + " with account id: " + accountId);
                return true;

            }catch (NumberFormatException e) {
                logger.warn("Service layer - the account id: " + accountId + " is invalid");
                throw new IllegalArgumentException("Account Id provided is invalid: " + accountId);
            }


        }catch (NumberFormatException e) {
            logger.warn("Service layer - the client id: " + clientId + " is invalid");
            throw new IllegalArgumentException("the client id provided is invalid: " + clientId);
        }

    }

    private void validateClientAccountInfo(ClientAccount clientAccount) throws SQLException, ClientNotFoundException, AccountNotFoundException {


        Client c = clientDao.getClientById(clientAccount.getClient_id());
        if(c == null){
            logger.warn("Service layer - the client with id: " + clientAccount.getClient_id() + " doesn't exist");
            throw new ClientNotFoundException("The client with the id " + clientAccount.getClient_id() + " doesn't exist");
        }

        Account a = clientAccountDao.getAccountById(clientAccount.getAccount_id());
        if (a == null){
            logger.warn("Service layer - the account with id: " + clientAccount.getAccount_id() + " doesn't exist");
            throw new AccountNotFoundException("The account with the id " + clientAccount.getAccount_id() + " doesn't exist");
        }

        if(clientAccount.getBalance() < 0){
            throw new IllegalArgumentException("Balance < 0 is not valid");
        }

    }



}
