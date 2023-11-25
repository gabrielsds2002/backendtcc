package br.com.pix.tcc.service;

import br.com.pix.tcc.domain.Response.ConsultaDestinatarioResponse;
import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.data.ConsultaPrePixData;
import br.com.pix.tcc.domain.request.ConsultaDestinatario;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
import br.com.pix.tcc.domain.request.RastreavelRequest;

public interface Consultaservice {

    ConsultaPrePixResponse consulta(ConsultaPrePixRequest request) ;

    ConsultaDestinatarioResponse consultaRemetente(ConsultaDestinatario request) ;

}
