package locadora;

import java.time.LocalDate;

public class Indisponivel {

    public enum Tipo { MANUTENCAO, RESERVADO, DEVOLUCAO_PENDENTE, OUTRO }

    private Tipo tipoDisponibilidade;
    private LocalDate data;

    public Indisponivel(Tipo tipoDisponibilidade, LocalDate data) {
        this.tipoDisponibilidade = tipoDisponibilidade;
        this.data = data;
    }

    public Tipo getTipoDisponibilidade() { return tipoDisponibilidade; }
    public LocalDate getData() { return data; }
}
