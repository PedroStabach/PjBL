package Utils;

import java.io.Serializable;

public class Livro extends ItemDeAcervo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String autor;
    private String isbn;
    private int edicao;
    public Livro(String codigo, String titulo, String anoPublicacao, String autor, String isbn, int edicao) {
        super(codigo, titulo, anoPublicacao);
        this.autor = autor;
        this.isbn = isbn;
        this.edicao = edicao;
    }

    String getAutor() {
        return autor;
    }
    void setAutor(String autor) {
        this.autor = autor;
    }
    String getIsbn() {
        return isbn;
    }
    void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    int getEdicao() {
        return edicao;
    }
    void setEdicao(int edicao) {
        this.edicao = edicao;
    }
}
