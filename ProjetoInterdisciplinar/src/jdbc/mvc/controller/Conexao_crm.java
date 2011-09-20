
package jdbc.mvc.controller;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.mvc.model.ConfiguracoesBd;



public class Conexao_crm 
{
    
      ConfiguracoesBd_CRUD configs = new   ConfiguracoesBd_CRUD();
      ConfiguracoesBd dados = configs.leDllBd();
      
      
    
      public Connection con; // declarando um objeto com qualquer nome, faz conexão com o BD;
      public Statement stmt; // faz referencia com a classe conexão;
      public ResultSet rs; // trabalhar com os dados das tabelas do BD;
      String usuario = dados.getUsuarioBanco().toString(); // user é root;
      String senha= dados.getSenhaBanco(); // senha do BD;
      String url="jdbc:mysql://"+dados.getEnderecoBancoDeDados()+"/" + dados.getNomeBanco(); // uma string própria de cada bando de dados;
                                            //nome da database 'javajdbc';
  
  public Conexao_crm(){  // construtor
      System.out.println(usuario);
      System.out.println(senha);
      System.out.println(url);
      conectar(); // vai chamar a função conectar;
  }
  public void conectar()
    {
        try{
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Driver OK!!!");

                con = DriverManager.getConnection(url,usuario,senha);
                System.out.println("Conexao OK!!!");
        }//try
                catch(ClassNotFoundException exc)
        {
            System.out.println("Erro no driver " + exc.getMessage());
        }//catch 1
                catch(SQLException exc)
        {
            System.out.println("Erro de conexao =" + exc.getMessage());
        }//catch 2

    }//void

    public void Fechar_Conexao()
    {
        try
        {
            con.close();
        }
        catch(SQLException exc)
        {
            System.out.println(exc.getMessage());
        }
    }
}
