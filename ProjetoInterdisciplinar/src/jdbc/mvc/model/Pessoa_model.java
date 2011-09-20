
package jdbc.mvc.model;

import java.util.Date;

public class Pessoa_model 
{
    private String nomepessoa;
    private int idadepessoa;
    private char sexopessoa;
    private String emailpessoa;
    private String telefonepessoa;
    private String celularpessoa;
    private char estadocivilpessoa;
    private String datacadastropessoa;
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getCelularpessoa() {
        return celularpessoa;
    }

    public void setCelularpessoa(String celularpessoa) {
        this.celularpessoa = celularpessoa;
    }

    public String getDatacadastropessoa() {
        return datacadastropessoa;
    }

    public void setDatacadastropessoa(String datacadastropessoa) {
        this.datacadastropessoa = datacadastropessoa;
    }

    public String getEmailpessoa() {
        return emailpessoa;
    }

    public void setEmailpessoa(String emailpessoa) {
        this.emailpessoa = emailpessoa;
    }

    public char getEstadocivilpessoa() {
        return estadocivilpessoa;
    }

    public void setEstadocivilpessoa(char estadocivilpessoa) {
        this.estadocivilpessoa = estadocivilpessoa;
    }

    public int getIdadepessoa() {
        return idadepessoa;
    }

    public void setIdadepessoa(int idadepessoa) {
        this.idadepessoa = idadepessoa;
    }

    public String getNomepessoa() {
        return nomepessoa;
    }

    public void setNomepessoa(String nomepessoa) {
        this.nomepessoa = nomepessoa;
    }

    public char getSexopessoa() {
        return sexopessoa;
    }

    public void setSexopessoa(char sexopessoa) {
        this.sexopessoa = sexopessoa;
    }

    public String getTelefonepessoa() {
        return telefonepessoa;
    }

    public void setTelefonepessoa(String telefonepessoa) {
        this.telefonepessoa = telefonepessoa;
    }
    
}
