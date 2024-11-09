package controle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import modelo.Categoria;
import modelo.Transacao;

public class ControleFinanceiro {
    private List<Transacao> transacoes;
    private List<Categoria> categorias;

    public ControleFinanceiro() {
        this.transacoes = new ArrayList<>();
        this.categorias = new ArrayList<>();
        inicializarCategoriasPadrao();
    }

    // categorias cadastradas
    private void inicializarCategoriasPadrao() {
        categorias.add(new Categoria("Alimentação"));
        categorias.add(new Categoria("Transporte"));
        categorias.add(new Categoria("Lazer"));
        categorias.add(new Categoria("Saúde"));
        categorias.add(new Categoria("Educação"));
        categorias.add(new Categoria("Salário"));
        categorias.add(new Categoria("Moradia"));
    }

    // método | adicionar uma transação
    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    // método | obter todas as transações
    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    // método | obter todas as categorias
    public List<Categoria> getCategorias() {
        return categorias;
    }

    // método | excluir uma transação
    public void excluirTransacao(Transacao transacao) {
        transacoes.remove(transacao);
    }

    // método para editar uma transação existente
    public void editarTransacao(int index, Transacao novaTransacao) {
        transacoes.set(index, novaTransacao);
    }

    // método | receber transações do mês atual
    public List<Transacao> getTransacoesDoMes() {
        List<Transacao> transacoesDoMes = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int mesAtual = calendar.get(Calendar.MONTH);
        int anoAtual = calendar.get(Calendar.YEAR);

        for (Transacao transacao : transacoes) {
            Calendar transacaoCalendar = Calendar.getInstance();
            transacaoCalendar.setTime(transacao.getData());
            int mesTransacao = transacaoCalendar.get(Calendar.MONTH);
            int anoTransacao = transacaoCalendar.get(Calendar.YEAR);

            if (mesTransacao == mesAtual && anoTransacao == anoAtual) {
                transacoesDoMes.add(transacao);
            }
        }
        return transacoesDoMes;
    }
}
