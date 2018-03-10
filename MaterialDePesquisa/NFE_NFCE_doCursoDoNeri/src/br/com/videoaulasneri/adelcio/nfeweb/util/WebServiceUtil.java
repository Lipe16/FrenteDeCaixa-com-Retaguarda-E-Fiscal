/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.util;

import java.io.IOException;
import java.io.InputStream;

import org.ini4j.Wini;

import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 */
public class WebServiceUtil {
    static Estados estado_codibge;

	public static String getUrl(String tipo, String servico) throws NfeException {

		try {
			ConfiguracoesIniciaisNfe config = ConfiguracoesIniciaisNfe.getInstance();
			String secao = tipo + "_" + config.getEstado() + "_"
					+ (config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "H" : "P");
			InputStream is = WebServiceUtil.class. getResourceAsStream("/WebServices.ini");
			Wini ini = new Wini(is);
			String url = ini.get(secao, "Usar");
			if (servico.equals(ConstantesUtil.SERVICOS.DOWNLOAD)
					|| servico.equals(ConstantesUtil.SERVICOS.DISTRIBUICAO_DFE)
					|| servico.equals(ConstantesUtil.SERVICOS.CONSULTA_DEST)
					|| servico.equals(ConstantesUtil.SERVICOS.MANIFESTACAO)) {
				secao = config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "NFe_AN_H" : "NFe_AN_P";

				if (servico.equals(ConstantesUtil.SERVICOS.MANIFESTACAO)) {
					servico = ConstantesUtil.SERVICOS.EVENTO;
				}

			} else if (!servico.equals(ConstantesUtil.SERVICOS.URL_CONSULTANFCE)
					&& !servico.equals(ConstantesUtil.SERVICOS.URL_QRCODE) && ObjetoUtil.differentNull(url)) {
				secao = url;
			} else if(config.isContigenciaSCAN()){
				//SVC-RS
				if(config.getEstado().equals(Estados.GO)
						|| config.getEstado().equals(Estados.AM)
						|| config.getEstado().equals(Estados.BA)
						|| config.getEstado().equals(Estados.CE)
						|| config.getEstado().equals(Estados.MA)
						|| config.getEstado().equals(Estados.MS)
						|| config.getEstado().equals(Estados.MT)
						|| config.getEstado().equals(Estados.PA)
						|| config.getEstado().equals(Estados.PE)
						|| config.getEstado().equals(Estados.PI)
						|| config.getEstado().equals(Estados.PR)){
					secao = tipo + "_SVRS_" + (config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "H" : "P");
				//SVC-AN	
				}else{
					secao = tipo + "_SVAN_" + (config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "H" : "P");
				}
			}
			url = ini.get(secao, servico);

			return url;

		} catch (IOException e) {
                    //JOptionPane.showMessageDialog(null, "WebServiceUtil - erro: "+e);
			throw new NfeException(e.getMessage());
            }

	}
	public static String getUrlConsulta(String tipo, String servico, String estado) throws NfeException {

		try {
			ConfiguracoesIniciaisNfe config = ConfiguracoesIniciaisNfe.getInstance();
                        estado_codibge = Estados.valueOf(estado);  // config.getEstado();
//JOptionPane.showMessageDialog(null, "WebServiceUtil - estado: "+estado + " - estado_codibge: " + estado_codibge);
			String secao = tipo + "_" + estado_codibge + "_"
					+ (config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "H" : "P");
			InputStream is = WebServiceUtil.class. getResourceAsStream("/WebServices.ini");
			Wini ini = new Wini(is);
			String url = ini.get(secao, "Usar");
			if (servico.equals(ConstantesUtil.SERVICOS.DOWNLOAD)
					|| servico.equals(ConstantesUtil.SERVICOS.DISTRIBUICAO_DFE)
					|| servico.equals(ConstantesUtil.SERVICOS.CONSULTA_DEST)
					|| servico.equals(ConstantesUtil.SERVICOS.MANIFESTACAO)) {
				secao = config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "NFe_AN_H" : "NFe_AN_P";

				if (servico.equals(ConstantesUtil.SERVICOS.MANIFESTACAO)) {
					servico = ConstantesUtil.SERVICOS.EVENTO;
				}

			} else if (!servico.equals(ConstantesUtil.SERVICOS.URL_CONSULTANFCE)
					&& !servico.equals(ConstantesUtil.SERVICOS.URL_QRCODE) && ObjetoUtil.differentNull(url)) {
				secao = url;
			} else if(config.isContigenciaSCAN()){
				//SVC-RS
				if(estado_codibge.equals(Estados.GO)
						|| estado_codibge.equals(Estados.AM)
						|| estado_codibge.equals(Estados.BA)
						|| estado_codibge.equals(Estados.DF)
						|| estado_codibge.equals(Estados.CE)
						|| estado_codibge.equals(Estados.MA)
						|| estado_codibge.equals(Estados.MS)
						|| estado_codibge.equals(Estados.MT)
						|| estado_codibge.equals(Estados.PA)
						|| estado_codibge.equals(Estados.PE)
						|| estado_codibge.equals(Estados.PI)
						|| estado_codibge.equals(Estados.PR)){
					secao = tipo + "_SVRS_" + (config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "H" : "P");
				//SVC-AN	
				}else{
					secao = tipo + "_SVAN_" + (config.getAmbiente().equals(ConstantesUtil.AMBIENTE.HOMOLOGACAO) ? "H" : "P");
				}
			}
			url = ini.get(secao, servico);
			return url;

		} catch (IOException e) {
                    //JOptionPane.showMessageDialog(null, "WebServiceUtil - erro: "+e);
			throw new NfeException(e.getMessage());
            }

	}

}
