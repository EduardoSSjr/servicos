/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.sgs.servicos.service;

import br.com.sgs.servicos.model.OrdemDeServico;
import br.com.sgs.servicos.model.Prioridade;
import br.com.sgs.servicos.model.Solicitante;

public interface OrdemDeServicoFactory {
    OrdemDeServico create(String titulo, String descricao, Solicitante solicitante, Prioridade prioridade);
}