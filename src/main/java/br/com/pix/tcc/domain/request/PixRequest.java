package br.com.pix.tcc.domain.request;

import jakarta.persistence.Entity;
import lombok.Data;

import javax.validation.constraints.Size;

@Entity
@Data
public class PixRequest {

    @Size(min = 12, max = 15)
    private String cpf_remetente;

    @Size(min = 3, max = 50)
    private String  chave_pix_destinatario;

    private String observacao;

    private Float valor_transferencia;

    private String localizacao_ransferencia;


    private String senha;





}
