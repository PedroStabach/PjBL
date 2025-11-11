package Utils;

abstract public class ItemDeAcervo {
    private String codigo;
    private String titulo;
    private String anoPublicacao;
    private boolean isEmprestado = false;

    public ItemDeAcervo(String codigo, String titulo, String anoPublicacao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
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
    public boolean isEmprestado() {
        return isEmprestado;
    }
    public void setEmprestado(boolean emprestado) {
        isEmprestado = emprestado;
    }
}
