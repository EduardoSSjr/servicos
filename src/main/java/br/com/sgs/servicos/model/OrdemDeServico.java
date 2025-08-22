/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sgs.servicos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemDeServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String tipo; // "Preventiva" ou "Corretiva"

    @Enumerated(EnumType.STRING) // Grava o nome do enum no banco (ex: "ABERTA") em vez do número (0)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private LocalDateTime dataDeAbertura;
    private LocalDateTime dataDeFechamento;

    @ManyToOne // Define o relacionamento: Muitas OS podem pertencer a UM Solicitante
    @JoinColumn(name = "solicitante_id", nullable = false) // Define a chave estrangeira
    private Solicitante solicitante;

    @ManyToOne // Muitas OS podem ser de UM Técnico
    @JoinColumn(name = "tecnico_id") // Pode ser nulo
    private Tecnico tecnicoResponsavel;
}
