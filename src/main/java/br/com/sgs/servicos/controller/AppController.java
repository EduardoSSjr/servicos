package br.com.sgs.servicos.controller;

import br.com.sgs.servicos.controller.dto.OrdemDeServicoRequest;
import br.com.sgs.servicos.model.*;
import br.com.sgs.servicos.repository.OrdemDeServicoRepository;
import br.com.sgs.servicos.repository.SolicitanteRepository;
import br.com.sgs.servicos.repository.TecnicoRepository;
import br.com.sgs.servicos.service.OrdemDeServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

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

    // --- LISTAGEM ---
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

    // --- GESTÃO DE SOLICITANTE (CRIAR E EDITAR) ---
    @GetMapping("/solicitantes/novo")
    public ModelAndView exibirFormularioSolicitante() {
        ModelAndView mav = new ModelAndView("solicitante/form");
        mav.addObject("solicitante", new Solicitante());
        return mav;
    }

    @GetMapping("/solicitantes/{id}/editar")
    public ModelAndView exibirFormularioEdicaoSolicitante(@PathVariable Long id) {
        Solicitante solicitante = solicitanteRepository.findById(id).orElseThrow(() -> new RuntimeException("Solicitante não encontrado!"));
        ModelAndView mav = new ModelAndView("solicitante/form");
        mav.addObject("solicitante", solicitante);
        return mav;
    }

    @PostMapping("/solicitantes/salvar")
    public String salvarSolicitante(@ModelAttribute Solicitante solicitante) {
        solicitanteRepository.save(solicitante);
        return "redirect:/sucesso";
    }

    @PostMapping("/solicitantes/{id}/excluir")
    public String excluirSolicitante(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            solicitanteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro: Não é possível excluir o solicitante, pois ele está associado a um ou mais chamados.");
            return "redirect:/solicitantes";
        }
        return "redirect:/solicitantes";
    }

    // --- GESTÃO DE TÉCNICO (CRIAR E EDITAR) ---
    @GetMapping("/tecnicos/novo")
    public ModelAndView exibirFormularioTecnico() {
        ModelAndView mav = new ModelAndView("tecnico/form");
        mav.addObject("tecnico", new Tecnico());
        return mav;
    }

    @GetMapping("/tecnicos/{id}/editar")
    public ModelAndView exibirFormularioEdicaoTecnico(@PathVariable Long id) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Técnico não encontrado!"));
        ModelAndView mav = new ModelAndView("tecnico/form");
        mav.addObject("tecnico", tecnico);
        return mav;
    }

    @PostMapping("/tecnicos/salvar")
    public String salvarTecnico(@ModelAttribute Tecnico tecnico) {
        tecnicoRepository.save(tecnico);
        return "redirect:/sucesso";
    }

    @PostMapping("/tecnicos/{id}/excluir")
    public String excluirTecnico(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tecnicoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro: Não é possível excluir o técnico, pois ele está associado a um ou mais chamados.");
            return "redirect:/tecnicos";
        }
        return "redirect:/tecnicos";
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
    
    @GetMapping("/os/{id}")
    public ModelAndView detalharOS(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("os/detalhes");
        OrdemDeServico os = ordemDeServicoRepository.findById(id).orElseThrow(() -> new RuntimeException("OS não encontrada!"));
        mav.addObject("os", os);
        mav.addObject("tecnicos", tecnicoRepository.findAll());
        mav.addObject("prioridades", Prioridade.values());
        mav.addObject("statusOptions", Status.values());
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
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId).orElse(null);
        os.setTecnicoResponsavel(tecnico);
        ordemDeServicoRepository.save(os);
        return "redirect:/os/" + id;
    }

    @PostMapping("/os/{id}/alterar-status")
    public String alterarStatusOS(@PathVariable Long id, @RequestParam Status novoStatus) {
        OrdemDeServico os = ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OS não encontrada!"));
        os.setStatus(novoStatus);

        if (novoStatus == Status.RESOLVIDA || novoStatus == Status.FECHADA) {
            if (os.getDataDeFechamento() == null) {
                os.setDataDeFechamento(LocalDateTime.now());
            }
        } else {
            os.setDataDeFechamento(null);
        }

        ordemDeServicoRepository.save(os);
        return "redirect:/os/" + id;
    }

    @PostMapping("/os/{id}/excluir")
    public String excluirOS(@PathVariable Long id) {
        ordemDeServicoRepository.deleteById(id);
        return "redirect:/";
    }

    // --- PÁGINA DE SUCESSO GENÉRICA ---
    @GetMapping("/sucesso")
    public String exibirPaginaSucesso() {
        return "common/sucesso";
    }
}