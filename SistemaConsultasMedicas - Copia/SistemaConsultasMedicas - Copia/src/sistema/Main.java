package sistema;

import com.formdev.flatlaf.FlatDarculaLaf;
import sistema.controller.ConsultaController;
import sistema.controller.MedicoController;
import sistema.controller.PacienteController;
import sistema.model.Consulta;
import sistema.model.Paciente;
import sistema.view.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Main {

    private static final MedicoController medicoController = new MedicoController();
    private static final PacienteController pacienteController = new PacienteController();
    private static final ConsultaController consultaController = new ConsultaController();

    private static final Font FONT_EMOJI = new Font("Segoe UI Emoji", Font.PLAIN, 14);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            System.err.println("Falha ao iniciar FlatLaf: " + e);
        }

        SwingUtilities.invokeLater(Main::criarTelaInicial);
    }

    private static void criarTelaInicial() {
        JFrame frame = new JFrame("🩺 Sistema de Consultas Médicas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 520);
        frame.setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        JPanel painelPaciente = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelPaciente.add(criarBotaoEstilizado("➕ Cadastrar Paciente", e -> new CadastroPaciente().setVisible(true)));
        painelPaciente.add(criarBotaoEstilizado("📋 Listar Pacientes", e -> new ListarPacientes().setVisible(true)));
        painelPaciente.add(criarBotaoEstilizado("🔍 Buscar por Nome", e -> buscarPacientePorNome()));
        painelPaciente.add(criarBotaoEstilizado("🔎 Buscar por ID", e -> buscarPacientePorId()));
        painelPaciente.add(criarBotaoEstilizado("✏️ Atualizar Paciente", e -> atualizarPaciente()));
        painelPaciente.add(criarBotaoEstilizado("🗑️ Excluir Paciente", e -> excluirPaciente()));
        abas.addTab("👤 Pacientes", painelPaciente);

        JPanel painelMedico = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelMedico.add(criarBotaoEstilizado("➕ Cadastrar Médico", e -> new CadastroMedico().setVisible(true)));
        painelMedico.add(criarBotaoEstilizado("✏️ Atualizar Médico", e -> atualizarMedico()));
        painelMedico.add(criarBotaoEstilizado("🗑️ Excluir Médico", e -> excluirMedico()));
        painelMedico.add(criarBotaoEstilizado("🔍 Buscar por Nome", e -> buscarMedicoPorNome()));
        painelMedico.add(criarBotaoEstilizado("🔎 Buscar por ID", e -> buscarMedicoPorId()));
        painelMedico.add(criarBotaoEstilizado("📋 Listar Médicos", e -> new ListarMedicos().setVisible(true)));
        abas.addTab("👨‍⚕️ Médicos", painelMedico);

        JPanel painelConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        painelConsulta.add(criarBotaoEstilizado("📅 Agendar Consulta", e -> new AgendarConsulta().setVisible(true)));
        painelConsulta.add(criarBotaoEstilizado("📋 Listar Consultas", e -> new ListarConsulta().setVisible(true)));
        painelConsulta.add(criarBotaoEstilizado("✏️ Atualizar Consulta", e -> atualizarConsulta()));
        painelConsulta.add(criarBotaoEstilizado("🗑️ Excluir Consulta", e -> excluirConsulta()));
        abas.addTab("📆 Consultas", painelConsulta);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSair = criarBotaoEstilizado("🚪 Sair", e -> System.exit(0));
        btnSair.setPreferredSize(new Dimension(120, 35));
        painelRodape.add(btnSair);

        frame.setLayout(new BorderLayout(10, 10));
        frame.add(abas, BorderLayout.CENTER);
        frame.add(painelRodape, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static JButton criarBotaoEstilizado(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(220, 40));
        botao.setFocusPainted(false);
        botao.setBackground(new Color(45, 45, 45));
        botao.setForeground(Color.WHITE);
        botao.setFont(FONT_EMOJI);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.addActionListener(acao);

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(65, 65, 65));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(45, 45, 45));
            }
        });

        return botao;
    }

    private static void buscarPacientePorNome() {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome do paciente para buscar:");
        if (nome == null || nome.trim().isEmpty()) return;

        List<Paciente> resultados = pacienteController.buscarPacientePorNome(nome);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente encontrado com o nome: " + nome);
        } else {
            StringBuilder sb = new StringBuilder("Pacientes encontrados:\n\n");
            for (Paciente p : resultados) {
                sb.append("ID: ").append(p.getId()).append("\n");
                sb.append("Nome: ").append(p.getNome()).append("\n");
                sb.append("CPF: ").append(p.getCpf()).append("\n");
                sb.append("Telefone: ").append(p.getTelefone()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    private static void buscarPacientePorId() {
        String idStr = JOptionPane.showInputDialog(null, "Digite o ID do paciente:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            Paciente paciente = pacienteController.buscarPacientePorId(id);

            if (paciente == null) {
                JOptionPane.showMessageDialog(null, "Paciente não encontrado com ID: " + id);
            } else {
                StringBuilder sb = new StringBuilder("Paciente encontrado:\n\n");
                sb.append("ID: ").append(paciente.getId()).append("\n");
                sb.append("Nome: ").append(paciente.getNome()).append("\n");
                sb.append("CPF: ").append(paciente.getCpf()).append("\n");
                sb.append("Telefone: ").append(paciente.getTelefone()).append("\n");

                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void atualizarPaciente() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do paciente para atualizar:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            Paciente paciente = pacienteController.buscarPacientePorId(id);
            if (paciente == null) {
                JOptionPane.showMessageDialog(null, "Paciente não encontrado com ID: " + id);
                return;
            }

            String novoNome = JOptionPane.showInputDialog(null, "Nome:", paciente.getNome());
            if (novoNome == null || novoNome.trim().isEmpty()) return;

            String novoCpf = JOptionPane.showInputDialog(null, "CPF:", paciente.getCpf());
            if (novoCpf == null || novoCpf.trim().isEmpty()) return;

            String novoTelefone = JOptionPane.showInputDialog(null, "Telefone:", paciente.getTelefone());
            if (novoTelefone == null || novoTelefone.trim().isEmpty()) return;

            String resultado = pacienteController.atualizarPaciente(id, novoNome.trim(), novoCpf.trim(), novoTelefone.trim());

            JOptionPane.showMessageDialog(null, resultado);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void excluirPaciente() {
        String idStr = JOptionPane.showInputDialog(null, "Digite o ID do paciente que deseja excluir:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            String resultado = pacienteController.excluirPaciente(id);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void buscarMedicoPorNome() {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome do médico para buscar:");
        if (nome == null || nome.trim().isEmpty()) return;

        List<sistema.model.Medico> resultados = medicoController.buscarMedicosPorNome(nome);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum médico encontrado com o nome: " + nome);
        } else {
            StringBuilder sb = new StringBuilder("Médicos encontrados:\n\n");
            for (sistema.model.Medico m : resultados) {
                sb.append("ID: ").append(m.getId()).append("\n");
                sb.append("Nome: ").append(m.getNome()).append("\n");
                sb.append("Especialidade: ").append(m.getEspecialidade()).append("\n");
                sb.append("CRM: ").append(m.getCrm()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    private static void buscarMedicoPorId() {
        String idStr = JOptionPane.showInputDialog(null, "Digite o ID do médico:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            sistema.model.Medico medico = medicoController.buscarPorId(id);

            if (medico == null) {
                JOptionPane.showMessageDialog(null, "Médico não encontrado com ID: " + id);
            } else {
                StringBuilder sb = new StringBuilder("Médico encontrado:\n\n");
                sb.append("ID: ").append(medico.getId()).append("\n");
                sb.append("Nome: ").append(medico.getNome()).append("\n");
                sb.append("Especialidade: ").append(medico.getEspecialidade()).append("\n");
                sb.append("CRM: ").append(medico.getCrm()).append("\n");

                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void atualizarMedico() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do médico para atualizar:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            sistema.model.Medico medico = medicoController.buscarPorId(id);
            if (medico == null) {
                JOptionPane.showMessageDialog(null, "Médico não encontrado com ID: " + id);
                return;
            }

            String novoNome = JOptionPane.showInputDialog(null, "Nome:", medico.getNome());
            if (novoNome == null || novoNome.trim().isEmpty()) return;

            String novaEspecialidade = JOptionPane.showInputDialog(null, "Especialidade:", medico.getEspecialidade());
            if (novaEspecialidade == null || novaEspecialidade.trim().isEmpty()) return;

            String novoCrm = JOptionPane.showInputDialog(null, "CRM:", medico.getCrm());
            if (novoCrm == null || novoCrm.trim().isEmpty()) return;

            String resultado = medicoController.atualizarMedico(id, novoNome.trim(), novaEspecialidade.trim(), novoCrm.trim());

            JOptionPane.showMessageDialog(null, resultado);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void excluirMedico() {
        String idStr = JOptionPane.showInputDialog(null, "Digite o ID do médico que deseja excluir:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            String resultado = medicoController.excluirMedico(id);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void atualizarConsulta() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID da consulta para atualizar:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            Consulta consulta = consultaController.buscarConsultaPorId(id);
            if (consulta == null) {
                JOptionPane.showMessageDialog(null, "Consulta não encontrada com ID: " + id);
                return;
            }

            String dataStr = JOptionPane.showInputDialog(null, "Data (AAAA-MM-DD):", consulta.getData().toString());
            if (dataStr == null || dataStr.trim().isEmpty()) return;
            Date data = Date.valueOf(dataStr);

            String horaStr = JOptionPane.showInputDialog(null, "Hora (HH:MM:SS):", consulta.getHora().toString());
            if (horaStr == null || horaStr.trim().isEmpty()) return;
            Time hora = Time.valueOf(horaStr);

            String idPacienteStr = JOptionPane.showInputDialog(null, "ID do Paciente:", String.valueOf(consulta.getIdPaciente()));
            if (idPacienteStr == null || idPacienteStr.trim().isEmpty()) return;
            int idPaciente = Integer.parseInt(idPacienteStr);

            String idMedicoStr = JOptionPane.showInputDialog(null, "ID do Médico:", String.valueOf(consulta.getIdMedico()));
            if (idMedicoStr == null || idMedicoStr.trim().isEmpty()) return;
            int idMedico = Integer.parseInt(idMedicoStr);

            // Usando construtor completo (com observacao vazia)
            Consulta novaConsulta = new Consulta(id, idPaciente, idMedico, data, hora, "");

            String resultado = consultaController.atualizarConsulta(novaConsulta);

            JOptionPane.showMessageDialog(null, resultado);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na atualização da consulta: " + e.getMessage());
        }
    }


    private static void excluirConsulta() {
        String idStr = JOptionPane.showInputDialog(null, "Digite o ID da consulta que deseja excluir:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            String resultado = consultaController.excluirConsulta(id);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

}
