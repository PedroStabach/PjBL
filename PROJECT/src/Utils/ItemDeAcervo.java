package Utils;

import java.io.Serializable;
import java.util.Date;

abstract public class ItemDeAcervo  implements Serializable {
    private String codigo;
    private String titulo;
    private String anoPublicacao;
    private Date dataEmprestimo;
    private boolean isEmprestado = false;
    private static final long serialVersionUID = 1L;


    public ItemDeAcervo(String codigo, String titulo, String anoPublicacao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
    }
    public boolean isEmprestado() {
        return isEmprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.isEmprestado = emprestado;
    }

    public String  getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(String anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    void emprestar(){
        isEmprestado = true;
        dataEmprestimo = new Date();
    }
    void devolver() {
        isEmprestado = false;
        dataEmprestimo = null;
    }
}
