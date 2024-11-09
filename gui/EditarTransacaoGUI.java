package gui;

import javax.swing.*;
import controle.ControleFinanceiro;
import modelo.Categoria;
import modelo.Transacao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarTransacaoGUI extends JFrame {
    private JTextField valorField;
    private JComboBox<Categoria> categoriaComboBox;
    private JComboBox<String> tipoComboBox;
    private JButton editarButton;
    private Transacao transacaoEditando;

    // construtor
    public EditarTransacaoGUI(ControleFinanceiro controleFinanceiro, Transacao transacao) {
        this.transacaoEditando = transacao;

        setTitle("Editar Transação");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 

        // layout para os campos de entrada
        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new GridLayout(5, 2, 10, 10)); 
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        // [feature] campo para editar o valor
        painelCampos.add(new JLabel("Valor:"));
        valorField = new JTextField(String.valueOf(transacao.getValor()));
        painelCampos.add(valorField);

        // [feature] campo para editar a categoria
        painelCampos.add(new JLabel("Categoria:"));
        categoriaComboBox = new JComboBox<>();
        for (Categoria categoria : controleFinanceiro.getCategorias()) {
            categoriaComboBox.addItem(categoria);
        }
        categoriaComboBox.setSelectedItem(transacao.getCategoria());
        painelCampos.add(categoriaComboBox);

        // [feature] campo para escolher o tipo (receita ou despesa)
        painelCampos.add(new JLabel("Tipo:"));
        tipoComboBox = new JComboBox<>(new String[] { "Receita", "Despesa" });
        tipoComboBox.setSelectedItem(transacao.getValor() > 0 ? "Receita" : (transacao.getValor() < 0 ? "Despesa" : "Receita"));
        painelCampos.add(tipoComboBox);

        // add o painel com os campos na janela
        add(painelCampos, BorderLayout.CENTER);

        // botão | editar
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        editarButton = new JButton("Editar");
        painelBotoes.add(editarButton);
        add(painelBotoes, BorderLayout.SOUTH);

        // [feature]ação para editar a transação
        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double novoValor = Double.parseDouble(valorField.getText());
                    Categoria novaCategoria = (Categoria) categoriaComboBox.getSelectedItem();
                    String tipo = (String) tipoComboBox.getSelectedItem();

                    // Ajusta o valor conforme o tipo
                    if (tipo.equals("Despesa")) {
                        novoValor = -Math.abs(novoValor);  // negativo para despesa
                    } else {
                        novoValor = Math.abs(novoValor);  // positivo para receita
                    }

                    Transacao novaTransacao = new Transacao(transacaoEditando.getData(), novaCategoria, novoValor);

                    // atualiza a transação na lista
                    int index = controleFinanceiro.getTransacoes().indexOf(transacaoEditando);
                    controleFinanceiro.editarTransacao(index, novaTransacao);

                    JOptionPane.showMessageDialog(null, "Transação editada com sucesso!");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite um valor válido.");
                }
            }
        });
    }
}
