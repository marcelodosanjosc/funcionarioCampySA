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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import sistema.BancoDeDados;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;

/**
 *
 * @author marcelo
 */
public class FuncionarioEditar extends JPanel{
    Funcionario funcionarioAtual;
    Cargo cargoAtual;
    
    JLabel labelTitulo, labelNome, labelSobreno, labelDataNascimento,labelEmail;
    JLabel labelCargo, labelSalario;
    JTextField campoNome, campoSobreno, campoEmail;
    JFormattedTextField campoDataNascimento, campoSalario;
    JComboBox<Cargo> comboxCargo;
    JButton botaoGravar;

    public FuncionarioEditar() {
        
        criarComponentes();
        criarEvento();
    }
    private void criarComponentes(){
        setLayout(null);
        
        String textoLabel = "Editar funcionario "+funcionarioAtual.getNome()+ " "+funcionarioAtual.getSobrenome();
        labelTitulo = new JLabel(textoLabel, JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(),Font.PLAIN,20));
        labelNome = new JLabel("Nome",JLabel.LEFT);
        campoNome = new JTextField();
        labelSobreno = new JLabel("Sobrenome",JLabel.LEFT);
        campoSobreno = new JTextField();
        labelDataNascimento = new JLabel("Data de nascimento: ",JLabel.LEFT);
        campoDataNascimento = new JFormattedTextField();
        
        MaskFormatter dateMask;
        try {
            dateMask = new MaskFormatter("##/##/####");
            dateMask.install(campoDataNascimento);
            
        } catch (ParseException ex) {
            Logger.getLogger(FuncionarioInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelEmail = new JLabel("E-mail",JLabel.LEFT);
        campoEmail = new JTextField();
        labelCargo = new JLabel("Cargo",JLabel.LEFT);
        comboxCargo = new JComboBox<>();
        labelSalario = new JLabel("Salário",JLabel.LEFT);
        DecimalFormat formater = new DecimalFormat("###0.00",new DecimalFormatSymbols(new Locale("pt","BR")));
        campoSalario = new JFormattedTextField(formater);
        campoSalario.setValue(0.00);
        botaoGravar = new JButton("Salvar");
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelNome.setBounds(150, 80, 400, 20);
        campoNome.setBounds(150, 100, 400, 40);
        labelSobreno.setBounds(150, 140, 400, 20);
        campoSobreno.setBounds(150, 160, 400, 40);
        labelDataNascimento.setBounds(150, 200, 400, 20);
        campoDataNascimento.setBounds(150, 220, 400, 40);
        labelEmail.setBounds(150, 260, 400, 20);
        campoEmail.setBounds(150, 280, 400, 40);
        labelCargo.setBounds(150, 320, 400, 20);
        comboxCargo.setBounds(150, 340, 400, 40);
        labelSalario.setBounds(150, 380, 400, 20);
        campoSalario.setBounds(150, 400, 400, 40);
        botaoGravar.setBounds(560, 400, 130, 40);
        
        add(labelTitulo);
        add(labelNome);
        add(campoNome);
        add(labelSobreno);
        add(campoSobreno);
        add(labelDataNascimento);
        add(campoDataNascimento);
        add(labelEmail);
        add(campoEmail);
        add(labelCargo);
        add(comboxCargo);
        add(labelSalario);
        add(campoSalario);
        add(botaoGravar);
        
        
        //sqlCarregarCargo();
        
        setVisible(true);
    }
      private void criarEvento(){
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setNome(campoNome.getText());
                novoFuncionario.setSobrenome(campoSobreno.getText());
                novoFuncionario.setDataNascimento(campoDataNascimento.getText());
                novoFuncionario.setEmail(campoEmail.getText());
                Cargo cargoSelecionado = (Cargo) comboxCargo.getSelectedItem();
                if (cargoSelecionado != null) novoFuncionario.setCargo(cargoSelecionado);
                    
                novoFuncionario.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));
            
                
                //sqlInserirFuncionario(novoFuncionario);
            }
        });
    }
      private void sqlPesquisarCargos(){
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
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargo order by nome = asc");
            comboxCargo.removeAll();
            
            while (resultados.next()){
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome"));
                comboxCargo.addItem(cargo);
                
              if (cargoAtual == cargo.getId()) comboxCargo.setSelectedItem(cargo);
            }
            
     }catch (SQLException e) {
         e.printStackTrace();
     }
        
     }
}
