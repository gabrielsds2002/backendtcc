package br.com.pix.tcc.domain.request;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class RastreavelRequest {

    String cpf;
    String token;
}
