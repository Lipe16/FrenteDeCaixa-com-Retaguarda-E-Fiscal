package br.com.videoaulasneri.adelcio.tef;

import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Interface que representa o ECF no sistema e todas suas funcionalidades.
 *
 * @author Pedro H. Lira
 */
public interface IECF {

    /**
     * Quebra de linha.
     */
    public String ENTER = "\n";
    /**
     * Numero de colunas.
     */
    public int COL = 48;
    /**
     * Expressao ERRO.
     */
    public String ERRO = "ERRO";
    /**
     * Linha Dupla [===].
     */
    public String LD = "================================================";

    /**
     * Linha Simples [---].
     */
    public String LS = "------------------------------------------------";
    /**
     * Expressao OK.
     */
    public String OK = "OK";
    /**
     * Separador de linhas
     */
    public String SL = "|";
    /**
     * Variavel de log
     */
    public Logger LOG = Logger.getLogger(IECF.class);

    /**
     * Metodo que faz a validacao se o sistema consegue ativar a ECF.
     *
     * @throws Exception dispara uma excecao caso nao consiga ativar.
     */
    public void ativar() throws Exception;

    /**
     * Metodo que realiza a conexao com o ECF.
     *
     * @param porta o numero da porta de comunicacao.
     * @param velocidade a velocidade da porta de comunicacao.
     * @param modelo o numero do modelo usado.
     * @throws Exception dispara um excecao caso nao cosiga.
     */
    public void conectar(String porta, int velocidade, int modelo) throws Exception;

    /**
     * Metodo que desativa o acesso ao ECF.
     */
    public void desativar();

    /**
     * Metodo que envia um comando ao ACBr que repassa para a ECF.
     *
     * @param comando um EComandoECF que representa um comando aceito.
     * @param parametros um sequencia de parametros, opcionais usado somente em
     * alguns comandos.
     * @return um Array de String com a resposta do do ECF.
     */
    public String[] enviar(EComando comando, String... parametros);

    /**
     * Metodo que retorna o estado do ECF.
     *
     * @return o tipo do estado do ECF.
     * @throws Exception dispara uma excecao caso nao consiga executar.
     */
    public EEstado validarEstado() throws Exception;

    /**
     * Metodo que faz a validacao se o ultimo numero do GT e o mesmo do ECF.
     *
     * @param gt o valor do gt atual.
     * @return retorna o valor do GT novo caso seja diferente.
     * @throws Exception dispara uma excecao caso nao consiga executar.
     */
    public double validarGT(double gt) throws Exception;

    /**
     * Metodo que faz a validacao se pode recuperar o GT automaticamente.
     *
     * @param crz ultima reducao z.
     * @param cro ultimo registro de operacao.
     * @param bruto valor bruto da ultima z.
     * @return retorna true se pode recuperar, false caso contrario.
     * @throws Exception dispara uma excecao caso nao consiga executar.
     */
    public boolean validarGT(int crz, int cro, double bruto) throws Exception;

    /**
     * Metodo que faz a validacao se o ECF conectado e o autorizado pelo
     * sistema.
     *
     * @param serie o numero de serie do ECF registrado no arquivo do PAF.
     * @throws Exception dispara uma excecao caso nao seja o ECF esperado.
     */
    public void validarSerial(String serie) throws Exception;

    /**
     * Metodo que retorna o mapa com os dados da ultima Z emitida.
     *
     * @return um mapa de dados, sendo a chave o significado do dado.
     */
    public Map<String, Object> getDadosZ();
}
