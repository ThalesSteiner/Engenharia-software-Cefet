package locadora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {

    private Motorista motorista;
    private final List<String> queixas = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();

    public Cliente(String cpf, String nome, String numCartao, String id,
                   String senha, String email, Motorista motorista) {
        super(cpf, nome, numCartao, id, senha, email);
        this.motorista = motorista;
    }

    public void realizarQueixa(String texto) {
        queixas.add(texto);
    }

    /** Além de criar a reserva, registra que o cliente a solicitou. */
    @Override
    public Reserva criarReserva(Carro.Grupo grupo, LocalDate dtRetirada,
                                LocalDate dtDevolucao, String cnhCondutor,
                                int kmsPretendidos) {
        Reserva r = super.criarReserva(grupo, dtRetirada, dtDevolucao, cnhCondutor, kmsPretendidos);
        reservas.add(r);
        return r;
    }

    public Motorista getMotorista() { return motorista; }
    public List<String> getQueixas() { return queixas; }
    public List<Reserva> getReservas() { return reservas; }
}
