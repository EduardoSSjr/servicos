/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sgs.servicos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca esta classe como uma entidade JPA (tabela no banco)
@Data   // Lombok: gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: gera um construtor sem argumentos
@AllArgsConstructor // Lombok: gera um construtor com todos os argumentos
public class Solicitante {

    @Id // Marca este campo como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de auto-incremento
    private Long id;

    private String nome;
    private String email;
    private String departamento;
    private String cargo;
    private String telefone;

}
