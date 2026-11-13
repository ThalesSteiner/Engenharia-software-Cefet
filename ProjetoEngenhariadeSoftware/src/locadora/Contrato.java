package locadora;

public class Contrato {

    private final String nome;
    private final String cpf;
    private final String cnpj;
    private final int periodoDeUso; // em dias
    private final Carro carro;
    private final double valor;
    private final FormaPagamento formaPagamento;
    private boolean ativo = true;

    public Contrato(String nome, String cpf, String cnpj, int periodoDeUso,
                    Carro carro, double valor, FormaPagamento formaPagamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.periodoDeUso = periodoDeUso;
        this.carro = carro;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }

    public void cancelarContrato() {
        this.ativo = false;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getCnpj() { return cnpj; }
    public int getPeriodoDeUso() { return periodoDeUso; }
    public Carro getCarro() { return carro; }
    public double getValor() { return valor; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public boolean isAtivo() { return ativo; }
}
