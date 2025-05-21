package sistema.dao;

import sistema.model.Consulta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public void agendarConsulta(Consulta consulta) {
        String sql = "INSERT INTO consulta (id_paciente, id_medico, data, hora, observacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consulta.getIdPaciente());
            stmt.setInt(2, consulta.getIdMedico());
            stmt.setDate(3, consulta.getData());
            stmt.setTime(4, consulta.getHora());
            stmt.setString(5, consulta.getObservacao());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Consulta> listarConsultas() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"),
                        rs.getDate("data"),
                        rs.getTime("hora"),
                        rs.getString("observacao")
                );
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultas;
    }

    public boolean atualizarConsulta(Consulta consulta) {
        String sql = "UPDATE consulta SET id_paciente = ?, id_medico = ?, data = ?, hora = ?, observacao = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consulta.getIdPaciente());
            stmt.setInt(2, consulta.getIdMedico());
            stmt.setDate(3, consulta.getData());
            stmt.setTime(4, consulta.getHora());
            stmt.setString(5, consulta.getObservacao());
            stmt.setInt(6, consulta.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirConsulta(int id) {
        String sql = "DELETE FROM consulta WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Consulta> buscarPorNomePaciente(String nome) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.* FROM consulta c JOIN paciente p ON c.id_paciente = p.id WHERE LOWER(p.nome) LIKE LOWER(?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"),
                        rs.getDate("data"),
                        rs.getTime("hora"),
                        rs.getString("observacao")
                );
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultas;
    }

    public List<Consulta> buscarPorData(Date data) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE data = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, data);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"),
                        rs.getDate("data"),
                        rs.getTime("hora"),
                        rs.getString("observacao")
                );
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultas;
    }

    public Consulta buscarPorId(int id) {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        Consulta consulta = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                consulta = new Consulta(
                        rs.getInt("id"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"),
                        rs.getDate("data"),
                        rs.getTime("hora"),
                        rs.getString("observacao")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consulta;
    }
}
