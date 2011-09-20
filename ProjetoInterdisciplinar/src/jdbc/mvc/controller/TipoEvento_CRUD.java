
package jdbc.mvc.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import jdbc.mvc.model.TipoEvento_model;

public class TipoEvento_CRUD {
    
    Conexao_crm co = new Conexao_crm();
    
    public static ArrayList<TipoEvento_model> tipo = new ArrayList<TipoEvento_model>();

  public void salvar(TipoEvento_model dados) {
   try{
      co.conectar();
      PreparedStatement stmt = co.con.prepareStatement(
     "INSERT INTO tipoEvento(descricao) " +
     "VALUES( ? )");

       stmt.setString(1, dados.getTipo());
  
       stmt.execute();
       stmt.close();
       co.Fechar_Conexao();

      } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"ERRO..."+e.getMessage(),"Cadastro de Clientes",0);
      }//try

    }//public salvar
  
  //**************************************************************************************************
  
  public List compara()
    {
        int i=0;

        List<TipoEvento_model> lista = new ArrayList<TipoEvento_model>();

        try
        {
            PreparedStatement stmt = co.con.prepareStatement(
                    "SELECT * FROM tipoEvento " );

            ResultSet rs = (ResultSet) stmt.executeQuery();

            while (rs.next())
            {
                TipoEvento_model c = new TipoEvento_model();
                
                c.setCodtipoevento(rs.getInt("idtipoEvento"));
                c.setTipo(rs.getString("Descricao"));


                lista.add(c);
            }


            stmt.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"ERRO..." + e.getMessage(), "Listar Eventos",0);
        }


        return lista;
    }
    
}
