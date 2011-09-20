
package jdbc.mvc.controller;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import jdbc.mvc.model.Categoria_model;

public class Categoria_CRUD 
{
    
    Conexao_crm co = new Conexao_crm();
    public void cadastrarcat(Categoria_model cat)
    {
        try
        {
            PreparedStatement stmt = co.con.prepareStatement(
            "INSERT INTO categoriaPessoa (descricao) " +
            "VALUES(?)");

            stmt.setString(1, cat.getDescricaocategoria());


            stmt.execute();
            stmt.close();
        }//try
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "ERRO..." + e.getMessage(), "Cadastro de Endereço",0);
        }//catch
    }
    
    public List compara()
    {
        int i=0;

        List<Categoria_model> lista = new ArrayList<Categoria_model>();

        try
        {
            PreparedStatement stmt = co.con.prepareStatement(
                    "SELECT * FROM categoriaPessoa " );

            ResultSet rs = (ResultSet) stmt.executeQuery();

            while (rs.next())
            {
                Categoria_model c = new Categoria_model();
                
                c.setCodcategoria(rs.getInt("idcategoriaPessoa"));
                c.setDescricaocategoria(rs.getString("descricao"));
                


                lista.add(c);
            }


            stmt.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"ERRO..." + e.getMessage(), "Listar Categoria de Pessoa",0);
        }


        return lista;
    }
    
}
