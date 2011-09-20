/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.mvc.model;

import java.io.Serializable;

/**
 *
 * @author Guilherme
 */
public class ConfiguracoesBd implements Serializable {
    
    private String enderecoBancoDeDados;
    private String nomeBanco;
    private String usuarioBanco;
    private String senhaBanco;

    public String getEnderecoBancoDeDados() {
        return enderecoBancoDeDados;
    }

    public void setEnderecoBancoDeDados(String enderecoBancoDeDados) {
        this.enderecoBancoDeDados = enderecoBancoDeDados;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getSenhaBanco() {
        return senhaBanco;
    }

    public void setSenhaBanco(String senhaBanco) {
        this.senhaBanco = senhaBanco;
    }

    public String getUsuarioBanco() {
        return usuarioBanco;
    }

    public void setUsuarioBanco(String usuarioBanco) {
        this.usuarioBanco = usuarioBanco;
    }
    
}
