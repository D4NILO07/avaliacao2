package sistema.controller;

import sistema.dao.PacienteDAO;
import sistema.model.Paciente;

import java.util.List;

public class PacienteController {

    public String cadastrarPaciente(String nome, String cpf, String telefone) {
        if (nome == null || nome.trim().isEmpty() ||
                cpf == null || cpf.trim().isEmpty() ||
                telefone == null || telefone.trim().isEmpty()) {
            return "Todos os campos devem ser preenchidos!";
        }

        Paciente paciente = new Paciente(0, nome, cpf, telefone);
        try {
            PacienteDAO dao = new PacienteDAO();
            dao.inserirPaciente(paciente);
            return "Paciente cadastrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar paciente: " + e.getMessage();
        }
    }

    public String atualizarPaciente(int id, String nome, String cpf, String telefone) {
        if (nome == null || nome.trim().isEmpty() ||
                cpf == null || cpf.trim().isEmpty() ||
                telefone == null || telefone.trim().isEmpty()) {
            return "Todos os campos devem ser preenchidos!";
        }

        Paciente paciente = new Paciente(id, nome, cpf, telefone);
        try {
            PacienteDAO dao = new PacienteDAO();
            dao.atualizarPaciente(paciente);
            return "Paciente atualizado com sucesso!";
        } catch (Exception e) {
            return "Erro ao atualizar paciente: " + e.getMessage();
        }
    }

    public String excluirPaciente(int id) {
        try {
            PacienteDAO dao = new PacienteDAO();
            dao.excluirPaciente(id);
            return "Paciente excluído com sucesso!";
        } catch (Exception e) {
            return "Erro ao excluir paciente: " + e.getMessage();
        }
    }

    public List<Paciente> buscarPacientePorNome(String nome) {
        PacienteDAO dao = new PacienteDAO();
        return dao.buscarPorNome(nome);
    }

    public Paciente buscarPacientePorId(int id) {
        PacienteDAO dao = new PacienteDAO();
        return dao.buscarPorId(id);
    }

    public List<Paciente> listarTodosPacientes() {
        PacienteDAO dao = new PacienteDAO();
        return dao.listarPacientes();
    }
}
