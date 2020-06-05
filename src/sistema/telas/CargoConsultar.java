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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;

/**
 *
 * @author marcelo
 */
public class CargoConsultar extends JPanel{
    
    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
     DefaultListModel<Cargo> listasCargosModelo = new DefaultListModel<>();
    JList<Cargo> listaCargo;

    public CargoConsultar() {
        criarComponentes();
        criarEventos();
    }
    
     private void criarComponentes(){
         setLayout(null);
         
         labelTitulo = new JLabel("Consulta de Cargo",JLabel.CENTER);
         labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
         labelCargo = new JLabel("Nome do cargo",JLabel.LEFT);
         campoCargo = new JTextField();
         botaoPesquisar = new JButton("Pesquisar Cargo");
         botaoEditar = new JButton("Editar Cargo");
         botaoEditar.setEnabled(false);
         botaoExcluir = new JButton("Excluir Cargo");
         botaoExcluir.setEnabled(false);
         listasCargosModelo = new DefaultListModel();
         listaCargo = new JList<>();
         listaCargo.setModel(listasCargosModelo);
         listaCargo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
         
         labelTitulo.setBounds(20, 20, 660, 40);
         labelCargo.setBounds(150, 120, 400, 20);
         campoCargo.setBounds(150, 140, 400, 40);
         botaoPesquisar.setBounds(560, 140, 130, 40);
         listaCargo.setBounds(150, 200, 400, 240);
         botaoEditar.setBounds(560, 360, 130, 40);
         botaoExcluir.setBounds(560, 400, 130, 40);
         
         add(labelTitulo);
         add(labelCargo);
         add(campoCargo);
         add(listaCargo);
         add(botaoPesquisar);
         add(botaoEditar);
         add(botaoExcluir);
         
         
         
         setVisible(true);
     }
     private void criarEventos(){
         botaoPesquisar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 sqlPesquisarCargos(campoCargo.getText());
             }
         });
         
         botaoEditar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Navegador.cargoEditar(cargoAtual);
             }
         });
         botaoExcluir.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 sqlDeletarCargo();
             }
         });
         listaCargo.addListSelectionListener(new ListSelectionListener() {
             @Override
             public void valueChanged(ListSelectionEvent e) {
                 cargoAtual = listaCargo.getPrototypeCellValue();
                 if(cargoAtual == null){
                     botaoEditar.setEnabled(false);
                     botaoExcluir.setEnabled(false);
                 }else{
                     botaoEditar.setEnabled(true);
                     botaoExcluir.setEnabled(true);
                 }
             }
         });
         
         
     }
     
     private void sqlPesquisarCargos(String nome){
             Connection conexao;
             Statement instrucaoSQL;
             ResultSet resultados;
        
        try {
                 try {
                     Class.forName("org.postgresql.Driver");
                 } catch (ClassNotFoundException ex) {
                     Logger.getLogger(CargoConsultar.class.getName()).log(Level.SEVERE, null, ex);
                 }
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user ,BancoDeDados.password );
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargo WHERE nome = "+nome);
            listasCargosModelo.clear();
            
            while (resultados.next()){
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome"));
                
                listasCargosModelo.addElement(cargo);
            }
            
     }catch (SQLException e) {
         e.printStackTrace();
     }
        
     }
     
     private void sqlDeletarCargo(){
         int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o Cargo"+
                 ""+cargoAtual.getNome() + "?"," Excluir",JOptionPane.YES_NO_OPTION);
         if(confirmacao == JOptionPane.YES_OPTION){
            Connection conexao;
            Statement instrucaoSQL;
             ResultSet resultados;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user ,BancoDeDados.password );
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); 
            instrucaoSQL.executeUpdate("DELETE cargo WHWRE id = "+cargoAtual.getId());
            JOptionPane.showMessageDialog(null, "Cargo deletado com sucesso!");
         }catch (SQLException e) {
             e.printStackTrace();
         }
     }
     }
}
