package ufc.pds.locagames4all.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import ufc.pds.locagames4all.enums.StatusJogo;
import ufc.pds.locagames4all.enums.TipoJogo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class JogoDTO extends RepresentationModel<JogoDTO> {
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoJogo tipo;
    private String descricao;
    private Integer qtdMinJogadores;
    private Integer qtdMaxJogadores;
    private Double valorDiaria;
    private Boolean excluido;
    @Enumerated(EnumType.STRING)
    private StatusJogo status;
}