package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.dto.ClienteDTO;
import ufc.pds.locagames4all.dto.JogoDTO;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.service.ClienteService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private JogoController jogoController;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrarCliente(clienteDTO));
    }

    @GetMapping
    //http://localhost:8080/clientes
    public List<ClienteDTO> buscarClientes() {
        return toCollectionDTO(clienteService.buscarTodosCLientes());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok().body(toDTO(clienteService.buscarClientePorCpf(cpf)));
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> atualizarCliente(
            @PathVariable String cpf, @RequestBody ClienteDTO clienteDTOAtualizado) {
        if (!cpf.equals(clienteDTOAtualizado.getCpf())) {
            throw new UnsupportedOperationException("CPF do path e do body da requisição precisam ser iguais");
        }
        return ResponseEntity.ok().body(toDTO(clienteService.atualizaCliente(clienteDTOAtualizado)));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<ClienteDTO> desativarCliente(@PathVariable String cpf) {
        return ResponseEntity.ok().body(toDTO(clienteService.desativaCliente(cpf)));
    }

    @PatchMapping("/{cpf}/jogosfavoritos/{jogoId}")
    public ResponseEntity<?> favoritarJogo(
            @PathVariable String cpf,
            @PathVariable Long jogoId,
            @RequestParam(name = "favoritar") boolean favoritar
    ) {
        if (favoritar) {
            return ResponseEntity.ok().body(toDTO(clienteService.favoritarJogo(cpf, jogoId)));
        } else {
            return ResponseEntity.ok().body(toDTO(clienteService.desfavoritarJogo(cpf, jogoId)));
        }
    }

    @GetMapping("/{cpf}/jogosfavoritos")
    public ResponseEntity<?> buscarJogosFavoritos(@PathVariable String cpf) {
        return ResponseEntity.ok().body(jogoController.toCollectionDTO(clienteService.listarJogosFavoritos(cpf)));
    }

    public ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
        clienteDTO.add(linkTo(methodOn(ClienteController.class)
                .buscarClientePorCpf(clienteDTO.getCpf())).withSelfRel());
        clienteDTO.add(linkTo(methodOn(ClienteController.class)
                .buscarClientes()).withRel("buscar-clientes"));
        clienteDTO.add(linkTo(methodOn(ClienteController.class)
                .buscarJogosFavoritos(clienteDTO.getCpf())).withRel("buscar-favoritos"));
        List<JogoDTO> jogosDTO = jogoController.toCollectionDTO(cliente.getJogosFavoritos());
        clienteDTO.setJogosFavoritos(jogosDTO);
        return clienteDTO;
    }

    public List<ClienteDTO> toCollectionDTO(List<Cliente> clientes) {
        return clientes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
