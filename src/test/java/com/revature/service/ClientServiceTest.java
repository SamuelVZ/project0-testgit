package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO 38: We create a tet class to test our methods in the service layer
public class ClientServiceTest {


@Test
    public void testGetAllClients() throws SQLException {
    //Arrange
    ClientDao mockClient = mock(ClientDao.class);//creates a client using the Dao class

    List<Client> fakeClients = new ArrayList<>();
    fakeClients.add(new Client(1,"test", "first", 44, "4567891234"));
    fakeClients.add(new Client(2,"test", "second", 77, "3692581477"));
    fakeClients.add(new Client(3,"test", "third", 59, "9876543215"));


    //when you call the getAllClients method then return the list of clients we created above
    when(mockClient.getAllClients()).thenReturn(fakeClients);

    ClientService clientService = new ClientService(mockClient);

    //Act
    List<Client> actual = clientService.getAllClients();//this will return null by default

    //Assert
    List<Client> expected = new ArrayList<>(fakeClients);
    Assertions.assertEquals(expected, actual);

    }

    //positive test "testing as expected"
    @Test
    public void testGetStudentById() throws SQLException, ClientNotFoundException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        Client fakeClient = new Client(1,"test", "client", 44, "1478523699");

        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        ClientService clientService = new ClientService(mockClient);

        //Act
        Client actual = clientService.getClientByID("1");

        //Assert
        Client expected = fakeClient;
        Assertions.assertEquals(expected, actual);

    }

    //Negative Test for an id that doesn't exist
    @Test
    public void testGetStudentById_notExistentId() throws ClientNotFoundException, SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockClient);

        //Act + Assert
        //we use assertThrows when we want to see if an exception were thrown
        Assertions.assertThrows(ClientNotFoundException.class, () ->{
            clientService.getClientByID("100");
        });
    }

    //Negative test for an incorrect ID
    @Test
    public void testGetStudentById_incorrectId(){
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockClient);

        //Act + Assert
        //we use assertThrows when we want to see if an exception were thrown
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            clientService.getClientByID("aaa");
        });
    }

    @Test
    public void testGetStudentById_SQLException() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        when(mockClient.getClientById(anyInt())).thenThrow(SQLException.class);

        ClientService clientService = new ClientService(mockClient);

        //Act + Assert
        //we use assertThrows when we want to see if an exception were thrown
        Assertions.assertThrows(SQLException.class, () ->{
            clientService.getClientByID("10");
        });
    }

    @Test
    public void testAddClient_positive() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        Client fakeClient = new Client(0,"test", "client", 44, "1478523699");

        when(mockClient.addClient(eq(fakeClient)))
                .thenReturn(new Client(10,"test", "client", 44, "1478523699"));

        ClientService clientService = new ClientService(mockClient);

        //Act
        Client actual = clientService.addClient(fakeClient);

        //Assert
        fakeClient.setId(10);
        Client expected = fakeClient;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testAddClient_Trim_positive() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        Client fakeClient = new Client(0,"test", "client", 44, "1478523699");

        when(mockClient.addClient(eq(fakeClient)))
                .thenReturn(new Client(10,"test", "client", 44, "1478523699"));

        ClientService clientService = new ClientService(mockClient);

        //Act
        Client fakeTrimClient = new Client(0,"  test  ", "  client  ", 44, "  1478523699  ");

        Client actual = clientService.addClient(fakeTrimClient);

        //Assert
        fakeClient.setId(10);
        Client expected = fakeClient;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testAddClient_NoLettersCharactersOnFirst_And_Lastname() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);
        ClientService clientService = new ClientService(mockClient);

        //
        Client fakeClient = new Client(0,"Name147", "hello547", 55, "4781561235");

        //Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(fakeClient);
        });
    }

    @Test
    public void testAddClient_NoNumbersOn_Phone_number() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);
        ClientService clientService = new ClientService(mockClient);

        //
        Client fakeClient = new Client(0,"name", "hello", 55, "abc147584");

        //Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(fakeClient);
        });
    }

    @Test
    public void testAddClient_AgeLowerThan_zero() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);
        ClientService clientService = new ClientService(mockClient);

        //
        Client fakeClient = new Client(0,"name", "hello", -2, "1234567897");

        //Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(fakeClient);
        });
    }

    @Test
    public void testUpdateClient_positive() throws SQLException, ClientNotFoundException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        Client fakeClient = new Client(1,"test", "client", 44, "1478523699");

        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        when(mockClient.updateClient(eq(fakeClient)))
                .thenReturn(fakeClient);

        ClientService clientService = new ClientService(mockClient);

        //Act
        Client actual = clientService.updateClient("1", fakeClient);

        //Assert
        Client expected = fakeClient;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateClient_Trim_positive() throws ClientNotFoundException, SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        Client fakeClient = new Client(1,"test", "client", 44, "1478523699");

        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        when(mockClient.updateClient(eq(fakeClient)))
                .thenReturn(new Client(1,"test", "client", 44, "1478523699"));

        ClientService clientService = new ClientService(mockClient);

        //Act
        Client fakeTrimClient = new Client(0,"  test  ", "  client  ", 44, "  1478523699  ");

        Client actual = clientService.updateClient("1", fakeTrimClient);

        //Assert
        Client expected = fakeClient;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateClient_NoLettersCharactersOnFirst_And_Lastname() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);
        ClientService clientService = new ClientService(mockClient);

        Client fakeClient = new Client(1,"Name147", "hello547", 55, "4781561235");

        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        //Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(fakeClient);
        });
    }

    @Test
    public void testUpdateClient_NoNumbersOn_Phone_number() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);
        ClientService clientService = new ClientService(mockClient);

        Client fakeClient = new Client(1,"Name", "hello", 55, "abc324234");

        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        //Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(fakeClient);
        });
    }

    @Test
    public void testUpdateClient_AgeLowerThan_zero() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);
        ClientService clientService = new ClientService(mockClient);

        Client fakeClient = new Client(1,"Name", "hello", 55, "abc324234");

        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);

        //Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(fakeClient);
        });
    }

    @Test
    public  void testDeleteClientById_positive() throws SQLException, ClientNotFoundException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        Client fakeClient = new Client(1,"test", "client", 44, "1478523699");

        when(mockClient.getClientById(eq(1))).thenReturn(fakeClient);
        when(mockClient.deleteClientById(eq(1))).thenReturn(true);

        ClientService clientService = new ClientService(mockClient);

        //Act
        boolean actual = clientService.deleteClientById("1");

        //Assert
        Assertions.assertEquals(true, actual);

    }

    @Test
    public  void testDeleteClientById_NonExistentId() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        when(mockClient.getClientById(eq(100))).thenReturn(null);

        ClientService clientService = new ClientService(mockClient);

        //Act + Assert
        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            clientService.deleteClientById("100");
        });
    }

    @Test
    public  void testDeleteClientById_invalidId() throws SQLException {
        //Arrange
        ClientDao mockClient = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockClient);

        //Act + Assert
        //we use assertThrows when we want to see if an exception were thrown
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            clientService.deleteClientById("aaa");
        });
    }
}
