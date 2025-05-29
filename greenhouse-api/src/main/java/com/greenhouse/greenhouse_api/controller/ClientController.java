package com.greenhouse.greenhouse_api.controller;

import com.greenhouse.greenhouse_api.model.Client;
import com.greenhouse.greenhouse_api.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService clientService) {
        service = clientService;
    }

    @GetMapping("get/one/{id}")
    public ResponseEntity<Client> getClient(@PathVariable int id) {
        Client client = service.findClient(id);
        return new ResponseEntity<>(client, client == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("get/all/{id}")
    public ResponseEntity<List<Client>> getUserClients(@PathVariable int id) {
        List<Client> clients = service.findUserClients(id);
        return new ResponseEntity<>(clients, clients == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("get/all")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = service.getAllClients();
        return new ResponseEntity<>(clients, clients == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @PostMapping("createClient")
    public ResponseEntity<String> createClient(@RequestBody Client client) {
        return service.createClient(client);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateClient(@RequestBody Client client) {
        return service.updateClient(client);
    }

    @DeleteMapping("delete/{clientID}")
    public ResponseEntity<String> deleteClient(@PathVariable int clientID){
        return service.deleteClient(clientID);
    }
}