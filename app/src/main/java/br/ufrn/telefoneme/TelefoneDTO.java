package br.ufrn.telefoneme;


/**
 * Created by Vinicius on 09/12/2015.
 */
public class TelefoneDTO {
    private String localizacao;
    private String setor;
    private String descricao;
    private String numero;

    public TelefoneDTO(String numero, String descricao, String localizacao, String setor){
        this.numero = numero;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.setor = setor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
