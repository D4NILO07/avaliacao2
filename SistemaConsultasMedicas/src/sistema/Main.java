package sistema;

import sistema.controller.ConsultaController;
import sistema.controller.MedicoController;
import sistema.controller.PacienteController;
import sistema.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    private static final MedicoController medicoController = new MedicoController();
    private static final PacienteController pacienteController = new PacienteController();
    private static final ConsultaController consultaController = new ConsultaController();

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(Main::criarTelaInicial);
    }

    private static void criarTelaInicial() {
        JFrame frame = new JFrame("🩺 Sistema de Consultas Médicas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        JPanel painelPaciente = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelPaciente.add(criarBotao("➕ Cadastrar Paciente", e -> new CadastroPaciente().setVisible(true)));
        painelPaciente.add(criarBotao("📋 Listar Pacientes", e -> new ListarPacientes().setVisible(true)));
        painelPaciente.add(criarBotao("🔍 Buscar por Nome", e -> buscarPacientePorNome()));
        painelPaciente.add(criarBotao("🔎 Buscar por ID", e -> buscarPacientePorId()));
        painelPaciente.add(criarBotao("🗑️ Excluir Paciente", e -> excluirPaciente()));
        abas.addTab("👤 Pacientes", painelPaciente);

        JPanel painelMedico = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelMedico.add(criarBotao("➕ Cadastrar Médico", e -> new CadastroMedico().setVisible(true)));
        painelMedico.add(criarBotao("✏️ Atualizar Médico", e -> atualizarMedico()));
        painelMedico.add(criarBotao("🗑️ Excluir Médico", e -> excluirMedico()));
        painelMedico.add(criarBotao("🔍 Buscar por Nome", e -> buscarMedicoPorNome()));
        painelMedico.add(criarBotao("🔎 Buscar por ID", e -> buscarMedicoPorId()));
        painelMedico.add(criarBotao("📋 Listar Médicos", e -> new ListarMedicos().setVisible(true)));
        abas.addTab("👨‍⚕️ Médicos", painelMedico);

        JPanel painelConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelConsulta.add(criarBotao("📅 Agendar Consulta", e -> new AgendarConsulta().setVisible(true)));
        painelConsulta.add(criarBotao("📋 Listar Consultas", e -> new ListarConsulta().setVisible(true)));
        painelConsulta.add(criarBotao("🗑️ Excluir Consulta", e -> excluirConsulta()));
        abas.addTab("📆 Consultas", painelConsulta);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSair = new JButton("🚪 Sair");
        btnSair.setPreferredSize(new Dimension(120, 35));
        btnSair.addActionListener(e -> System.exit(0));
        painelRodape.add(btnSair);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(abas, BorderLayout.CENTER);
        frame.add(painelRodape, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(200, 35));
        botao.addActionListener(acao);
        return botao;
    }

    private static void buscarPacientePorNome() {
        String nome = JOptionPane.showInputDialog(null, "Informe o nome (ou parte do nome) do paciente:");
        if (nome == null || nome.trim().isEmpty()) return;

        List<?> pacientes = pacienteController.buscarPacientePorNome(nome);
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente encontrado com nome: " + nome);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Object obj : pacientes) {
            var p = (sistema.model.Paciente) obj;
            sb.append("🆔 ID: ").append(p.getId())
                    .append(" | 👤 Nome: ").append(p.getNome())
                    .append(" | 📄 CPF: ").append(p.getCpf())
                    .append(" | ☎️ Telefone: ").append(p.getTelefone())
                    .append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 220));

        JOptionPane.showMessageDialog(null, scrollPane, "🔍 Resultado da busca", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void buscarPacientePorId() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do paciente:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            var paciente = pacienteController.buscarPacientePorId(id);
            if (paciente == null) {
                JOptionPane.showMessageDialog(null, "Paciente não encontrado com ID: " + id);
                return;
            }

            String info = "🆔 ID: " + paciente.getId() +
                    "\n👤 Nome: " + paciente.getNome() +
                    "\n📄 CPF: " + paciente.getCpf() +
                    "\n☎️ Telefone: " + paciente.getTelefone();

            JOptionPane.showMessageDialog(null, info, "📋 Informações do Paciente", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void excluirPaciente() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do paciente para excluir:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            int confirma = JOptionPane.showConfirmDialog(null, "Confirma exclusão do paciente ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirma != JOptionPane.YES_OPTION) return;

            String resultado = pacienteController.excluirPaciente(id);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void excluirConsulta() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID da consulta para excluir:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            int confirma = JOptionPane.showConfirmDialog(null, "Confirma exclusão da consulta ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirma != JOptionPane.YES_OPTION) return;

            String resultado = consultaController.excluirConsulta(id);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void atualizarMedico() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do médico para atualizar:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            var medico = medicoController.buscarPorId(id);
            if (medico == null) {
                JOptionPane.showMessageDialog(null, "Médico não encontrado com ID: " + id);
                return;
            }

            String nome = JOptionPane.showInputDialog(null, "Nome:", medico.getNome());
            if (nome == null) return;

            String especialidade = JOptionPane.showInputDialog(null, "Especialidade:", medico.getEspecialidade());
            if (especialidade == null) return;

            String crm = JOptionPane.showInputDialog(null, "CRM:", medico.getCrm());
            if (crm == null) return;

            String resultado = medicoController.atualizarMedico(id, nome, especialidade, crm);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void excluirMedico() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do médico para excluir:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            int confirma = JOptionPane.showConfirmDialog(null, "Confirma exclusão do médico ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirma != JOptionPane.YES_OPTION) return;

            String resultado = medicoController.excluirMedico(id);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void buscarMedicoPorNome() {
        String nome = JOptionPane.showInputDialog(null, "Informe o nome (ou parte do nome) do médico:");
        if (nome == null || nome.trim().isEmpty()) return;

        List<?> medicos = medicoController.buscarMedicosPorNome(nome);
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum médico encontrado com nome: " + nome);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Object obj : medicos) {
            var m = (sistema.model.Medico) obj;
            sb.append("🆔 ID: ").append(m.getId())
                    .append(" | 👨‍⚕️ Nome: ").append(m.getNome())
                    .append(" | 🔢 CRM: ").append(m.getCrm())
                    .append(" | 🏥 Especialidade: ").append(m.getEspecialidade())
                    .append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 220));

        JOptionPane.showMessageDialog(null, scrollPane, "🔍 Resultado da busca", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void buscarMedicoPorId() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do médico:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            var medico = medicoController.buscarPorId(id);
            if (medico == null) {
                JOptionPane.showMessageDialog(null, "Médico não encontrado com ID: " + id);
                return;
            }

            String info = "🆔 ID: " + medico.getId() +
                    "\n👨‍⚕️ Nome: " + medico.getNome() +
                    "\n🔢 CRM: " + medico.getCrm() +
                    "\n🏥 Especialidade: " + medico.getEspecialidade();

            JOptionPane.showMessageDialog(null, info, "📋 Informações do Médico", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }
}
