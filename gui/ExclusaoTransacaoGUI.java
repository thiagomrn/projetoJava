package gui;
import javax.swing.*;

import controle.ControleFinanceiro;
import modelo.Transacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExclusaoTransacaoGUI extends JFrame {
    private JTextField indiceField;
    private JButton excluirButton;
    public ExclusaoTransacaoGUI(ControleFinanceiro controleFinanceiro) {

        excluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int indice = Integer.parseInt(indiceField.getText());
                    Transacao transacao = controleFinanceiro.getTransacoes().get(indice);
                    controleFinanceiro.excluirTransacao(transacao);
                    JOptionPane.showMessageDialog(null, "Transação excluída com sucesso!");
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Índice inválido.");
                }
            }
        });
    }
}
