

package jdbc.mvc.model;


public class Evento_model {

    private String descricao;
    private String data;
    private String local;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int pos) {
        this.id = pos;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    private String hora;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    
}
