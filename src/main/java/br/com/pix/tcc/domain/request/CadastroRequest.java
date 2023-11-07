package br.com.pix.tcc.domain.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class CadastroRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Digite um nome valido")
    @Size(min = 12, max = 14)
    @NotNull
    private Integer cpf_cnpj;
    @NotBlank(message = "Digite um nome valido")
    @Size(min = 3, max = 15)
    @NotNull
    private String nome;

    @NotBlank(message = "Digite um Sobrenome valido")
    @Size(min = 2, max = 30)
    @NotNull
    private String sobrenome;

    @NotBlank(message = "Digite uma idade valida")
    @NotNull
    private int idade;

    @NotBlank(message = "Digite data valida")
    @NotNull
    @Pattern(regexp = "/^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/")
    private String data_nac_criacao;

    @NotBlank(message = "Digite um sexo valido")
    @NotNull
    private String sexo;

    @NotBlank(message = "Digite um CEP valido")
    @NotNull
    @Size(min = 8, max = 8)
    private String cep;

    @NotBlank(message = "Digite um CEP valido")
    @NotNull
    @Size(min = 8, max = 8)
    private String telefone;

    @NotBlank(message = "Digite um Endereço valido")
    @NotNull
    @Size(max = 100)
    private String estado_civil;

    @NotBlank(message = "Digite um Pais valido")
    @NotNull
    @Size(max = 100)
    private String nacionalidade;

    @NotBlank(message = "Digite um Endereço valido")
    @NotNull
    @Size(max = 100)
    private String lougradouro;

    @Size(max = 100000)
    @NotNull
    private Integer numero;


    @Size(max = 100)
    @NotNull
    private String complemento;


    @Size(max = 50)
    @NotNull
    private String bairro;

    @Size(max = 50)
    @NotNull
    private String cidade;

    @Size(max = 50)
    @NotNull
    private String estado;

    @Size(max = 50)
    @NotNull
    private String pais;

    //@EmailValidator(message = "Digite um email valido")
    @NotNull
    private String email;

    @NotBlank(message = "Digite uma senha valida")
    @NotNull
    @Size(min = 6, max = 12)
    private String senha_app;

    @NotBlank(message = "Digite uma senha valida")
    @NotNull
    @Size(min = 6, max = 12)
    private Integer numero_conta;

    @NotBlank(message = "Digite uma chave valida")
    @NotNull
    @Size(min = 6, max = 12)
    private String tipo_chave_pix;

    @NotBlank(message = "Digite uma chave valida")
    @NotNull
    @Size(min = 6, max = 12)
    private String chave_pix;

    @NotBlank(message = "Digite uma senha valida")
    @NotNull
    @Size(min = 6, max = 12)
    private String senha_transacoes;

    @NotBlank(message = "Digite uma senha valida")
    @NotNull
    @Size(min = 6, max = 12)
    private String senha_seguranca;

    @NotNull
    private float saldo;

    @NotNull
    private float limite_diario;

    @NotNull
    private float limite_noturno;

    @NotNull
    @NotBlank(message = "Digite uma senha valida")
    @Size(min = 6, max = 12)
    private String instituicao_financeira;

    @NotNull
    private String rastreavel = "False";

}