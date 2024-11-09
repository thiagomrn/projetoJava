package gui;
import javax.swing.*;

import controle.ControleFinanceiro;
import modelo.Transacao;

import java.awt.*;
import java.util.List;

public class RelatorioFinanceiroGUI extends JFrame {
    private ControleFinanceiro controleFinanceiro;
    private JTextArea textAreaRelatorio;

    public RelatorioFinanceiroGUI(ControleFinanceiro controleFinanceiro) {
        this.controleFinanceiro = controleFinanceiro;
        setTitle("Relatório Mensal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        textAreaRelatorio = new JTextArea();
        textAreaRelatorio.setEditable(false);
        textAreaRelatorio.setFont(new Font("Arial", Font.PLAIN, 14));

        add(new JScrollPane(textAreaRelatorio), BorderLayout.CENTER);
        exibirRelatorio();
    }

    // mostra o relatório do mês atual
    private void exibirRelatorio() {
        List<Transacao> transacoesDoMes = controleFinanceiro.getTransacoesDoMes();

        StringBuilder relatorio = new StringBuilder("Relatório Financeiro do Mês Atual:\n\n");

        if (transacoesDoMes.isEmpty()) {
            relatorio.append("Nenhuma transação encontrada para o mês atual.");
        } else {
            double totalReceitas = 0;
            double totalDespesas = 0;

            for (Transacao transacao : transacoesDoMes) {
                relatorio.append("Data: ").append(transacao.getData()).append("\n");
                relatorio.append("Categoria: ").append(transacao.getCategoria().getNome()).append("\n");
                relatorio.append("Valor: ").append(transacao.getValor()).append("\n\n");

                if (transacao.getValor() > 0) {
                    totalReceitas += transacao.getValor();
                } else {
                    totalDespesas += Math.abs(transacao.getValor());
                }
            }

            relatorio.append("Total de Receitas: ").append(totalReceitas).append("\n");
            relatorio.append("Total de Despesas: ").append(totalDespesas).append("\n");
            relatorio.append("Saldo Final: ").append(totalReceitas - totalDespesas).append("\n");
        }

        textAreaRelatorio.setText(relatorio.toString());
    }
}
