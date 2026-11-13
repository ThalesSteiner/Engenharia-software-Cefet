package locadora;

import java.util.List;

public class Retirada {

    private Filial filialAtual;

    public Retirada(Filial filialAtual) {
        this.filialAtual = filialAtual;
    }

    /**
     * Transfere carros da filial atual para outra filial.
     * @return quantidade de carros efetivamente transferidos.
     */
    public int alocarCarros(Filial destino, List<Carro> carros) {
        int transferidos = 0;
        for (Carro c : carros) {
            if (c.getLocalAtual() == filialAtual) {
                filialAtual.removerCarro(c);
                destino.adicionarCarros(c);
                transferidos++;
            }
        }
        return transferidos;
    }

    public Filial getFilialAtual() { return filialAtual; }
}
