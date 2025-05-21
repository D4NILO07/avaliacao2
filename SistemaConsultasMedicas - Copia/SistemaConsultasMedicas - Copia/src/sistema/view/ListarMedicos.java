package sistema.view;

import sistema.dao.MedicoDAO;
import sistema.model.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarMedicos extends JFrame {

    private JTable tabelaMedicos;
    private JScrollPane scrollPane;

    public ListarMedicos() {
        setTitle("Listagem de Médicos");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Tabela de médicos
        tabelaMedicos = new JTable();
        scrollPane = new JScrollPane(tabelaMedicos);
        add(scrollPane, BorderLayout.CENTER);

        // Carregar dados na tabela
        carregarMedicos();

        setVisible(true);
    }

    private void carregarMedicos() {
        MedicoDAO medicoDAO = new MedicoDAO();
        List<Medico> medicos = medicoDAO.listarMedicos();

        // Definir as colunas da tabela
        String[] colunas = {"ID", "Nome", "Especialidade", "CRM"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        // Preencher a tabela com os dados dos médicos
        for (Medico medico : medicos) {
            Object[] linha = {medico.getId(), medico.getNome(), medico.getEspecialidade(), medico.getCrm()};
            modelo.addRow(linha);
        }

        tabelaMedicos.setModel(modelo);
    }

    public static void main(String[] args) {
        new ListarMedicos();
    }
}
