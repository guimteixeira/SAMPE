package jdbc.mvc.view;


import com.sun.awt.*;
import com.sun.org.apache.xalan.internal.xsltc.dom.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.util.logging.*;
import javax.swing.*;

import jdbc.mvc.controller.ConfiguracoesBd_CRUD;
import jdbc.mvc.controller.SystemMethods;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Form.java
 *
 * Created on 10/09/2011, 16:27:34
 */
/**
 *
 * @author Guilherme
 */
public class SplashScreen extends javax.swing.JFrame {

    SystemMethods sistema = new SystemMethods();
    /** Creates new form Form */
    public SplashScreen() {
        initComponents();
        
        //alinha a janela um pouco acima do centro
        int x = (sistema.retornaLarguraTela()/2) - 400;
        int y =  (sistema.retornaAlturaTela()/2) - 230;
        
        this.setLocation(x, y);
       
        setTransparente(this);
        setAlpha(this, 0.0f);
    }
    
  void setTransparente(JFrame window) {
    AWTUtilities.setWindowOpaque(window, false);
    window.getRootPane().setOpaque(false);
    }
     
    void setAlpha(JFrame window, float alpha) {
    // cover for temporary API expected to change for Java 7
    // should become: window.setOpacity(alpha);
    AWTUtilities.setWindowOpacity(window, alpha);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoStatus = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(128, 0, 0));
        setResizable(false);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textoStatus.setFont(new java.awt.Font("Arial", 0, 11));
        textoStatus.setForeground(new java.awt.Color(255, 255, 255));
        textoStatus.setText("Carregando o sistema...");
        getContentPane().add(textoStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setForeground(new java.awt.Color(128, 0, 0));
        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setIndeterminate(true);
        getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 209, 550, 3));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jdbc/mvc/view/images/splash.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-550)/2, (screenSize.height-300)/2, 550, 300);
    }// </editor-fold>//GEN-END:initComponents

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
    
        
        float i = 0.01f;
        
        while(i <= 1.0f) {
            setTransparente(this);
            setAlpha(this, i);
            i = i + 0.001f;
        }   
        //verificar as configurações básicas BD
        
        
        ConfiguracoesBd_CRUD configsBd = new ConfiguracoesBd_CRUD();
        
        textoStatus.setText("Verificando as configurações básicas...");
        if(!configsBd.verificaBdDll()){
            textoStatus.setText("Agurdando informações para conexão com o Banco de Dados...");
            JOptionPane.showMessageDialog(null, "É necessário definir as configurações do Banco de Dados!", "Configuração do Sistema", JOptionPane.WARNING_MESSAGE);
            
            //instancia telaConfiguracao com TelaConfiguracoesBd
            //tela configuracao é uma JDialog logo no construtor enviamos este JFrame como pai e definimos modal como TRUE
            TelaConfiguracoesBd telaConfiguracao = new TelaConfiguracoesBd(this, true);
            telaConfiguracao.setVisible(true);
        } // verifica configuração básicas de BD
        
            
        //abre tela de login
        textoStatus.setText("Aguardando login do usuário...");
        Login tela = new Login(this, true);
        tela.setVisible(true);
        
        //tenta o login, se login ok só fechar Splash senão fecha o sistema
        
        this.dispose();
        
        
}//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
               /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new SplashScreen().setVisible(true);
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel textoStatus;
    // End of variables declaration//GEN-END:variables
}