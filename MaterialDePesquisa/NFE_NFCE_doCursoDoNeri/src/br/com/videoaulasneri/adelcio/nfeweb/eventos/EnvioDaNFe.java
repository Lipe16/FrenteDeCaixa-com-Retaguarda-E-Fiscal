/*

Descrição: Exemplo Básico de Transmissão / Autorização da NFe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

public class EnvioDaNFe {
/*
try {

        //Inicia As Configurações
        ConfiguracoesIniciaisNfe config = iniciaConfigurações();

        TNFe nfe = new TNFe();
        InfNFe infNFe = new InfNFe();

        // Substitua X Pela Chave
        String chave = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

        infNFe.setId("NFe" + chave);
        infNFe.setVersao("3.10");

        Ide ide = new Ide();
        ide.setCUF("52");

        // Substitua X Pelo CNF
        ide.setCNF("xxxx");
        ide.setNatOp("NOTA FISCAL ELETRONICA");
        ide.setIndPag("0");
        ide.setMod("55");
        ide.setSerie("1");

        // Substitua X Pelos Valores Desejados
        ide.setNNF("X");
        ide.setDhEmi("2016-09-26T13:40:11-03:00");
        ide.setDhSaiEnt("2016-09-26T13:40:11-03:00");
        ide.setTpNF("1");
        ide.setIdDest("1");
        ide.setCMunFG("xxxxxx");
        ide.setTpImp("1");
        ide.setTpEmis("1");
                    ide.setCDV(xxxx);
            ide.setTpAmb("2");
            ide.setFinNFe("1");
            ide.setIndFinal("1");
            ide.setIndPres("1");
            ide.setProcEmi("0");
            ide.setVerProc("XXX");
        infNFe.setIde(ide);

        Emit emit = new Emit();
        emit.setCNPJ("X");
        emit.setXNome("X");
        emit.setXFant("X");

        TEnderEmi enderEmit = new TEnderEmi();
        enderEmit.setXLgr("X");
        enderEmit.setNro("X");
        enderEmit.setXCpl("X");
        enderEmit.setXBairro("X");
        enderEmit.setCMun("X");
        enderEmit.setXMun("X");
        enderEmit.setUF(TUfEmi.valueOf("GO"));
        enderEmit.setCEP("X");
        enderEmit.setCPais("x");
        enderEmit.setXPais("X");
        enderEmit.setFone("X");
        emit.setEnderEmit(enderEmit);

        emit.setIE("X");
        emit.setCRT("3");
        infNFe.setEmit(emit);

        Dest dest = new Dest();
        dest.setCNPJ("X");
        dest.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");

        TEndereco enderDest = new TEndereco();
        enderDest.setXLgr("X");
        enderDest.setNro("X");
        enderDest.setXBairro("X");
        enderDest.setCMun("X");
        enderDest.setXMun("X");
        enderDest.setUF(TUf.valueOf("GO"));
        enderDest.setCEP("X");
        enderDest.setCPais("X");
        enderDest.setXPais("X");
        enderDest.setFone("X");
        dest.setEnderDest(enderDest);
        dest.setEmail("X");
        dest.setIndIEDest("1");
        dest.setIE("X");
        infNFe.setDest(dest);

        Det det = new Det();
        det.setNItem("1");

        //  Dados do Produro
        Prod prod = new Prod();
        prod.setCProd("7898480650104");
        prod.setCEAN("7898480650104");
        prod.setXProd("LUBRAX 20/50");
        prod.setNCM("27101932");
        prod.setCEST("0600500");
        prod.setCFOP("5656");
        prod.setUCom("UN");
        prod.setQCom("1.0000");
        prod.setVUnCom("13.0000");
        prod.setVProd("13.00");
        prod.setCEANTrib("");
        prod.setUTrib("UN");
        prod.setQTrib("1.0000");
        prod.setVUnTrib("13.0000");
        prod.setIndTot("1");

        // Grupo Combustivel Somente para Lubrificantes e Combustiveis
        Comb comb = new Comb();
        comb.setCProdANP("620505001");
        comb.setQTemp("1");
        comb.setUFCons(TUf.valueOf("GO"));

        prod.setComb(comb);
        det.setProd(prod);

        //  Impostos da NF-e
        Imposto imposto = new Imposto();

        ICMS icms = new ICMS();

        ICMS60 icms60 = new ICMS60();

        icms60.setOrig("0");
        icms60.setCST("60");
        icms60.setVBCSTRet("0.00");
        icms60.setVICMSSTRet("0.00");
        icms.setICMS60(icms60);

        PIS pis = new PIS();
        PISAliq pisAliq = new PISAliq();
        pisAliq.setCST("01");
        pisAliq.setVBC("13.00");
        pisAliq.setPPIS("1.65");
        pisAliq.setVPIS("0.21");
        pis.setPISAliq(pisAliq);

        COFINS cofins = new COFINS();
        COFINSAliq cofinsAliq = new COFINSAliq();
        cofinsAliq.setCST("01");
        cofinsAliq.setVBC("13.00");
        cofinsAliq.setPCOFINS("7.60");
        cofinsAliq.setVCOFINS("0.99");
        cofins.setCOFINSAliq(cofinsAliq);

        JAXBElement<ICMS> icmsElement = new JAXBElement<ICMS>(new QName("ICMS"), ICMS.class, icms);
        imposto.getContent().add(icmsElement);

        JAXBElement<PIS> pisElement = new JAXBElement<PIS>(new QName("PIS"), PIS.class, pis);
        imposto.getContent().add(pisElement);

        JAXBElement<COFINS> cofinsElement = new JAXBElement<COFINS>(new QName("COFINS"), COFINS.class, cofins);
        imposto.getContent().add(cofinsElement);

        det.setImposto(imposto);
        infNFe.getDet().add(det);

        // TOTAIS
        Total total = new Total();

        ICMSTot icmstot = new ICMSTot();
        icmstot.setVBC("0.00");
        icmstot.setVICMS("0.00");
        icmstot.setVICMSDeson("0.00");
        icmstot.setVBCST("0.00");
        icmstot.setVST("0.00");
        icmstot.setVProd("13.00");
        icmstot.setVFrete("0.00");
        icmstot.setVSeg("0.00");
        icmstot.setVDesc("0.00");
        icmstot.setVII("0.00");
        icmstot.setVIPI("0.00");
        icmstot.setVPIS("0.21");
        icmstot.setVCOFINS("0.99");
        icmstot.setVOutro("0.00");
        icmstot.setVNF("13.00");
        total.setICMSTot(icmstot);
        infNFe.setTotal(total);

        Transp transp = new Transp();
        transp.setModFrete("9");
        infNFe.setTransp(transp);

        InfAdic infAdic = new InfAdic();
        infAdic.setInfCpl("Observacao...");
        infNFe.setInfAdic(infAdic);
        nfe.setInfNFe(infNFe);

        // Monta EnviNfe
        TEnviNFe enviNFe = new TEnviNFe();
        enviNFe.setVersao("3.10");
        enviNFe.setIdLote("1");
        enviNFe.setIndSinc("1");
        enviNFe.getNFe().add(nfe);

        // MOnta e Assina o XML
        //Informe false para não fazer a validação do XML - Ganho De tempo.
        enviNFe = Nfe.montaNfe(enviNFe,false);

        // Envia a Nfe para a Sefaz
        TRetEnviNFe retorno = Nfe.enviarNfe(enviNFe, ConstantesUtil.NFE);
        System.out.println("Status:" + retorno.getProtNFe().getInfProt().getCStat());
        System.out.println("Motivo:" + retorno.getProtNFe().getInfProt().getXMotivo());
        System.out.println("Data:" + retorno.getProtNFe().getInfProt().getDhRecbto());
        System.out.println("Protocolo:" + retorno.getProtNFe().getInfProt().getNProt());

        System.out.println(XmlUtil.criaNfeProc(enviNFe, retorno.getProtNFe()));

    } catch (NfeException | JAXBException e) {
        System.out.println("Erro:" + e.getMessage());
    }
*/
}