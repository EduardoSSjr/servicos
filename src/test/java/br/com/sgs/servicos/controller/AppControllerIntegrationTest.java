package br.com.sgs.servicos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home_deveRetornarStatus200EViewHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // Verifica se a resposta foi 200 OK
                .andExpect(view().name("home")) // Verifica se o controller selecionou a view "home.jsp"
                .andExpect(model().attributeExists("chamados")); // Verifica se o controller colocou a lista de "chamados" no modelo
    }

    @Test
    void listarSolicitantes_deveRetornarPaginaDeLista() throws Exception {
        mockMvc.perform(get("/solicitantes"))
                .andExpect(status().isOk()) // Verifica se a resposta foi 200 OK
                .andExpect(view().name("solicitante/lista")) // Verifica se o controller selecionou a view "solicitante/lista.jsp"
                .andExpect(model().attributeExists("solicitantes")); // Verifica se o controller colocou a lista de "solicitantes"
    }
}