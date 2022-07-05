package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.dto.LocacaoDTO;
import ufc.pds.locagames4all.model.Locacao;
import ufc.pds.locagames4all.service.LocacaoService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/locacoes")
@Tag(name = "Locacao")
public class LocacaoController {
    @Autowired
    private LocacaoService locacaoService;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<LocacaoDTO> cadastrarLocacao(@RequestBody LocacaoDTO locacaoDTO) {
        Locacao locacaoCriada = locacaoService.cadastrarLocacao(locacaoDTO);
        URI locacaoURI = linkTo(methodOn(LocacaoController.class).buscarLocacaoPorId(locacaoCriada.getId())).toUri();
        return ResponseEntity.created(locacaoURI).body(toDTO(locacaoCriada));
    }

    @GetMapping
    public ResponseEntity<List<LocacaoDTO>> buscarLocacoes() {
        return ResponseEntity.ok().body(toCollectionDTO(locacaoService.buscarTodasLocacoes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocacaoDTO> buscarLocacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(toDTO(locacaoService.buscarLocacoesPorId(id)));
    }

    @GetMapping("/jogoid/{jogoId}")
    public ResponseEntity<List<LocacaoDTO>> buscarHistoricoDeLocacoesPorJogoId(@PathVariable Long jogoId) {
        List<Locacao> locacoes = locacaoService.buscarHistoricoDeLocacoesPorJogoId(jogoId);
        return ResponseEntity.ok().body(toCollectionDTO(locacoes));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<LocacaoDTO>> buscarHistoricoDeLocacoesPorCPF(
            @PathVariable String cpf,
            @RequestParam(name = "locacaoativa", required = false, defaultValue = "false") boolean locacaoativa) {
        if (locacaoativa) {
            return ResponseEntity.ok().body(toCollectionDTO(locacaoService.buscarLocacoesAtivasPorCPF(cpf)));
        } else {
            return ResponseEntity.ok().body(toCollectionDTO(locacaoService.buscarHistoricoDeLocacoesPorCPF(cpf)));
        }
    }

    @GetMapping("/{cpf}/{jogoId}")
    public ResponseEntity<List<LocacaoDTO>> buscarLocacoesPorCPFeJogoId(
            @PathVariable String cpf, @PathVariable Long jogoId) {
        return ResponseEntity.ok().body(toCollectionDTO(locacaoService.buscarLocacoesPorCPFeJogoId(cpf, jogoId)));
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<LocacaoDTO> consultarLocacaoParaDevolucao(@PathVariable Long id) {
        return ResponseEntity.ok().body(toDTO(locacaoService.consultarLocacaoParaDevolucao(id)));
    }

    @PatchMapping("/devolucao/{id}")
    public ResponseEntity<LocacaoDTO> devolverLocacao(@PathVariable Long id) {
        return ResponseEntity.ok().body(toDTO(locacaoService.devolverLocacao(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirLocacao(@PathVariable Long id) {
        locacaoService.excluirLocacao(id);
        return ResponseEntity.ok().body("Locação excluída com sucesso!");
    }

    public LocacaoDTO toDTO(Locacao locacao) {
        LocacaoDTO locacaoDTO = modelMapper.map(locacao, LocacaoDTO.class);
        locacaoDTO.add(linkTo(methodOn(LocacaoController.class)
                .buscarLocacaoPorId(locacaoDTO.getId())).withSelfRel());
        locacaoDTO.add(linkTo(methodOn(LocacaoController.class)
                .buscarLocacoes()).withRel("buscar-locacoes"));
        locacaoDTO.add(linkTo(methodOn(ClienteController.class)
                .buscarClientePorCpf(locacaoDTO.getClienteCpf())).withRel("cliente-locador"));
        locacaoDTO.add(linkTo(methodOn(JogoController.class)
                .buscarJogoPorId(locacaoDTO.getJogoId())).withRel("jogo-locado"));
        return locacaoDTO;
    }

    public List<LocacaoDTO> toCollectionDTO(List<Locacao> locacoes) {
        return locacoes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
