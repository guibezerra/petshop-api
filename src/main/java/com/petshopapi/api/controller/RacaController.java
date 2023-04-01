package com.petshopapi.api.controller;

import com.petshopapi.api.assembler.RacaInputDisassembler;
import com.petshopapi.api.assembler.RacaModelAssembler;
import com.petshopapi.api.model.RacaModel;
import com.petshopapi.api.model.input.RacaInput;
import com.petshopapi.domain.model.Raca;
import com.petshopapi.domain.service.RacaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/raca")
public class RacaController {
    @Autowired
    RacaService racaService;

    @Autowired
    RacaModelAssembler racaModelAssembler;

    @Autowired
    RacaInputDisassembler racaInputDisassembler;

    @GetMapping
    public Page<RacaModel> buscarTodasAsRacas(@PageableDefault(size = 10) Pageable pageable) {
        Page<Raca> racasPage = racaService.buscarTodos(pageable);

        return retornaRacasModelPaginado(racasPage, pageable);
    }

    private Page<RacaModel> retornaRacasModelPaginado(Page<Raca> racasPage, Pageable pageable) {
        Page<RacaModel> racasModelPage = new PageImpl<>(racaModelAssembler.toCollectionModel(racasPage.getContent()),
                pageable, racasPage.getTotalElements());

        return racasModelPage;
    }

    @GetMapping("/{idRaca}")
    public RacaModel buscarPorId(@PathVariable Long idRaca) {
        Raca raca = racaService.buscarPorId(idRaca);

        return racaModelAssembler.toModel(raca);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RacaModel cadastrarRaca(@RequestBody RacaInput racaInput) {
        Raca raca = racaInputDisassembler.toDomainObject(racaInput);

        raca = racaService.salvar(raca);

        return racaModelAssembler.toModel(raca);
    }

    @PutMapping("/{idRaca}")
    public RacaModel atualizarRaca(@PathVariable Long idRaca, @RequestBody RacaInput racaInput) {
        Raca raca = racaService.buscarPorId(idRaca);

        racaInputDisassembler.copyToDomainObject(racaInput, raca);
        raca.setIdRaca(idRaca);

        raca = racaService.salvar(raca);

        return racaModelAssembler.toModel(raca);
    }

    @DeleteMapping("/{idRaca}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarRaca(@PathVariable Long idRaca) {
        racaService.deletar(idRaca);
    }
}
