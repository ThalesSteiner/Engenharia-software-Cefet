package locadora;

import java.util.List;

public class Motorista {

    private final String cpf;
    private String nome;
    private final String cnh;

    public Motorista(String cpf, String nome, String cnh) {
        this.cpf = cpf;
        this.nome = nome;
        this.cnh = cnh;
    }

    /** Adiciona à base se a CNH ainda não estiver cadastrada. */
    public boolean cadastrarMotorista(List<Motorista> base) {
        boolean jaExiste = base.stream().anyMatch(m -> m.cnh.equals(cnh));
        if (jaExiste) return false;
        base.add(this);
        return true;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getCnh() { return cnh; }
}
