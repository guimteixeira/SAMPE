/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.mvc.controller;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import jdbc.mvc.model.ConfiguracoesBd;

/**
 *
 * @author Guilherme
 */
public class ConfiguracoesBd_CRUD {
    
    private String arquivo = "configBD.dll";    
    private File arquivoConfiguracoes = new File(arquivo); //variavel File com o arquivo da conexão
    
    //método para verificação se o arquivo existe
    public boolean verificaBdDll(){
        
        if (arquivoConfiguracoes.exists()) {
            return true;
        } else {
            return false;
        }
    }
    // -- fim do método de verificação de existencia do araquivo
    
    // método para escrever na DLL dados da conexão com o banco
    public void escreveDllBd(ConfiguracoesBd configs) {
        
        System.out.println("Acesso ao método escreveDllBd(): ");
        
        //emiti mensangem para confirmar a gravação
       
            
            try { 
                    if(verificaBdDll())
                        arquivoConfiguracoes.delete(); // exclui arquivo para criação de um novo
                    
                        ObjectOutputStream os = new ObjectOutputStream( // cria um objeto de saida para o arquivo
                        new FileOutputStream(arquivo));
                        os.writeObject(configs); // escreve a variavel configs do tipo ConfiguracoesBd no arquivo
                        System.out.println("Gravou o DLL.");
                        os.close();
                        configs = null;
            }catch(IOException ex){
               ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO NA "
                        + "GRAVAÇÃO",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Não gravou o DLL.");
            }// fim try-catch
       
    }
    // -- fim do método para escrever informações de conexão em um arquivo dll
    
    // método para ler na DLL dados da conexão com o banco
    public ConfiguracoesBd leDllBd(){
        
        ConfiguracoesBd dadosBasicos = new ConfiguracoesBd();
        dadosBasicos.setEnderecoBancoDeDados("localhost");
        dadosBasicos.setNomeBanco("CRM");
        dadosBasicos.setUsuarioBanco("root");
        dadosBasicos.setSenhaBanco("root");
        
         ObjectInputStream is;
        try {
            is = new ObjectInputStream(new FileInputStream(arquivo));
            try {
                return (ConfiguracoesBd) is.readObject();
            } catch (ClassNotFoundException ex) {
                
                return dadosBasicos;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracoesBd_CRUD.class.getName()).log(Level.SEVERE, null, ex);
             return dadosBasicos;
        }
    }
}
