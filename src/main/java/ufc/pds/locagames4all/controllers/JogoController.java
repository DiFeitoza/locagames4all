package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.enums.StatusJogo;
import ufc.pds.locagames4all.enums.TipoJogo;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.service.JogoService;

import java.util.List;

@RestController
@RequestMapping("/jogos")
@Tag(name = "Jogo")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @PostMapping
    @Operation(summary = "Cadastrar jogo", description = "Permite o cadastro de jogos." +
            "<br>Retorna a location do jogo cadastrado." +
            "<br><small>Instruções: clique em 'try it out', manipule o 'body' e clique no botão 'Execute'.")
    public ResponseEntity<Jogo> cadastrarJogo(@RequestBody Jogo jogo){
        return  ResponseEntity.status(HttpStatus.CREATED).body(jogoService.cadastrarJogo(jogo));
    }

    @GetMapping
    @Operation(summary = "Buscar jogos", description = "Permite a busca de todos os jogos." +
            "<br>Retorna a lista com todos os jogos do sistema." +
            "<br><small>Instruções: clique em 'try it out' e clique no botão 'Execute'.")
    public List<Jogo> buscarJogos(){
        return jogoService.buscarTodosJogos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar jogo por 'id'", description = "Permite a busca de um jogo por seu 'id'." +
            "<br>Retorna o jogo caso o id informado corresponda a um jogo cadastrado." +
            "<br><small>Instruções: clique em 'try it out', informe 'id' e clique no botão 'Execute'.")
    public ResponseEntity<Jogo> buscarJogoPorId(
            @Parameter(description = "id do jogo que será buscado")
            @PathVariable Long id){
        return ResponseEntity.ok().body(jogoService.buscarJogoPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Buscar jogo por 'tipo'", description = "Permite a busca de jogos pelo 'tipo'." +
            "<br>Retorna todos os jogos com o 'tipo' informado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'tipo' e clique no botão 'Execute'.")
    public ResponseEntity<List<Jogo>> buscarJogosPorTipo(
            @Parameter(description = "tipo do jogo que será buscado")
            @PathVariable TipoJogo tipo){
        return ResponseEntity.ok().body(jogoService.buscarJogosPorTipo(tipo));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar jogos por 'status'", description = "Permite a busca de jogos pelo 'status'." +
            "<br>Retorna todos os jogos com o 'status' informado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'status' e clique no botão 'Execute'.")
    public ResponseEntity<List<Jogo>> buscarJogosPorStatus(
            @Parameter(description = "status do jogo que será buscado'")
            @PathVariable StatusJogo status){
        return ResponseEntity.ok().body(jogoService.buscarJogosPorStatus(status));
    }

    @GetMapping("/qtdjogadores/{quantidade}")
    @Operation(summary = "Buscar jogos pela quantidade de jogadores", description = "Permite a busca de jogos pela " +
            "quantidade de jogadores que pretendem jogar.<br>Retorna todos os jogos que permitam a quantidade " +
            "informada entre a sua quantidade mínima e máxima de jogadores permitidos." +
            "<br><small>Instruções: clique em 'try it out', informe a 'quantidade' e clique no botão 'Execute'.")
    public ResponseEntity<List<Jogo>> buscarJogosPorQtdJogadores(
            @Parameter(description = "quantidade de jogadores da busca")
            @PathVariable Integer quantidade){
        return ResponseEntity.ok().body(jogoService.buscarJogosPorQtdJogadores(quantidade));
    }

    @GetMapping("/valordiaria")
    @Operation(summary = "Buscar jogos por 'valor de diária'", description = "Permite a busca de jogos pelo valor da " +
            "diária.<br>Retorna todos os jogos com valor igual ou menor ao informado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'valor' e clique no botão 'Execute'.")
    public ResponseEntity<List<Jogo>> buscarJogosPorValorDiaria(
            @Parameter(description = "valor de busca")
            @RequestParam("valor") Double valor){
        return ResponseEntity.ok().body(jogoService.buscarJogosPorValorDiaria(valor));
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar jogos por 'nome'", description = "Permite a busca de jogos por parte do nome." +
            "<br>Retorna todos os jogos que contenham o texto informado como parte do nome do jogo." +
            "<br><small>Instruções: clique em 'try it out', informe o 'texto' e clique no botão 'Execute'.")
    public ResponseEntity<List<Jogo>> buscarJogosPorNome(
            @Parameter(description = "texto para busca no nome dos jogos")
            @RequestParam("texto") String texto){
        return ResponseEntity.ok().body(jogoService.buscarJogosPorNome(texto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar jogos por 'id'", description = "Permite a atualização dos dados de um jogo no " +
            "sistema.<br>Retorna o objeto atualizado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'id', manipule o 'body' e " +
            "clique no botão 'Execute'.")
    public ResponseEntity<Jogo> atualizarJogo(
            @Parameter(description = "id do jogo que será atualizado")
            @PathVariable Long id, @RequestBody Jogo jogoAtualizado){
        if(id.equals(jogoAtualizado.getId())){
            return ResponseEntity.ok().body(jogoService.atualizarJogo(jogoAtualizado));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping ("/{id}")
    @Operation(summary = "Desativar jogos por 'id'", description = "Permite que um jogo seja desativado do sistema" +
            "<br>Retorna os dados do jogo que foi desativado." +
            "<br><small>Instruções: clique em 'try it out', informe o 'id' e clique no botão 'Execute'.")
    public ResponseEntity<Jogo> excluirJogo(
            @Parameter(description = "id do jogo que será desativado")
            @PathVariable Long id){
        return ResponseEntity.ok().body(jogoService.excluirJogo(id));
    }
}