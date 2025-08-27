package br.com.sgs.servicos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseInitializer {

    public static void createDatabaseIfNotExists() {
        // Carrega as configurações do application.properties para não repetir informação
        Properties props = new Properties();
        try (InputStream input = DatabaseInitializer.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        } catch (Exception e) {
            System.err.println("Erro ao carregar application.properties. Verifique se o arquivo existe.");
            e.printStackTrace();
            return;
        }

        // Pega as variáveis do arquivo de propriedades
        String dbName = "sgs_servicos"; // O nome do nosso banco
        String dbUser = props.getProperty("spring.datasource.username");
        String dbPassword = props.getProperty("spring.datasource.password");
        // Monta a URL para conectar ao servidor PostgreSQL, mas não a um banco específico (usamos 'postgres')
        String serverUrl = "jdbc:postgresql://localhost:5432/postgres";

        // Usamos try-with-resources para garantir que a conexão e o statement sejam fechados
        try (Connection conn = DriverManager.getConnection(serverUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {

            System.out.println("Conectado ao servidor PostgreSQL para verificar/criar o banco de dados.");

            // Verifica se o banco de dados já existe
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");
            if (!rs.next()) {
                // Se não existir, cria o banco de dados
                System.out.println("Banco de dados \"" + dbName + "\" não encontrado. Criando...");
                stmt.executeUpdate("CREATE DATABASE \"" + dbName + "\"");
                System.out.println("Banco de dados \"" + dbName + "\" criado com sucesso.");
            } else {
                System.out.println("Banco de dados \"" + dbName + "\" já existe.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao verificar/criar o banco de dados \"" + dbName + "\": " + e.getMessage());
            // Lançamos a exceção para parar a aplicação se não conseguirmos criar o banco
            throw new RuntimeException(e);
        } finally {
            System.out.println("Desconectado do servidor PostgreSQL (verificação/criação).");
        }
    }
}