package Models;

import java.sql.SQLOutput;
import java.util.List;
import Events.Emprestimo;
import java.util.Date;
abstract public class Usuario {
    private String id;
    private String nome;
    private String endereco;
    private String status = "Ativo";
    private List<Emprestimo> emprestimos;
    private int limiteEmprestimo;

    public Usuario(String id, String nome, String endereco, int limiteEmprestimo) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.limiteEmprestimo = limiteEmprestimo;
    }
    public String getNome() {return this.nome;}
    public String getEndereco() { return this.endereco;}
    public String getStatus() {return this.status;}
    public String getEmprestimos() {
       return this.emprestimos.toString();
    }

    public String getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    //fazer funcoes da lista

    public boolean isAptoParaEmprestimo() {
        if (this.status == "Ativo" &&  this.limiteEmprestimo > this.emprestimos.toArray().length) {
            return true;
        } else {
            return false;
        }
    }

    abstract public Date calculaPrazoDevolucao();

    public void adicionarEmprestimo(Emprestimo e) {
        if (isAptoParaEmprestimo()) emprestimos.add(e);
        else System.out.println("Nao foi possivel fazer emprestimo");
    }

}

