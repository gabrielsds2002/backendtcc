package br.com.pix.tcc.domain.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultaResponse {

        float Saldo;
        String mes;
        String ano;


}
