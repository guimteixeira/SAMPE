/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.mvc.controller;

import java.io.*;
import javax.swing.JOptionPane;
import jdbc.mvc.model.ConfiguracoesSmtp;

/**
 *
 * @author Guilherme
 */
public class ConfiguracoesSmtp_CRUD {
    
    public void gravaConfiguracaoSmtp(ConfiguracoesSmtp config){
        
        System.out.println("Acesso o método gravaConfiguracaoSmtp(ConfiguracoesSmtp config): ");
        
        try {

                File file = new File("configSMTP.dll");
                file.delete();

                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("configSMTP.dll"));
                os.writeObject(config);
                System.out.println("Gravou o DLL.");
                os.close();
                
              
            
        }catch(IOException ex){
           ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO NA GRAVAÇÃO",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Não gravou o DLL.");
        }
        
    }//fim gravaconfiguracao SMTP
    
    //le configuracao SMTP
    public ConfiguracoesSmtp leConfiguracaoSmtp() {
        
        File file = new File("configSMTP.dll");
        ConfiguracoesSmtp config = new ConfiguracoesSmtp();
        
        config.setEndereco("smtp.dominio");
        config.setPorta(25);
        config.setUsuario("usuario");
        config.setSenha("   ");
        
        if(file.exists()) {
            try{
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream("configSMTP.dll"));
                    config = (ConfiguracoesSmtp) is.readObject();
                     return config;
                
            }catch(IOException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO",JOptionPane.INFORMATION_MESSAGE);
                 return config;
        
               } catch(ClassNotFoundException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO",JOptionPane.INFORMATION_MESSAGE);
                 return config;
            }
        }
        
        return config;
    }
    
}
