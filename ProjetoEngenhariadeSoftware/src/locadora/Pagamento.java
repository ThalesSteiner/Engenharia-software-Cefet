package locadora;

public class Pagamento {

    public enum Situacao { PENDENTE, CONFIRMADO, RECUSADO }

    private final String cpf;
    private final double valor;
    private final FormaPagamento formaPagamento;
    private Situacao situacao = Situacao.PENDENTE;

    public Pagamento(String cpf, double valor, FormaPagamento formaPagamento) {
        this.cpf = cpf;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }

    public void confirmarPagamento() {
        this.situacao = Situacao.CONFIRMADO;
    }

    /** Emite a nota fiscal; só é possível após a confirmação do pagamento. */
    public NFS emitirNFS(String cnpj, String banco) {
        if (!isConfirmado()) {
            throw new IllegalStateException("Pagamento ainda não confirmado");
        }
        return new NFS(cpf, cnpj, valor, banco, formaPagamento, java.time.LocalDate.now());
    }

    public boolean isConfirmado() { return situacao == Situacao.CONFIRMADO; }

    public String getCpf() { return cpf; }
    public double getValor() { return valor; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public Situacao getSituacao() { return situacao; }
}
