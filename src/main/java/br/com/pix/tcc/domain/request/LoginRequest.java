package br.com.pix.tcc.domain.request;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotBlank(message = "Digite um nome valido")
    @Size(min = 12, max = 15)
    String cpf_cnpj;
    @NotBlank(message = "Digite uma senha valida")
    @Size(min = 6, max = 12)
    String senha;

}
