package locadora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Devolucao {

    private final String chassi;
    private LocalDate dataDevolucao;
    private int kmsRodados;
    private boolean checkoutRev;
    private Filial filialDestino;
    private final Carro carro;
    private final List<Manutencao> manutencoes = new ArrayList<>();

    public Devolucao(Carro carro, LocalDate dataDevolucao, int kmsRodados, Filial filialDestino) {
        this.carro = carro;
        this.chassi = carro.getChassi();
        this.dataDevolucao = dataDevolucao;
        this.kmsRodados = kmsRodados;
        this.filialDestino = filialDestino;
    }

    public void adicionarManutencao(Manutencao m) {
        manutencoes.add(m);
    }

    /**
     * Faz o checkout/revisão: move o carro para a filial de destino e o deixa
     * disponível, ou em manutenção se houver manutenção pendente.
     * @return true se o carro voltou para a frota disponível.
     */
    public boolean revisarCarro() {
        checkoutRev = true;

        Filial origem = carro.getLocalAtual();
        if (origem != null && origem != filialDestino) origem.removerCarro(carro);
        filialDestino.adicionarCarros(carro);

        boolean pendente = manutencoes.stream()
                .anyMatch(m -> m.getSituacao() != Manutencao.Situacao.FINALIZADA);

        if (pendente) {
            carro.atualizarDisponibilidade(Carro.Estado.EM_MANUTENCAO,
                    new Indisponivel(Indisponivel.Tipo.MANUTENCAO, LocalDate.now()));
        } else {
            carro.atualizarDisponibilidade(Carro.Estado.DISPONIVEL, null);
        }
        filialDestino.atualizarEstoque();
        return !pendente;
    }

    public String getChassi() { return chassi; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public int getKmsRodados() { return kmsRodados; }
    public boolean isCheckoutRev() { return checkoutRev; }
    public Filial getFilialDestino() { return filialDestino; }
    public List<Manutencao> getManutencoes() { return manutencoes; }
}
