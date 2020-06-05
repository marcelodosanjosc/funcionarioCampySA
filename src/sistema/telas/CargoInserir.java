
package sistema.telas;

import com.sun.istack.internal.logging.Logger;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.BancoDeDados;
import sistema.entidades.Cargo;


public class CargoInserir extends JPanel{

    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoGravar;
    
    public CargoInserir() {
        criarComponentes();
        criarEventos();
    }
    
    private void criarComponentes(){
        setLayout(null);
        
        labelTitulo = new JLabel("Cadastro de Cargo");
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
        campoCargo = new JTextField();
        botaoGravar = new JButton("Adicionar Cargo");
        
       labelTitulo.setBounds(20, 20, 660, 40);
       labelCargo.setBounds(150, 120, 400, 20);
       campoCargo.setBounds(150, 140, 400, 40);
       botaoGravar.setBounds(250, 380, 200, 40);
       
        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(botaoGravar);
        
        setVisible(true);
    }
    private void criarEventos(){
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Cargo novoCargo = new Cargo();
                novoCargo.setNome(campoCargo.getText());
                
                
                sqlInserirCargo(novoCargo);
                
            }
        });
    }
    
    private void sqlInserirCargo(Cargo novoCargo){
        if (campoCargo.getText().length() <= 3){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente. ");
            return;
        }
        
        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;
        
        try {
            
            Class.forName("org.postgresql.Driver");
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user ,BancoDeDados.password );
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            instrucaoSQL.executeUpdate("INSERT INTO cargo (nome) VALUES ("+novoCargo.getNome()+")");
            JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            
        } catch (Exception e2){
            e2.printStackTrace();
        }
        
    }
    
}
