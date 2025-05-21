package sistema.view;

import sistema.controller.ConsultaController;
import sistema.model.Consulta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class ListarConsulta extends JFrame {

    private JTable tabela;
    private JTextField campoNomePaciente;
    private JTextField campoData;
    private DefaultTableModel modelo;
    private ConsultaController controller;

    public ListarConsulta() {
        setTitle("Lista de Consultas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        controller = new ConsultaController();

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoNomePaciente = new JTextField(15);
        campoData = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");

        painelFiltros.add(new JLabel("Paciente:"));
        painelFiltros.add(campoNomePaciente);
        painelFiltros.add(new JLabel("Data (AAAA-MM-DD):"));
        painelFiltros.add(campoData);
        painelFiltros.add(btnBuscar);
        painelFiltros.add(btnAtualizar);
        painelFiltros.add(btnExcluir);

        add(painelFiltros, BorderLayout.NORTH);

        tabela = new JTable();
        modelo = new DefaultTableModel(new String[]{"ID", "Paciente", "Médico", "Data", "Hora", "Observação"}, 0);
        tabela.setModel(modelo);
        tabela.getColumnModel().getColumn(0).setMinWidth(0);  // Esconde a coluna ID
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarConsultas();

        btnBuscar.addActionListener(e -> buscarConsultas());
        btnAtualizar.addActionListener(e -> atualizarConsulta());
        btnExcluir.addActionListener(e -> excluirConsulta());

        setVisible(true);
    }

    private void carregarConsultas() {
        modelo.setRowCount(0);
        List<Consulta> consultas = controller.listarConsultas();

        for (Consulta c : consultas) {
            String nomePaciente = controller.getNomePaciente(c.getIdPaciente());
            String nomeMedico = controller.getNomeMedico(c.getIdMedico());
            modelo.addRow(new Object[]{
                    c.getId(),
                    nomePaciente,
                    nomeMedico,
                    c.getData(),
                    c.getHora(),
                    c.getObservacao()
            });
        }
    }

    private void buscarConsultas() {
        modelo.setRowCount(0);
        String nome = campoNomePaciente.getText().trim();
        String dataStr = campoData.getText().trim();
        List<Consulta> consultas = null;

        if (!nome.isEmpty()) {
            consultas = controller.buscarPorNomePaciente(nome);
        } else if (!dataStr.isEmpty()) {
            try {
                Date data = Date.valueOf(dataStr);
                consultas = controller.buscarPorData(data);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use o formato AAAA-MM-DD.");
                return;
            }
        } else {
            carregarConsultas();
            return;
        }

        for (Consulta c : consultas) {
            String nomePaciente = controller.getNomePaciente(c.getIdPaciente());
            String nomeMedico = controller.getNomeMedico(c.getIdMedico());
            modelo.addRow(new Object[]{
                    c.getId(),
                    nomePaciente,
                    nomeMedico,
                    c.getData(),
                    c.getHora(),
                    c.getObservacao()
            });
        }
    }

    private void atualizarConsulta() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para atualizar.");
            return;
        }

        int id = (int) modelo.getValueAt(linhaSelecionada, 0);
        // Aqui você pode abrir uma nova janela de edição passando o ID da consulta
        JOptionPane.showMessageDialog(this, "Abriria janela de edição para consulta ID: " + id);
    }

    private void excluirConsulta() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para excluir.");
            return;
        }

        int id = (int) modelo.getValueAt(linhaSelecionada, 0);
        int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta consulta?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            controller.excluirConsulta(id);
            carregarConsultas();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListarConsulta::new);
    }
}
