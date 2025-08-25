package br.com.sgs.servicos.service;

import br.com.sgs.servicos.model.OrdemDeServico;
import br.com.sgs.servicos.model.Prioridade;
import br.com.sgs.servicos.model.Solicitante;
import br.com.sgs.servicos.repository.OrdemDeServicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Habilita o uso de anotações do Mockito (como @Mock e @InjectMocks)
@ExtendWith(MockitoExtension.class)
class OrdemDeServicoServiceTest {

    // Cria um "dublê" (mock) das dependências. Elas não se conectarão ao banco de verdade.
    @Mock
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Mock
    private OrdemPreventivaFactory preventivaFactory;

    @Mock
    private OrdemCorretivaFactory corretivaFactory;

    // Injeta os mocks criados acima na instância do serviço que vamos testar
    @InjectMocks
    private OrdemDeServicoService service;

    @Test
    void abrirOS_quandoTipoForCorretiva_deveUsarCorretivaFactoryESalvar() {
        // ARRANGE (Preparação)
        Solicitante solicitante = new Solicitante(); // Objeto de teste
        OrdemDeServico osMock = new OrdemDeServico(); // Simula uma OS criada pela fábrica
        osMock.setTipo("Corretiva");

        // Dizemos ao mock da fábrica o que fazer quando for chamado
        when(corretivaFactory.create(any(), any(), any(), any())).thenReturn(osMock);
        // Dizemos ao mock do repositório para retornar o mesmo objeto que recebeu ao salvar
        when(ordemDeServicoRepository.save(any(OrdemDeServico.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // ACT (Ação)
        // Chamamos o método que queremos testar
        OrdemDeServico resultado = service.abrirOS("corretiva", "Teste", "Desc", solicitante, Prioridade.ALTA);


        // ASSERT (Verificação)
        // Verificamos se os resultados são os esperados
        assertNotNull(resultado);
        assertEquals("Corretiva", resultado.getTipo());

        // Verificamos se os mocks foram chamados como esperado
        verify(corretivaFactory, times(1)).create(any(), any(), any(), any()); // Verifica se a fábrica correta foi chamada 1 vez
        verify(preventivaFactory, never()).create(any(), any(), any(), any());   // Verifica se a outra fábrica NUNCA foi chamada
        verify(ordemDeServicoRepository, times(1)).save(any(OrdemDeServico.class)); // Verifica se o save foi chamado 1 vez
    }
}