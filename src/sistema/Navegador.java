/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicSliderUI;
import sistema.entidades.Cargo;
import sistema.telas.CargoConsultar;
import sistema.telas.CargoEditar;
import sistema.telas.CargoInserir;
import sistema.telas.FuncionarioConsultar;
import sistema.telas.FuncionarioInserir;
import sistema.telas.Inicio;
import sistema.telas.Login;

/**
 *
 * @author marcelo
 */
public class Navegador {
    //menu
    
    private static boolean menuConstruido;
    private static boolean menuHabilitado;
    private static JMenuBar menuBar;
    private static JMenu menuArquivo, menuFuncionarios, menuCargo, menuRelatorios;
    private static JMenuItem miSair, miFuncionarioConsultar, miFuncionarioCadastrar, miCargoConsultar;
    private static JMenuItem miCargosCadastrar, miRelatorioCargos, miRelatorioSalarios;
    
    
    public static void login(){
        Sistema.tela = new Login();
        Sistema.frame.setTitle("Funcionário Company SA");
        Navegador.atualizarTela();
        
    }
    public static void inicio(){
        Sistema.tela = new Inicio();
        Sistema.frame.setTitle("Funcionário Company SA");
        Navegador.atualizarTela();
    }
    public static void cargoCadastrar(){
        Sistema.tela = new CargoInserir();
        Sistema.frame.setTitle("Funcionário Company SA - Cadastra cargo");
        Navegador.atualizarTela();
    }
    public static void cargoConsultar(){
        Sistema.tela = new CargoConsultar();
        Sistema.frame.setTitle("Funcionário Company SA - Consultar Cargo");
        Navegador.atualizarTela();
    }
    public static void cargoEditar(Cargo cargo){
        Sistema.tela = new CargoEditar(cargo);
        Sistema.frame.setTitle("Funcionário Company SA - Editar cargo");
        Navegador.atualizarTela();
    }
    public static void funcionarioInserir(){
        Sistema.tela = new FuncionarioInserir();
        Sistema.frame.setTitle("Funcionário Company SA - Cadastra funcionario");
        Navegador.atualizarTela();
    }
    
    private static void funcionarioConsultar(){
        Sistema.tela = new FuncionarioConsultar();
        Sistema.frame.setTitle("Funcionário Company SA - cunsultar funcionario");
        Navegador.atualizarTela();
    }
    private static void atualizarTela(){
        Sistema.frame.getContentPane().removeAll();
        Sistema.tela.setVisible(true);
        Sistema.frame.add(Sistema.tela);
        
        Sistema.frame.setVisible(true);
        
    }
    
    private static void construirMenu(){
        if(!menuConstruido){
            menuConstruido = true;
            menuBar = new JMenuBar();
            
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);
            
            menuFuncionarios = new JMenu("Funcionário");
            menuBar.add(menuFuncionarios);
            miFuncionarioCadastrar = new JMenuItem("Cadastrar");
            menuFuncionarios.add(miFuncionarioCadastrar);
            miFuncionarioConsultar = new JMenuItem("Consultar");
            menuFuncionarios.add(miFuncionarioConsultar);
            
            menuCargo = new JMenu("Cargo");
            menuBar.add(menuCargo);
            miCargosCadastrar = new JMenuItem("Cadastrar");
            menuCargo.add(miCargosCadastrar);
            miCargoConsultar = new JMenuItem("Consultar");
            menuCargo.add(miCargoConsultar);
            
            menuRelatorios = new JMenu("Relatórios");
            menuBar.add(menuRelatorios);
            miRelatorioCargos = new JMenuItem("Funcionário por cargos");
            menuRelatorios.add(miRelatorioCargos);
            miRelatorioSalarios = new JMenuItem("Salários dos funcionários");
            menuRelatorios.add(miRelatorioSalarios);
            
            criarEventosMenu();
            
        }
    }
    
   
    public static void habilitaMenu(){
        if (!menuConstruido) construirMenu();
        if (!menuHabilitado){
            menuHabilitado = true;
            Sistema.frame.setJMenuBar(menuBar);
        }
    }
    public static void desbilitaMenu(){
        if(menuHabilitado){
            menuHabilitado = false;
            Sistema.frame.setJMenuBar(menuBar);
        }
    }
    
     private static void criarEventosMenu(){
        miSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        miFuncionarioCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionarioInserir();
            }
        });
        miFuncionarioConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionarioConsultar();
            }
        });
        miCargosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargoCadastrar();
            }
        });
        miCargoConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargoConsultar();
            }
        });
        miRelatorioCargos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           
            }
        });
        miRelatorioSalarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
    }
     
}
