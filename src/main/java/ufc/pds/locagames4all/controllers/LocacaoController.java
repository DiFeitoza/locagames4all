package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.dto.LocacaoDTO;
import ufc.pds.locagames4all.model.Locacao;
import ufc.pds.locagames4all.service.LocacaoService;

import java.util.List;

@RestController
@RequestMapping("/locacoes")
@Tag(name = "Locacao")
public class LocacaoController {
    @Autowired
    private LocacaoService locacaoService;

    @PostMapping
    public ResponseEntity<Locacao> cadastrarLocacao(@RequestBody LocacaoDTO locacaoDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locacaoService.cadastrarLocacao(locacaoDTO));
    }

    @GetMapping
    public ResponseEntity<List<Locacao>> buscarLocacoes() {
        return ResponseEntity.ok().body(locacaoService.buscarTodasLocacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacao> buscarLocacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(locacaoService.buscarLocacoesPorId(id));
    }

    @GetMapping("/jogoid/{jogoId}")
    public ResponseEntity<List<Locacao>> buscarHistoricoDeLocacoesPorJogoId(@PathVariable Long jogoId) {
        List<Locacao> locacoes = locacaoService.buscarHistoricoDeLocacoesPorJogoId(jogoId);
        return ResponseEntity.ok().body(locacoes);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<Locacao>> buscarHistoricoDeLocacoesPorCPF(
            @PathVariable String cpf,
            @RequestParam(name = "locacaoativa", required = false, defaultValue = "false") boolean locacaoativa) {
        if (locacaoativa) {
            return ResponseEntity.ok().body(locacaoService.buscarLocacoesAtivasPorCPF(cpf));
        } else {
            return ResponseEntity.ok().body(locacaoService.buscarHistoricoDeLocacoesPorCPF(cpf));
        }
    }

    @GetMapping("/{cpf}/{jogoId}")
    public ResponseEntity<List<Locacao>> buscarLocacoesPorCPFeJogoId(
            @PathVariable String cpf, @PathVariable Long jogoId) {
        return ResponseEntity.ok().body(locacaoService.buscarLocacoesPorCPFeJogoId(cpf, jogoId));
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<Locacao> consultarLocacaoParaDevolucao(@PathVariable Long id) {
        return ResponseEntity.ok().body(locacaoService.consultarLocacaoParaDevolucao(id));
    }

    @PatchMapping("/devolucao/{id}")
    public ResponseEntity<Locacao> devolverLocacao(@PathVariable Long id) {
        return ResponseEntity.ok().body(locacaoService.devolverLocacao(id));
    }
}
