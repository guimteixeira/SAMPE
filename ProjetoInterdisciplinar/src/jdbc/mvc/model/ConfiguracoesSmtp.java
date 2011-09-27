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
public class ConfiguracoesSmtp implements Serializable {
    
    private String endereco;
    private String usuario;
    private String senha;
    private int porta;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
