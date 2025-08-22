/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sgs.servicos.controller;

import br.com.sgs.servicos.controller.dto.OrdemDeServicoRequest;
import br.com.sgs.servicos.model.OrdemDeServico;
import br.com.sgs.servicos.model.Solicitante;
import br.com.sgs.servicos.repository.SolicitanteRepository;
import br.com.sgs.servicos.service.OrdemDeServicoService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController // Marca a classe como um Controller REST
@RequestMapping("/os") // Todas as rotas neste controller começarão com "/os"
public class OrdemDeServicoController {

    @Autowired
    private OrdemDeServicoService osService;

    @Autowired
    private SolicitanteRepository solicitanteRepository;

    @PostMapping // Mapeia este método para requisições HTTP POST para "/os"
    public ResponseEntity<OrdemDeServico> abrirOS(@RequestBody OrdemDeServicoRequest request, UriComponentsBuilder uriBuilder) {
        // 1. Busca o solicitante no banco de dados pelo ID recebido
        Solicitante solicitante = solicitanteRepository.findById(request.getSolicitanteId())
                .orElseThrow(() -> new RuntimeException("Solicitante não encontrado!"));

        // 2. Chama nosso serviço, que usa o Factory Method para criar a OS
        OrdemDeServico novaOS = osService.abrirOS(
            request.getTipo(),
            request.getTitulo(),
            request.getDescricao(),
            solicitante,
            request.getPrioridade()
        );

        // 3. Devolve uma resposta HTTP 201 (Created) com a OS criada no corpo e a URL para acessá-la no cabeçalho
        URI uri = uriBuilder.path("/os/{id}").buildAndExpand(novaOS.getId()).toUri();
        return ResponseEntity.created(uri).body(novaOS);
    }
}
