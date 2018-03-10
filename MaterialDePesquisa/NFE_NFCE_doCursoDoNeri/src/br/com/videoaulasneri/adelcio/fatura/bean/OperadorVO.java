/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.bean;

public class OperadorVO  {
    private Integer id;
    private String login;
    private String senha;
    private FuncionarioVO funcionarioVO;

    public OperadorVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public FuncionarioVO getFuncionarioVO() {
        return funcionarioVO;
    }

    public void setFuncionarioVO(FuncionarioVO funcionarioVO) {
        this.funcionarioVO = funcionarioVO;
    }

}