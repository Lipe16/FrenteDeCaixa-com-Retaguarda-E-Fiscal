package br.com.videoaulasneri.adelcio.fatura.bean;

/*

Descrição: Tabela de Fun

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
public class FuncionarioVO {
    private Integer id;
    private String nome;
    private String telefone;
    private String celular;
    private String email;
    private Double comissaoVista;
    private Double comissaoPrazo;
    private String nivelAutorizacao;

    public FuncionarioVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getComissaoVista() {
        return comissaoVista;
    }

    public void setComissaoVista(Double comissaoVista) {
        this.comissaoVista = comissaoVista;
    }

    public Double getComissaoPrazo() {
        return comissaoPrazo;
    }

    public void setComissaoPrazo(Double comissaoPrazo) {
        this.comissaoPrazo = comissaoPrazo;
    }

    public String getNivelAutorizacao() {
        return nivelAutorizacao;
    }

    public void setNivelAutorizacao(String nivelAutorizacao) {
        this.nivelAutorizacao = nivelAutorizacao;
    }


}