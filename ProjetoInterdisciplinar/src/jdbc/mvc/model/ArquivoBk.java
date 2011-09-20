/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ArquivoBk implements Serializable {
    
    private String titulo;
    private Date data;
    private String scriptBd;
    private List<String> scriptInsert = new ArrayList();
    private String configuraBd;

    public String getConfiguraBd() {
        return configuraBd;
    }

    public void setConfiguraBd(String configuraBd) {
        this.configuraBd = configuraBd;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getScriptBd() {
        return scriptBd;
    }

    public void setScriptBd(String scriptBd) {
        this.scriptBd = scriptBd;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getScriptInsert() {
        return scriptInsert;
    }

    public void setScriptInsert(List<String> scriptInsert) {
        this.scriptInsert = scriptInsert;
    }
    
    
    
}
