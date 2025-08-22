/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sgs.servicos.service;

import br.com.sgs.servicos.model.OrdemDeServico;
import br.com.sgs.servicos.model.Prioridade;
import br.com.sgs.servicos.model.Solicitante;
import br.com.sgs.servicos.repository.OrdemDeServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Anotação específica para classes de serviço (lógica de negócio)
public class OrdemDeServicoService {

    @Autowired // Injeção de Dependência: O Spring nos dará a instância do repositório
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Autowired // O Spring nos dará a instância da fábrica de OS Preventiva
    private OrdemPreventivaFactory preventivaFactory;

    @Autowired // O Spring nos dará a instância da fábrica de OS Corretiva
    private OrdemCorretivaFactory corretivaFactory;

    public OrdemDeServico abrirOS(String tipo, String titulo, String descricao, Solicitante solicitante, Prioridade prioridade) {
        OrdemDeServicoFactory factory;

        // Decidimos qual fábrica usar com base no tipo
        if ("preventiva".equalsIgnoreCase(tipo)) {
            factory = preventivaFactory;
        } else if ("corretiva".equalsIgnoreCase(tipo)) {
            factory = corretivaFactory;
        } else {
            throw new IllegalArgumentException("Tipo de Ordem de Serviço inválido: " + tipo);
        }

        // Usamos a fábrica selecionada para criar o objeto
        OrdemDeServico novaOS = factory.create(titulo, descricao, solicitante, prioridade);

        // Salvamos o objeto criado no banco de dados
        return ordemDeServicoRepository.save(novaOS);
    }
}