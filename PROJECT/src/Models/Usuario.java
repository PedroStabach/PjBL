package Models;

import java.util.Date;

public abstract class Usuario {

    protected String id;
    protected String nome;
    protected String endereco;

    protected int emprestimosAtivos = 0;
    protected double multaPendente = 0;

    public Usuario(String id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    // ================== GETTERS NECESSÁRIOS ==================
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }

    // ================== POLIMORFISMO ==================
    public abstract Date calcularDataDevolucao();
    public abstract int getLimiteEmprestimos();

    // ================== CONTROLE ==================
    public int getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public void incrementarEmprestimos() {
        emprestimosAtivos++;
    }

    public void decrementarEmprestimos() {
        emprestimosAtivos--;
    }

    // ================== MULTA / BLOQUEIO ==================
    public double getMultaPendente() {
        return multaPendente;
    }

    public void adicionarMulta(double valor) {
        multaPendente += valor;
    }

    public boolean possuiBloqueio() {
        return multaPendente > 0;
    }
}
