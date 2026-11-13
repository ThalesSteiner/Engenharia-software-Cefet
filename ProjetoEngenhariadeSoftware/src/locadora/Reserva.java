package locadora;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {

    public enum Status { ABERTA, EXECUTANDO, FINALIZADA, CANCELADA }

    private final String cpf;
    private String cor;
    private String modelo;
    private String chassi;
    private LocalDate dtRetirada;
    private LocalDate dtDevolucao;
    private String marca;
    private final Carro.Grupo grupo;
    private String cnhCondutor;
    private int kmsPretendidos;

    private Status status = Status.ABERTA;
    private Carro carro;
    private Contrato contrato;
    private final List<Pagamento> pagamentos = new ArrayList<>();

    public Reserva(String cpf, Carro.Grupo grupo, LocalDate dtRetirada,
                   LocalDate dtDevolucao, String cnhCondutor, int kmsPretendidos) {
        this.cpf = cpf;
        this.grupo = grupo;
        this.dtRetirada = dtRetirada;
        this.dtDevolucao = dtDevolucao;
        this.cnhCondutor = cnhCondutor;
        this.kmsPretendidos = kmsPretendidos;
    }

    public void escolherCarro(Carro carro) {
        if (status != Status.ABERTA) {
            throw new IllegalStateException("A reserva não está aberta");
        }
        if (!carro.isDisponivel()) {
            throw new IllegalStateException("Carro indisponível");
        }
        if (carro.getGrupo() != grupo) {
            throw new IllegalArgumentException("Carro de grupo diferente do reservado");
        }
        this.carro = carro;
        this.chassi = carro.getChassi();
        this.cor = carro.getCor();
        this.modelo = carro.getModelo();
        this.marca = carro.getMarca();
    }

    public Pagamento gerarPagamento(FormaPagamento forma) {
        exigirCarro();
        Pagamento p = new Pagamento(cpf, calcularValor(), forma);
        pagamentos.add(p);
        return p;
    }

    /** Só executa com carro escolhido e pagamento confirmado. */
    public void executarReserva() {
        exigirCarro();
        if (status != Status.ABERTA) {
            throw new IllegalStateException("A reserva não está aberta");
        }
        if (pagamentos.stream().noneMatch(Pagamento::isConfirmado)) {
            throw new IllegalStateException("Nenhum pagamento confirmado");
        }
        carro.atualizarDisponibilidade(Carro.Estado.ALUGADO, null);
        if (carro.getLocalAtual() != null) carro.getLocalAtual().atualizarEstoque();
        status = Status.EXECUTANDO;
    }

    public Contrato gerarContrato(String nome, String cnpjFilial, FormaPagamento forma) {
        exigirCarro();
        this.contrato = new Contrato(nome, cpf, cnpjFilial, (int) dias(), carro,
                calcularValor(), forma);
        return contrato;
    }

    public void informarDtDevolucao(LocalDate novaData) {
        if (novaData.isBefore(dtRetirada)) {
            throw new IllegalArgumentException("Devolução antes da retirada");
        }
        this.dtDevolucao = novaData;
    }

    public void finalizarReserva() {
        if (status != Status.EXECUTANDO) {
            throw new IllegalStateException("Só é possível finalizar uma reserva em execução");
        }
        status = Status.FINALIZADA;
    }

    public void cancelarReserva() {
        if (status == Status.EXECUTANDO || status == Status.FINALIZADA) {
            throw new IllegalStateException("Reserva não pode mais ser cancelada");
        }
        status = Status.CANCELADA;
        if (contrato != null) contrato.cancelarContrato();
    }

    public double calcularValor() {
        double diaria = switch (grupo) {
            case A -> 120.0;
            case B -> 180.0;
            case C -> 260.0;
        };
        return diaria * dias();
    }

    private long dias() {
        return Math.max(1, ChronoUnit.DAYS.between(dtRetirada, dtDevolucao));
    }

    private void exigirCarro() {
        if (carro == null) throw new IllegalStateException("Nenhum carro escolhido");
    }

    public String getCpf() { return cpf; }
    public Status getStatus() { return status; }
    public Carro getCarro() { return carro; }
    public Contrato getContrato() { return contrato; }
    public List<Pagamento> getPagamentos() { return pagamentos; }
    public LocalDate getDtRetirada() { return dtRetirada; }
    public LocalDate getDtDevolucao() { return dtDevolucao; }
    public String getCnhCondutor() { return cnhCondutor; }
    public int getKmsPretendidos() { return kmsPretendidos; }
    public Carro.Grupo getGrupo() { return grupo; }
    public String getChassi() { return chassi; }
    public String getCor() { return cor; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
}
