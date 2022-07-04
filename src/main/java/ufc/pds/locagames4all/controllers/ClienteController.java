package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.dto.ClienteDTO;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrarCliente(clienteDTO));
    }

    @GetMapping
    //http://localhost:8080/clientes
    public List<Cliente> buscarClientes() {
        return clienteService.buscarTodosCLientes();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarClientePorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok().body(clienteService.buscarClientePorCpf(cpf));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable String cpf, @RequestBody ClienteDTO clienteDTOAtualizado) {
        if (!cpf.equals(clienteDTOAtualizado.getCpf())) {
            throw new UnsupportedOperationException("CPF do path e do body da requisição precisam ser iguais");
        }
        return ResponseEntity.ok().body(clienteService.atualizaCliente(clienteDTOAtualizado));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Cliente> desativarCliente(@PathVariable String cpf) {
        return ResponseEntity.ok().body(clienteService.desativaCliente(cpf));
    }

    @PatchMapping("/{cpf}/jogosfavoritos/{jogoId}")
    public ResponseEntity<?> favoritarJogo(
            @PathVariable String cpf,
            @PathVariable Long jogoId,
            @RequestParam(name = "favoritar") boolean favoritar
    ) {
        if (favoritar) {
            return ResponseEntity.ok().body(clienteService.favoritarJogo(cpf, jogoId));
        } else {
            return ResponseEntity.ok().body(clienteService.desfavoritarJogo(cpf, jogoId));
        }
    }

    @GetMapping("/{cpf}/jogosfavoritos")
    public ResponseEntity<?> listarJogosFavoritos(@PathVariable String cpf) {
        return ResponseEntity.ok().body(clienteService.listarJogosFavoritos(cpf));
    }
}
