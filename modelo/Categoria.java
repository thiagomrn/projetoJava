package modelo;

public class Categoria {
    private String nome;
    private int codigo;

    // construtor que recebe nome e código
    public Categoria(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    // construtor que recebe apenas o nome cadastrado e gera um código automaticamente
    public Categoria(String nome) {
        this.nome = nome;
        this.codigo = nome.hashCode();  // [feature] pode usar qualquer lógica de código, como hashCode
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
