package com.greenhouse.greenhouse_api.service;

import com.greenhouse.greenhouse_api.model.Client;
import com.greenhouse.greenhouse_api.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    public Client findClient(int id) {
        return clientRepo.findClient(id);
    }

    public List<Client> findUserClients(int userID) {
        return clientRepo.findClientsByUser(userID);
    }

    public ResponseEntity<String> createClient(Client client) {
        return clientRepo.insert(client);
    }

    public ResponseEntity<String> updateClient(Client client) {
        return clientRepo.update(client);
    }

    public ResponseEntity<String> deleteClient(int clientID) {
        return clientRepo.delete(clientID);
    }
}