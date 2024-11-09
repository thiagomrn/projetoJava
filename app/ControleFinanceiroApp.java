package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import controle.ControleFinanceiro;
import gui.CadastroTransacaoGUI;
import gui.ConsultaTransacaoGUI;
import modelo.Transacao;

public class ControleFinanceiroApp extends JFrame {
    private ControleFinanceiro controleFinanceiro;
    private JLabel saldoLabel;  // para exibir o saldo restante

    public ControleFinanceiroApp(ControleFinanceiro controleFinanceiro) {
        this.controleFinanceiro = controleFinanceiro;
        setTitle("Controle Financeiro");
        setSize(575, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // painel de ações
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new GridLayout(5, 1, 10, 10));  // layout vertical com espaço
        painelAcoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // margens internas
        painelAcoes.setBackground(new Color(240, 240, 240));  // cor do fundo

        // botão | tela de cadastro
        JButton btnCadastro = new JButton("Cadastrar Transação");
        btnCadastro.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastro.setBackground(new Color(45, 18, 108));  // cor roxa | destaque
        btnCadastro.setForeground(Color.WHITE);
        btnCadastro.setFocusPainted(false);
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroTransacaoGUI(controleFinanceiro).setVisible(true);
            }
        });
        painelAcoes.add(btnCadastro);

        // botão | tela de consulta
        JButton btnConsulta = new JButton("Consultar Transação");
        btnConsulta.setFont(new Font("Arial", Font.BOLD, 14));
        btnConsulta.setBackground(new Color(45, 18, 108));
        btnConsulta.setForeground(Color.WHITE);
        btnConsulta.setFocusPainted(false);
        btnConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaTransacaoGUI(controleFinanceiro).setVisible(true);
            }
        });
        painelAcoes.add(btnConsulta);

        // botão | relatório do mês
        JButton relatorioMesButton = new JButton("Exibir Relatório do Mês");
        relatorioMesButton.setFont(new Font("Arial", Font.BOLD, 14));
        relatorioMesButton.setBackground(new Color(45, 18, 108));
        relatorioMesButton.setForeground(Color.WHITE);
        relatorioMesButton.setFocusPainted(false);
        relatorioMesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirRelatorioMes();
            }
        });
        painelAcoes.add(relatorioMesButton);

        // botão | exportação em .csv
        JButton btnExportar = new JButton("Exportar para CSV");
        btnExportar.setFont(new Font("Arial", Font.BOLD, 14));
        btnExportar.setBackground(new Color(62, 156, 68)); // cor | verde
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFocusPainted(false);
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarParaCSV();
            }
        });
        painelAcoes.add(btnExportar);  // add o botão ao painel de ações

        // add o painel de ações à tela
        add(painelAcoes, BorderLayout.CENTER);

        // [configurações] tela principal
        JLabel tituloLabel = new JLabel("Bem-vindo ao Controle Financeiro", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(tituloLabel, BorderLayout.NORTH);

        // calcula e exibe o saldo restante
        saldoLabel = new JLabel("Seu saldo é de " + calcularSaldo() + " reais", SwingConstants.CENTER);
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(saldoLabel, BorderLayout.SOUTH);  // Coloca o saldo na parte inferior da tela

        // [ajustes] tamanho da janela
        setLocationRelativeTo(null);  // centraliza a janela
        setResizable(false);  // bloqueia o redimensionamento da janela
        setVisible(true);
    }

    // método | calcula o saldo total
    private double calcularSaldo() {
        double totalReceitas = 0;
        double totalDespesas = 0;
        for (Transacao transacao : controleFinanceiro.getTransacoes()) {
            if (transacao.getValor() > 0) {
                totalReceitas += transacao.getValor();
            } else {
                totalDespesas += transacao.getValor();
            }
        }
        return totalReceitas + totalDespesas;
    }

    // método | atualiza o saldo na tela
    public void atualizarSaldo() {
        saldoLabel.setText("Saldo: " + calcularSaldo());
    }

    // [feature] ação para mostrar o relatório do mês
    private void exibirRelatorioMes() {
        // calcula o valor total das receitas e despesas do mês atual
        double totalReceitas = 0;
        double totalDespesas = 0;
        for (Transacao transacao : controleFinanceiro.getTransacoesDoMes()) {
            if (transacao.getValor() > 0) {
                totalReceitas += transacao.getValor();
            } else {
                totalDespesas += transacao.getValor();
            }
        }

        // [feature] mostra o relatório em uma janela de diálogo
        JOptionPane.showMessageDialog(this,
            "Aqui estão seus gastos deste mês\n" +
            "Total de Receitas: " + totalReceitas + "\n" +
            "Total de Despesas: " + totalDespesas + "\n" +
            "Saldo: " + (totalReceitas + totalDespesas),
            "Relatório do Mês",
            JOptionPane.INFORMATION_MESSAGE);
    }
 
    // [feature] exportação das transações em arquivo .csv
    private void exportarParaCSV() {
        // tela de seleção de caminho do arquivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como CSV");
        fileChooser.setSelectedFile(new File("transacoes.csv"));

        // mostra a janela de diálogo para salvar o arquivo
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File arquivoCSV = fileChooser.getSelectedFile();
            
            // add a extensão .csv (caso não tenha)
            if (!arquivoCSV.getName().endsWith(".csv")) {
                arquivoCSV = new File(arquivoCSV.getAbsolutePath() + ".csv");
            }

            // [feature] pergunta ao usuário se quer exportar com ou sem o cabeçalho
            int escolha = JOptionPane.showConfirmDialog(this,
                "Você deseja incluir cabeçalho na exportação?", "Cabeçalho", JOptionPane.YES_NO_OPTION);
            boolean incluirCabecalho = (escolha == JOptionPane.YES_OPTION);

            // [ajustes] formatação de data
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            // inicia a exportação 
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCSV))) {
                // Escrever cabeçalho
                if (incluirCabecalho) {
                    writer.write("Data,Categoria,Valor,Tipo");
                    writer.newLine();
                }
                
                // [feature] escreve as transações no arquivo
                for (Transacao transacao : controleFinanceiro.getTransacoes()) {
                    String data = dateFormat.format(transacao.getData());
                    String categoria = transacao.getCategoria().getNome();
                    String valor = String.format("%.2f", transacao.getValor());
                    String tipo = transacao.getValor() > 0 ? "Receita" : "Despesa";
                    
                    writer.write(data + "," + categoria + "," + valor + "," + tipo);
                    writer.newLine();
                }

                // [feature] mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Transações exportadas com sucesso para o arquivo CSV!", "Exportação Concluída", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar transações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        ControleFinanceiro controleFinanceiro = new ControleFinanceiro();
        SwingUtilities.invokeLater(() -> new ControleFinanceiroApp(controleFinanceiro).setVisible(true));
    }
}
