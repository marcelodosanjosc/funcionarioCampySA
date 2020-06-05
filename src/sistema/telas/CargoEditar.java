/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.BancoDeDados;
import sistema.entidades.Cargo;

/**
 *
 * @author marcelo
 */
public class CargoEditar extends JPanel{
     
     Cargo cargoAtual;
     JLabel labelTitulo, labelCargo;
     JTextField campoCargo;
     JButton botaoGrava;

    public CargoEditar(Cargo cargo) {
        cargoAtual = cargo;
        criarComponentes();
        criaEventos();
    }
     
     private void criarComponentes(){
         setLayout(null);
         
         
         labelTitulo = new JLabel("Editar de Cargo", JLabel.CENTER);
         labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
         labelCargo = new JLabel("Nome do cargo",JLabel.LEFT);
         campoCargo = new JTextField(cargoAtual.getNome());
         botaoGrava = new JButton("Salvar");
         
         labelTitulo.setBounds(20, 20, 660, 40);
         labelCargo.setBounds(150, 120, 400, 20);
         campoCargo.setBounds(150, 140, 400, 40);
         botaoGrava.setBounds(250 , 380, 200, 40);
         
         add(labelTitulo);
         add(labelCargo);
         add(campoCargo);
         add(botaoGrava);
         
         setVisible(true);
     }
     
     private void criaEventos(){
         botaoGrava.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 cargoAtual.setNome(campoCargo.getText());
                 
                 sqlAtualizarCargo();
             }
         });
     }
     
     private void sqlAtualizarCargo(){
         if (campoCargo.getText().length() <=3){
             JOptionPane.showMessageDialog(null, "por favor, preencha o nome corretamente.");
             return;
         }
         
         Connection conexao;
            Statement instrucaoSQL;
             ResultSet resultados;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user ,BancoDeDados.password );
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            instrucaoSQL.executeUpdate("UPDATE cargo set name ="+campoCargo.getText()+ " WHERE id = "+cargoAtual.getId());
            JOptionPane.showMessageDialog(null, "Cargo atualizado com sucesso!");
            conexao.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
     }
}

