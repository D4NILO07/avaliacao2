package sistema.view;

import sistema.controller.ConsultaController;
import sistema.dao.MedicoDAO;
import sistema.dao.PacienteDAO;
import sistema.model.Medico;
import sistema.model.Paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgendarConsulta extends JFrame {

    private JComboBox<Paciente> pacienteComboBox;
    private JComboBox<Medico> medicoComboBox;
    private JTextField dataField, horaField, observacaoField;
    private JButton btnAgendar;

    public AgendarConsulta() {
        setTitle("Agendamento de Consulta");
        setLayout(new GridLayout(6, 2));
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Paciente:"));
        pacienteComboBox = new JComboBox<>();
        add(pacienteComboBox);

        add(new JLabel("Médico:"));
        medicoComboBox = new JComboBox<>();
        add(medicoComboBox);

        add(new JLabel("Data (AAAA-MM-DD):"));
        dataField = new JTextField();
        add(dataField);

        add(new JLabel("Hora (HH:MM:SS):"));
        horaField = new JTextField();
        add(horaField);

        add(new JLabel("Observação:"));
        observacaoField = new JTextField();
        add(observacaoField);

        btnAgendar = new JButton("Agendar");
        add(btnAgendar);

        carregarPacientes();
        carregarMedicos();

        btnAgendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agendarConsulta();
            }
        });

        setVisible(true);
    }

    private void carregarPacientes() {
        PacienteDAO pacienteDAO = new PacienteDAO();
        for (Paciente paciente : pacienteDAO.listarPacientes()) {
            pacienteComboBox.addItem(paciente);
        }
    }

    private void carregarMedicos() {
        MedicoDAO medicoDAO = new MedicoDAO();
        for (Medico medico : medicoDAO.listarMedicos()) {
            medicoComboBox.addItem(medico);
        }
    }

    private void agendarConsulta() {
        Paciente paciente = (Paciente) pacienteComboBox.getSelectedItem();
        Medico medico = (Medico) medicoComboBox.getSelectedItem();
        String dataStr = dataField.getText();
        String horaStr = horaField.getText();
        String observacao = observacaoField.getText();

        if (paciente == null || medico == null) {
            JOptionPane.showMessageDialog(this, "Paciente e Médico devem ser selecionados!");
            return;
        }

        ConsultaController controller = new ConsultaController();
        String mensagem = controller.agendarConsulta(
                paciente.getId(), medico.getId(), dataStr, horaStr, observacao);
        JOptionPane.showMessageDialog(this, mensagem);

        if (mensagem.contains("sucesso")) {
            dataField.setText("");
            horaField.setText("");
            observacaoField.setText("");
        }
    }

    public static void main(String[] args) {
        new AgendarConsulta();
    }
}
