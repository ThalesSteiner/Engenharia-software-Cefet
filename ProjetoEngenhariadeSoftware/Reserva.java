import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

    // ------------------------------------------------------------------
    // Tela de boas-vindas
    // ------------------------------------------------------------------

    /**
     * Tela inicial do sistema. Devolve true se o usuário clicou em "Acessar"
     * e false se clicou em "Sair" (ou fechou a janela no X).
     */
    static boolean telaBemVindo() {
        String[] opcoes = {"Acessar", "Sair"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Bem-vindo ao Sistema de Reserva de Carros Você-Aluga! \nClique em Acessar para entrar ou Sair para encerrar o programa.",
                "Sistema Você-Aluga",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        return escolha == 0;
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
     * Se o usuário escolher Login ou Cadastrar com o campo nome em branco, avisa e
     * repete a tela de login.
     * Devolve a opção escolhida para o fluxo seguir para a etapa correta.
     */
    static int logar() {
        String[] opcoes = {"Login", "Cadastrar", "Cancelar"};

        while (true) {
            JTextField campoNome = new JTextField(15);
            JPasswordField campoSenha = new JPasswordField(15);

            JPanel painel = new JPanel(new GridLayout(2, 2, 5, 5));
            painel.add(new JLabel("Nome:"));
            painel.add(campoNome);
            painel.add(new JLabel("Senha:"));
            painel.add(campoSenha);

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

            // Login ou Cadastrar com nome em branco: avisa e repete a tela
            if (campoNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo nome não pode ficar em branco.",
                        "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            // Login ou Cadastrar: guarda o que foi digitado
            nomeUsuario = campoNome.getText();
            senhaUsuario = new String(campoSenha.getPassword());

            return escolha;
        }
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
    // Edição de perfil (igual para cliente e funcionário)
    // ------------------------------------------------------------------

    /**
     * Objeto temporário com os mesmos campos de Cliente/Usuario/Motorista.
     * Guarda o que foi digitado na tela de edição de perfil. Mais para frente
     * será usado para conferir/atualizar os dados no banco de dados.
     */
    static class PerfilTemporario {
        // Campos de Usuario / Cliente
        String cpf;
        String nome;
        String numCartao;
        String id;
        String senha;
        String email;

        // Campos de Motorista (associado ao cliente)
        String motoristaCpf;
        String motoristaNome;
        String motoristaCnh;

        @Override
        public String toString() {
            return "PerfilTemporario{cpf=" + cpf + ", nome=" + nome + ", numCartao=" + numCartao
                    + ", id=" + id + ", email=" + email
                    + ", motoristaCpf=" + motoristaCpf + ", motoristaNome=" + motoristaNome
                    + ", motoristaCnh=" + motoristaCnh + "}";
        }
    }

    // Último perfil salvo na tela de edição (temporário, ainda não vai ao banco)
    static PerfilTemporario perfilTemporario = null;

    /**
     * Tela "Editar meu perfil": campos em branco, um para cada variável do cliente,
     * com os botões "Salvar novos dados" e "Cancelar".
     * Salvar guarda os valores em um PerfilTemporario; Cancelar (ou X) descarta tudo.
     */
    static PerfilTemporario formularioCliente(String titulo) {
        JTextField campoCpf = new JTextField(20);
        JTextField campoNome = new JTextField(20);
        JTextField campoNumCartao = new JTextField(20);
        JTextField campoId = new JTextField(20);
        JPasswordField campoSenha = new JPasswordField(20);
        JTextField campoEmail = new JTextField(20);
        JTextField campoMotoristaCpf = new JTextField(20);
        JTextField campoMotoristaNome = new JTextField(20);
        JTextField campoMotoristaCnh = new JTextField(20);

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.add(new JLabel("CPF:"));
        painel.add(campoCpf);
        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);
        painel.add(new JLabel("Número do cartão:"));
        painel.add(campoNumCartao);
        painel.add(new JLabel("ID:"));
        painel.add(campoId);
        painel.add(new JLabel("Senha:"));
        painel.add(campoSenha);
        painel.add(new JLabel("E-mail:"));
        painel.add(campoEmail);
        painel.add(new JLabel("Motorista - CPF:"));
        painel.add(campoMotoristaCpf);
        painel.add(new JLabel("Motorista - Nome:"));
        painel.add(campoMotoristaNome);
        painel.add(new JLabel("Motorista - CNH:"));
        painel.add(campoMotoristaCnh);

        String[] opcoes = {"Salvar novos dados", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                painel,
                titulo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (escolha != 0) {
            return null; // Cancelar ou X: descarta o que foi digitado
        }

        PerfilTemporario novo = new PerfilTemporario();
        novo.cpf = campoCpf.getText();
        novo.nome = campoNome.getText();
        novo.numCartao = campoNumCartao.getText();
        novo.id = campoId.getText();
        novo.senha = new String(campoSenha.getPassword());
        novo.email = campoEmail.getText();
        novo.motoristaCpf = campoMotoristaCpf.getText();
        novo.motoristaNome = campoMotoristaNome.getText();
        novo.motoristaCnh = campoMotoristaCnh.getText();

        return novo;
    }

    /** Edição do próprio perfil (cliente ou funcionário). */
    static void editarPerfil() {
        PerfilTemporario novo = formularioCliente("Editar meu perfil");
        if (novo == null) {
            return; // cancelou
        }
        perfilTemporario = novo;
        info("Novos dados salvos temporariamente.");
    }

    // ------------------------------------------------------------------
    // Edição de entidades pelo funcionário (Modificar > Editar)
    // ------------------------------------------------------------------

    /** Objeto temporário com as variáveis de Carro. */
    static class CarroTemporario {
        String chassi;
        String placa;
        String cor;
        String modelo;
        String ano;
        String marca;
        String grupo;              // A, B ou C
        String estado;             // DISPONIVEL, ALUGADO, EM_MANUTENCAO, INDISPONIVEL
        String localAtualCnpj;     // filial onde o carro está (CNPJ)
        // Indisponivel (só vale quando o carro está em manutenção/indisponível)
        String indisponivelTipo;   // MANUTENCAO, RESERVADO, DEVOLUCAO_PENDENTE, OUTRO
        String indisponivelData;   // dd/mm/aaaa
    }

    /** Objeto temporário com as variáveis de Contrato. */
    static class ContratoTemporario {
        String nome;
        String cpf;
        String cnpj;
        String periodoDeUso; // em dias
        String carroChassi;  // carro do contrato (chassi)
        String valor;
        String formaPagamento; // CARTAO_CREDITO, CARTAO_DEBITO, PIX, BOLETO, DINHEIRO
        String ativo;        // Sim ou Não
    }

    /** Objeto temporário com as variáveis de Filial. */
    static class FilialTemporario {
        String cnpj;
        String endereco;
        String principal; // Sim ou Não
        FuncionarioTemporario gerente;
    }

    /** Objeto temporário com as variáveis de Funcionario (Usuario + próprias). */
    static class FuncionarioTemporario {
        String cpf;
        String nome;
        String numCartao;
        String id;
        String senha;
        String email;
        String cargo;
        String endereco;
        String salario;
    }

    // Últimos dados salvos em cada tela de edição (temporários, ainda não vão ao banco)
    static CarroTemporario carroEditado = null;
    static ContratoTemporario contratoEditado = null;
    static FilialTemporario filialEditada = null;
    static PerfilTemporario clienteEditado = null;

    /**
     * Tela genérica com um campo para cada rótulo e os botões "Salvar novos dados"
     * e "Cancelar". Se opcoes[i] não for null, o campo i vira uma caixa de seleção
     * (a primeira opção deve ser vazia). Se senha[i] for true, o campo esconde o texto.
     * Devolve os valores na ordem dos rótulos, ou null se cancelar / fechar no X.
     */
    static String[] telaCampos(String titulo, String[] rotulos, String[][] opcoes, boolean[] senha) {
        JComponent[] campos = new JComponent[rotulos.length];
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));

        for (int i = 0; i < rotulos.length; i++) {
            if (opcoes != null && opcoes[i] != null) {
                campos[i] = new JComboBox<>(opcoes[i]);
            } else if (senha != null && senha[i]) {
                campos[i] = new JPasswordField(20);
            } else {
                campos[i] = new JTextField(20);
            }
            painel.add(new JLabel(rotulos[i] + ":"));
            painel.add(campos[i]);
        }

        String[] botoes = {"Salvar novos dados", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                painel,
                titulo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                botoes,
                botoes[0]);

        if (escolha != 0) {
            return null; // Cancelar ou X
        }

        String[] valores = new String[rotulos.length];
        for (int i = 0; i < rotulos.length; i++) {
            if (campos[i] instanceof JComboBox<?> combo) {
                Object item = combo.getSelectedItem();
                valores[i] = item == null ? "" : item.toString();
            } else if (campos[i] instanceof JPasswordField pw) {
                valores[i] = new String(pw.getPassword());
            } else {
                valores[i] = ((JTextField) campos[i]).getText();
            }
        }
        return valores;
    }

    static void editarCarro() {
        String[] rotulos = {
                "Chassi", "Placa", "Cor", "Modelo", "Ano", "Marca",
                "Grupo", "Estado", "Local atual (CNPJ da filial)",
                "Indisponível - tipo", "Indisponível - data (dd/mm/aaaa)"
        };
        String[][] opcoes = new String[rotulos.length][];
        opcoes[6] = new String[]{"", "A", "B", "C"};
        opcoes[7] = new String[]{"", "DISPONIVEL", "ALUGADO", "EM_MANUTENCAO", "INDISPONIVEL"};
        opcoes[9] = new String[]{"", "MANUTENCAO", "RESERVADO", "DEVOLUCAO_PENDENTE", "OUTRO"};

        String[] v = telaCampos("Editando carro", rotulos, opcoes, null);
        if (v == null) {
            return;
        }

        CarroTemporario c = new CarroTemporario();
        c.chassi = v[0];
        c.placa = v[1];
        c.cor = v[2];
        c.modelo = v[3];
        c.ano = v[4];
        c.marca = v[5];
        c.grupo = v[6];
        c.estado = v[7];
        c.localAtualCnpj = v[8];
        c.indisponivelTipo = v[9];
        c.indisponivelData = v[10];

        carroEditado = c;
        info("Novos dados do carro salvos temporariamente.");
    }

    static void editarContrato() {
        String[] rotulos = {
                "Nome", "CPF", "CNPJ", "Período de uso (dias)",
                "Carro (chassi)", "Valor", "Forma de pagamento", "Ativo"
        };
        String[][] opcoes = new String[rotulos.length][];
        opcoes[6] = new String[]{"", "CARTAO_CREDITO", "CARTAO_DEBITO", "PIX", "BOLETO", "DINHEIRO"};
        opcoes[7] = new String[]{"", "Sim", "Não"};

        String[] v = telaCampos("Editando contrato", rotulos, opcoes, null);
        if (v == null) {
            return;
        }

        ContratoTemporario c = new ContratoTemporario();
        c.nome = v[0];
        c.cpf = v[1];
        c.cnpj = v[2];
        c.periodoDeUso = v[3];
        c.carroChassi = v[4];
        c.valor = v[5];
        c.formaPagamento = v[6];
        c.ativo = v[7];

        contratoEditado = c;
        info("Novos dados do contrato salvos temporariamente.");
    }

    static void editarFilial() {
        String[] rotulos = {
                "CNPJ", "Endereço", "Filial principal",
                "Gerente - CPF", "Gerente - Nome"
        };
        String[][] opcoes = new String[rotulos.length][];
        opcoes[2] = new String[]{"", "Sim", "Não"};

        String[] v = telaCampos("Editando filial", rotulos, opcoes, null);
        if (v == null) {
            return;
        }

        FilialTemporario f = new FilialTemporario();
        f.cnpj = v[0];
        f.endereco = v[1];
        f.principal = v[2];

        FuncionarioTemporario g = new FuncionarioTemporario();
        g.cpf = v[3];
        g.nome = v[4];
        f.gerente = g;

        filialEditada = f;
        info("Novos dados da filial salvos temporariamente.");
    }

    /**
     * Pergunta o CPF e o nome do cliente a ser editado.
     * Devolve {cpf, nome}, ou null se o funcionário cancelar / fechar no X.
     * Repete a tela enquanto algum dos campos estiver em branco.
     */
    static String[] perguntarCliente() {
        String[] botoes = {"Continuar", "Cancelar"};

        while (true) {
            JTextField campoCpf = new JTextField(15);
            JTextField campoNome = new JTextField(15);

            JPanel painel = new JPanel(new GridLayout(2, 2, 5, 5));
            painel.add(new JLabel("CPF do cliente:"));
            painel.add(campoCpf);
            painel.add(new JLabel("Nome do cliente:"));
            painel.add(campoNome);

            int escolha = JOptionPane.showOptionDialog(
                    null,
                    painel,
                    "Editar cliente",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    botoes,
                    botoes[0]);

            if (escolha != 0) {
                return null; // Cancelar ou X
            }

            if (campoCpf.getText().trim().isEmpty() || campoNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Informe o CPF e o nome do cliente.",
                        "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            return new String[]{campoCpf.getText().trim(), campoNome.getText().trim()};
        }
    }

    /** Editar cliente: primeiro identifica o cliente (CPF e nome), depois mostra o formulário. */
    static void editarCliente() {
        String[] identificacao = perguntarCliente();
        if (identificacao == null) {
            return;
        }

        PerfilTemporario novo = formularioCliente("Editando cliente " + identificacao[1]);
        if (novo == null) {
            return; // cancelou
        }

        clienteEditado = novo;
        info("Novos dados do cliente " + identificacao[1] + " salvos temporariamente.");
    }
    /** Direciona o "Editar ..." do submenu Modificar para a tela da entidade. */
    static void editarEntidade(String entidade) {
        switch (entidade) {
            case "carro" -> editarCarro();
            case "contrato" -> editarContrato();
            case "filial" -> editarFilial();
            case "cliente" -> editarCliente();
            default -> info("Editar " + entidade + " (ainda não implementado).");
        }
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
        final int EDITAR_PERFIL = 8;
        final int LOGOFF = opcoes.length - 1;

        while (true) {
            int escolha = menuVertical("Menu do cliente", "O que deseja fazer?", opcoes);

            if (escolha < 0 || escolha == LOGOFF) {
                return; // Logoff ou X -> volta para o login
            }
            if (escolha == 0) {
                fluxoReserva();
            } else if (escolha == EDITAR_PERFIL) {
                editarPerfil();
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
                case 5 -> editarPerfil();
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
            if (escolha == 2) {
                editarEntidade(entidade); // "Editar ..."
            } else {
                info(opcoes[escolha] + " (ainda não implementado).");
            }
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

        // Tela de boas-vindas: "Sair" (ou X) encerra o programa
        while (telaBemVindo()) {

            // Daqui em diante é o fluxo de login (Logoff volta para o login)
            boolean continuarNoLogin = true;
            while (continuarNoLogin) {
                // Logar (Login / Cadastrar / Cancelar)
                int opcao = logar();

                if (opcao == CANCELAR) {
                    continuarNoLogin = false; // volta para a tela de boas-vindas
                    continue;
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
}
