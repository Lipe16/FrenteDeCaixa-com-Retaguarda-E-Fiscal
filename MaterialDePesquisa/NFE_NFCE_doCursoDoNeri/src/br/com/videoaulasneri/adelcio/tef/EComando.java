package br.com.videoaulasneri.adelcio.tef;

/**
 * Enumerador que define os comando do ECF no ACBrMonitor. <code>Link para as definicoes http://acbr.sourceforge.net/drupal/?q=node/24</code>
 *
 * @author Pedro H. Lira
 */
public enum EComando {

    /**
     * Ativa o ECF.
     */
    ECF_Ativar,
    /**
     * Desativa o ECF.
     */
    ECF_Desativar,
    /**
     * Responde True para ECF ativo e False para ECF desativado.
     */
    ECF_Ativo,
    /**
     * Retorna número de colunas do ECF.
     */
    ECF_Colunas,
    /**
     * Retorna o último comando enviado para o ECF, no formato da sintaxe suportada pelo ECF.
     */
    ECF_ComandoEnviado,
    /**
     * Retorna a resposta exata do ECF, sem tratamento, na sintaxe de retorno do ECF.
     */
    ECF_RespostaComando,
    /**
     * Retorna modelo do ECF configurado no ACBrMonitor.
     */
    ECF_ModeloStr,
    /**
     * Retorna modelo no formato usado no componente ACBrECF.
     */
    ECF_Modelo,
    /**
     * Retorna porta que ECF está configurada.
     */
    ECF_Porta,
    /**
     * Retorna data e hora do ECF.
     */
    ECF_DataHora,
    /**
     * Retorna numero do cupom.
     */
    ECF_NumCupom,
    /**
     * Retorna número da loja.
     */
    ECF_NumLoja,
    /**
     * Retorna número do CRO.
     */
    ECF_NumCRO,
    /**
     * Retorna número do CCF (Contador de Cupom Fiscal).
     */
    ECF_NumCCF,
    /**
     * Retorna número do GRG (Contador Relatórios Gerenciais).
     */
    ECF_NumGRG,
    /**
     * Retorna número do GNF (Contador de Não Fiscal).
     */
    ECF_NumGNF,
    /**
     * Retorna número do CDC (Contador de Crédito Débito).
     */
    ECF_NumCDC,
    /**
     * Retorna número do CRZ.
     */
    ECF_NumCRZ,
    /**
     * Retorna número do ECF.
     */
    ECF_NumECF,
    /**
     * Retorna número do CFC (Cupom Fiscal Cancelado).
     */
    ECF_NumCFC,
    /**
     * Retorna o número do ONF (Operação Não Fiscal).
     */
    ECF_NumCNF,
    /**
     * Retorna número de série do ECF.
     */
    ECF_NumSerie,
    /**
     * Retorna Versão do Software do ECF.
     */
    ECF_NumVersao,
    /**
     * Retorna a data do Software do ECF.
     */
    ECF_DataHoraSB,
    /**
     * Retorna data do movimento do ECF.
     */
    ECF_DataMovimento,
    /**
     * Retorna CNPJ cadastrado no ECF.
     */
    ECF_CNPJ,
    /**
     * Retorna IE cadastrado no ECF.
     */
    ECF_IE,
    /**
     * Retorna Numero COO Inicial.
     */
    ECF_NumCOOInicial,
    /**
     * Retorna Venda Bruta.
     */
    ECF_VendaBruta,
    /**
     * Retorna Grande Total.
     */
    ECF_GrandeTotal,
    /**
     * Retorna Total de Cancelamentos.
     */
    ECF_TotalCancelamentos,
    /**
     * Retorna Total de Descontos.
     */
    ECF_TotalDescontos,
    /**
     * Retorno Total de Acréscimos.
     */
    ECF_TotalAcrescimos,
    /**
     * Retorna Total Vendido em Substituição Tributária.
     */
    ECF_TotalSubstituicaoTributaria,
    /**
     * Retorna Total Vendido Não Tributado.
     */
    ECF_TotalNaoTributado,
    /**
     * Retorna Total Vendido Isento.
     */
    ECF_TotalIsencao,
    /**
     * Retorna Total Vendido Tributado.
     */
    ECF_TotalTributado,
    /**
     * Retorna Total de Sangrias.
     */
    ECF_TotalSangria,
    /**
     * Retorna Total de Suprmientos.
     */
    ECF_TotalSuprimento,
    /**
     * Retorna número do último item vendido.
     */
    ECF_NumUltItem,
    /**
     * Retorna dados da Redução Z.
     */
    ECF_DadosReducaoz,
    /**
     * Retorna dados da última Redução Z.
     */
    ECF_DadosUltimaReducaoZ,
    /**
     * Retorna todas as alíquotas cadastradas no ECF. IIIIT999.99|IIIIT999.99|IIIIT999.99|....<br><br>
     *
     * <b>Onde:</b><br>
     *
     * <b>IIII</b> = índice da Alíquota usado pelo ACBrECF<br>
     *
     * <b>T</b> = Tipo da Alíquota, ( "T" = ICMS, "S" = ISS)<br>
     *
     * <b>999.99</b> = Valor da Alíquota<br><br>
     *
     * <b>Nota:</b>Esse comando quando executado a primeira vez, se comunica com o ECF a fim de carregar a tabela de alíquotas. Após a carga, elas são transferidas para a memória do ACBrECF.
     */
    ECF_Aliquotas,
    /**
     * Semelhante ao comando ECF.Aliquotas, porém esse comando sempre se comunica com o ECF a fim de ler novamente todas as alíquotas cadastradas.
     */
    ECF_CarregaAliquotas,
    /**
     * Retorna Total Vendido em cada alíquota.
     */
    ECF_LerTotaisAliquota,
    /**
     * <b>Parâmetros:</b><br>
     *
     * <b>nValorAliquota</b> - Valor da Alíquota a programar.<br>
     *
     * <b>cTipoAliquota</b> - Tipo da Alíquota , Use "T" para ICMS ou "S" para ISS. Pode ser omitido, nesse caso assume "T".<br>
     *
     * <b>cPosicaoAliquota</b> - Posição de cadastro da Alíquota. Não é aceito em todos os modelos de ECFs, e em alguns outros apenas é aceito em modo de Intervenção. <b>Normalmente esse parâmetro
     * deve ser omitido.</b><br><br>
     *
     * <b>Notas:</b><br>
     *
     * Na maioria dos ECFs este comando somente é aceito quando o Movimento não foi iniciado, ou seja, após uma Redução Z e antes de uma Venda ou Leitura X.<br>
     *
     * Não é possível apagar as alíquotas programadas, portanto CUIDADO ao programar novas Alíquotas. Apenas uma intervenção técnica pode remover as Alíquotas já programadas.
     */
    ECF_ProgramaAliquota,
    /**
     * Procura Alíquota no ECF e retorna o índice da alíquota.
     */
    ECF_AchaIcmsAliquota,
    /**
     * Retorna Formas de Pagamento Cadastradas.<br><br>
     *
     * <b>Nota: </b>Esse comando quando executado a primeira vez, se comunica com o ECF a fim de carregar as formas de pagamentos. Após a carga, elas são transferidas para a memória do ACBrECF.
     */
    ECF_FormasPagamento,
    /**
     * Semelhante ao comando ECF.FormasPagamento, porém esse comando sempre se comunica com o ECF a fim de ler novamente todas as formas de pagamentos cadastradas.
     */
    ECF_CarregaFormasPagamento,
    /**
     * Retorna Total de cada Forma de Pagamento.
     */
    ECF_LerTotaisFormaPagamento,
    /**
     * Programa Forma de Pagamento e retorna índice em que a Forma foi cadastrada.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cDescricao</b> - A descrição da forma de pagamento.<br>
     *
     * <b>bPermitevinculado</b> Opcional - Define se a forma de pagamento esta vinculado ao cupom.<br>
     *
     * <b>cPosicao</b> Opcional - Informa a posicao que deseja inserir a forma de pagamento.
     */
    ECF_ProgramaFormaPagamento,
    /**
     * Acha a forma de pagamento usndo como filtro a descricao da mesma e retorna sua posicao e descricao.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cDescricao</b> - A descrição da forma de pagamento.
     */
    ECF_AchaFPGDescricao,
    /**
     * Retorna CNFs cadastrados.<br><br>
     *
     * <b>Nota: </b>Esse comando quando executado a primeira vez, se comunica com o ECF a fim de carregar a tabela de CNFs. Após a carga, elas são transferidas para a memória do ACBrECF.
     */
    ECF_ComprovantesNaoFiscais,
    /**
     * Semelhante ao comando ECF.ComprovantesNaoFiscais, porém esse comando sempre se comunica com o ECF a fim de ler novamente todas as CNFs cadastradas.
     */
    ECF_CarregaComprovantesNaoFiscais,
    /**
     * Retorna total dos CNFs.
     */
    ECF_LerTotaisComprovanteNaoFiscal,
    /**
     * Programa CNF e retorna índice em que a CNF foi cadastrada.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cDescricao</b> - A descrição do CNF.<br>
     *
     * <b>cTipo</b> Opcional - Define o tipo da CNF.<br>
     *
     * <b>cPosicao</b> Opcional - Informa a posicao que deseja inserir a CNF.
     */
    ECF_ProgramaComprovanteNaoFiscal,
    /**
     * Acha a CNF usndo como filtro a descricao da mesma e retorna sua posicao e descricao.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cDescricao</b> - A descrição da CNF.
     */
    ECF_AchaCNFDescricao,
    /**
     * Retorna unidades de medida cadastradas.<br><br>
     *
     * <b>Nota: </b>Esse comando quando executado a primeira vez, se comunica com o ECF a fim de carregar a tabela de Unidades. Após a carga, elas são transferidas para a memória do ACBrECF.
     */
    ECF_UnidadesMedida,
    /**
     * Semelhante ao comando ECF.UnidadesMedida, porém esse comando sempre se comunica com o ECF a fim de ler novamente todas as Unidades cadastradas.
     */
    ECF_CarregaUnidadesMedida,
    /**
     * Programa Unidade de Medida e retorna índice em que a UM foi cadastrada.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cDescricao</b> - A descrição do Unidade de Medida.<br>
     */
    ECF_ProgramaUnidadeMedida,
    /**
     * Realiza a sangria no sistema, onde é retirado o dinheiro do caixa.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>nValor</b> - O valor total desta sangria em double.<br>
     *
     * <b>cObservacao</b> - Nao suportado por muitas impressoras.<br>
     *
     * <b>cCNF</b> - O nome do cupom nao fiscal.<br>
     *
     * <b>cFPG</b> - O nome da forma de pagamento.<br>
     */
    ECF_Sangria,
    /**
     * Realiza o suprimento no sistema, onde é inserido o dinheiro do caixa.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>nValor</b> - O valor total deste suprimento em double.<br>
     *
     * <b>cObservacao</b> - Nao suportado por muitas impressoras.<br>
     *
     * <b>cCNF</b>. O nome do cupom nao fiscal.<br>
     *
     * <b>cFPG</b> - O nome da forma de pagamento.<br>
     */
    ECF_Suprimento,
    /**
     * Realiza um corte no papel.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>bCorteParcial</b> - Parâmetro Opcional. Se informado como True efetua corte parcial do papel no ECF para equipamentos com guilhotina.
     */
    ECF_CortaPapel,
    /**
     * Se não puder abrir um Cupom Fiscal, retorna um erro com o motivo, caso contrário retorna OK.
     */
    ECF_TestaPodeAbrirCupom,
    /**
     * Avisa ao ECF para abrir um cupom.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cCPF_CNPJ</b> - Parâmetro opcional. Se necessário, informe o CPF/CNPJ do Consumidor.<br>
     *
     * <b>cNOME</b> - Parâmetro opcional. Se necessário, informe o NOME do Consumidor.<br>
     *
     * <b>cENDERECO</b> - Parâmetro opcional. Se necessário, informe o ENDEREÇO do Consumidor.<br>
     *
     * <b>Nota: </b>Nem todos os modelos de ECF fazem uso do parâmetro cCPF_CNPJ, nesse caso o Cupom será aberto, porém o número de identificação do cliente não será impresso.<br>
     *
     * <b>Dica: </b>Para identificar o cliente prefira usar o Rodapé do Cupom, que permite até 8 linhas de texto livre
     */
    ECF_AbreCupom,
    /**
     * O Método Identifica Consumidor, se for usado antes da abertura do cupom, os dados do consumidor serão usados no comando de AbreCupom, caso seja usado após o abre cupom, se o ECF não for MFD, os
     * dados serão impressos nas linhas de mensagens do cupom.
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cCPF_CNPJ</b> - Se necessário, informe o CPF/CNPJ do Consumidor.<br>
     *
     * <b>cNOME</b> - Parâmetro opcional. Se necessário, informe o NOME do Consumidor.<br>
     *
     * <b>cENDERECO</b> - Parâmetro opcional. Se necessário, informe o ENDEREÇO do Consumidor.<br>
     */
    ECF_IdentificaConsumidor,
    /**
     * Permite gravar no ECF informações sobre operadores.
     * 
     * <b>Parâmetros:</b><br>
     * 
     * <b>cNome</b> - Nome do operador.<br>
     */
    ECF_IdentificaOperador,
    /**
     * Permite gravar no ECF, informações sobre o Programa Aplicativo Fiscal (PAF) em operação. Esta informação deve ser impressa em todos os cupons, de acordo com a lei atual.
     * 
     * <b>Parâmetros:</b><br>
     * 
     * <b>cNomeVersao</b> - Nome e versão do aplicativo fiscal.<br>
     * 
     * <b>cMD5</b> - Numero do MD5.<br>
     */
    ECF_IdentificaPAF,
    /**
     * O Método Vende Item inform ao ECF o item a ser vendido.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cCodigo</b> - Texto com o código do produto, geralmente á aceito até 13 caracteres, alguns ECFs apenas aceitam numéricos no código.<br>
     *
     * <b>cDescricao</b> - Texto com a descrição do Produto vendido. Procure não usar acentos, pois alguns ECFs não aceitam caracteres acentuados. Para imprimir Descrições "grandes" habilite a opção
     * "Descrição Grande" no ACBrMonitor.<br>
     *
     * <b>cAliquotaICMS</b> - Texto com a representação da Alíquota do ICMS. - As alíquotas podem ser informadas em Valor (mesmo sendo texto). Exemplos: "18", "2.46". - Se no ECF existem alíquotas
     * iguais para ICMS e ISS, use o sufixo "T" para informar que a alíquota é do ICMS ou "S" para alíquotas do ISS. Exemplo: "18T" = alíquota de 18% do ICMS; "2.5S" alíquota de 2,5% do ISS - As
     * alíquotas podem ser informadas em índice, de acordo com a Tabela de alíquotas do ECF, nesse caso use a letra "T", seguida da posição da Alíquota: Exemplo: "T01", "T10" - Existem alíquotas
     * internas do ECF para tratar produtos Isentos, nesse caso use: "FF" para Substituição Tributária, "NN" = Não incidência ou "II" = Isento<br>
     *
     * <b>nQtd</b> - Quantidade de Produtos a Vender. Permite valores com até 3 casas decimais. O ACBr verifica quantas casas decimais existem no valor informado e utiliza o comando apropriado para o
     * ECF, otimizando a impressão para Inteiros o 2 casas decimais, sempre que possível.<br>
     *
     * <b>nValorUnitario</b> – Preço Unitário do produto vendido. Permite valores com até 3 casas decimais. O ACBr verifica quantas casas decimais existem no valor informado e utiliza o comando
     * apropriado para o ECF, otimizando a impressão para 2 casas decimais, sempre que possível.<br>
     *
     * <b>nDescontoPorc</b> - Parâmetro opcional, Se necessário, informe a Porcentagem de Desconto a aplicar no item Vendido. Dependendo do ECF o valor e porcentagem do Desconto será impresso no
     * Cupom.<br>
     *
     * <b>cUnidade</b> - Parâmetro opcional, Se necessário, informe o Texto com a unidade de medida do Item. Exemplo: "UN", "LT", "MT", "KG", etc<br>
     *
     * <b>cTipoDesc</b> - Parâmetro opcional, Se necessário, informe o tipo de desconto. Pode ser "%" ou "$".<br>
     *
     * <b>cDescAcres</b> - Parâmetro opcional, Se necessário, informe o se será dado desconto ou acréscimo. O valor default é "%D "%(Desconto) mas pode ser passado como "%A "%(Acréscimo) .<br><br>
     *
     * <b>Notas:</b><br>
     *
     * - O ACBr tentará otimizar a impressão ocupando o menor numero de linhas possíveis, de acordo com o tamanho dos parâmetros cCodigo e cDescricao. Espaços a direita de cDescricao são
     * ignorados.<br>
     *
     * - Para evitar "diferenças" entre o seu programa e o impresso no ECF, procure informar os campos nQtd e nValorUnitario já arredondados para o número máximo de casas decimais que você deseja
     * utilizar.
     */
    ECF_VendeItem,
    /**
     * Método que cancela um item vendido no ECF.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>nNumItem</b> - Numero do Item, de acordo com a ordem de impressão no Cupom fiscal, a cancelar.
     */
    ECF_CancelaItemVendido,
    /**
     * Método que totaliza o cupom fiscal.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>nDescontoAcrescimo</b> - Parâmetro opcional. Para Descontos, informe valores negativos, para acréscimos valores positivos.<br>
     *
     * <b>cMensagemRodape</b> – Parâmetro opcional. Informe até 8 linhas de mensagem a serem impressas no rodapé do cupom. Usado apenas para o ECF DataRegis que não possui o método FechaCupom, nos
     * demais ECFs, se a mensagem for informada nesse momento, ela será armazenada pelo ACBr e utilizada em FechaCupom.
     */
    ECF_SubtotalizaCupom,
    /**
     * Método que informa a forma de pagamento do cupom fiscal.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cCodFormaPagto</b> - �?ndice da Forma de pagamento cadastrada no ECF. Para conhecer todas as Formas de pagamento cadastradas e seus respectivos índices, utilize o comando
     * ECF.FormasPagamento.<br>
     *
     * <b>nValor</b> - Valor pago para essa forma de pagamento.<br>
     *
     * <b>cObservacao</b> - Pode ser omitido. Alguns ECFs permitem a impressão de até 2 linhas de observação para cada forma de pagamento.<br>
     *
     * <b>bImprimeVinculado</b> - Pode ser omitido, nesse caso assume "False". Se for informado "True" para este parâmetro, o ACBr apenas verifica se é permitido imprimir Cupom Não Fiscal Vinculado
     * para essa forma de Pagamento.
     */
    ECF_EfetuaPagamento,
    /**
     * Método que fecha a vendo do cupom fiscal.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cMensagemRodape</b> - Parâmetro opcional. Informe até 8 linhas de mensagem a serem impressas no rodapé do cupom. Se esse parâmetro for omitido aqui, porém foi informado em
     * ECF.SubtotalizaCupom , o texto informado anteriormente será utilizado.<br><br>
     *
     * <b>Notas: </b>Utilize a mensagem no rodapé para identificar o Cliente, (Nome, Documento, Endereço, etc) Este comando só pode ser executado após o Total de Pagamentos (ECF.TotalPago) efetuados
     * com ECF.EfetuaPagamento atingir ou ultrapassar o valor de ECF.SubTotal.
     */
    ECF_FechaCupom,
    /**
     * Cancela o Cupom Fiscal atual (se estiver aberto) em qualquer estágio do Cupom. <br>
     *
     * Se não houver cupom aberto, cancela o Último Cupom emitido, caso o último documento seja realmente um Cupom Fiscal.
     */
    ECF_CancelaCupom,
    /**
     * Retorna o Sub-total do cupom atual (em aberto). Considerando a soma dos Itens Vendidos, o Desconto / Acréscimo concedido em ECF.SubtotalizaCupom e os pagamentos já efetuados com
     * ECF.EfetuaPagamento.
     */
    ECF_SubTotal,
    /**
     * Retorna o Total de pagamentos já efetuados quando o ECF está no estado estPagamento.
     */
    ECF_TotalPago,
    /**
     * Informa se o o cupom nao fiscal esta completo.
     */
    ECF_NaoFiscalCompleto,
    /**
     * @see #ECF_AbreCupom
     */
    ECF_AbreNaoFiscal,
    /**
     * @see #ECF_VendeItem
     */
    ECF_RegistraItemNaoFiscal,
    /**
     * @see #ECF_SubtotalizaCupom
     */
    ECF_SubtotalizaNaoFiscal,
    /**
     * @see #ECF_EfetuaPagamento
     */
    ECF_EfetuaPagamentoNaoFiscal,
    /**
     * @see #ECF_FechaCupom
     */
    ECF_FechaNaoFiscal,
    /**
     * @see #ECF_CancelaCupom
     */
    ECF_CancelaNaoFiscal,
    /**
     * Emite o relatório de Leitura X.<br><br>
     *
     * <b>Nota: </b>Em alguns estados, é necessário imprimir uma Leitura X todo inicio de dia e cada inicio de Bobina.
     */
    ECF_LeituraX,
    /**
     * Emite o relatório de Leitura Z.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>dDataHora</b> - Data / Hora atual do micro. Parâmetro pode ser omitido. Se dDataHora for informado, o ACBrECF tentará acertar o relógio do ECF (disponível apenas em alguns ECFs), aumentando
     * ou diminuindo o horário no máximo de 5 minutos por dia.<br><br>
     *
     * <b>Nota: </b>Se emitido no mesmo dia fiscal, bloqueia o ECF até as 24:00hs. Se não for emitida no mesmo dia fiscal o ECF ficará bloqueado, e o ACBr retornará o Estado como estRequerZ. Nesse
     * caso será necessário emitir a Redução Z pendente (do dia anterior) para liberar o ECF.<br><br>
     *
     * <b>Cuidado: </b>Apenas comande a Redução Z se o estado do ECF for estRequerZ ou se você deseja realmente bloquear o ECF até a meia-noite (fim de dia).
     */
    ECF_ReducaoZ,
    /**
     * Retorna “True�? se o ECF está com Pouco Papel.
     */
    ECF_PoucoPapel,
    /**
     * Retorna “True�? se o está em Horário de verão.<br><br>
     *
     * <b>Nota: </b>Isso também pode ser constatado observando a letra “V�? ao lado da Hora no rodapé do Cupom Fiscal.
     */
    ECF_HorarioVerao,
    /**
     * Retorna “True�? se o ECF arredonda os valores do Total por Item durante a Venda.<br><br>
     *
     * <b>Nota: </b>Na maioria dos ECFs o Arredondamento deve ser programado, o que pode ser feito com o comando ECF.MudaArredondamento Em alguns ECFs o arredondamento é definido de acordo com o
     * comando enviado para a Venda de Item, nesse caso, o ACBr procura usar o Arredondamento sempre que possível. Após o convênio do ICMS 56/94 o arredondamento não é mais permitido, portanto todos
     * os novos ECFs apenas Truncam o Total por Item.
     */
    ECF_Arredonda,
    /**
     * Retorna “True�? se o ECF possui MFD.
     */
    ECF_MFD,
    /**
     * Retorna “True�? se o ECF possui impressão Térmica.
     */
    ECF_Termica,
    /**
     * Retorna o estado atual do ECF, podendo ser:<br><br>
     *
     * <b>estNaoInicializada</b>, Porta Serial ainda não foi aberta;
     *
     * <b>estDesconhecido</b>, Porta aberta, mas estado ainda não definido;
     *
     * <b>estLivre</b>, Impressora Livre, sem nenhum cupom aberto pronta para nova venda, Redução Z e Leitura X ok, pode ou não já ter ocorrido 1ª venda no dia...;
     *
     * <b>estVenda</b>, Cupom de Venda Aberto com ou sem venda do 1º Item;
     *
     * <b>estPagamento</b>, Iniciado Fechamento de Cupom com Formas Pagamento pode ou não ter efetuado o 1º pagamento. Não pode mais vender itens, ou alterar Sub-total;
     *
     * <b>estRelatorio</b>, Imprimindo Cupom Fiscal Vinculado ou Relatório Gerencial;
     *
     * <b>estBloqueada</b>, Redução Z já emitida, bloqueada até as 00:00;
     *
     * <b>estRequerZ</b>, Redução Z do dia anterior ainda não foi emitida. Emitir agora;
     *
     * <b>estRequerX</b>, Esta impressora requer Leitura X todo inicio de dia. É necessário imprimir uma Leitura X para poder vender.
     */
    ECF_Estado,
    /**
     * Aciona a abertura da gaveta de dinheiro ligada ao ECF.
     */
    ECF_AbreGaveta,
    /**
     * Retorna “True�? se a gaveta de dinheiro está aberta.
     */
    ECF_GavetaAberta,
    /**
     * Método que executa o comando de impressão do cheque.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cBanco</b> - Nome do banco suportado pelo ACBrMonitor.<br>
     *
     * <b>nValor</b> - Valor total a ser impresso.<br>
     *
     * <b>cFavorecido</b> - Nome da empresa que será favorecida.<br>
     *
     * <b>cCidade</b> - Nome da cidade do local atual.<br>
     *
     * <b>dData</b> - Data atual no formato dd/mm/aaaa<br>
     *
     * <b>cObservacao</b> - Opcional uma mensagem para colocar no cheque.
     */
    ECF_ImprimeCheque,
    /**
     * Método que cancela a operação de impressão de cheque.
     */
    ECF_CancelaImpressaoCheque,
    /**
     * Retorna “True�? se o Cheque já foi impresso.
     */
    ECF_ChequePronto,
    /**
     * Método que muda o horário para verão ou normal.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>bHorarioVerao</b> - Um booleano, passar "True" para ativar o horário de verão, ou passar "False" para desativar.
     */
    ECF_MudaHorarioVerao,
    /**
     * Método que muda o tipo de arredondamento de casas decimais no ECF.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>bArredonda</b> - Um booleano, passar "True" para ativar o arredondamento em 2 casas , ou passar "False" para truncar o número em 2 casas decimais.
     */
    ECF_MudaArredondamento,
    /**
     * - Verifica se existe algum relatório Gerencial ou Vinculado aberto, se for o caso, fecha-o.<br>
     *
     * - Carrega as tabelas de Formas de Pagamento e Comprovantes não Fiscais na memória.<br>
     *
     * - Deve ser chamado apenas no inicio da aplicação.
     */
    ECF_PreparaTEF,
    /**
     * Verifica o estado atual do ECF e efetua as operações necessárias para deixar o ECF no estado livre. Portanto esse método tenta fechar ou cancelar qualquer documento que esteja aberto. Em alguns
     * ECFs comandos adicionais são enviados para tentar “desbloquear�? o ECF de alguma condição de erro que impeça a impressão de novos documentos.
     */
    ECF_CorrigeEstadoErro,
    /**
     * Método que abre o relatorio gerencial.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cNome</b> - O nome do relatorio cadastro no ECF (nao informar para usar o padrao).
     */
    ECF_AbreRelatorioGerencial,
    /**
     * Método que envia varias linhas para impressao no relatorio, permitindo ainda informar quantas vias do mesmo.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cTexto</b> - Um texto com o conteudo a ser impresso, com as linhas separandas por | (pipe).<br>
     *
     * <b>nVias</b> - O numero de vias que deseja imprimir.
     */
    ECF_RelatorioGerencial,
    /**
     * Método que envia uma linha para imprimir no relatorio gerencial.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cLinha</b> Um texto a ser impresso em uma linha ou enviado com | (pipe) imprime varias linhas.
     */
    ECF_LinhaRelatorioGerencial,
    /**
     * Método que pula linhas dentro do relatorio gerencial.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>nNumLinhas</b> Um número de linhas para pular.
     */
    ECF_PulaLinhas,
    /**
     * Método que abre um cupom vinculado de pagamento.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cCOO</b> Número do COO vinculado.<br>
     *
     * <b>cCodFormaPagto</b> Número do código de pagamento.<br>
     *
     * <b>cCodComprovanteNaoFiscal</b> Caso seja Não Fiscal informar o código do comprovante, caso contrário ignorar este campo.<br>
     *
     * <b>nValor</b> O valor do documento.
     */
    ECF_AbreCupomVinculado,
    /**
     * Método que envia uma linha para imprimir no documento vinculado.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cLinha</b> Um texto a ser impresso em uma linha ou enviado com | (pipe) imprime varias linhas.
     */
    ECF_LinhaCupomVinculado,
    /**
     * @see #ECF_AbreCupomVinculado
     *
     * Mais um parametro de texto no final com as linhas a serem impressas separadas por | (pipe).
     */
    ECF_CupomVinculado,
    /**
     * Método que fecha o relatorio gerencial.
     */
    ECF_FechaRelatorio,
    /**
     * Método que emite a leitura de memória fiscal completa ou simplificada.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes e um booleano para informar se e simplificada.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.<br>
     *
     * <b>bSimplificada</b> Um booleano para informa que deseja simplificada.
     */
    ECF_LeituraMemoriaFiscal,
    /**
     * Método que emite a leitura de memória fiscal completa ou simplificada pela serial.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.<br>
     *
     * <b>bSimplificada</b> Um booleano para informa que deseja simplificada.<br>
     *
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo.
     */
    ECF_LeituraMemoriaFiscalSerial,
    /**
     * Sem definicao.
     */
    ECF_LeituraMFDSerial,
    /**
     * Método de envio de comando específico ao ECF.<br><br>
     *
     * <b>Parâmetros:</b><br>
     *
     * <b>cComando</b> O comando em si a ser executado diretamente na ECF.<br>
     *
     * <b>nTimeOut</b> Opcinal pode-se passar um tempo em milissegundos para esperar.
     */
    ECF_EnviaComando,
    /**
     * Método que imprime a leitura de memória fiscal completa.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.
     */
    ECF_PafMf_Lmfc_Impressao,
    /**
     * Método que gerar o arquivo espelho da leitura de memória fiscal completa.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.<br>
     *
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo.
     */
    ECF_PafMf_Lmfc_Espelho,
    /**
     * Método que gerar o arquivo espelho da leitura de memória fiscal completa no layout do ato cotepe 17/04.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.<br>
     *
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo.
     */
    ECF_PafMf_Lmfc_Cotepe1704,
    /**
     * Método que imprime a leitura de memória fiscal simplificada.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.
     */
    ECF_PafMf_Lmfs_Impressao,
    /**
     * Método que gerar o arquivo espelho da leitura de memória fiscal simplificada.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.<br>
     *
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo.
     */
    ECF_PafMf_Lmfs_Espelho,
    /**
     * Método que gerar o arquivo memoria de fita detalhe.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.<br>
     *
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo.
     */
    ECF_PafMf_Mfd_Espelho,
    /**
     * Método que gerar o arquivo memoria de fita detalhe no layout do ato cotepe 17/04.<br><br>
     *
     * <b>Parâmetros:</b> Pode passar duas datas ou duas reducoes.<br>
     *
     * <b>dDt.Inicial / nReducaoInicial</b> A data / reducao inicial do relatorio.<br>
     *
     * <b>dDt.Final / nReducaoFinal</b> A data / reducao final do relatorio.<br>
     *
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo.
     */
    ECF_PafMf_Mfd_Cotepe1704,
    /**
     * Essa função retornará dois arquivos, um binário e um outro arquivo texto com a assintatura EAD.<br><br>
     * 
     * <b>Parâmetros:</b> Deve passar o path completo do arquivo binario.<br>
     * 
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo. 
     */
    ECF_PafMF_ArqMF,
    /**
     * Essa função retornará dois arquivos, um binário e um outro arquivo texto com a assintatura EAD.<br><br>
     * 
     * <b>Parâmetros:</b> Deve passar o path completo do arquivo binario.<br>
     * 
     * <b>cArquivo</b> Um texto que informa o local onde salvar o arquivo. 
     */
    ECF_PafMF_ArqMFD;
    
    @Override
    public String toString() {
        return super.toString().replaceFirst("_", ".");
    }
}
