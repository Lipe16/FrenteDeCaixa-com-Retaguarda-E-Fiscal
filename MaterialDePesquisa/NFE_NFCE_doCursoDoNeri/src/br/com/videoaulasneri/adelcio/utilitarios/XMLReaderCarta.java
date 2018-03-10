/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

import br.com.videoaulasneri.adelcio.nfefacil.bean.CartaCorr;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLReaderCarta {
    CartaCorr carta;
    private boolean finalizar = false;
    public CartaCorr trataXML(String arquivoXML, String caminho_corrige){
        try{
            String fsep = System.getProperty("file.separator");
            File file = new File(arquivoXML);
            SAXBuilder builder = new SAXBuilder();
            carta = new CartaCorr();
            Document document;
            System.out.println("Aquivo: "+arquivoXML);
            document = builder.build(file);
            Element el = document.getRootElement();
            trataElemento(el);
        } catch(JDOMException | IOException e){
            JOptionPane.showMessageDialog(null, "Erro(origem) ao abrir o arquivo: "+arquivoXML+". \nErro: "+e);
        }
        return carta;
    }
    private void trataElemento(Element el){
        try {
            List list = el.getChildren();
            int tamanho = list.size();
            System.out.println("Elemento lido: "+el.getName()+" - tipo: "+el.getContent());
            System.out.println("Tamanho da lista: "+tamanho);
            if (tamanho > 0){  //Elemento
                Iterator it = list.iterator();
                for (int i=0;i<tamanho;i++){
                    Element el1 = (Element)it.next();
                    String campo = el1.getQualifiedName().toString().trim();
//JOptionPane.showMessageDialog(null, "Elemento:"+el1.getQualifiedName().toString().trim()+" - Valor: "+el1.getText().toString());
                    if (!finalizar) {  
                        if (campo.equalsIgnoreCase(("tpAmb"))){
                            carta.setTpAmb(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("verAplic"))){
                            carta.setVerAplic(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("cOrgao"))){
                            carta.setcOrgao(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("cStat"))){
                            carta.setcStat(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("xMotivo"))){
                            carta.setxMotivo(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("chNFe"))){
                            carta.setChNFe(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("tpEvento"))){
                            carta.setTpEvento(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("xEvento"))){
                            carta.setxEvento(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("nSeqEvento"))){
                            carta.setnSeqEvento(el1.getText().toString());
                            //JOptionPane.showMessageDialog(null, "verAplic: "+carta.getVerAplic());
                        } else
                        if (campo.equalsIgnoreCase(("dhRegEvento"))){
                            carta.setDhRegEvento(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("nProt"))){
                            carta.setnProt(el1.getText().toString());
                            finalizar = true;
                        } else
                        if (campo.equalsIgnoreCase(("retEvento"))){
                            trataElemento(el1);
                        } else
                        if (campo.equalsIgnoreCase(("infEvento"))){
                            trataElemento(el1);
                        } else
                        if (campo.equalsIgnoreCase(("evento"))){
                            trataElemento(el1);
                        } else
                        if (campo.equalsIgnoreCase(("xCorrecao"))){
                            carta.setxCorrecao(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("xCondUso"))){
                            carta.setxCondUso(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("detEvento"))){
                            trataElemento(el1);
                        }
                    } else {
                    }
                }
            } else {
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"XMLReaderCarta.XMLReader() - Erro: "+e);
        }
    }

    public static void main(String[] args){
        XMLReaderCarta xr = new XMLReaderCarta();
        xr.trataXML("", "");
    }

}
