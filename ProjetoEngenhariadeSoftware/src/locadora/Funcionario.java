package locadora;

import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Usuario {

    private String cargo;
    private String endereco;
    private double salario;
    private Filial filial;

    public Funcionario(String cpf, String nome, String numCartao, String id,
                       String senha, String email,
                       String cargo, String endereco, double salario) {
        super(cpf, nome, numCartao, id, senha, email);
        this.cargo = cargo;
        this.endereco = endereco;
        this.salario = salario;
    }

    public List<Cliente> verTodosCadastros(List<Cliente> clientes) {
        return new ArrayList<>(clientes);
    }

    /** Vincula o funcionário a uma filial ("trabalha para"). */
    public void cadastrarFuncionario(Filial filial) {
        this.filial = filial;
        filial.adicionarFuncionario(this);
    }

    public String getCargo() { return cargo; }
    public String getEndereco() { return endereco; }
    public double getSalario() { return salario; }
    public Filial getFilial() { return filial; }
}
