package locadora;

import java.time.LocalDate;

public abstract class Manutencao {

    public enum Situacao { ABERTA, EM_ANDAMENTO, FINALIZADA }

    protected int idPedido;
    protected LocalDate dataInicio;
    protected LocalDate previsaoFim;
    protected String estacaoDeServico;
    protected Situacao situacao;
    protected double valor;
    protected Carro carro;

    protected Manutencao(int idPedido, Carro carro, LocalDate dataInicio,
                         LocalDate previsaoFim, String estacaoDeServico, double valor) {
        this.idPedido = idPedido;
        this.carro = carro;
        this.dataInicio = dataInicio;
        this.previsaoFim = previsaoFim;
        this.estacaoDeServico = estacaoDeServico;
        this.valor = valor;
        this.situacao = Situacao.ABERTA;
    }

    public void alterarManutencao(LocalDate previsaoFim, String estacaoDeServico, double valor) {
        if (situacao == Situacao.FINALIZADA) {
            throw new IllegalStateException("Manutenção já finalizada");
        }
        if (previsaoFim != null) this.previsaoFim = previsaoFim;
        if (estacaoDeServico != null) this.estacaoDeServico = estacaoDeServico;
        if (valor > 0) this.valor = valor;
    }

    public String verManutencao() {
        return String.format("Pedido #%d | carro %s | %s -> %s | %s | %s | R$ %.2f",
                idPedido, carro.getChassi(), dataInicio, previsaoFim,
                estacaoDeServico, situacao, calcularValorTotal());
    }

    public void realizarManutencao() {
        situacao = Situacao.EM_ANDAMENTO;
        carro.atualizarDisponibilidade(Carro.Estado.EM_MANUTENCAO,
                new Indisponivel(Indisponivel.Tipo.MANUTENCAO, dataInicio));
        if (carro.getLocalAtual() != null) carro.getLocalAtual().atualizarEstoque();
    }

    public void finalizarManutencao() {
        situacao = Situacao.FINALIZADA;
        carro.atualizarDisponibilidade(Carro.Estado.DISPONIVEL, null);
        if (carro.getLocalAtual() != null) carro.getLocalAtual().atualizarEstoque();
    }

    /** Cada tipo de manutenção calcula o seu total. */
    public abstract double calcularValorTotal();

    public int getIdPedido() { return idPedido; }
    public Situacao getSituacao() { return situacao; }
    public Carro getCarro() { return carro; }
    public double getValor() { return valor; }
}
