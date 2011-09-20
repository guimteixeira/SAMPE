
package jdbc.mvc.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import jdbc.mvc.model.Pessoa_model;

public class Pessoa_CRUD 
{
    

    Conexao_crm co = new Conexao_crm();

    public void cadastrar(Pessoa_model dados)
    {
         
        try
        {
            PreparedStatement st = co.con.prepareStatement(
            "SELECT MAX(idEndereco) as idEndereco from Endereco");
            ResultSet rs = (ResultSet) st.executeQuery();
            rs.next();
            
            
            PreparedStatement stmt = co.con.prepareStatement(
            "INSERT INTO Pessoa (nome,idade,idEndereco,sexo,email,telefone,celular,idcategoriaPessoa,estadocivil,datacadastro) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, dados.getNomepessoa());
            stmt.setString(2, String.valueOf(dados.getIdadepessoa()));
            stmt.setString(3, String.valueOf(rs.getInt("idEndereco")));
            stmt.setString(4, String.valueOf(dados.getSexopessoa()));
            stmt.setString(5, dados.getEmailpessoa());
            stmt.setString(6, dados.getTelefonepessoa());
            stmt.setString(7, dados.getCelularpessoa());
            stmt.setString(8, String.valueOf(dados.getPos()));
            stmt.setString(9, String.valueOf(dados.getEstadocivilpessoa()));
            stmt.setString(10, String.valueOf(dados.getDatacadastropessoa()));

            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Pessoa cadastrada com sucesso");
        }//try
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "ERRO..." + e.getMessage(), "Cadastro de Pessoa",0);
        }//catch    

    }//salvar
    
    
}
