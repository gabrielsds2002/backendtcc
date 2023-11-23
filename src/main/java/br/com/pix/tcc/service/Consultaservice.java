package br.com.pix.tcc.service;

import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.data.ConsultaPrePixData;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;

public interface Consultaservice {

    ConsultaPrePixResponse consulta(ConsultaPrePixRequest request) ;

}
