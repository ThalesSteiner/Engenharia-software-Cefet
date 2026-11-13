import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Fluxo de Reserva de carro seguindo o diagrama de atividade.
 * As verificações (cadastrado, disponível, motorista, pagamento, entrega imediata)
 * estão como "true" fixo para teste, mas já existem como métodos separados.
 */
public class Reserva {

    private static final String TITULO = "Reserva";

    // ------------------------------------------------------------------
    // Verificações (por enquanto sempre true; troque pela lógica real depois)
    // ------------------------------------------------------------------
    static boolean clienteCadastrado() {
        return true;
    }

    static boolean carroDisponivel(String carro) {
        return true;
    }

    static boolean clienteEhOMotorista() {
        return true;
    }

    static boolean pagamentoConfirmado() {
        return true;
    }

    static boolean entregaImediata() {
        return true;
    }

    // ------------------------------------------------------------------
    // Atividades
    // ------------------------------------------------------------------
    static void info(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, TITULO, JOptionPane.INFORMATION_MESSAGE);
    }

    // Opções do popup de logar
    static final int LOGIN = 0;
    static final int CADASTRAR = 1;
    static final int CANCELAR = 2;

    // Dados digitados no popup de logar (guardados, mas ainda não utilizados)
    static String nomeUsuario = "";
    static String senhaUsuario = "";

    /**
     * Popup "Logar" com campos de nome e senha e as opções Login, Cadastrar e Cancelar.
     * Nome e senha são salvos nas variáveis estáticas acima (sem validação por enquanto).
     * Devolve a opção escolhida para o fluxo seguir para a etapa correta.
     */
    static int logar() {
        JTextField campoNome = new JTextField(15);
        JPasswordField campoSenha = new JPasswordField(15);

        JPanel painel = new JPanel(new GridLayout(2, 2, 5, 5));
        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);

        String[] opcoes = {"Login", "Cadastrar", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                painel,
                "Logar no sistema",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        // Fechar o popup no X conta como Cancelar
        if (escolha == JOptionPane.CLOSED_OPTION || escolha == CANCELAR) {
            return CANCELAR;
        }

        // Login ou Cadastrar: guarda o que foi digitado
        nomeUsuario = campoNome.getText();
        senhaUsuario = new String(campoSenha.getPassword());

        return escolha;
    }

    static void cadastrarCliente() {
        // Por enquanto só passa pela etapa, sem coletar dados
        info("Cliente cadastrado com sucesso!");
    }

    static void iniciarReserva() {
        info("Reserva iniciada.");
    }

    static String escolherCarro() {
        String carro = JOptionPane.showInputDialog(null, "Qual carro deseja reservar?", "Escolher Carro", JOptionPane.QUESTION_MESSAGE);
        return carro;
    }

    static void mostrarCarrosSimilares(String carro) {
        info("O carro \"" + carro + "\" está indisponível.\nCarros similares:\n- Similar 1\n- Similar 2\n- Similar 3");
    }

    static void executarReserva() {
        info("Reserva executada.");
    }

    static void cadastrarMotorista() {
        String nome = JOptionPane.showInputDialog(null, "O cliente não é o motorista.\nInforme o nome do motorista:", "Cadastrar Motorista", JOptionPane.QUESTION_MESSAGE);
        info("Motorista " + nome + " cadastrado com sucesso!");
    }

    static void gerarContrato() {
        info("Contrato gerado.");
    }

    static void finalizarReserva() {
        info("Reserva finalizada.");
    }

    static void gerarPagamento() {
        info("Pagamento gerado.");
    }

    static void cancelarContrato() {
        info("Contrato cancelado.");
    }

    static void cancelarReserva() {
        info("Reserva cancelada.");
    }

    static void entregarCarro() {
        info("Carro entregue ao cliente.");
    }

    static void informarDataRetirada() {
        String data = JOptionPane.showInputDialog(null, "Informe a data de retirada (dd/mm/aaaa):", "Data de Retirada", JOptionPane.QUESTION_MESSAGE);
        info("Data de retirada registrada: " + data);
    }

    static void atualizarEstoque() {
        info("Estoque atualizado.");
    }

    static void emitirNFS() {
        info("NFS emitida.");
    }

    // ------------------------------------------------------------------
    // Telas / menus (componentes reutilizáveis)
    // ------------------------------------------------------------------

    /**
     * Menu com os botões empilhados na vertical (bom para muitas opções).
     * Devolve o índice do botão clicado, ou -1 se a janela for fechada no X.
     */
    static int menuVertical(String titulo, String mensagem, String[] opcoes) {
        final int[] resultado = {-1};

        JDialog dialogo = new JDialog((java.awt.Frame) null, titulo, true);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel botoes = new JPanel(new GridLayout(opcoes.length, 1, 5, 5));
        for (int i = 0; i < opcoes.length; i++) {
            final int indice = i;
            JButton botao = new JButton(opcoes[i]);
            botao.addActionListener(e -> {
                resultado[0] = indice;
                dialogo.dispose();
            });
            botoes.add(botao);
        }

        dialogo.setLayout(new BorderLayout(10, 10));
        ((JComponent) dialogo.getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        dialogo.add(new JLabel("<html>" + mensagem.replace("\n", "<br>") + "</html>"), BorderLayout.NORTH);
        dialogo.add(botoes, BorderLayout.CENTER);

        dialogo.pack();
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true); // bloqueia até o diálogo ser fechado
        return resultado[0];
    }

    /**
     * Tela genérica de uma seção: mostra "<seção> com sucesso" com as opções
     * Voltar e Avançar. Por enquanto, Avançar reabre a mesma tela.
     * Voltar (ou fechar no X) retorna para a tela anterior.
     */
    static void telaSecao(String secao) {
        String[] opcoes = {"Voltar", "Avançar"};
        while (true) {
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    secao + " com sucesso",
                    secao,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);

            if (escolha != 1) {
                return; // Voltar ou X
            }
            // Avançar: por enquanto acessa a mesma aba de novo
        }
    }

    // ------------------------------------------------------------------
    // Menu do cliente
    // ------------------------------------------------------------------

    /**
     * Menu que aparece depois do login/cadastro e ao final de cada reserva.
     * "Logoff" (ou fechar no X) volta para a tela de login.
     */
    static void menuCliente() {
        String[] opcoes = {
                "Fazer reserva",
                "Ver carros A",
                "Ver carros B",
                "Ver carros C",
                "Ver minhas reservas",
                "Ver meus contratos",
                "Ver minhas notas fiscais",
                "Ver meu perfil",
                "Editar meu perfil",
                "Logoff"
        };
        final int LOGOFF = opcoes.length - 1;

        while (true) {
            int escolha = menuVertical("Menu do cliente", "O que deseja fazer?", opcoes);

            if (escolha < 0 || escolha == LOGOFF) {
                return; // Logoff ou X -> volta para o login
            }
            if (escolha == 0) {
                fluxoReserva();
            } else {
                telaSecao(opcoes[escolha]);
            }
        }
    }

    // ------------------------------------------------------------------
    // Área do funcionário (usuário "adm")
    // ------------------------------------------------------------------
    static final String USUARIO_FUNCIONARIO = "adm";

    static boolean ehFuncionario() {
        return USUARIO_FUNCIONARIO.equals(nomeUsuario.trim());
    }

    /**
     * Tela principal do funcionário. Cada botão abre o submenu da entidade
     * (ou a tela de perfil) e, ao voltar dele, retorna para esta tela.
     * "Logoff" (ou fechar no X) volta para a tela de login.
     */
    static void menuFuncionario() {
        String[] opcoes = {
                "Modificar carros",
                "Modificar contratos",
                "Modificar filial",
                "Modificar cliente",
                "Ver meu perfil",
                "Editar meu perfil",
                "Logoff"
        };
        while (true) {
            int escolha = menuVertical("Funcionário", "Área do funcionário.\nO que deseja fazer?", opcoes);

            switch (escolha) {
                case 0 -> menuModificar("carro");
                case 1 -> menuModificar("contrato");
                case 2 -> menuModificar("filial");
                case 3 -> menuModificar("cliente");
                case 4 -> telaSecao("Ver meu perfil");
                case 5 -> telaSecao("Editar meu perfil");
                default -> {
                    return; // "Logoff" ou X
                }
            }
        }
    }

    /**
     * Submenu genérico: Adicionar / Remover / Editar / Ver / Voltar.
     * Por enquanto cada ação só mostra um aviso; depois do aviso a tela do
     * submenu é reaberta. "Voltar" (ou X) retorna ao menu do funcionário.
     */
    static void menuModificar(String entidade) {
        String[] opcoes = {
                "Adicionar " + entidade,
                "Remover " + entidade,
                "Editar " + entidade,
                "Ver " + entidade,
                "Voltar"
        };
        String titulo = "Modificar " + entidade;
        while (true) {
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Escolha uma opção:",
                    titulo,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);

            if (escolha < 0 || escolha == opcoes.length - 1) {
                return; // Voltar ou X
            }
            info(opcoes[escolha] + " (ainda não implementado).");
        }
    }

    // ------------------------------------------------------------------
    // Fluxo da reserva (a partir de "Fazer reserva" no menu do cliente)
    // Ao terminar (ou cancelar), volta para o menu do cliente.
    // ------------------------------------------------------------------
    static void fluxoReserva() {

        // Iniciar Reserva
        iniciarReserva();

        // Escolher Carro
        String carro = escolherCarro();
        if (carro == null) {
            return; // cancelou -> volta ao menu
        }

        // Verificar Disponibilidade / Disponível?
        if (!carroDisponivel(carro)) {
            mostrarCarrosSimilares(carro);
        }

        // Executar Reserva
        executarReserva();

        // Cliente é o motorista?
        if (!clienteEhOMotorista()) {
            cadastrarMotorista();
        }

        // Gerar Contrato
        gerarContrato();

        // Finalizar Reserva
        finalizarReserva();

        // Gerar pagamento
        gerarPagamento();

        // Pagamento confirmado?
        if (!pagamentoConfirmado()) {
            cancelarContrato();
            cancelarReserva();
            return; // fim do fluxo (cancelado) -> volta ao menu
        }

        // Entrega imediata?
        if (entregaImediata()) {
            entregarCarro();
        } else {
            informarDataRetirada();
        }

        // Atualizar estoque
        atualizarEstoque();

        // Emitir NFS
        emitirNFS();

        // Fim
        info("Fluxo de reserva concluído!");
    }

    // ------------------------------------------------------------------
    // Fluxo principal
    // ------------------------------------------------------------------
    public static void main(String[] args) {

        while (true) {
            // Logar (Login / Cadastrar / Cancelar)
            int opcao = logar();

            if (opcao == CANCELAR) {
                info("Operação cancelada.");
                return;
            }

            // Usuário "adm" entra na área do funcionário (Logoff volta para o login)
            if (opcao == LOGIN && ehFuncionario()) {
                menuFuncionario();
                continue;
            }

            if (opcao == CADASTRAR) {
                // Cadastrar Cliente -> Menu do cliente
                cadastrarCliente();
            } else {
                // Login -> Cadastrado? (sim: Menu do cliente / não: Cadastrar Cliente)
                if (!clienteCadastrado()) {
                    cadastrarCliente();
                }
            }

            // Menu do cliente (Logoff volta para o login)
            menuCliente();
        }
    }
}