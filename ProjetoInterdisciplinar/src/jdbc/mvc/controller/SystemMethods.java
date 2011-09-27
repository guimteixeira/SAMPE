/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.mvc.controller;

import com.mysql.jdbc.ResultSetMetaData;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.plaf.RootPaneUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import jdbc.mvc.model.ArquivoBk;
import jdbc.mvc.model.ConfiguracoesBd;

/**
 *
 * @author Guilherme
 */
public class SystemMethods {
    
    
    Toolkit tk = Toolkit.getDefaultToolkit(); 
    Dimension d=tk.getScreenSize();
    
    //método que retorna a largura da tela
    public int retornaLarguraTela(){
        return d.width;
    } // fim retorna largura da tela
    
    //método que retorna a altura da tela
    public int retornaAlturaTela(){
        return d.height;
    }
    // fim retorna altura da tela
    
    public void backupBd(JTextArea textoStatus, JProgressBar barraEstado, JProgressBar barraProcesso){
       
        //envia texto para quadro
        textoStatus.setText("Criar o arquivo de Backup...\n");
        //1% para barradeEstado
        barraEstado.setValue(1);
        //envia texto para barra do processo
        barraProcesso.setString("Processo em Andamento...");
        //anima indeterminadamento a barra do processo
        barraProcesso.setIndeterminate(true);
      
        //cria tela para salvar
        JFileChooser fileChooser = new JFileChooser();  
        
        //limita somente arquivo do tipo .bkc
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Backup do CRM (*.bkc)", "bkc");
        
        //proibido outras extensões
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        //adiciona somente o tipo .bkc
        fileChooser.setFileFilter(filter);  
        
        //defini titulo da janela de salvar
        fileChooser.setDialogTitle("Criar arquivo de Backup CRM");
        
                
        //chama a tela e retorna o que usuario fez
        int option = fileChooser.showSaveDialog(null); 
        
        //se criou o arquivo
        if (option == JFileChooser.APPROVE_OPTION) {  
            
            //abre conexão
            Conexao_crm c = new Conexao_crm();
            
            // cria Statement para recuperar as tabelas suas colunas e dados.
            PreparedStatement stmtTabela, stmtColunas, stmtDados;
            
            //instancia o modelo de Arquivo para BK
            ArquivoBk arquivo = new ArquivoBk();
            
            //adiciona titulo dentro do arquivo
            arquivo.setTitulo("Arquivo de Backup CRM Versão 1.02");
            
            //envia data de criação do arquivo
            arquivo.setData(new Date());
            
            //envia script de criação do BD sem autoincremento
            arquivo.setScriptBd("");
            
            //envia script para alterar tabelas para autoincremento
            arquivo.setConfiguraBd("");
            
            List<String> scriptInsert = new ArrayList();
            
            //escreve 100% na barra estado, pois acabou de criar arquivo
            barraEstado.setValue(100);
            
            //envia texto para quadro
            textoStatus.setText(textoStatus.getText() + "Arquivo criado...\nInício da geração de scripts de BK...\n");
       

            try {
                
                //lista todas as tabelas do BD
                stmtTabela = c.con.prepareStatement("Show tables");
                ResultSet rsTabelas = (ResultSet) stmtTabela.executeQuery(); 
                
                /*
                 * Cálculo do percentual do processo
                 * aqui pega-se o total de tabelas para calcular % de cada
                       */
                
                int totalTabelas = 0; 
                double percentual = 0;
                while(rsTabelas.next())
                             totalTabelas++; 
                
                percentual = (100/totalTabelas);
                
                //arredonda
                
                if(percentual/((int) percentual) > 0) 
                    percentual = percentual + 1;
                
                //apos contar e calcular vai para primeira tabelas
                rsTabelas.first();
                
                String comando = ""; // string para armazenar o camando que vai para o arquivos
                
                
                /*
                 *Aqui começa o código de criação dos scripts de inserção dos 
                 * dados no banco utilizando os dados do atual BD.
                 */
                
                /*
                 * Este WHILE acontece para garantir um INSERT para cada tabela
                 * existente no BD
                 */
                
                //envia texto para quadro
                textoStatus.setText(textoStatus.getText() + "Realizando leitura das tabelas do Banco de Dados...\n");
       
                // o -> contador de scripts criados
                //posicaoTabela -> qual tabela do total está presente
                
                
                int o = 0, posicaoTabela = 1;
                while(rsTabelas.next()) {

                    barraEstado.setValue((int) (percentual*posicaoTabela));
                    posicaoTabela++;
                    
                    //envia texto para quadro
                    textoStatus.setText(textoStatus.getText() + "Preparando script para a tabela: " + rsTabelas.getString(1) + "\n");
       
                       comando =  "INSERT INTO " + rsTabelas.getString(1) + " (";

                       /*Faz a leitura das caracteríscas da atual tabela, assim 
                        recuperando cada coluna*/
                       stmtColunas = c.con.prepareStatement("Desc " + rsTabelas.getString(1));
                       ResultSet rsColunas = (ResultSet) stmtColunas.executeQuery();
                       
                       
                       //para cada coluna na tabela escreve o rótulo dela
                       while(rsColunas.next()) {
                           comando = comando + rsColunas.getString(1);
                           if (!rsColunas.isLast()){
                                comando = comando + ", ";
                       } 
                       } // fim while coluna
                       
                       // termina a escrita das colunas
                        comando = comando + ") VALUES ";

                       //faz um select da atual tabela para recuperar seus dados
                       stmtDados = c.con.prepareStatement("Select * from "  + rsTabelas.getString(1));
                       ResultSet rsDados = (ResultSet) stmtDados.executeQuery();
                       
                       //variavel rsmd para recuperar informações de cada coluna
                       ResultSetMetaData rsmd = (ResultSetMetaData) rsDados.getMetaData(); 

                       String tipoColuna, separador; // tipoColuna para recuperar o tipo e separador para armazenar o separador do script
                       boolean vazio = true; //vazio para saber se existe algum dado na tabela ou nao, inicialmente acredita-se que não haja
                       
                       //looping para todos os dados na tabela
                       while(rsDados.next()) {
                           
                           
                           vazio = false; // quer dizer que tem dado na tabela
                           
                           //para cada coluna da tabela recupera seu dado
                           for(int i = 1; i <= rsmd.getColumnCount(); i++){

                               tipoColuna = rsmd.getColumnTypeName(i);


                               //faz o separador para cada tipo de coluna
                               if(tipoColuna.equals("VARCHAR") || tipoColuna.equals("DATE") 
                                  || tipoColuna.equals("TIME") || tipoColuna.equals("DATETIME")
                                  || tipoColuna.equals("LONGTEXT")) {
                                   separador = "\"";
                               } else {

                                   if(tipoColuna.equals("INT") || tipoColuna.equals("FLOAT") || 
                                      tipoColuna.equals("DOUBLE")) {
                                       separador = "";
                                   } else {
                                    if(tipoColuna.equals("CHAR")) {
                                       separador = "'";
                                   } else {
                                           // se for um tipo de coluna nao listada no IFs acima sai do processo
                                           JOptionPane.showMessageDialog(null, "Foi encontrado um tipo de "
                                                   + "coluna não documentada. O BACKUP "
                                                   + "será finalizado.\n Tipo coluna: " + tipoColuna, "Backup do Sistema",
                                                   JOptionPane.ERROR_MESSAGE);

                                            //volta as barras ao normal
                                            barraEstado.setValue(0);
                                            barraProcesso.setString("Aguardando início do processo...");
                                            barraProcesso.setIndeterminate(false);
                                            //exibi mensagem no Text
                                            textoStatus.setText(textoStatus.getText() + "Processo não concluído...");
                                            

                                            return;
                                       }
                                   }
                               }

                               //fim do faz o separador
                               
                               //se é a primeira coluna abre o parenteses que vem após "VALUES"
                               if(i == 1) {
                                  comando = comando + "(";
                                           }
                               
                               //escreve o dado com seu separador
                              comando = comando + separador + rsDados.getString(i) + separador;

                              //se não estiver na ultima coluna do registro escreve uma vírgula
                               if(i != rsmd.getColumnCount()) {
                                   comando = comando + ", ";
                               }


                           }//fim for do select;
                           
                           //se não for o ultimo registro do select fecha o registro do Insert com ","
                           if(!rsDados.isLast()) {
                                   comando = comando + "), ";
                                           }
                           else {
                                  comando = comando + ")";
                                           }

                       }//fim dos Dados Select
                       
                       //";" para fechar comando
                       comando = comando + ";";
                       
                       //se a tabela não estiver vazio escreve na classe o comando gerado
                      if(!vazio) {
                           scriptInsert.add(comando);
                           System.out.println(scriptInsert.get(o));
                           o++;
                           textoStatus.setText(textoStatus.getText() +"    Escrevendo script de recuperação de dados.\n");
                           textoStatus.setText(textoStatus.getText() +"    Total de registros processados: " + o + "\n");
                      } else {
                           textoStatus.setText(textoStatus.getText() +"    Tabela vazia.\n");
                      }
                      
                      comando = "";
                }// fim WHILE acontece para garantir um INSERT para cada tabela existente no BD

            textoStatus.setText(textoStatus.getText() +"Processo finalizado com sucesso!"); 
            
            c.Fechar_Conexao();
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERRO NA "
                        + "LEITURA DO BANCO!\n Erro: " + ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
            }
            catch(NullPointerException exc){
                //devido a erro na coexao
            System.out.println(exc.getMessage());
            
            textoStatus.setText("Reinicie o processo!");  
            barraEstado.setValue(0); 
            barraProcesso.setString("Aguardando início do processo..."); 
            barraProcesso.setIndeterminate(false);
            
            return;
        }
            
            //envia todos scripts de Insert para classe arquivobk
            arquivo.setScriptInsert(scriptInsert);

       
            File file = fileChooser.getSelectedFile();
             
            if(file.exists()) {
                System.out.println("Arquivo existe.");
                String[] botoes = {"Sim","Não"};  //botoes a substituir o JOptionPane.YES_NO_OPTION
        
                //tratar sobreescrever arquivo
                 if (JOptionPane.showOptionDialog(fileChooser,"O arquivo já existe. Deseja sobrescrevê-lo?","Criar arquivo de Backup",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,null, botoes, botoes[1]) == 1) {
                        backupBd(textoStatus, barraEstado, barraProcesso);
                        return;
                 }
            }//fim da verificação se o arquivo criado existe
            
            //trata a extensão do arquivo
             String ext[] = file.getName().split("\\.");   
             int i = ext.length;  
             System.out.println(i);
             if(i <= 1) {//não tem extensão
                 file = new File(file.getPath()+".bkc");
            }
            
            
            System.out.println("Nome do arquivo: " + file.getName());
            
            //insere dados no arquivo criado pelo usuário
            try { 
                ObjectOutputStream os = new ObjectOutputStream( // cria um objeto de saida para o arquivo
                        new FileOutputStream(file));
                os.writeObject(arquivo); // escreve a variavel configs do tipo ConfiguracoesBd no arquivo
                System.out.println("Gravou arquivo de BK.");
                os.close();
                arquivo = null;
            }catch(IOException ex){
               ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO NA "
                        + "GRAVAÇÃO",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Não gravou arquivo de BK");
            }// fim try-catch
                 
        } // fim if de salvar arquivo
         else{
            textoStatus.setText("Cancelado pelo usuário.");  
            barraEstado.setValue(0); 
            barraProcesso.setString("Aguardando início do processo..."); 
            barraProcesso.setIndeterminate(false);
            return;
            
            
    }
        // ao terminar volta as barras ao seu estado inicial
        
       barraProcesso.setString("Processo finalizado..."); 
       barraProcesso.setIndeterminate(false);
       
    }
    
    //le bk 
    
    public void restauraBd(JTextArea textoStatus, JProgressBar barraEstado, JProgressBar barraProcesso){
        
        //envia texto para quadro
        textoStatus.setText("Ler o arquivo de Backup...\n");
        //1% para barradeEstado
        barraEstado.setValue(1);
        //envia texto para barra do processo
        barraProcesso.setString("Processo em Andamento...");
        //anima indeterminadamento a barra do processo
        barraProcesso.setIndeterminate(true);
      
        //cria tela para salvar
        JFileChooser fileChooser = new JFileChooser();  
        
        //limita somente arquivo do tipo .bkc
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Backup do CRM (*.bkc)", "bkc");
        
        //proibido outras extensões
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        //adiciona somente o tipo .bkc
        fileChooser.setFileFilter(filter);  
        
        //defini titulo da janela de salvar
        fileChooser.setDialogTitle("Ler arquivo de Backup CRM");
        
                
        //chama a tela e retorna o que usuario fez
        int option = fileChooser.showOpenDialog(null); 
        
        //se criou o arquivo
        if (option == JFileChooser.APPROVE_OPTION) {
            
            //instancia o modelo de Arquivo para BK
            ArquivoBk arquivo = new ArquivoBk();
            //escreve 100% na barra estado, pois acabou de criar arquivo
            barraEstado.setValue(100);
            
            //instancia o novo arquivo
            File file = fileChooser.getSelectedFile();
            
            //amarra extensao
            String ext[] = file.getName().split("\\.");   
             int i = ext.length;  
             System.out.println(i);
             if(i <= 1) {//não tem extensão
                 file = new File(file.getPath()+".bkc");
            }
            
            //verifica se o arquivo existe
            if(file.exists()) {
                System.out.println("Arquivo existe. " + file.getPath());
                textoStatus.setText(textoStatus.getText() + "Arquivo lido...\nInício da leitura de scripts de BK...\n");
                
                //faz leitura do arquivo
                ObjectInputStream is = null;
                
                //tenta criar a leitura do arquivo
                try {
                    is = new ObjectInputStream(new FileInputStream(file.getPath()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(fileChooser, "Arquivo inválido!","Recuperar arquivo de Backup",JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                } 
                
                //tenta setar o arquivo na classe arquivoBK na instancia arquivo
                try {
                    arquivo = (ArquivoBk) is.readObject();
                    
                    System.out.println(arquivo.getTitulo());
                    System.out.println(arquivo.getData());
                    for(int y = 0; y < arquivo.getScriptInsert().size(); y++)
                        System.out.println(arquivo.getScriptInsert().get(y));
                    
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(fileChooser, "Erro ao ler o arquivo!","Recuperar arquivo de Backup",JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(fileChooser, "Arquivo inválido!","Recuperar arquivo de Backup",JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
                
            }
            else {
                //se arquivo não existe reinicia o processo
                JOptionPane.showMessageDialog(fileChooser, "Arquivo inexistente!", "Recuperar arquivo de Backup", JOptionPane.INFORMATION_MESSAGE);
                restauraBd(textoStatus,barraEstado,barraProcesso);
                return;
            }//fim da verificação se arquivo criado não existe
            
        }//fim da criação do arquivo
        else{
            textoStatus.setText("Cancelado pelo usuário.");  
            barraEstado.setValue(0); 
            barraProcesso.setString("Aguardando início do processo..."); 
            barraProcesso.setIndeterminate(false);
            return;
    }//fim de quando usuário cancela
        
        // ao terminar volta as barras ao seu estado inicial
       barraProcesso.setIndeterminate(false);
       barraProcesso.setString("Processo finalizado..."); 
       
 
    }//fim do recupera BK
    
    //retorna data do sistema
    public String getData(String formato) {
       DateFormat dataform = new SimpleDateFormat(formato);
       Date x = new Date();
       return  dataform.format(x);
    }
    
    //testa conexao com o BD
    public boolean testeConexao() {
      
       try{
           
            ConfiguracoesBd_CRUD configs = new   ConfiguracoesBd_CRUD();
            ConfiguracoesBd dados = configs.leDllBd();
      
      
    
                Connection con; // declarando um objeto com qualquer nome, faz conexão com o BD;
                String usuario = dados.getUsuarioBanco().toString(); // user é root;
                String senha= dados.getSenhaBanco(); // senha do BD;
                String url="jdbc:mysql://"+dados.getEnderecoBancoDeDados()+"/" + dados.getNomeBanco(); // uma string própria de cada bando de dados;
           
                Class.forName("com.mysql.jdbc.Driver");
                //System.out.println("Driver OK!!!");

                con = DriverManager.getConnection(url,usuario,senha);
                //System.out.println("Conexao OK!!!");
                
                return true;
                
        }//try
                catch(ClassNotFoundException exc)
        {
                return false;
            
        }//catch 1
                catch(SQLException exc)
        {
                return false;
        }//catch 2
      
      
      
  }
}
