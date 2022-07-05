package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.dto.ClienteDTO;
import ufc.pds.locagames4all.dto.JogoDTO;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.service.ClienteService;

import java.net.URI;
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
    @Operation(summary = "Cadastrar cliente'",
            description = "Permite o cadastro de clientes.<br>Retorna o cliente criado com sua location.")
    public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente clienteCriado = clienteService.cadastrarCliente(clienteDTO);
        URI clienteURI = linkTo(methodOn(ClienteController.class).buscarClientePorCpf(clienteCriado.getCpf())).toUri();
        return ResponseEntity.created(clienteURI).body(toDTO(clienteCriado));
    }

    @GetMapping
    @Operation(summary = "Buscar clientes'",
            description = "Permite a busca de todos os clientes.<br>" +
                    "Retorna a lista com todos os clientes cadastrados.")
    public List<ClienteDTO> buscarClientes() {
        return toCollectionDTO(clienteService.buscarTodosCLientes());
    }

    @GetMapping("/{cpf}")
    @Operation(summary = "Buscar cliente por CPF",
            description = "Permite a busca de um cliente pelo CPF.<br>" +
                    "Retorna o cliente cadastrado para o CPF informado.")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(
            @Parameter(description = "cpf do cliente que será buscado")
            @PathVariable String cpf) {
        return ResponseEntity.ok().body(toDTO(clienteService.buscarClientePorCpf(cpf)));
    }

    @PutMapping("/{cpf}")
    @Operation(summary = "Atualizar cliente por CPF",
            description = "Permite a busca de um cliente pelo CPF.<br>" +
                    "Retorna o cliente cadastrado para o CPF informado.")
    public ResponseEntity<ClienteDTO> atualizarCliente(
            @Parameter(description = "cpf do cliente que será atualizado")
            @PathVariable String cpf,
            @RequestBody ClienteDTO clienteDTOAtualizado) {
        if (!cpf.equals(clienteDTOAtualizado.getCpf())) {
            throw new UnsupportedOperationException("CPF do path e do body da requisição precisam ser iguais");
        }
        return ResponseEntity.ok().body(toDTO(clienteService.atualizaCliente(clienteDTOAtualizado)));
    }

    @DeleteMapping("/{cpf}")
    @Operation(summary = "Desativar cliente por CPF",
            description = "Permite que o cliente tenha seus dados apagados e o cadastro desativado.<br>" +
                    "Retorna o cliente com dados atualizados e cadastro com atributo 'excluido = true'.")
    public ResponseEntity<ClienteDTO> desativarCliente(
            @Parameter(description = "cpf do cliente que será desativado.")
            @PathVariable String cpf) {
        return ResponseEntity.ok().body(toDTO(clienteService.desativaCliente(cpf)));
    }

    @PatchMapping("/{cpf}/jogosfavoritos/{jogoId}")
    @Operation(summary = "Favoritar jogo",
            description = "Permite que o cliente favorite um jogo pelo id deste. Um jogo favoritado não pode ser " +
                    "favoritado novamente, da mesma forma, se não for um favorito não pode ser desfavoritado.<br>" +
                    "Retorna o cliente com dados atualizados, incluindo o jogo favoritado ou desfavoritado.")
    public ResponseEntity<?> favoritarJogo(
            @Parameter(description = "cpf do cliente que irá favoritar o jogo.")
            @PathVariable String cpf,
            @Parameter(description = "id do jogo que será favoritado.")
            @PathVariable Long jogoId,
            @Parameter(description = "favoritar igual a true para favoritar, false para desfavoritar.")
            @RequestParam(name = "favoritar") boolean favoritar
    ) {
        if (favoritar) {
            return ResponseEntity.ok().body(toDTO(clienteService.favoritarJogo(cpf, jogoId)));
        } else {
            return ResponseEntity.ok().body(toDTO(clienteService.desfavoritarJogo(cpf, jogoId)));
        }
    }

    @GetMapping("/{cpf}/jogosfavoritos")
    @Operation(summary = "Listar jogos favoritos",
            description = "Permite listar os jogos favoritos de um cliente pelo cpf.<br>" +
                    "Retorna a lista de jogos favoritos.")
    public ResponseEntity<?> buscarJogosFavoritos(
            @Parameter(description = "cpf do cliente para busca de favoritos")
            @PathVariable String cpf) {
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

        List<Jogo> jogosFavoritos = cliente.getJogosFavoritos();
        if (jogosFavoritos != null) {
            List<JogoDTO> jogosDTO = jogoController.toCollectionDTO(jogosFavoritos);
            clienteDTO.setJogosFavoritos(jogosDTO);
        }
        return clienteDTO;
    }

    public List<ClienteDTO> toCollectionDTO(List<Cliente> clientes) {
        return clientes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
