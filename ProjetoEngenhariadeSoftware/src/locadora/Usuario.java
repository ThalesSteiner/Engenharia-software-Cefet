package locadora;

import java.time.LocalDate;

public class Usuario {

    protected String cpf;
    protected String nome;
    protected String numCartao;
    protected String id;
    protected String senha;
    protected String email;

    public Usuario(String cpf, String nome, String numCartao, String id,
                   String senha, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.numCartao = numCartao;
        this.id = id;
        this.senha = senha;
        this.email = email;
    }

    public boolean logar(String email, String senha) {
        return this.email.equalsIgnoreCase(email) && this.senha.equals(senha);
    }

    /** Transforma este usuário em cliente, associando um motorista. */
    public Cliente cadastrarCliente(Motorista motorista) {
        return new Cliente(cpf, nome, numCartao, id, senha, email, motorista);
    }

    public Reserva criarReserva(Carro.Grupo grupo, LocalDate dtRetirada,
                                LocalDate dtDevolucao, String cnhCondutor,
                                int kmsPretendidos) {
        return new Reserva(cpf, grupo, dtRetirada, dtDevolucao, cnhCondutor, kmsPretendidos);
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
}
