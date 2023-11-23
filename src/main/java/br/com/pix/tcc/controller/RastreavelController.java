package br.com.pix.tcc.controller;

import br.com.pix.tcc.dao.RastreavelDAO;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.Response.RastreavelConsultaResponse;
import br.com.pix.tcc.domain.Response.RastreavelResponse;
import br.com.pix.tcc.domain.request.RastreavelAllRequest;
import br.com.pix.tcc.domain.request.RastreavelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RastreavelController {


    @GetMapping("/consulta")
    public List<RastreavelConsultaResponse> consulta(){
        RastreavelDAO rastreavelDAO = new RastreavelDAO();
        return rastreavelDAO.consultaTodosRastreavel();
    }

    @GetMapping("/consulta_cpf")
    public List<RastreavelConsultaResponse> consultaporCPF(RastreavelRequest request){
        RastreavelDAO rastreavelDAO = new RastreavelDAO();
        return rastreavelDAO.consultaTodosRastreavelDeUmCpf(request.getCpf());
    }

    @GetMapping("/detalhes")
    public List<RastreavelResponse> consultaDetalhada(RastreavelAllRequest request){
        RastreavelDAO rastreavelDAO = new RastreavelDAO();
        return rastreavelDAO.consultaRastreavel(request.getCpf(),request.getIdTransferencia());
    }

    @GetMapping("/deletarCpfID")
    public String deletarCpfDaTabelaRastreavel(RastreavelAllRequest request){
        RastreavelDAO rastreavelDAO = new RastreavelDAO();
        return rastreavelDAO.DeleteSituaçãoRastreavel(request.getCpf(),request.getIdTransferencia());
    }

    @GetMapping("/deletarCpf")
    public String deletarCpf(RastreavelAllRequest request){
        RastreavelDAO rastreavelDAO = new RastreavelDAO();
        return rastreavelDAO.DeleteCpfRastreavel(request.getCpf());
    }
}
