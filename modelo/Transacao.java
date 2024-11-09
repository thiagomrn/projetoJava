package modelo;

import java.util.Date;

public class Transacao {
    private Date data;
    private Categoria categoria;
    private double valor;
    private String nome;

    // construtor - c/nome
    public Transacao(Date data, Categoria categoria, double valor, String nome) {
        this.data = data;
        this.categoria = categoria;
        this.valor = valor;
        this.nome = nome;
    }

    // construtor - s/nome (caso o usuário não queira definir um nome)
    public Transacao(Date data, Categoria categoria, double valor) {
        this(data, categoria, valor, "Transação sem nome");  // Valor padrão caso o nome não seja passado
    }

    public String getNome() {
        return nome;
    }

    // método toString | sobrescrito
    @Override
    public String toString() {
        return nome + " - " + categoria.getNome() + " - R$" + String.format("%.2f", valor);
    }

    public Date getData() {
        return data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getValor() {
        return valor;
    }
}
