package gui;

import javax.swing.*;
import controle.ControleFinanceiro;
import modelo.Transacao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsultaTransacaoGUI extends JFrame {
    private ControleFinanceiro controleFinanceiro;
    private JList<Transacao> transacoesList;
    private DefaultListModel<Transacao> listModel;

    public ConsultaTransacaoGUI(ControleFinanceiro controleFinanceiro) {
        this.controleFinanceiro = controleFinanceiro;
        setTitle("Consulta de Transações");
        setSize(500, 400); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); 

        // painel para o título e texto da consulta
        JPanel painelConsulta = new JPanel();
        painelConsulta.setLayout(new BorderLayout());
        painelConsulta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        listModel = new DefaultListModel<>();
        for (Transacao transacao : controleFinanceiro.getTransacoes()) {
            listModel.addElement(transacao);
        }

        // [feature] cria a lista de transações e define as propriedades
        transacoesList = new JList<>(listModel);
        transacoesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transacoesList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(transacoesList);
        painelConsulta.add(scrollPane, BorderLayout.CENTER);

        // botões | edição e exclusão
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());

        // [feature] botão de editar
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transacao transacaoSelecionada = transacoesList.getSelectedValue();
                if (transacaoSelecionada != null) {
                    new EditarTransacaoGUI(controleFinanceiro, transacaoSelecionada).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma transação para editar.");
                }
            }
        });

        // [feature] botão de excluir
        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transacao transacaoSelecionada = transacoesList.getSelectedValue();
                if (transacaoSelecionada != null) {
                    controleFinanceiro.excluirTransacao(transacaoSelecionada);
                    listModel.removeElement(transacaoSelecionada);
                    JOptionPane.showMessageDialog(null, "Transação excluída com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma transação para excluir.");
                }
            }
        });

        painelBotoes.add(editarButton);
        painelBotoes.add(excluirButton);
        painelConsulta.add(painelBotoes, BorderLayout.SOUTH);

        // add o painel de consulta à janela
        add(painelConsulta, BorderLayout.CENTER);

        // atualiza a área de consulta com as transações
        atualizarConsulta();

        // [ajustes] centraliza a tela na tela principal
        setLocationRelativeTo(null);
        setResizable(false); 
    }

    public void atualizarConsulta() {
        // atualiza a lista de transações
        listModel.clear();
        for (Transacao transacao : controleFinanceiro.getTransacoes()) {
            listModel.addElement(transacao);
        }
    }
}
