package br.com.pix.tcc.domain.data;

import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultaPrePixData {

    private List<ConsultaPrePixResponse> data;
}
