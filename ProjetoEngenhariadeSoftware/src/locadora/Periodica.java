package locadora;

import java.time.LocalDate;

public class Periodica extends Manutencao {

    private LocalDate previsaoProx;
    private Manutencao manutencaoAnterior;

    public Periodica(int idPedido, Carro carro, LocalDate dataInicio, LocalDate previsaoFim,
                     String estacaoDeServico, double valor, Manutencao manutencaoAnterior) {
        super(idPedido, carro, dataInicio, previsaoFim, estacaoDeServico, valor);
        this.manutencaoAnterior = manutencaoAnterior;
    }

    /** Agenda a próxima revisão daqui a N meses da data de início. */
    public void agendarProxima(int intervaloMeses) {
        this.previsaoProx = dataInicio.plusMonths(intervaloMeses);
    }

    @Override
    public double calcularValorTotal() {
        return valor;
    }

    public LocalDate getPrevisaoProx() { return previsaoProx; }
    public Manutencao getManutencaoAnterior() { return manutencaoAnterior; }
}
