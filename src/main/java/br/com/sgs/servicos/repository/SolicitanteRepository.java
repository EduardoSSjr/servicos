/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.sgs.servicos.repository;

import br.com.sgs.servicos.model.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, mas boa prática para indicar que é um bean de persistência
public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {
    // A mágica acontece aqui! Não precisamos escrever nenhum código.
}
