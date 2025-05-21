package sistema.view;

import sistema.controller.MedicoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroMedico extends JFrame {

    private JTextField nomeField, especialidadeField, crmField;
    private JButton btnCadastrar;

    public CadastroMedico() {
        setTitle("Cadastro de Médico");
        setLayout(new GridLayout(4, 2));
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Especialidade:"));
        especialidadeField = new JTextField();
        add(especialidadeField);

        add(new JLabel("CRM:"));
        crmField = new JTextField();
        add(crmField);

        btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarMedico();
            }
        });

        setVisible(true);
    }

    private void cadastrarMedico() {
        String nome = nomeField.getText();
        String especialidade = especialidadeField.getText();
        String crm = crmField.getText();

        MedicoController controller = new MedicoController();
        String mensagem = controller.cadastrarMedico(nome, especialidade, crm);
        JOptionPane.showMessageDialog(this, mensagem);

        if (mensagem.contains("sucesso")) {
            nomeField.setText("");
            especialidadeField.setText("");
            crmField.setText("");
        }
    }

    public static void main(String[] args) {
        new CadastroMedico();
    }
}
