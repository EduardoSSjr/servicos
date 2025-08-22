/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sgs.servicos.controller.dto;

import br.com.sgs.servicos.model.Prioridade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdemDeServicoRequest {
    
    // Dados que o cliente da API vai nos enviar
    private String tipo; // "preventiva" ou "corretiva"
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private Long solicitanteId; // Apenas o ID de um solicitante que já existe

}
