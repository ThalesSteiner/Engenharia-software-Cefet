package locadora;

import java.util.ArrayList;
import java.util.List;

public class Filial {

    private final String cnpj;
    private String endereco;
    private Funcionario gerente;
    private boolean principal;

    private final List<Funcionario> funcionarios = new ArrayList<>();
    private final List<Carro> carros = new ArrayList<>();

    // Estoque (recalculado em atualizarEstoque)
    private final List<Carro> carrosA = new ArrayList<>();
    private final List<Carro> carrosB = new ArrayList<>();
    private final List<Carro> carrosC = new ArrayList<>();
    private final List<Carro> carrosAlocacaoImediata = new ArrayList<>(); // disponíveis agora
    private final List<Carro> carrosAlocacaoNormal = new ArrayList<>();   // alugados, voltam à frota
    private final List<Carro> carrosIndisponiveis = new ArrayList<>();    // manutenção / indisponíveis

    public Filial(String cnpj, String endereco, Funcionario gerente) {
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.gerente = gerente;
    }

    /** Registra a filial na rede; a primeira cadastrada vira a principal. */
    public void cadastrarFilial(List<Filial> rede) {
        boolean existe = rede.stream().anyMatch(f -> f.cnpj.equals(cnpj));
        if (existe) {
            throw new IllegalArgumentException("Já existe filial com o CNPJ " + cnpj);
        }
        rede.add(this);
        if (rede.size() == 1) principal = true;
    }

    public void definirComoFilialPrincipal(List<Filial> rede) {
        rede.forEach(f -> f.principal = false);
        this.principal = true;
    }

    public void editarInformacoes(String endereco, Funcionario gerente) {
        if (endereco != null) this.endereco = endereco;
        if (gerente != null) this.gerente = gerente;
    }

    public void adicionarCarros(Carro... novos) {
        for (Carro c : novos) {
            if (!carros.contains(c)) carros.add(c);
            c.setLocalAtual(this);
        }
        atualizarEstoque();
    }

    public void removerCarro(Carro carro) {
        carros.remove(carro);
        atualizarEstoque();
    }

    /** Quantidade de carros de um grupo disponíveis para alocação imediata. */
    public int verificarDisponibilidade(Carro.Grupo grupo) {
        return (int) carrosAlocacaoImediata.stream()
                .filter(c -> c.getGrupo() == grupo)
                .count();
    }

    public void atualizarEstoque() {
        carrosA.clear();
        carrosB.clear();
        carrosC.clear();
        carrosAlocacaoImediata.clear();
        carrosAlocacaoNormal.clear();
        carrosIndisponiveis.clear();

        for (Carro c : carros) {
            switch (c.getGrupo()) {
                case A -> carrosA.add(c);
                case B -> carrosB.add(c);
                case C -> carrosC.add(c);
            }
            switch (c.getEstado()) {
                case DISPONIVEL -> carrosAlocacaoImediata.add(c);
                case ALUGADO -> carrosAlocacaoNormal.add(c);
                default -> carrosIndisponiveis.add(c);
            }
        }
    }

    void adicionarFuncionario(Funcionario f) {
        if (!funcionarios.contains(f)) funcionarios.add(f);
    }

    public int getCarrosTotais() { return carros.size(); }
    public List<Carro> getCarrosA() { return carrosA; }
    public List<Carro> getCarrosB() { return carrosB; }
    public List<Carro> getCarrosC() { return carrosC; }
    public List<Carro> getCarrosAlocacaoImediata() { return carrosAlocacaoImediata; }
    public List<Carro> getCarrosAlocacaoNormal() { return carrosAlocacaoNormal; }
    public List<Carro> getCarrosIndisponiveis() { return carrosIndisponiveis; }
    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public String getCnpj() { return cnpj; }
    public String getEndereco() { return endereco; }
    public Funcionario getGerente() { return gerente; }
    public boolean isPrincipal() { return principal; }
}
