package locadora;

import java.time.LocalDate;

public class Corretiva extends Manutencao {

    private double valorPecasDanificadas;

    public Corretiva(int idPedido, Carro carro, LocalDate dataInicio, LocalDate previsaoFim,
                     String estacaoDeServico, double valor, double valorPecasDanificadas) {
        super(idPedido, carro, dataInicio, previsaoFim, estacaoDeServico, valor);
        this.valorPecasDanificadas = valorPecasDanificadas;
    }

    @Override
    public double calcularValorTotal() {
        return valor + valorPecasDanificadas;
    }

    public double getValorPecasDanificadas() { return valorPecasDanificadas; }
}
