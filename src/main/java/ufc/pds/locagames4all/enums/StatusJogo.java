package ufc.pds.locagames4all.enums;

import lombok.Getter;

@Getter
public enum StatusJogo {

    DISPONIVEL("Descrição"), INDISPONIVEL("Indisponivel"), ALUGADO("Alugado");

    private String descricao;

    StatusJogo(String status) {
        this.descricao = status;
    }
}
