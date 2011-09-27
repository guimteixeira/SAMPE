/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.mvc.controller;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jdbc.mvc.model.UsuarioSistema;

/**
 *
 * @author Guilherme
 */
public class Usuario_CRUD {

    Conexao_crm c = new Conexao_crm();

    //verifica existencia de usuario
    public boolean verificaUsuario(UsuarioSistema usuario){

        //abre conexao com o BD para verificar o usuário

        try {
            PreparedStatement stmtDados = (PreparedStatement) c.con.prepareStatement("SELECT * FROM Usuario"
                    + " WHERE Usuario = \"" + usuario.getUsuario() + "\" AND "
                    + "SENHA = SHA1(\"" + usuario.getSenha() + "\")");
            ResultSet rsDados = (ResultSet) stmtDados.executeQuery();

            if(rsDados.next()) {
                usuario.setNome(rsDados.getString("Nome"));
                return true;
                }
            else {
                return false;
                }


        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao verficar usuário.\n ERRO: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;

        }

    }

    public void cadastrarusuario(UsuarioSistema usu)
    {
        try
        {
            PreparedStatement stmt = (PreparedStatement) c.con.prepareStatement(
            "INSERT INTO Usuario (nome,usuario,senha) " +
            "VALUES(?,?,SHA1(?))");

            stmt.setString(1, usu.getNome());
            stmt.setString(2, usu.getUsuario());
            stmt.setString(3, usu.getSenha());



            stmt.execute();
            stmt.close();
        }//try
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "ERRO..." + e.getMessage(), "Cadastro de Usuário",0);
        }//catch

    }//salvar

    public boolean verifica(UsuarioSistema u)
    {
        try {
            PreparedStatement stmtDados = (PreparedStatement) c.con.prepareStatement("SELECT * FROM usuario WHERE Usuario = " + "'" + u.getUsuario()+"'");
            ResultSet rsDados = (ResultSet) stmtDados.executeQuery();

            if(rsDados.next())
                return true;
            else
                return false;


        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao verficar usuário.\n ERRO: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;

        }
    }

}
