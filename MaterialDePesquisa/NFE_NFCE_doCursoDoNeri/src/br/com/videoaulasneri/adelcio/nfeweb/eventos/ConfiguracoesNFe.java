/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

//Configurações NFe

import br.com.videoaulasneri.adelcio.nfeweb.Certificado;
import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;


//Para executar qualquer função NFe, deve-se inicializar as Configuração.

//Certificado Digital do Repositorio Do Windows A1 e A3:
public class ConfiguracoesNFe {

/*
            public static ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
    // Certificado Windows
    Certificado certificado = CertificadoUtil.listaCertificadosWindows().get(0);

    return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(Estados.GO , ConstantesUtil.AMBIENTE.HOMOLOGACAO,
            certificado, "C:\\SRA\\Nfe\\Schemas", ConstantesUtil.VERSAO.V3_10);
}

*/
//Certificado Digital por Arquivo .pfx

public static ConfiguracoesIniciaisNfe iniciaConfigurações(Estados estado, String caminho_cert, String senha_cert) throws NfeException {
    // Certificado Arquivo, Parametros: -Caminho Certificado, - Senha
    Certificado certificado = CertificadoUtil.certificadoPfx(caminho_cert, senha_cert);

    return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, "2",
            certificado, "C:\\Nfe\\Schemas", "3.10");
}

}
//Para Iniciar as Configurações utilize os Parametros:

//Código da UF
//Ambiente (1 - Produção, 2 - Homologação)
//Certificado
//Caminho da pasta que se encontra os Schemas Xsd
//Versão da NFE.