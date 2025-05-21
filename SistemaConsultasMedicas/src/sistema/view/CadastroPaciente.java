package sistema.view;

import sistema.controller.PacienteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroPaciente extends JFrame {

    private JTextField nomeField, cpfField, telefoneField;
    private JButton btnCadastrar;

    public CadastroPaciente() {
        setTitle("Cadastro de Paciente");
        setLayout(new GridLayout(4, 2));
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("CPF:"));
        cpfField = new JTextField();
        add(cpfField);

        add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        add(telefoneField);

        btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPaciente();
            }
        });

        setVisible(true);
    }

    private void cadastrarPaciente() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();

        PacienteController controller = new PacienteController();
        String mensagem = controller.cadastrarPaciente(nome, cpf, telefone);
        JOptionPane.showMessageDialog(this, mensagem);

        if (mensagem.contains("sucesso")) {
            nomeField.setText("");
            cpfField.setText("");
            telefoneField.setText("");
        }
    }

    public static void main(String[] args) {
        new CadastroPaciente();
    }
}
