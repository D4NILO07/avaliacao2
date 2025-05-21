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

    private final ConsultaDAO consultaDAO = new ConsultaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final MedicoDAO medicoDAO = new MedicoDAO();

    public String agendarConsulta(int idPaciente, int idMedico, String dataStr, String horaStr, String observacao) {
        if (dataStr == null || dataStr.trim().isEmpty() ||
                horaStr == null || horaStr.trim().isEmpty()) {
            return "Data e hora são obrigatórias!";
        }

        try {
            Date data = Date.valueOf(dataStr);
            Time hora = Time.valueOf(horaStr);

            Consulta consulta = new Consulta(0, idPaciente, idMedico, data, hora, observacao);
            consultaDAO.agendarConsulta(consulta);

            return "Consulta agendada com sucesso!";
        } catch (Exception e) {
            return "Erro ao agendar consulta: " + e.getMessage();
        }
    }

    public String atualizarConsulta(Consulta consulta) {
        try {
            boolean sucesso = consultaDAO.atualizarConsulta(consulta);
            if (sucesso) {
                return "Consulta atualizada com sucesso!";
            } else {
                return "Falha ao atualizar consulta.";
            }
        } catch (Exception e) {
            return "Erro ao atualizar consulta: " + e.getMessage();
        }
    }

    public String excluirConsulta(int id) {
        try {
            boolean sucesso = consultaDAO.excluirConsulta(id);
            if (sucesso) {
                return "Consulta excluída com sucesso!";
            } else {
                return "Falha ao excluir consulta.";
            }
        } catch (Exception e) {
            return "Erro ao excluir consulta: " + e.getMessage();
        }
    }

    public List<Consulta> listarConsultas() {
        return consultaDAO.listarConsultas();
    }

    public List<Consulta> buscarPorNomePaciente(String nome) {
        return consultaDAO.buscarPorNomePaciente(nome);
    }

    public List<Consulta> buscarPorData(Date data) {
        return consultaDAO.buscarPorData(data);
    }

    public Consulta buscarPorId(int id) {
        return consultaDAO.buscarPorId(id);
    }

    public Consulta buscarConsultaPorId(int id) {
        return buscarPorId(id);
    }

    public String getNomePaciente(int idPaciente) {
        Paciente paciente = pacienteDAO.buscarPorId(idPaciente);
        return paciente != null ? paciente.getNome() : "Desconhecido";
    }

    public String getNomeMedico(int idMedico) {
        Medico medico = medicoDAO.buscarPorId(idMedico);
        return medico != null ? medico.getNome() : "Desconhecido";
    }
}
