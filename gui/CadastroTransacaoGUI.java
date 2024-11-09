package gui;

import javax.swing.*;
import controle.ControleFinanceiro;
import modelo.Categoria;
import modelo.Transacao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CadastroTransacaoGUI extends JFrame {
    private JTextField valorField; // valor da transação
    private JComboBox<Categoria> categoriaBox;  // ComboBox || para selecionar categorias
    private JComboBox<String> tipoBox; // ComboBox || para selecionar receita ou despesa
    private JTextField nomeField; // nome da transação
    private JButton cadastrarButton;

    public CadastroTransacaoGUI(ControleFinanceiro controleFinanceiro) {
        setTitle("Cadastro de Transação");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // painel para os rótulos e campos de texto
        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new GridLayout(6, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCampos.setBackground(new Color(240, 240, 240));

        // campo para o nome da transação
        JLabel nomeLabel = new JLabel("Nome da Transação:");
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nomeField = new JTextField();
        painelCampos.add(nomeLabel);
        painelCampos.add(nomeField);

        // campo para digitar o valor da transação
        JLabel valorLabel = new JLabel("Valor:");
        valorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        valorField = new JTextField();
        painelCampos.add(valorLabel);
        painelCampos.add(valorField);

        // campo para escolher a categoria para a transação
        JLabel categoriaLabel = new JLabel("Categoria:");
        categoriaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        categoriaBox = new JComboBox<>();
        for (Categoria categoria : controleFinanceiro.getCategorias()) {
            categoriaBox.addItem(categoria);
        }
        if (controleFinanceiro.getCategorias().isEmpty()) {
            // add um item genérico se não tiver categorias
            categoriaBox.addItem(new Categoria("Sem Categorias", 0));
        }
        painelCampos.add(categoriaLabel);
        painelCampos.add(categoriaBox);

        // campo para escolher o tipo de transação (receita ou despesa)
        JLabel tipoLabel = new JLabel("Tipo:");
        tipoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tipoBox = new JComboBox<>(new String[] {"Receita", "Despesa"});
        painelCampos.add(tipoLabel);
        painelCampos.add(tipoBox);

        // botão | cadastrar a transação
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        cadastrarButton.setBackground(new Color(34, 167, 240));  // Cor azul para o botão
        cadastrarButton.setForeground(Color.WHITE);
        cadastrarButton.setFocusPainted(false);
        painelCampos.add(new JLabel());  // Para manter o layout alinhado
        painelCampos.add(cadastrarButton);

        // adicionando painel de campos na tela
        add(painelCampos, BorderLayout.CENTER);

        // [feature] ação para cadastrar a transação
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeTransacao = nomeField.getText().trim();
                    if (nomeTransacao.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "O campo nome da transação não pode ser vazio.");
                        return;
                    }

                    String valorTexto = valorField.getText().trim();
                    if (valorTexto.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "O campo valor não pode ser vazio.");
                        return;
                    }

                    double valor = Double.parseDouble(valorTexto);
                    Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
                    String tipo = (String) tipoBox.getSelectedItem();
                    
                    // [feature] bloqueia transação com valor zero ou negativo
                    if (valor <= 0) {
                        JOptionPane.showMessageDialog(null, "O valor da transação deve ser maior que zero.");
                        return;
                    }

                    // [ajuste] valor para receitas/despesas
                    if (tipo.equals("Despesa")) {
                        valor = -Math.abs(valor);  // valor negativo para despesa
                    } else {
                        valor = Math.abs(valor);   // valor positivo para receita
                    }

                    Transacao transacao = new Transacao(new Date(), categoria, valor, nomeTransacao);
                    controleFinanceiro.adicionarTransacao(transacao);

                    JOptionPane.showMessageDialog(null, "Transação cadastrada com sucesso!");
                    valorField.setText("");  // limpa o campo de depois do cadastro
                    nomeField.setText("");  // limpa o campo de depois do cadastro

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite um valor válido.");
                }
            }
        });

        // botão | fecha a tela de cadastro
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new FlowLayout());
        JButton fecharButton = new JButton("Fechar");
        fecharButton.setFont(new Font("Arial", Font.PLAIN, 12));
        fecharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        painelRodape.add(fecharButton);
        add(painelRodape, BorderLayout.SOUTH);

        setLocationRelativeTo(null);  // [ajustes] centraliza a tela na janela principal
        setResizable(false);  // [ajustes] bloqueia o redimensionamento da janela
    }
}
