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
import sistema.entidades.Funcionario;

/**
 *
 * @author marcelo
 */
public class FuncionarioConsultar extends JPanel{
    Funcionario funcionarioAtual;
    JLabel labelTitulo, labelFuncionario;
    JTextField campoFuncionario;
    JButton boataoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Funcionario> listaFuncionarioModelo = new DefaultListModel<>();
    JList<Funcionario> listaFuncionario;

    public FuncionarioConsultar() {
        criarComponentes();
        criarEventos();
        
    }
    
    private void criarComponentes(){
        setLayout(null);
        
        labelTitulo = new JLabel("Consulta de Funcionario",JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN,20));
        labelFuncionario = new JLabel("Nome do funcionario",JLabel.LEFT);
        campoFuncionario = new JTextField();
        boataoPesquisar = new JButton("Pesquisar funcionario");
        botaoEditar = new JButton("Editar funcionario");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir funcionario");
        botaoExcluir.setEnabled(false);
        listaFuncionarioModelo = new DefaultListModel();
        listaFuncionario = new JList();
        listaFuncionario.setModel(listaFuncionarioModelo);
        listaFuncionario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelFuncionario.setBounds(150, 120, 400, 20);
        campoFuncionario.setBounds(150, 140, 400, 40);
        boataoPesquisar.setBounds(560, 140, 130, 40);
        listaFuncionario.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40);
        botaoExcluir.setBounds(560, 400, 130, 40);
        
        
        add(labelTitulo);
        add(labelFuncionario);
        add(campoFuncionario);
        add(listaFuncionario);
        add(boataoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        
        setVisible(true);
    }
    private void criarEventos(){
        boataoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarFuncionarios(campoFuncionario.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // cria tela funcionarioEditar
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarFuncionario();
            }
        });
        listaFuncionario.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                funcionarioAtual = listaFuncionario.getSelectedValue();
                if(funcionarioAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else {
                     botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
        
    }
    
    private void sqlPesquisarFuncionarios(String nome){
        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user, BancoDeDados.password);
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("select * from funcionarios where nome like '%"+nome+"%' order  by nome ASC");
            listaFuncionarioModelo.clear();
            while (resultados.next()){
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultados.getInt("id"));
                funcionario.setNome(resultados.getString("nome"));
                funcionario.setSobrenome(resultados.getString("sobrenome"));
                funcionario.setDataNascimento(resultados.getString("dataNascimento"));
                funcionario.setEmail(resultados.getString("email"));
                if(resultados.getString("cargo") != null) funcionario.setCargo(Cargo.class.cast(resultados.getString("cargo")));
                funcionario.setSalario(Double.parseDouble(resultados.getString("salario")));
                listaFuncionarioModelo.addElement(funcionario);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(FuncionarioConsultar.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    private void sqlDeletarFuncionario(){
        String pergunta = "Deseja realmente excluir o funcionario "+funcionarioAtual.getNome() +"?";
        int confirmacao = JOptionPane.showConfirmDialog(null, pergunta, "Excluir",JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION){
            Connection conexao;
            Statement instrucaoSQL;
            ResultSet resultados;
            
            try {
                
                conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user, BancoDeDados.password);
                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("delete funcionarios where id="+funcionarioAtual.getId());
                JOptionPane.showMessageDialog(null, "Funcionario deletado com sucesso!");
                Navegador.inicio();
                        
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(FuncionarioConsultar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
