
package jdbc.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import jdbc.mvc.model.Filho_model;

public class Filho_CRUD 
{
    public static ArrayList<Filho_model> dados = new ArrayList<Filho_model>();
    
    
    public void inserir(Object o)
    {
        if (o instanceof Filho_model)
            {
                Filho_model c = (Filho_model) o;
                try{
                    
                    dados.add(c);

                }//try
                catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Adicionar Filhos", 0);
                }//catch
            }//if
    }//void
    
    public List busca()
    {
        List<Filho_model> lista = new ArrayList<Filho_model>();

        try {

                for (int i=0; i<dados.size();i++)
                {
 
                      Filho_model fm = new Filho_model();
                      fm.setNome(dados.get(i).getNome());
                      fm.setIdade(dados.get(i).getIdade());
                      
                      lista.add(fm);

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Adicionar Filhos", 0);
            }
        return lista;
    }
    
}
