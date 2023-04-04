package com.petshopapi.api.controller;

import com.petshopapi.api.assembler.AtendimentoInputDisassembler;
import com.petshopapi.api.assembler.AtendimentoModelAssembler;
import com.petshopapi.api.controller.openapi.AtendimentoControllerOpenApi;
import com.petshopapi.api.model.AtendimentoModel;
import com.petshopapi.api.model.input.AtendimentoInput;
import com.petshopapi.domain.model.Atendimento;
import com.petshopapi.domain.service.AtendimetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("atendimento")
public class AtendimentoController implements AtendimentoControllerOpenApi {
    @Autowired
    AtendimetoService atendimetoService;

    @Autowired
    AtendimentoModelAssembler atendimentoModelAssembler;

    @Autowired
    AtendimentoInputDisassembler atendimentoInputDisassembler;

    @GetMapping
    public Page<AtendimentoModel> buscarTodosAtendimentosParaCliente(Long idCliente, @PageableDefault(size = 10) Pageable pageable) {
        Page<Atendimento> atendimentos = atendimetoService.buscarTodosParaCliente(idCliente, pageable);

        return retornarAtendimentosModelPaginados(atendimentos, pageable);
    }

    private Page<AtendimentoModel> retornarAtendimentosModelPaginados(Page<Atendimento> atendimentos, Pageable pageable) {
        Page<AtendimentoModel> atendimentoModels = new PageImpl<>(
                atendimentoModelAssembler.toCollectionModel(atendimentos.getContent()),
                                                          pageable,
                                                          atendimentos.getTotalElements());

        return atendimentoModels;
    }

    @GetMapping("/{idAtendimento}")
    public AtendimentoModel buscarAtendimentoPorId(@PathVariable Long idAtendimento) {
        Atendimento atendimento = atendimetoService.buscarPorId(idAtendimento);

       return atendimentoModelAssembler.toModel(atendimento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AtendimentoModel salvarAtendimento(@Valid @RequestBody AtendimentoInput atendimentoInput) {
        Atendimento atendimento = atendimentoInputDisassembler.toDomainObject(atendimentoInput);

        atendimento = atendimetoService.salvarAtentimento(atendimento);

        return atendimentoModelAssembler.toModel(atendimento);
    }

    @PutMapping("/{idAtendimento}")
    public AtendimentoModel alterarAtendimento(@Valid @RequestBody AtendimentoInput atendimentoInput, @PathVariable Long idAtendimento) {
        Atendimento atendimento = atendimetoService.buscarPorId(idAtendimento);

        atendimentoInputDisassembler.copyToDomainObject(atendimentoInput, atendimento);
        atendimento.setIdAtendimento(idAtendimento);

        atendimento = atendimetoService.salvarAtentimento(atendimento);

        return atendimentoModelAssembler.toModel(atendimento);
    }

    @DeleteMapping("/{idAtendimento}")
    public void deletarAtendimento(@PathVariable Long idAtendimento) {
        atendimetoService.deletarAtendimento(idAtendimento);
    }
}
