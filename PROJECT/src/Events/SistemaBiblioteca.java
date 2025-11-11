package Events;
import java.util.ArrayList;
import java.util.List;
import Models.Usuario;
import Utils.ItemDeAcervo;

public class SistemaBiblioteca {
    private List<Usuario> listaUsuarios;
    private List<ItemDeAcervo> acervo;
    private List<Emprestimo> historicoEmprestimos;

    // 🔹 Construtor
    public SistemaBiblioteca() {
        this.listaUsuarios = new ArrayList<>();
        this.acervo = new ArrayList<>();
        this.historicoEmprestimos = new ArrayList<>();
    }

    // 🔹 Getters e Setters
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<ItemDeAcervo> getAcervo() {
        return acervo;
    }

    public void setAcervo(List<ItemDeAcervo> acervo) {
        this.acervo = acervo;
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }

    public void setHistoricoEmprestimos(List<Emprestimo> historicoEmprestimos) {
        this.historicoEmprestimos = historicoEmprestimos;
    }

    // 🔹 Métodos auxiliares

    /** Adiciona um novo usuário ao sistema */
    public void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    /** Adiciona um novo item ao acervo */
    public void adicionarItem(ItemDeAcervo item) {
        acervo.add(item);
    }

    /** Registra um novo empréstimo */
    public void registrarEmprestimo(Emprestimo emprestimo) {
        historicoEmprestimos.add(emprestimo);
    }

    /** Lista todos os usuários cadastrados */
    public void listarUsuarios() {
        System.out.println("📚 Lista de Usuários:");
        for (Usuario u : listaUsuarios) {
            System.out.println(" - " + u);
        }
    }

    /** Lista todos os itens do acervo */
    public void listarAcervo() {
        System.out.println("🎞️ Acervo da Biblioteca:");
        for (ItemDeAcervo item : acervo) {
            System.out.println(" - " + item);
        }
    }

    /** Lista todos os empréstimos feitos */
    public void listarHistoricoEmprestimos() {
        System.out.println("🧾 Histórico de Empréstimos:");
        for (Emprestimo e : historicoEmprestimos) {
            System.out.println(" - " + e);
        }
    }
}
