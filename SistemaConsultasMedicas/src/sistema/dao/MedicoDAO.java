package sistema.dao;

import sistema.model.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public void inserirMedico(Medico medico) {
        String sql = "INSERT INTO medico (nome, especialidade, crm) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setString(3, medico.getCrm());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medico> listarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getString("crm")
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    public void atualizarMedico(Medico medico) {
        String sql = "UPDATE medico SET nome = ?, especialidade = ?, crm = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setString(3, medico.getCrm());
            stmt.setInt(4, medico.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirMedico(int id) {
        String sql = "DELETE FROM medico WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Medico buscarPorId(int id) {
        String sql = "SELECT * FROM medico WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Medico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getString("crm")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Medico> buscarPorNome(String nome) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico WHERE nome LIKE ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getString("crm")
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }
}
