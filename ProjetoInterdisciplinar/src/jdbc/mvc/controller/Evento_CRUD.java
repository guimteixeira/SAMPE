/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbc.mvc.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import jdbc.mvc.model.Evento_model;

/**
 *
 * @author a602025
 */
public class Evento_CRUD {

    Conexao_crm co = new Conexao_crm();

  public void salvar(Evento_model dados) {
   try{
      co.conectar();
      PreparedStatement stmt = co.con.prepareStatement(
     "INSERT INTO Evento(descricao,tipo,data,hora,local,idtipoEvento) " +
     "VALUES(?,?,?,?,?,?)");

       stmt.setString(1, dados.getDescricao());
       stmt.setString(2, dados.getTipo());
       stmt.setString(3, dados.getData());
       stmt.setString(4, dados.getHora());
       stmt.setString(5, dados.getLocal());
       stmt.setString(6, String.valueOf(dados.getPos()));
       

       stmt.execute();
       stmt.close();
       
       JOptionPane.showMessageDialog(null, "Operação realizada com sucesso.", "Cadastro de Clientes", JOptionPane.INFORMATION_MESSAGE);

      } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"ERRO..."+e.getMessage(),"Cadastro de Clientes",0);
      }//try

    }//public salvar

}
