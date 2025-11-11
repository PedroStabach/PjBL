package Models;

import java.sql.SQLOutput;
import java.util.List;
import Events.Emprestimo;
abstract public class Usuario {
    private int id;
    private String nome;
    private String endereco;
    private String status = "Ativo";
    private List<Emprestimo> emprestimos;

    public Usuario(int id, String nome, String endereco, String status) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.status = status;
    }
    String getNome() {return this.nome;}
    String getEndereco() { return this.endereco;}
    String getStatus() {return this.status;}
    String getEmprestimos() {
       return this.emprestimos.toString();
    }

    void setNome(String nome) {
        this.nome = nome;
    }
    void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    void setStatus(String status) {
        this.status = status;
    }
    //fazer funcoes da lista

}

