package locadora;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Carro {

    public enum Grupo { A, B, C }

    public enum Estado { DISPONIVEL, ALUGADO, EM_MANUTENCAO, INDISPONIVEL }

    private final String chassi;
    private String placa;
    private String cor;
    private final String modelo;
    private final int ano;
    private final String marca;
    private Filial localAtual;
    private Estado estado;
    private final Grupo grupo;
    private Indisponivel indisponivel;

    public Carro(String chassi, String placa, String cor, String modelo,
                 int ano, String marca, Grupo grupo) {
        this.chassi = chassi;
        this.placa = placa;
        this.cor = cor;
        this.modelo = modelo;
        this.ano = ano;
        this.marca = marca;
        this.grupo = grupo;
        this.estado = Estado.DISPONIVEL;
    }

    /** Cadastra o carro na frota de uma filial. */
    public void cadastrarCarro(Filial filial) {
        filial.adicionarCarros(this);
    }

    /** Lista (somente leitura) os carros de uma frota. */
    public static List<Carro> verCarros(List<Carro> frota) {
        return Collections.unmodifiableList(new ArrayList<>(frota));
    }

    public void alterarDados(String placa, String cor, Estado estado) {
        if (placa != null) this.placa = placa;
        if (cor != null) this.cor = cor;
        if (estado != null) this.estado = estado;
    }

    /** Carros disponíveis do mesmo grupo (alternativas a este carro). */
    public List<Carro> mostrarCarrosSimilares(List<Carro> frota) {
        return frota.stream()
                .filter(c -> c != this && c.grupo == this.grupo && c.isDisponivel())
                .collect(Collectors.toList());
    }

    public void atualizarDisponibilidade(Estado novoEstado, Indisponivel motivo) {
        this.estado = novoEstado;
        boolean indisponivelDeFato =
                novoEstado == Estado.EM_MANUTENCAO || novoEstado == Estado.INDISPONIVEL;
        this.indisponivel = indisponivelDeFato ? motivo : null;
    }

    public boolean isDisponivel() { return estado == Estado.DISPONIVEL; }

    public String getChassi() { return chassi; }
    public String getPlaca() { return placa; }
    public String getCor() { return cor; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public String getMarca() { return marca; }
    public Grupo getGrupo() { return grupo; }
    public Estado getEstado() { return estado; }
    public Filial getLocalAtual() { return localAtual; }
    public Indisponivel getIndisponivel() { return indisponivel; }
    void setLocalAtual(Filial filial) { this.localAtual = filial; }
}
