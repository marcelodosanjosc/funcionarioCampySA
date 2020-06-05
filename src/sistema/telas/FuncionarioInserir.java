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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;

/**
 *
 * @author marcelo
 */
public class FuncionarioInserir extends JPanel{
    
    JLabel labelTitulo, labelNome, labelSobreno, labelDataNascimento,labelEmail;
    JLabel labelCargo, labelSalario;
    JTextField campoNome, campoSobreno, campoEmail;
    JFormattedTextField campoDataNascimento, campoSalario;
    JComboBox<Cargo> comboxCargo;
    JButton botaoGravar;

    public FuncionarioInserir() {
        criarComponentes();
        criarEvento();
    }
    
    
    
    
    private void criarComponentes(){
        setLayout(null);
        
        
        labelTitulo = new JLabel("Cadastro de Funcionario", JLabel.CENTER);
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
        botaoGravar = new JButton("Adicionar");
        
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
        
        
        sqlCarregarCargo();
        
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
            
                
                sqlInserirFuncionario(novoFuncionario);
            }
        });
    }
    
    private void sqlCarregarCargo(){
        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user, BancoDeDados.password);
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("select * from cargo order by nome asc");
            comboxCargo.removeAll();
            while(resultados.next()){
                Cargo cargo  = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome"));
                comboxCargo.addItem(cargo);
            }
            comboxCargo.updateUI();
            
            conexao.close();
           
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    
    private void sqlInserirFuncionario(Funcionario funcionario){
        if(campoNome.getText().length() <=3){
            JOptionPane.showMessageDialog(null, "por favor, preenccha o nome correntamente");
            return;
        }
        if(campoSobreno.getText().length() <=3){
            JOptionPane.showMessageDialog(null, "por favor, preenccha o sobrenome correntamente");
            return;
        }
        if (Double.parseDouble(campoSalario.getText().replace(",", "."))<=100){
            JOptionPane.showMessageDialog(null, "por favor, preenccha o salario correntamente");
            return;
        }
        Boolean emailValidado = false;
        String ePattern = "^([\\w\\-]+\\.)*[\\w\\- ]+@([\\w\\- ]+\\.)+([\\w\\-]{2,3})$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(campoEmail.getText());
        emailValidado = m.matches();
        
        if (!emailValidado){
            JOptionPane.showMessageDialog(null, "por favor, preenccha o mail correntamente");
            return;
        }
        
        Connection conexao;
        
        PreparedStatement instrucaoSQL;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.user, BancoDeDados.password);
            String template = "INSERT INTO public.funcionarios( nome, sobrenome, dataNascimento, email, cargo, salario)";
            template = template+"VALUES ( ?, ?, ?, ?, ?, ?)";
            instrucaoSQL = conexao.prepareStatement(template);
            instrucaoSQL.setString(1, funcionario.getNome());
            instrucaoSQL.setString(2, funcionario.getSobrenome());
            instrucaoSQL.setString(3, funcionario.getDataNascimento());
            instrucaoSQL.setString(4, funcionario.getEmail());
            if(funcionario.getCargo().getId() > 0){
                instrucaoSQL.setInt(5, funcionario.getCargo().getId());
            }else {
                instrucaoSQL.setNull(5, java.sql.Types.INTEGER);
            }
            instrucaoSQL.setString(6, Double.toString(funcionario.getSalario()));
            instrucaoSQL.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionario adicionado com sucesso!");
            Navegador.inicio();
            conexao.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicona o funcionario");
            Logger.getLogger(FuncionarioInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
