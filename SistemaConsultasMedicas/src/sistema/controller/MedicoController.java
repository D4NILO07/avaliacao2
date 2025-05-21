package sistema.controller;

import sistema.dao.MedicoDAO;
import sistema.model.Medico;

import java.util.List;

public class MedicoController {

    public String cadastrarMedico(String nome, String especialidade, String crm) {
        if (nome == null || nome.trim().isEmpty() ||
                especialidade == null || especialidade.trim().isEmpty() ||
                crm == null || crm.trim().isEmpty()) {
            return "Todos os campos devem ser preenchidos!";
        }

        Medico medico = new Medico(0, nome, especialidade, crm);
        try {
            MedicoDAO dao = new MedicoDAO();
            dao.inserirMedico(medico);
            return "Médico cadastrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar médico: " + e.getMessage();
        }
    }

    public String atualizarMedico(int id, String nome, String especialidade, String crm) {
        if (nome == null || nome.trim().isEmpty() ||
                especialidade == null || especialidade.trim().isEmpty() ||
                crm == null || crm.trim().isEmpty()) {
            return "Todos os campos devem ser preenchidos!";
        }

        Medico medico = new Medico(id, nome, especialidade, crm);
        try {
            MedicoDAO dao = new MedicoDAO();
            dao.atualizarMedico(medico);
            return "Médico atualizado com sucesso!";
        } catch (Exception e) {
            return "Erro ao atualizar médico: " + e.getMessage();
        }
    }

    public String excluirMedico(int id) {
        try {
            MedicoDAO dao = new MedicoDAO();
            dao.excluirMedico(id);
            return "Médico excluído com sucesso!";
        } catch (Exception e) {
            return "Erro ao excluir médico: " + e.getMessage();
        }
    }

    public List<Medico> buscarMedicosPorNome(String nome) {
        MedicoDAO dao = new MedicoDAO();
        return dao.buscarPorNome(nome);
    }

    public List<Medico> listarMedicos() {
        MedicoDAO dao = new MedicoDAO();
        return dao.listarMedicos();
    }

    public Medico buscarPorId(int id) {
        MedicoDAO dao = new MedicoDAO();
        return dao.buscarPorId(id);
    }
}
