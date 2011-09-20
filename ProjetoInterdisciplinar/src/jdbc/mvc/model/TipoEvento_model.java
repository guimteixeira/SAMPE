
package jdbc.mvc.model;


public class TipoEvento_model {
    
private String tipo;
private int codtipoevento;

    public int getCodtipoevento() {
        return codtipoevento;
    }

    public void setCodtipoevento(int codtipoevento) {
        this.codtipoevento = codtipoevento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
