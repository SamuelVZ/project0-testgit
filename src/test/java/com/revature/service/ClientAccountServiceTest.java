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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientAccountServiceTest {

    @Test
    public void getAllClientAccounts_positive() throws SQLException, ClientNotFoundException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",1,"savings",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",2,"checking",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",3,"money market",200));

        when(mockClientAccount.getAllClientAccountsByClientId(eq(1))).thenReturn(fakeClients);


        //act

        List<ClientAccount> actual = clientService.getAllClientAccounts("1",null,null);

        //Assert
        List<ClientAccount> expected = new ArrayList<>(fakeClients);
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void getAllClientAccountsLessGreater_positive() throws SQLException, ClientNotFoundException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",1,"savings",300));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",2,"checking",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",3,"money market",250));

        when(mockClientAccount.getClientAccountByIdLessGreater(eq(1),eq(500),eq(100))).thenReturn(fakeClients);


        //act

        List<ClientAccount> actual = clientService.getAllClientAccounts("1","500","100");

        //Assert
        List<ClientAccount> expected = new ArrayList<>(fakeClients);
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void getAllClientAccounts_NonExistentID() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        when(mockClient.getClientById(eq(100))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(ClientNotFoundException.class, () ->{
            clientService.getAllClientAccounts("100", null, null);
        });
    }

    @Test
    public void getAllClientAccounts_InvalidID() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);


        //act + assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            clientService.getAllClientAccounts("abc", null, null);
        });
    }

    @Test
    public void getAllClientAccounts_InvalidLessGreater() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        //act + assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            clientService.getAllClientAccounts("1", "abc", "abc");
        });
    }

    @Test
    public void getAllClientAccountById_positive() throws SQLException, ClientNotFoundException, ClientAccountNotFoundException, AccountNotFoundException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",1,"savings",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",2,"checking",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",3,"money market",200));

        when(mockClientAccount.getAllClientAccountsByClientId(eq(1))).thenReturn(fakeClients);

        Account fakeAccount = new Account(1, "savings", "savings account");
        when(mockClientAccount.getAccountById(eq(1))).thenReturn(fakeAccount);

        ClientAccount fakeClientAccount = new ClientAccount(1, "samuel", "valencia", 1, "savings", 200);
        when(mockClientAccount.getClientAccountById(eq(1), eq(1))).thenReturn(fakeClientAccount);
        //act

        ClientAccount actual = clientService.getClientAccountById("1","1");

        //Assert
        ClientAccount expected = fakeClientAccount;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void getAllClientAccountById_NonExistentClient() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        when(mockClient.getClientById(eq(100))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(ClientNotFoundException.class, () ->{
            clientService.getClientAccountById("100", "1");
        });

    }

    @Test
    public void getAllClientAccountById_NonExistentAccounts_forClient() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(100))).thenReturn(fakeClient);

        when(mockClientAccount.getAllClientAccountsByClientId(eq(100))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(ClientNotFoundException.class, () ->{
            clientService.getClientAccountById("1", "10");
        });

    }

    @Test
    public void getAllClientAccountById_NonExistentAccount() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",1,"savings",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",2,"checking",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",3,"money market",200));
        when(mockClientAccount.getAllClientAccountsByClientId(eq(1))).thenReturn(fakeClients);

        when(mockClientAccount.getAccountById(eq(100))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(AccountNotFoundException.class, () ->{
            clientService.getClientAccountById("1", "100");
        });

    }

    @Test
    public void getAllClientAccountById_NonExistentAccounts() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1, "samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1, "samuel", "valencia", 1, "savings", 200));
        fakeClients.add(new ClientAccount(1, "samuel", "valencia", 2, "checking", 200));
        fakeClients.add(new ClientAccount(1, "samuel", "valencia", 3, "money market", 200));
        when(mockClientAccount.getAllClientAccountsByClientId(eq(1))).thenReturn(fakeClients);

        Account fakeAccount = new Account(1, "savings", "savings account");
        when(mockClientAccount.getAccountById(eq(1))).thenReturn(fakeAccount);

        when(mockClientAccount.getClientAccountById(eq(1), eq(1))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(ClientAccountNotFoundException.class, () -> {
            clientService.getClientAccountById("1", "1");
        });

    }

    @Test
    public void getAllClientAccountById_invalidClientId() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1, "samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1, "samuel", "valencia", 1, "savings", 200));
        fakeClients.add(new ClientAccount(1, "samuel", "valencia", 2, "checking", 200));
        fakeClients.add(new ClientAccount(1, "samuel", "valencia", 3, "money market", 200));
        when(mockClientAccount.getAllClientAccountsByClientId(eq(1))).thenReturn(fakeClients);

        //act + assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.getClientAccountById("1", "abc");
        });

    }

    @Test
    public void getAllClientAccountById_invalidAccountId()  {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);


        //act + assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.getClientAccountById("abc", "1");
        });

    }

    @Test
    public void addClientAccount_invalidClientId() throws SQLException, ClientNotFoundException, ClientAccountNotFoundException, AccountNotFoundException, ClientAccountFoundException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);



        //act + Assert
        ClientAccount clientAccount = new ClientAccount(1,2,40);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClientAccount("abc", clientAccount);
        });
    }

    @Test
    public void addClientAccount_NonExistentClient() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        when(mockClient.getClientById(eq(1))).thenReturn(null);

        //act + Assert
        ClientAccount clientAccount = new ClientAccount(1,2,40);
        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.addClientAccount("1", clientAccount);
        });

    }

    @Test
    public void addClientAccount_NonExistentClientAccounts() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(100))).thenReturn(fakeClient);

        when(mockClientAccount.getAllClientAccountsByClientId(eq(100))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(ClientNotFoundException.class, () ->{
            clientService.addClientAccount("1", new ClientAccount());
        });

    }

    @Test
    public void addClientAccount_NonExistentAccount() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(100))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",1,"savings",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",2,"checking",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",3,"money market",200));
        when(mockClientAccount.getAllClientAccountsByClientId(eq(100))).thenReturn(fakeClients);

        when(mockClientAccount.getAccountById(eq(100))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(ClientNotFoundException.class, () ->{
            clientService.addClientAccount("1", new ClientAccount(1,1,9));
        });

    }

    @Test
    public void updateClientAccount_invalidClientId() throws SQLException, ClientNotFoundException, ClientAccountNotFoundException, AccountNotFoundException, ClientAccountFoundException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);



        //act + Assert
        ClientAccount clientAccount = new ClientAccount(1,1,40);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.updateClientAccount("abc", "1", clientAccount);
        });
    }

    @Test
    public void updateClientAccount_invalidAccountId() throws SQLException, ClientNotFoundException, ClientAccountNotFoundException, AccountNotFoundException, ClientAccountFoundException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);



        //act + Assert
        ClientAccount clientAccount = new ClientAccount(1,1,40);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.updateClientAccount("1", "abc", clientAccount);
        });
    }

    @Test
    public void updateClientAccount_NonExistentClient() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        when(mockClient.getClientById(eq(1))).thenReturn(null);

        //act + Assert
        ClientAccount clientAccount = new ClientAccount(1,1,40);
        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.updateClientAccount("1", "1", clientAccount);
        });

    }

    @Test
    public void updateClientAccount_NonExistentClientAccounts() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(100))).thenReturn(fakeClient);

        when(mockClientAccount.getAllClientAccountsByClientId(eq(100))).thenReturn(null);

        //act + assert
        ClientAccount clientAccount = new ClientAccount(1,1,40);
        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.updateClientAccount("1", "1", clientAccount);
        });

    }

    @Test
    public void deleteClientAccount_invalidClientID() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);


        //act + assert

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.deleteClientAccount("abc", "1");
        });

    }

    @Test
    public void deleteClientAccount_invalidAccountId() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);


        //act + assert

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.deleteClientAccount("1", "abc");
        });

    }

    @Test
    public void deleteClientAccount_NonExistentClient() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        when(mockClient.getClientById(eq(1))).thenReturn(null);

        //act + assert

        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.deleteClientAccount("1", "1");
        });

    }

    @Test
    public void deleteClientAccount_NonExistentAccounts() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);
        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        when(mockClientAccount.getAllClientAccountsByClientId(eq(2))).thenReturn(null);


        //act + assert

        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.deleteClientAccount("1", "1");
        });

    }

    @Test
    public void deleteClientAccount_NonExistentAccount() throws SQLException {
        //Arrange
        ClientAccountDao mockClientAccount = mock(ClientAccountDao.class);//creates a clientAccount using the Dao class
        ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

        ClientAccountService clientService = new ClientAccountService(mockClientAccount, mockClient);

        Client fakeClient = new Client(1,"samuel", "valencia", 27, "1234567899");
        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        List<ClientAccount> fakeClients = new ArrayList<>();
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",1,"savings",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",2,"checking",200));
        fakeClients.add(new ClientAccount(1,"samuel", "valencia",3,"money market",200));
        when(mockClientAccount.getAllClientAccountsByClientId(eq(1))).thenReturn(fakeClients);

        when(mockClientAccount.getAccountById(eq(100))).thenReturn(null);

        //act + assert
        Assertions.assertThrows(AccountNotFoundException.class, () ->{
            clientService.deleteClientAccount("1", "100");
        });


    }
}
