package ufc.pds.locagames4all.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufc.pds.locagames4all.enums.StatusJogo;
import ufc.pds.locagames4all.enums.TipoJogo;

import javax.persistence.*;

@Entity
@Table(name = "Jogo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoJogo tipo;
    private String descricao;
    private Integer qtdMinJogadores;
    private Integer qtdMaxJogadores;
    private Double valorDiario;
    private Boolean excluido;

    @Enumerated(EnumType.STRING)
    private StatusJogo status;

    public Jogo (String nome, TipoJogo tipo, String descricao, Integer qtdMinJogadores, Integer qtdMaxJogadores, Double valorDiario) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.qtdMaxJogadores = qtdMaxJogadores;
        this.qtdMinJogadores = qtdMinJogadores;
        this.valorDiario=valorDiario;
        this.excluido=false;
        this.status=StatusJogo.DISPONIVEL;
    }
}
