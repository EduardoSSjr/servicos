package br.com.sgs.servicos.controller;

import br.com.sgs.servicos.controller.dto.OrdemDeServicoRequest;
import br.com.sgs.servicos.model.OrdemDeServico;
import br.com.sgs.servicos.model.Prioridade;
import br.com.sgs.servicos.model.Solicitante;
import br.com.sgs.servicos.model.Tecnico;
import br.com.sgs.servicos.repository.OrdemDeServicoRepository;
import br.com.sgs.servicos.repository.SolicitanteRepository;
import br.com.sgs.servicos.repository.TecnicoRepository;
import br.com.sgs.servicos.service.OrdemDeServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Autowired
    private SolicitanteRepository solicitanteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private OrdemDeServicoService osService;

    // --- PÁGINA INICIAL / DASHBOARD ---
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("chamados", ordemDeServicoRepository.findAll());
        return mav;
    }

    // --- LISTAGEM DE SOLICITANTES E TÉCNICOS ---
    @GetMapping("/solicitantes")
    public ModelAndView listarSolicitantes() {
        ModelAndView mav = new ModelAndView("solicitante/lista");
        mav.addObject("solicitantes", solicitanteRepository.findAll());
        return mav;
    }

    @GetMapping("/tecnicos")
    public ModelAndView listarTecnicos() {
        ModelAndView mav = new ModelAndView("tecnico/lista");
        mav.addObject("tecnicos", tecnicoRepository.findAll());
        return mav;
    }

    // --- CADASTRO DE SOLICITANTE ---
    @GetMapping("/solicitantes/novo")
    public ModelAndView exibirFormularioSolicitante() {
        ModelAndView mav = new ModelAndView("solicitante/form");
        mav.addObject("solicitante", new Solicitante());
        return mav;
    }

    @PostMapping("/solicitantes/salvar")
    public String salvarSolicitante(@ModelAttribute Solicitante solicitante) {
        solicitanteRepository.save(solicitante);
        return "redirect:/sucesso";
    }

    // --- CADASTRO DE TÉCNICO ---
    @GetMapping("/tecnicos/novo")
    public ModelAndView exibirFormularioTecnico() {
        ModelAndView mav = new ModelAndView("tecnico/form");
        mav.addObject("tecnico", new Tecnico());
        return mav;
    }

    @PostMapping("/tecnicos/salvar")
    public String salvarTecnico(@ModelAttribute Tecnico tecnico) {
        tecnicoRepository.save(tecnico);
        return "redirect:/sucesso";
    }
    
    // --- GESTÃO DE ORDEM DE SERVIÇO (OS) ---
    @GetMapping("/os/novo")
    public ModelAndView exibirFormularioOS() {
        ModelAndView mav = new ModelAndView("os/form");
        mav.addObject("solicitantes", solicitanteRepository.findAll());
        mav.addObject("prioridades", Prioridade.values());
        mav.addObject("osRequest", new OrdemDeServicoRequest());
        return mav;
    }

    @PostMapping("/os/salvar")
    public String salvarOS(@ModelAttribute OrdemDeServicoRequest osRequest) {
        Solicitante solicitante = solicitanteRepository.findById(osRequest.getSolicitanteId())
                .orElseThrow(() -> new RuntimeException("Solicitante não encontrado!"));
        osService.abrirOS(osRequest.getTipo(), osRequest.getTitulo(), osRequest.getDescricao(), solicitante, osRequest.getPrioridade());
        return "redirect:/sucesso";
    }
    
    // -- NOVOS MÉTODOS PARA VISUALIZAR, EDITAR E ATRIBUIR OS --

    @GetMapping("/os/{id}")
    public ModelAndView detalharOS(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("os/detalhes");
        OrdemDeServico os = ordemDeServicoRepository.findById(id).orElseThrow(() -> new RuntimeException("OS não encontrada!"));
        mav.addObject("os", os);
        mav.addObject("tecnicos", tecnicoRepository.findAll()); // Lista de técnicos para o dropdown de atribuição
        mav.addObject("prioridades", Prioridade.values()); // Lista de prioridades para o form de edição
        return mav;
    }

    @PostMapping("/os/{id}/editar")
    public String editarOS(@PathVariable Long id, @RequestParam String titulo, @RequestParam String descricao, @RequestParam String tipo, @RequestParam Prioridade prioridade) {
        OrdemDeServico os = ordemDeServicoRepository.findById(id).orElseThrow(() -> new RuntimeException("OS não encontrada!"));
        os.setTitulo(titulo);
        os.setDescricao(descricao);
        os.setTipo(tipo);
        os.setPrioridade(prioridade);
        ordemDeServicoRepository.save(os);
        return "redirect:/os/" + id;
    }

    @PostMapping("/os/{id}/atribuir-tecnico")
    public String atribuirTecnico(@PathVariable Long id, @RequestParam Long tecnicoId) {
        OrdemDeServico os = ordemDeServicoRepository.findById(id).orElseThrow(() -> new RuntimeException("OS não encontrada!"));
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId).orElse(null); // Permite desatribuir se selecionar uma opção vazia
        os.setTecnicoResponsavel(tecnico);
        ordemDeServicoRepository.save(os);
        return "redirect:/os/" + id;
    }


    // --- PÁGINA DE SUCESSO GENÉRICA ---
    @GetMapping("/sucesso")
    public String exibirPaginaSucesso() {
        return "common/sucesso";
    }
}