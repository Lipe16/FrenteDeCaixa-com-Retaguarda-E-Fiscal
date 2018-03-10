/*

Descrição: Efetua o Download da NFe / NFce

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

//Função para Consultar as Notas Emitidas para o CNPJ, já trazendo o xml das Notas, Evitando assim o Download.
public class DonwloadNFe {
/*
    try{
        //Inicia As Configurações
        ConfiguracoesIniciaisNfe config = iniciaConfigurações();

        DistDFeInt distDFeInt = new DistDFeInt();
        distDFeInt.setVersao("1.00");
        distDFeInt.setTpAmb(config.getAmbiente());
        distDFeInt.setCUFAutor(config.getEstado().getCodigoIbge());
        //Substitua os X pelo CNPJ
        distDFeInt.setCNPJ("XXXXXXXXXXXXXXXX");

        DistNSU distNSU = new DistNSU();
        // Ultilize NSU 0 para pegar todas as Notas, depois troque pelo Ultimo NSU
        distNSU.setUltNSU("000000000000000");
        distDFeInt.setDistNSU(distNSU);

        //Informe false para não fazer a validação do XML - Ganho De tempo.
        RetDistDFeInt retorno = Nfe.distribuicaoDfe(distDFeInt,false);
        System.out.println("Status:" + retorno.getCStat());
        System.out.println("Motivo:" + retorno.getXMotivo());
        System.out.println("NSU:" + retorno.getUltNSU());
        System.out.println("Max NSU:" + retorno.getMaxNSU());
        List<DocZip> listaDoc =  retorno.getLoteDistDFeInt().getDocZip();

        System.out.println("Encontrado "+listaDoc.size() + " Notas.");
        for (DocZip docZip : listaDoc) {
            System.out.println("Schema: "+docZip.getSchema());
            System.out.println("NSU:" + docZip.getNSU());
            System.out.println("XML: "+XmlUtil.gZipToXml(docZip.getValue()));
        }

    } catch (NfeException | IOException e) {
        System.out.println("Erro:" + e.getMessage());
    }
*/
}