package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.dto.JogoDTO;
import ufc.pds.locagames4all.enums.StatusJogo;
import ufc.pds.locagames4all.enums.TipoJogo;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.service.JogoService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jogos")
@Tag(name = "Jogo")
public class JogoController {
    @Autowired
    private JogoService jogoService;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    @Operation(summary = "Cadastrar jogo", description = "Permite o cadastro de jogos." +
            "<br>Retorna a location do jogo cadastrado." +
            "<br><small>Instruções: clique em 'try it out', manipule o 'body' e clique no botão 'Execute'.")
    public ResponseEntity<?> cadastrarJogo(@RequestBody Jogo jogo) {
        Jogo jogoCriado = jogoService.cadastrarJogo(jogo);
        URI jogoURI = linkTo(methodOn(JogoController.class).buscarJogoPorId(jogoCriado.getId())).toUri();
        return ResponseEntity.created(jogoURI).build();
        // TODO: 04/07/2022 verificar a forma correta de retorno!
        /*Jogo jogoCriado = jogoService.cadastrarJogo(jogo);
        URI jogoURI = linkTo(methodOn(JogoController.class).buscarJogoPorId(jogoCriado.getId())).toUri();
        return ResponseEntity.created(jogoURI).body(toDTO(jogoCriado));*/
    }

    @GetMapping
    @Operation(summary = "Buscar jogos", description = "Permite a busca de todos os jogos." +
            "<br>Retorna a lista com todos os jogos do sistema." +
            "<br><small>Instruções: clique em 'try it out' e clique no botão 'Execute'.")
    public ResponseEntity<List<JogoDTO>> buscarJogos() {
        return ResponseEntity.ok().body(toCollectionDTO(jogoService.buscarTodosJogos()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar jogo por 'id'", description = "Permite a busca de um jogo por seu 'id'." +
            "<br>Retorna o jogo caso o id informado corresponda a um jogo cadastrado." +
            "<br><small>Instruções: clique em 'try it out', informe 'id' e clique no botão 'Execute'.")
    public ResponseEntity<JogoDTO> buscarJogoPorId(
            @Parameter(description = "id do jogo que será buscado")
            @PathVariable Long id) {
        return ResponseEntity.ok().body(toDTO(jogoService.buscarJogoPorId(id)));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Buscar jogo por 'tipo'", description = "Permite a busca de jogos pelo 'tipo'." +
            "<br>Retorna todos os jogos com o 'tipo' informado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'tipo' e clique no botão 'Execute'.")
    public ResponseEntity<List<JogoDTO>> buscarJogosPorTipo(
            @Parameter(description = "tipo do jogo que será buscado")
            @PathVariable TipoJogo tipo) {
        return ResponseEntity.ok().body(toCollectionDTO(jogoService.buscarJogosPorTipo(tipo)));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar jogos por 'status'", description = "Permite a busca de jogos pelo 'status'." +
            "<br>Retorna todos os jogos com o 'status' informado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'status' e clique no botão 'Execute'.")
    public ResponseEntity<List<JogoDTO>> buscarJogosPorStatus(
            @Parameter(description = "status do jogo que será buscado'")
            @PathVariable StatusJogo status) {
        return ResponseEntity.ok().body(toCollectionDTO(jogoService.buscarJogosPorStatus(status)));
    }

    @GetMapping("/qtdjogadores/{quantidade}")
    @Operation(summary = "Buscar jogos pela quantidade de jogadores", description = "Permite a busca de jogos pela " +
            "quantidade de jogadores que pretendem jogar.<br>Retorna todos os jogos que permitam a quantidade " +
            "informada entre a sua quantidade mínima e máxima de jogadores permitidos." +
            "<br><small>Instruções: clique em 'try it out', informe a 'quantidade' e clique no botão 'Execute'.")
    public ResponseEntity<List<JogoDTO>> buscarJogosPorQtdJogadores(
            @Parameter(description = "quantidade de jogadores da busca")
            @PathVariable Integer quantidade) {
        return ResponseEntity.ok().body(toCollectionDTO(jogoService.buscarJogosPorQtdJogadores(quantidade)));
    }

    @GetMapping("/valordiaria")
    @Operation(summary = "Buscar jogos por 'valor de diária'", description = "Permite a busca de jogos pelo valor da " +
            "diária.<br>Retorna todos os jogos com valor igual ou menor ao informado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'valor' e clique no botão 'Execute'.")
    public ResponseEntity<List<JogoDTO>> buscarJogosPorValorDiaria(
            @Parameter(description = "valor de busca")
            @RequestParam("valor") Double valor) {
        return ResponseEntity.ok().body(toCollectionDTO(jogoService.buscarJogosPorValorDiaria(valor)));
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar jogos por 'nome'", description = "Permite a busca de jogos por parte do nome." +
            "<br>Retorna todos os jogos que contenham o texto informado como parte do nome do jogo." +
            "<br><small>Instruções: clique em 'try it out', informe o 'texto' e clique no botão 'Execute'.")
    public ResponseEntity<List<JogoDTO>> buscarJogosPorNome(
            @Parameter(description = "texto para busca no atributo 'nome' dos jogos")
            @RequestParam("texto") String texto) {
        return ResponseEntity.ok().body(toCollectionDTO(jogoService.buscarJogosPorNome(texto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar jogos por 'id'", description = "Permite a atualização dos dados de um jogo no " +
            "sistema.<br>Retorna o objeto atualizado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'id', manipule o 'body' e " +
            "clique no botão 'Execute'.")
    public ResponseEntity<?> atualizarJogo(
            @Parameter(description = "id do jogo que será atualizado")
            @PathVariable Long id, @RequestBody Jogo jogoAtualizado) {
        if (id.equals(jogoAtualizado.getId())) {
            return ResponseEntity.ok().body(toDTO(jogoService.atualizarJogo(jogoAtualizado)));
        } else {
            return ResponseEntity.badRequest().body("O id informado no PATH deve ser o mesmo do jogo informado.");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar jogos por 'id'", description = "Permite que um jogo seja desativado do sistema" +
            "<br>Retorna os dados do jogo que foi desativado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'id' e clique no botão 'Execute'.")
    public ResponseEntity<JogoDTO> excluirJogo(
            @Parameter(description = "id do jogo que será desativado")
            @PathVariable Long id) {
        return ResponseEntity.ok().body(toDTO(jogoService.excluirJogo(id)));
    }

    public JogoDTO toDTO(Jogo jogo) {
        JogoDTO jogoDTO = modelMapper.map(jogo, JogoDTO.class);
        jogoDTO.add(linkTo(methodOn(JogoController.class)
                .buscarJogoPorId(jogo.getId())).withSelfRel());
        jogoDTO.add(linkTo(methodOn(JogoController.class)
                .buscarJogos()).withRel("buscar-jogos"));
        return jogoDTO;
    }

    public List<JogoDTO> toCollectionDTO(List<Jogo> jogos) {
        return jogos.stream().map(this::toDTO).collect(Collectors.toList());
    }

}