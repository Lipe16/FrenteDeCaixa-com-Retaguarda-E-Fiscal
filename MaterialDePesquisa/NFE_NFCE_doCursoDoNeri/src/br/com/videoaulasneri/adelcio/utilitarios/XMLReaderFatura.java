/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

import java.io.File;
import java.io.FileWriter;
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
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLReaderFatura {

    private String y_localsgbd = "", y_driver = "", y_url = "", y_usuarioBD = "", y_senhaBD = "", y_drive = "", 
            y_nomebd, y_fusoHorario = "", y_contatoCupom = "Porto Informatica Ltda - portoinfo@sercomtel.com.br",
            y_uriCupom = "http://www.sped.fazenda.pr.gov.br/modules/conteudo/conteudo.php?conteudo=100",
            y_interval = "5", y_informarCnpjCartao = "N", y_cupomNaTela = "N";

    public void trataXML(String arquivoXML){
//        JOptionPane.showMessageDialog(null,"arq.conexao: "+arquivoXML);
        //String fsep = System.getProperty("file.separator");
        //File file = new File(System.getProperty("user.dir")+fsep+"conexao.xml");
        File file = new File(arquivoXML);
        SAXBuilder builder = new SAXBuilder();
        Document document;

        try{
            //System.out.println("Aquivo: "+fileName);
            document = builder.build(file);
            Element el = document.getRootElement();

           //System.out.println("Elemento Raiz: "+document.getRootElement().getName());
/*
            if (!document.getRootElement().getName().equalsIgnoreCase("Conexao")){
                JOptionPane.showMessageDialog(null, "Este Documento nao contem informacoes sobre a Conexao!");
                System.exit(0);
            }
 *
 */
            trataElemento(el);
            //gravaSaidaTeste(document);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo conexao.xml. \nErro: "+e);
        }

    }
    private void gravaSaidaTeste(Document doc) {
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            xmlOutput.output(doc, new FileWriter("c:\\fileSai.xml"));
        } catch (IOException ex) {
            Logger.getLogger(XMLReaderFatura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void trataElemento(Element el){
        List list = el.getChildren();
        int tamanho = list.size();
        //System.out.println("Elemento lido: "+el.getName()+" - tipo: "+el.getContent());
        //System.out.println("Tamanho da lista: "+tamanho);
        if (tamanho > 0){  //Elemento
            Iterator it = list.iterator();
            for (int i=0;i<tamanho;i++){
                Element el1 = (Element)it.next();
                String campo = el1.getQualifiedName().toString().trim();
                //System.out.println("Elemento:"+el1.getQualifiedName().toString().trim()+" - Valor: "+el1.getText().toString());
                if (campo.equalsIgnoreCase(("localsgbd"))){
                    setY_localsgbd(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("driver"))){
                    setY_driver(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("url"))){
                    setY_url(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("usuariobd"))){
                    setY_usuarioBD(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("senhabd"))){
                    setY_senhaBD(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("drive"))){
                    setY_drive(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("nomebd"))){
                    setY_nomebd(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("fusoHorario"))){
                    setY_fusoHorario(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("contatoCupom"))){
                    setY_contatoCupom(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("informarCnpjCartao"))){
                    setY_informarCnpjCartao(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("uriCupom"))){
                    setY_uriCupom(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("intervaloCaptura"))){
                    setY_interval(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("imprimirCupomNaTela"))){
                    setY_cupomNaTela(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("NFe")) || campo.equalsIgnoreCase(("infNFe")) || campo.equalsIgnoreCase(("ide"))){
                    trataElemento(el1);
                }
            }
        } else {
        }
        //JOptionPane.showMessageDialog(null,"MÃ©todo XMLReader().trataElemento()\nLocalSGBD: "+getY_localsgbd()+"\nDriver: "+getY_driver()+"\nURL: "+getY_url()+"\nUsuarioBD: "+getY_usuarioBD()+"\nSenhaBD: "+getY_senhaBD()+"\nDrive: "+getY_drive());
    }

    public static void main(String[] args){
        XMLReaderFatura xr = new XMLReaderFatura();
        xr.trataXML("c:\\NFe\\conexaoNFefacil.xml");
    }

    /**
     * @return the y_interval
     */
    public String getY_interval() {
        return y_interval;
    }

    /**
     * @param y_interval the y_interval to set
     */
    public void setY_interval(String y_interval) {
        this.y_interval = y_interval;
    }

    /**
     * @return the y_localsgbd
     */
    public String getY_localsgbd() {
        return y_localsgbd;
    }

    /**
     * @param y_localsgbd the y_localsgbd to set
     */
    public void setY_localsgbd(String y_localsgbd) {
        this.y_localsgbd = y_localsgbd;
    }

    /**
     * @return the y_driver
     */
    public String getY_driver() {
        return y_driver;
    }

    /**
     * @param y_driver the y_driver to set
     */
    public void setY_driver(String y_driver) {
        this.y_driver = y_driver;
    }

    /**
     * @return the y_url
     */
    public String getY_url() {
        return y_url;
    }

    /**
     * @param y_url the y_url to set
     */
    public void setY_url(String y_url) {
        this.y_url = y_url;
    }

    /**
     * @return the y_usuarioBD
     */
    public String getY_usuarioBD() {
        return y_usuarioBD;
    }

    /**
     * @param y_usuarioBD the y_usuarioBD to set
     */
    public void setY_usuarioBD(String y_usuarioBD) {
        this.y_usuarioBD = y_usuarioBD;
    }

    /**
     * @return the y_senhaBD
     */
    public String getY_senhaBD() {
        return y_senhaBD;
    }

    /**
     * @param y_senhaBD the y_senhaBD to set
     */
    public void setY_senhaBD(String y_senhaBD) {
        this.y_senhaBD = y_senhaBD;
    }

    /**
     * @return the y_drive
     */
    public String getY_drive() {
        return y_drive;
    }

    /**
     * @param y_drive the y_drive to set
     */
    public void setY_drive(String y_drive) {
        this.y_drive = y_drive;
    }

    /**
     * @return the y_nomebd
     */
    public String getY_nomebd() {
        return y_nomebd;
    }

    /**
     * @param y_nomebd the y_nomebd to set
     */
    public void setY_nomebd(String y_nomebd) {
        this.y_nomebd = y_nomebd;
    }

    /**
     * @return the y_fusoHorario
     */
    public String getY_fusoHorario() {
        return y_fusoHorario;
    }

    /**
     * @param y_fusoHorario the y_fusoHorario to set
     */
    public void setY_fusoHorario(String y_fusoHorario) {
        this.y_fusoHorario = y_fusoHorario;
    }

    /**
     * @return the y_contatoCupom
     */
    public String getY_contatoCupom() {
        return y_contatoCupom;
    }

    /**
     * @param y_contatoCupom the y_contatoCupom to set
     */
    public void setY_contatoCupom(String y_contatoCupom) {
        this.y_contatoCupom = y_contatoCupom;
    }

    /**
     * @return the y_uriCupom
     */
    public String getY_uriCupom() {
        return y_uriCupom;
    }

    /**
     * @param y_uriCupom the y_uriCupom to set
     */
    public void setY_uriCupom(String y_uriCupom) {
        this.y_uriCupom = y_uriCupom;
    }

    /**
     * @return the y_informarCnpjCartao
     */
    public String getY_informarCnpjCartao() {
        return y_informarCnpjCartao;
    }

    /**
     * @param y_informarCnpjCartao the y_informarCnpjCartao to set
     */
    public void setY_informarCnpjCartao(String y_informarCnpjCartao) {
        this.y_informarCnpjCartao = y_informarCnpjCartao;
    }

    /**
     * @return the y_cupomNaTela
     */
    public String getY_cupomNaTela() {
        return y_cupomNaTela;
    }

    /**
     * @param y_cupomNaTela the y_cupomNaTela to set
     */
    public void setY_cupomNaTela(String y_cupomNaTela) {
        this.y_cupomNaTela = y_cupomNaTela;
    }
}
