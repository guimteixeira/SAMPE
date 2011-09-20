

package jdbc.mvc.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import jdbc.mvc.model.Endereco_model;

public class Endereco_CRUD 
{
    Conexao_crm co = new Conexao_crm();
    
    public void cadastrarend(Endereco_model end)
    {
        try
        {
            PreparedStatement stmt = co.con.prepareStatement(
            "INSERT INTO Endereco (logradouro,numero,bairro,complemento,cep,estado) " +
            "VALUES(?,?,?,?,?,?)");

            stmt.setString(1, end.getLogradouro());
            stmt.setString(2, end.getNumero());
            stmt.setString(3, end.getBairro());
            stmt.setString(4, end.getComplemento());
            stmt.setString(5, end.getCep());
            stmt.setString(6, end.getEstado());

            stmt.execute();
            stmt.close();
        }//try
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "ERRO..." + e.getMessage(), "Cadastro de Endereço",0);
        }//catch
    }
    
}
