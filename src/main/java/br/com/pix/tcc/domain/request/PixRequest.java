package br.com.pix.tcc.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class PixRequest {

    @Size(min = 12, max = 15)
    private String cpfRemetente;

    @Size(min = 3, max = 50)
    private String  nomeRemetente;

    @Size(min = 3, max = 15)
    private String chavepixRemetente;

    private String bancoRemetente;
    private String bancoDestinatario;

    @Size(min = 3, max = 50)
    private String cpfDestinatario;

    @Size(min = 3, max = 50)
    private String  nomeDestinatario;

    private String observacao;
    @Size(min = 3, max = 50)
    private String  chavePixDestinatario;
    private Float valortransferencia;

    private String localizacaoTransferencia;

    private String tipoTransferencia;

    private String numeroContaPagador;
    private String numeroContaRemetente;
    private Boolean rastreavel;

    private String senha;


    private float limite_diario;

    private LocalTime limite_noturno;

}
