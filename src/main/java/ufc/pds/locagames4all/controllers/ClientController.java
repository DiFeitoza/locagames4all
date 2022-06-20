package ufc.pds.locagames4all.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    //http://localhost:8080/clientes
    public List<Cliente> buscarClientes() {
        return clienteService.buscarTodosCLientes();
    }
}
