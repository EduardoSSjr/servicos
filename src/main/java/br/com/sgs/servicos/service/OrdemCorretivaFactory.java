/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sgs.servicos.service;

import br.com.sgs.servicos.model.OrdemDeServico;
import br.com.sgs.servicos.model.Prioridade;
import br.com.sgs.servicos.model.Solicitante;
import br.com.sgs.servicos.model.Status;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class OrdemCorretivaFactory implements OrdemDeServicoFactory {

    @Override
    public OrdemDeServico create(String titulo, String descricao, Solicitante solicitante, Prioridade prioridade) {
        OrdemDeServico os = new OrdemDeServico();
        os.setTitulo(titulo);
        os.setDescricao(descricao);
        os.setSolicitante(solicitante);
        os.setPrioridade(prioridade);

        // Lógica específica da fábrica de OS Corretiva
        os.setTipo("Corretiva");
        os.setStatus(Status.ABERTA);
        os.setDataDeAbertura(LocalDateTime.now());

        return os;
    }
}