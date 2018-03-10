/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;
/*
 *   classe: XMLReaderUmaTag.java
 *
Exemplos de utilização da classe XMLReaderUmaTag:
Obs: No inicio da classe, tem a explicação para cada parametro a ser passado.


     XMLReaderUmaTag lerxml;

//  Para capturar o conteúdo da TAG: mod que é filha da TAG ide, faça:

     lerxml = new XMLReaderUmaTag();
     String wmodnfe = lerxml.trataXML(arq_xml, "ide", "T", "mod", 1);

//  Para capturar o conteúdo do atributo: Id que é filha da TAG infNFe, faça:
     lerxml = new XMLReaderUmaTag();
     String Id = lerxml.trataXML(arq_xml, "infNFe", "A", "Id", 1);

//  Para capturar o conteúdo da TAG: vTotTrib do Item 3 que é filha da TAG imposto, faça:
     lerxml = new XMLReaderUmaTag();
     String vTotTrib = lerxml.trataXML(arq_xml, "imposto", "T", "vTotTrib", 3);

//  Se quiser ler o valor de vTotTrib de todos os itens do arquivo, faça algo como:
    private void testePegarItensDoXml() {
        XMLReaderUmaTag lerxml;
        String arq_xml = "C:\\NFCe\\XML Automaq\\41161025258952000130650050000000161000000166-nfe.xml";  //  "C:\\NFCe\\XML Automaq\\41161025258952000130650050000000101000000103-nfe.xml";
        int item = 0;
        String resultado = "";
         while (true) {
            item++;
            //  busca o item para saber se existe
            lerxml = new XMLReaderUmaTag();
            String nItem = lerxml.trataXML(arq_xml, "det", "A", "nItem", item);
//JOptionPane.showMessageDialog(null, "Leu o item ["+item+"] e retornou: "+nItem);
            if (nItem != null) { // se o item/produto existe, capture o valor de vTotTrib
               lerxml = new XMLReaderUmaTag();
               String vTotTrib = lerxml.trataXML(arq_xml, "imposto", "T", "vTotTrib", item);
//JOptionPane.showMessageDialog(null, "Valor de vTotTrib do item ["+item+"] . . . : "+vTotTrib);
               resultado += "   Valor de vTotTrib do item ["+item+"] . . . : "+vTotTrib+"\n";
            } else {  // fim dos produtos do arquivo
               break;
            }
         }
        JOptionPane.showMessageDialog(null, "Resultado da Busca: \n"+resultado);
    }
 */
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLReaderUmaTag {
    private String tagPai, tipoDeElemento, elementoAlvo, conteudo;
    boolean jaAchouTagPai = false, display = false;
    int ocorreu = 0, ocorrencia = 0;

    public String trataXML(
            String arquivoXML, //  caminho e nome do arquivo XML a ser lido
            String tagPai, //  TAG onde procurar a informacao
            String tipoDeElemento,   //  tipo de elemento para procurar (T = TAG, A = Atributo)
            String elementoAlvo,   //  nome da TAG?Atributo a ser procurada
            int ocorrencia  //  numero de ocorrencias do elemento (mais utilizado qdo o elemento ocorre mais de uma vez. Ex.: elementos de um produto)
            ){
        String retorno = null;
        this.tipoDeElemento = tipoDeElemento;
        this.tagPai = tagPai;
        this.elementoAlvo = elementoAlvo;
        this.ocorrencia = ocorrencia;
        try{
            File file = new File(arquivoXML);
            SAXBuilder builder = new SAXBuilder();
            Document document;
if (display)             JOptionPane.showMessageDialog(null, "XMLReaderUmaTag - entrou . . .");
            document = builder.build(file);
            Element el = document.getRootElement();

           //System.out.println("Elemento Raiz: "+document.getRootElement().getName());
            trataElemento(el);
            retorno = getConteudo();
        } catch (JDOMException ex) {
            JOptionPane.showMessageDialog(null, "XMLReaderUmaTag - "
                    + "\n Tipo: " + this.tipoDeElemento
                    + " - Tag Pai: " + this.tagPai
                    + " - Elemento: " + this.elementoAlvo
                    + "\nErro: " + ex);
        } catch (IOException ex) {
            if (this.tagPai.equals("infProt")) {
                JOptionPane.showMessageDialog(null, "Não encontrou o Protocolo de Autorização para este Documento!");
            } else {
//                JOptionPane.showMessageDialog(null, "XMLReaderUmaTag - Arquivo não encontrado: "+arquivoXML);
            }
        }
        return retorno;
    }
    private void trataElemento(Element el){
        List list = el.getChildren();
        int tamanho = list.size();
//if (display) JOptionPane.showMessageDialog(null,"Elemento lido: "+el.getName()+" - tipo: "+el.getContent());
//if (display) JOptionPane.showMessageDialog(null,"Tamanho da lista: "+tamanho);
        if (tamanho > 0){  //Elemento
            Iterator it = list.iterator();
            for (int i=0;i<tamanho;i++){
                Element el1 = (Element)it.next();
                String campo = el1.getQualifiedName().toString().trim();
                if(el1.getQualifiedName().toString().trim().equals(tagPai)) {
                   ocorreu++;

if (display) JOptionPane.showMessageDialog(null,"Encontrou tagPai:"+el1.getQualifiedName().toString().trim());
                    jaAchouTagPai = true;
                    if (tipoDeElemento.equals("A")) { //  A - attribute
                        if (ocorreu == ocorrencia) {
if (display) JOptionPane.showMessageDialog(null,"Encontrou o Atributo informado para a ocorrencia ("+ocorreu+"/"+ocorrencia+"):"+el1.getAttributeValue(elementoAlvo));
                            setConteudo(el1.getAttributeValue(elementoAlvo));
                            break;
                        }
                    } else {
                        trataElemento(el1);
                    }
                } else if (jaAchouTagPai) {  //
//JOptionPane.showMessageDialog(null,"Lendo elemento da TAG aberta:"+el1.getQualifiedName().toString().trim()+" - Valor: "+el1.getText().toString());
                    if (campo.equalsIgnoreCase(elementoAlvo)) {  //  tipo = T - TAG
if (display) JOptionPane.showMessageDialog(null,"Encontrou elementoAlvo informado para a ocorrencia ("+ocorreu+"/"+ocorrencia+"):"+el1.getQualifiedName().toString().trim()+" - Valor: "+el1.getText().toString());
                       if (ocorreu == ocorrencia) {
                            setConteudo(el1.getText().toString());  //(el1.getChildText(elementoAlvo));
                            ocorreu++;
                            break;
                        } else {
                            jaAchouTagPai = false;
                        }
                    } else if (ocorreu > ocorrencia) {
                        break;
                    } else {
                        trataElemento(el1);
                    }
                } else {
                    if (el1.getText().toString().equalsIgnoreCase("")) {
//JOptionPane.showMessageDialog(null,"Elemento Vazio:"+el1.getQualifiedName().toString().trim());  //+" - Valor: "+el1.getText().toString());
                    trataElemento(el1);
                    }
                }
            }
        } else {
        }
    }

    public static void main(String[] args){
        XMLReaderUmaTag xr = new XMLReaderUmaTag();
        xr.trataXML("c:\\arquivo.xml", null, null, null, 1);
    }

    /**
     * @return the conteudo
     */
    public String getConteudo() {
        return conteudo;
    }

    /**
     * @param conteudo the conteudo to set
     */
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

}
