package sistema.view;

import sistema.dao.PacienteDAO;
import sistema.model.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarPacientes extends JFrame {

    private JTable tabelaPacientes;
    private JScrollPane scrollPane;

    public ListarPacientes() {
        setTitle("Listagem de Pacientes");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Tabela de pacientes
        tabelaPacientes = new JTable();
        scrollPane = new JScrollPane(tabelaPacientes);
        add(scrollPane, BorderLayout.CENTER);

        // Carregar dados na tabela
        carregarPacientes();

        setVisible(true);
    }

    private void carregarPacientes() {
        PacienteDAO pacienteDAO = new PacienteDAO();
        List<Paciente> pacientes = pacienteDAO.listarPacientes();

        // Definir as colunas da tabela
        String[] colunas = {"ID", "Nome", "CPF", "Telefone"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        // Preencher a tabela com os dados dos pacientes
        for (Paciente paciente : pacientes) {
            Object[] linha = {paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getTelefone()};
            modelo.addRow(linha);
        }

        tabelaPacientes.setModel(modelo);
    }

    public static void main(String[] args) {
        new ListarPacientes();
    }
}
