package sistema.view;

import sistema.dao.PacienteDAO;
import sistema.dao.MedicoDAO;
import sistema.dao.ConsultaDAO;
import sistema.model.Paciente;
import sistema.model.Medico;
import sistema.model.Consulta;

import java.sql.Date;
import java.sql.Time;

public class TestaDAO {

    public static void main(String[] args) {

        // Criar objetos de teste
        Paciente paciente = new Paciente(0, "João Silva", "123.456.789-00", "987654321");
        Medico medico = new Medico(0, "Dr. Carlos Oliveira", "Cardiologia", "CRM12345");
        Consulta consulta = new Consulta(0, 1, 1, Date.valueOf("2025-05-10"), Time.valueOf("14:30:00"), "Consulta de rotina");

        // Instanciar os DAOs
        PacienteDAO pacienteDAO = new PacienteDAO();
        MedicoDAO medicoDAO = new MedicoDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();

        // Testar inserção de paciente
        System.out.println("Inserindo paciente...");
        pacienteDAO.inserirPaciente(paciente);

        // Testar inserção de médico
        System.out.println("Inserindo médico...");
        medicoDAO.inserirMedico(medico);

        // Testar inserção de consulta
        System.out.println("Agendando consulta...");
        consultaDAO.agendarConsulta(consulta);

        // Testar listar pacientes
        System.out.println("Listando pacientes...");
        pacienteDAO.listarPacientes().forEach(p -> System.out.println(p));

        // Testar listar médicos
        System.out.println("Listando médicos...");
        medicoDAO.listarMedicos().forEach(m -> System.out.println(m));

        // Testar listar consultas
        System.out.println("Listando consultas...");
        consultaDAO.listarConsultas().forEach(c -> System.out.println(c));

        // Testar atualização de paciente
        paciente.setNome("João Silva Neto");
        pacienteDAO.atualizarPaciente(paciente);
        System.out.println("Paciente atualizado: " + paciente);

        // Testar atualização de médico
        medico.setNome("Dr. Carlos Oliveira Silva");
        medicoDAO.atualizarMedico(medico);
        System.out.println("Médico atualizado: " + medico);

        // Testar atualização de consulta
        consulta.setObservacao("Consulta de rotina atualizada");
        consultaDAO.atualizarConsulta(consulta);
        System.out.println("Consulta atualizada: " + consulta);

        // Testar exclusão de paciente
        pacienteDAO.excluirPaciente(paciente.getId());
        System.out.println("Paciente excluído.");

        // Testar exclusão de médico
        medicoDAO.excluirMedico(medico.getId());
        System.out.println("Médico excluído.");

        // Testar exclusão de consulta
        consultaDAO.excluirConsulta(consulta.getId());
        System.out.println("Consulta excluída.");
    }
}
