/*

Descrição: Exemplo Basico de Transmissão / Autorização da NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

public class EnvioDaNFCe {
/*
    try {

        // Inicia As Configurações
        ConfiguracoesIniciaisNfe config =  iniciaConfigurações();

        TNFe nfe = new TNFe();
        InfNFe infNFe = new InfNFe();

        // Substitua X Pelo ID
        infNFe.setId(xxxxxxxxxxxxx);
        infNFe.setVersao(config.getVersaoNfe());

        Ide ide = new Ide();
        ide.setCUF(config.getEstado().getCodigoIbge());

        // Substitua X Pelo CNF
        ide.setCNF(xxxx);
        ide.setNatOp("NOTA FISCAL CONSUMIDOR ELETRONICA");
        ide.setIndPag("0");
        ide.setMod("65");
        ide.setSerie("1");

        // Substitua X Pelos Valores Desejados
        ide.setNNF(xxxxx);
        ide.setDhEmi(xxxxxxxx);
        ide.setTpNF("1");
        ide.setIdDest("1");
        ide.setCMunFG(xxxxxx);
        ide.setTpImp("4");
        ide.setTpEmis("1");
        ide.setCDV(xxxxx);
                ide.setTpAmb(config.getAmbiente());
                ide.setFinNFe("1");
                ide.setIndFinal("1");
                ide.setIndPres("1");
                ide.setProcEmi("0");
                ide.setVerProc(xxx);
        infNFe.setIde(ide);

        Emit emit = new Emit();
        emit.setCNPJ(xxxxx);
        emit.setXNome(xxxxxx);
        emit.setXFant(xxxxxxx);

        TEnderEmi enderEmit = new TEnderEmi();
        enderEmit.setXLgr(xxxxx);  
        enderEmit.setNro(xxxxx); 
        enderEmit.setXCpl(xxxxx);
        enderEmit.setXBairro(xxxxx);  
        enderEmit.setCMun(xxxxx);  
        enderEmit.setXMun(xxxxx;  
        enderEmit.setUF(xxxxx);  
        enderEmit.setCEP(xxxxx);  
        enderEmit.setCPais(xxxxx);  
        enderEmit.setXPais(xxxxx);  
        enderEmit.setFone(xxxxx);  
        emit.setEnderEmit(enderEmit);

        emit.setIE(xxxxx);  
        emit.setCRT(xxxxx);         
        infNFe.setEmit(emit);

        Dest dest = new Dest();
        dest.setCNPJ(xxxxx);  
        dest.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");  

        TEndereco enderDest = new TEndereco();   
        enderDest.setXLgr(xxxxx);  
        enderDest.setNro(xxxxx);  
        enderDest.setXBairro(xxxxx);  
        enderDest.setCMun(xxxxx);  
        enderDest.setXMun(xxxxx);  
        enderDest.setUF(xxxxx);  
        enderDest.setCEP(xxxxx);  
        enderDest.setCPais(xxxxx);  
        enderDest.setXPais(xxxxx);
        enderDest.setFone(xxxxx);  
        dest.setEnderDest(enderDest);  
        dest.setEmail(xxxxx);
        dest.setIndIEDest("9");
        infNFe.setDest(dest);

        Det det = new Det();
        det.setNItem("1");

        //  Dados do Produro
        Prod prod = new Prod();
        prod.setCProd("7898480650104");
        prod.setCEAN("7898480650104");
        prod.setXProd("NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
        prod.setNCM("27101932");
        prod.setCEST("0600500");
        prod.setCFOP("5405");
        prod.setUCom("UN");
        prod.setQCom("1.0000");
        prod.setVUnCom("13.0000");
        prod.setVProd("13.00");
        prod.setCEANTrib("");
        prod.setUTrib("UN");
        prod.setQTrib("1.0000");
        prod.setVUnTrib("13.0000");
        prod.setIndTot("1");

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

        Pag pag = new Pag();
        pag.setTPag("01");
        pag.setVPag("13.00");
        infNFe.getPag().add(pag);

        nfe.setInfNFe(infNFe);

        // Monta EnviNfe
        TEnviNFe enviNFe = new TEnviNFe();
        enviNFe.setVersao("3.10");
        enviNFe.setIdLote("1");
        enviNFe.setIndSinc("1");
        enviNFe.getNFe().add(nfe);

        // MOnta e Assina o XML
        enviNFe = Nfe.montaNfe(enviNFe , false);

        //QRCODE
        String qrCode = NFCeUtil.getCodeQRCode(
                infNFe.getId().substring(3), 
                "100", 
                config.getAmbiente(), 
                dest.getCNPJ() == null ? dest.getCPF() : dest.getCNPJ(), 
                ide.getDhEmi(), 
                total.getICMSTot().getVNF(), 
                total.getICMSTot().getVICMS(),
                Base64.getEncoder().encodeToString(enviNFe.getNFe().get(0).getSignature().getSignedInfo().getReference().getDigestValue()), 
                idToken, 
                csc, 
                WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.URL_QRCODE));
        InfNFeSupl infNFeSupl = new InfNFeSupl();
        infNFeSupl.setQrCode(qrCode);
        enviNFe.getNFe().get(0).setInfNFeSupl(infNFeSupl);

        // Envia a Nfe para a Sefaz
        TRetEnviNFe retorno = Nfe.enviarNfe(enviNFe,ConstantesUtil.NFCE);
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