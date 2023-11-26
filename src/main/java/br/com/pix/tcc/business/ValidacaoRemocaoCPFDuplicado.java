package br.com.pix.tcc.business;

import br.com.pix.tcc.domain.CpfJaEnviado;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ValidacaoRemocaoCPFDuplicado {
    public static List<CpfJaEnviado> removerCPFDuplicado(List<CpfJaEnviado> cpfsEnviados) {
        Set<String> cpfSet = new HashSet<>();
        List<CpfJaEnviado> cpfsSemDuplicatas = new ArrayList<>();
        for (CpfJaEnviado cpfEnviado : cpfsEnviados) {
            if (cpfSet.add(cpfEnviado.getCpf_cnpj())) {
                cpfsSemDuplicatas.add(cpfEnviado);
            }
        }
        return cpfsSemDuplicatas;
    }

}
