package locadora;

import java.time.LocalDate;

public class NFS {

    private final String cpf;
    private final String cnpj;
    private final double valor;
    private final String banco;
    private final FormaPagamento formaPagamento;
    private final LocalDate data;

    public NFS(String cpf, String cnpj, double valor, String banco,
               FormaPagamento formaPagamento, LocalDate data) {
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.valor = valor;
        this.banco = banco;
        this.formaPagamento = formaPagamento;
        this.data = data;
    }

    public String getCpf() { return cpf; }
    public String getCnpj() { return cnpj; }
    public double getValor() { return valor; }
    public String getBanco() { return banco; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public LocalDate getData() { return data; }
}
