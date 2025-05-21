package sistema.view;

import sistema.dao.Conexao;
import java.sql.Connection;

public class TestaConexao {
    public static void main(String[] args) {
        try {
            Connection conn = Conexao.conectar();
            System.out.println("✅ Conexão realizada com sucesso!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
        }
    }
}

