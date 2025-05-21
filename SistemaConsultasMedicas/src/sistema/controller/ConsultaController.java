package sistema.controller;

import sistema.dao.ConsultaDAO;
import sistema.dao.PacienteDAO;
import sistema.dao.MedicoDAO;
import sistema.model.Consulta;
import sistema.model.Paciente;
import sistema.model.Medico;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ConsultaController {

    public String agendarConsulta(int idPaciente, int idMedico, String dataStr, String horaStr, String observacao) {
        if (dataStr == null || dataStr.trim().isEmpty() ||
                horaStr == null || horaStr.trim().isEmpty()) {
            return "Data e hora são obrigatórias!";
        }

        try {
            Date data = Date.valueOf(dataStr);
            Time hora = Time.valueOf(horaStr);

            Consulta consulta = new Consulta(0, idPaciente, idMedico, data, hora, observacao);
            ConsultaDAO dao = new ConsultaDAO();
            dao.agendarConsulta(consulta);

            return "Consulta agendada com sucesso!";
        } catch (Exception e) {
            return "Erro ao agendar consulta: " + e.getMessage();
        }
    }

    public String atualizarConsulta(int id, int idPaciente, int idMedico, String dataStr, String horaStr, String observacao) {
        try {
            Date data = Date.valueOf(dataStr);
            Time hora = Time.valueOf(horaStr);

            Consulta consulta = new Consulta(id, idPaciente, idMedico, data, hora, observacao);
            ConsultaDAO dao = new ConsultaDAO();
            dao.atualizarConsulta(consulta);

            return "Consulta atualizada com sucesso!";
        } catch (Exception e) {
            return "Erro ao atualizar consulta: " + e.getMessage();
        }
    }

    public String excluirConsulta(int id) {
        try {
            ConsultaDAO dao = new ConsultaDAO();
            dao.excluirConsulta(id);
            return "Consulta excluída com sucesso!";
        } catch (Exception e) {
            return "Erro ao excluir consulta: " + e.getMessage();
        }
    }

    // Lista todas as consultas (retorna List<Consulta>)
    public List<Consulta> listarConsultas() {
        ConsultaDAO dao = new ConsultaDAO();
        return dao.listarConsultas();
    }

    // Busca consultas por nome do paciente
    public List<Consulta> buscarPorNomePaciente(String nome) {
        ConsultaDAO dao = new ConsultaDAO();
        return dao.buscarPorNomePaciente(nome);
    }

    // Busca consultas por data
    public List<Consulta> buscarPorData(Date data) {
        ConsultaDAO dao = new ConsultaDAO();
        return dao.buscarPorData(data);
    }

    // Busca consulta por ID
    public Consulta buscarPorId(int id) {
        ConsultaDAO dao = new ConsultaDAO();
        return dao.buscarPorId(id);
    }

    // Retorna o nome do paciente pelo id
    public String getNomePaciente(int idPaciente) {
        PacienteDAO dao = new PacienteDAO();
        Paciente paciente = dao.buscarPorId(idPaciente);
        return paciente != null ? paciente.getNome() : "Desconhecido";
    }

    // Retorna o nome do médico pelo id
    public String getNomeMedico(int idMedico) {
        MedicoDAO dao = new MedicoDAO();
        Medico medico = dao.buscarPorId(idMedico);
        return medico != null ? medico.getNome() : "Desconhecido";
    }
}
