package locadora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Funcionario gerente = new Funcionario("111", "Ana", "0000", "f1", "123", "ana@loc.com",
                "Gerente", "Rua A, 10", 5000);
        Filial filial = new Filial("12.345.678/0001-90", "Av. Central, 100", gerente);
        List<Filial> rede = new ArrayList<>();
        filial.cadastrarFilial(rede);
        gerente.cadastrarFuncionario(filial);

        Carro carro = new Carro("CH123", "ABC1D23", "Preto", "Onix", 2024, "Chevrolet", Carro.Grupo.A);
        carro.cadastrarCarro(filial);
        System.out.println("Disponíveis grupo A: " + filial.verificarDisponibilidade(Carro.Grupo.A));

        Motorista mot = new Motorista("222", "Bruno", "CNH999");
        Usuario u = new Usuario("222", "Bruno", "1111", "u1", "abc", "bruno@mail.com");
        Cliente cliente = u.cadastrarCliente(mot);

        Reserva r = cliente.criarReserva(Carro.Grupo.A, LocalDate.now(), LocalDate.now().plusDays(3),
                mot.getCnh(), 600);
        r.escolherCarro(carro);
        Pagamento p = r.gerarPagamento(FormaPagamento.PIX);
        p.confirmarPagamento();
        r.executarReserva();
        r.gerarContrato(cliente.getNome(), filial.getCnpj(), FormaPagamento.PIX);
        NFS nf = p.emitirNFS(filial.getCnpj(), "Banco X");
        System.out.println("Valor: R$ " + nf.getValor() + " | status: " + r.getStatus()
                + " | disponíveis A: " + filial.verificarDisponibilidade(Carro.Grupo.A));

        Corretiva c = new Corretiva(1, carro, LocalDate.now(), LocalDate.now().plusDays(2), "Oficina Z", 300, 150);
        Devolucao d = new Devolucao(carro, LocalDate.now().plusDays(3), 580, filial);
        d.adicionarManutencao(c);
        c.realizarManutencao();
        System.out.println("Voltou disponível? " + d.revisarCarro());
        c.finalizarManutencao();
        System.out.println(c.verManutencao());
        r.finalizarReserva();
        System.out.println("Status final: " + r.getStatus() + " | carro: " + carro.getEstado());
    }
}
