package br.com.sgs.servicos;

import br.com.sgs.servicos.config.DatabaseInitializer; // Importe a nossa nova classe
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServicosApplication {

    public static void main(String[] args) {
        try {
            System.out.println("Inicializando a verificação do banco de dados...");
            // 1. Chama nosso método para garantir que o banco de dados físico exista
            DatabaseInitializer.createDatabaseIfNotExists();
            System.out.println("Verificação do banco de dados concluída com sucesso.");
            
            // 2. Inicia a aplicação Spring Boot normalmente
            SpringApplication.run(ServicosApplication.class, args);
            
        } catch (Exception e) {
            System.err.println("Falha ao iniciar a aplicação:");
            e.printStackTrace();
            System.exit(1); // Encerra a aplicação se a inicialização do DB falhar
        }
    }
}