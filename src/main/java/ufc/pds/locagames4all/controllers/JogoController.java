package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    //http://localhost:8080/jogos
    public List<Jogo> buscarJogos(){
        return jogoService.buscarTodosJogos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarJogoPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(jogoService.buscarJogoPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Jogo>> buscarJogosPorTipo(@PathVariable TipoJogo tipo){
        return ResponseEntity.ok().body(jogoService.buscarJogosPorTipo(tipo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Jogo>> buscarJogosPorStatus(@PathVariable StatusJogo status){
        return ResponseEntity.ok().body(jogoService.buscarJogosPorStatus(status));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Jogo> excluirJogo(@PathVariable Long id){
        return ResponseEntity.ok().body(jogoService.excluirJogo(id));
    }
}