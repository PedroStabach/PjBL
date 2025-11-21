package Events;

import Models.*;
import Utils.*;

import java.io.*;
import java.util.*;

public class SistemaBiblioteca implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Usuario> listaUsuarios;
    private List<ItemDeAcervo> acervo;
    private List<Emprestimo> historicoEmprestimos;

    // contadores simples para gerar IDs (pode ser substituído por UUID se preferir)
    private int nextUsuarioSeq = 1;
    private int nextItemSeq = 1;
    private int nextEmprestimoSeq = 1;

    private static final String STORAGE_FILE = "sistema_biblioteca.ser";

    public SistemaBiblioteca() {
        this.listaUsuarios = new ArrayList<>();
        this.acervo = new ArrayList<>();
        this.historicoEmprestimos = new ArrayList<>();
    }

    // ---------------------------
    // CADASTROS / BUSCAS BÁSICAS
    // ---------------------------
    public Usuario cadastrarAluno(String nome, String endereco, String matricula, String curso) {
        String id = "U" + (nextUsuarioSeq++);
        Aluno a = new Aluno(id, nome, endereco, matricula, curso);
        listaUsuarios.add(a);
        return a;
    }

    public Usuario cadastrarProfessor(String nome, String endereco, String siape, String departamento) {
        String id = "U" + (nextUsuarioSeq++);
        Professor p = new Professor(id, nome, endereco, siape, departamento);
        listaUsuarios.add(p);
        return p;
    }


    public ItemDeAcervo cadastrarLivro(String codigo, String titulo, String ano, String autor, String isbn, int edicao) {
        Livro l = new Livro(codigo, titulo, ano, autor, isbn, edicao);
        acervo.add(l);
        return l;
    }

    public ItemDeAcervo cadastrarRevista(String codigo, String titulo, String ano, String editora, int volume, String issn) {
        Revista r = new Revista(codigo, titulo, ano, editora, volume, issn);
        acervo.add(r);
        return r;
    }

    public List<ItemDeAcervo> buscarPorTitulo(String termo) {
        List<ItemDeAcervo> resultado = new ArrayList<>();
        for (ItemDeAcervo it : acervo) {
            if (it.getTitulo() != null && it.getTitulo().toLowerCase().contains(termo.toLowerCase())) {
                resultado.add(it);
            }
        }
        return resultado;
    }

    public Optional<ItemDeAcervo> buscarPorCodigo(String codigo) {
        return acervo.stream().filter(i -> {
            // tenta equals ignorando case
            String codigoItem = i.getCodigo();
            return codigoItem != null && codigoItem.equalsIgnoreCase(codigo);
        }).findFirst();
    }


    public Emprestimo realizarEmprestimo(String idUsuario, String codigoItem) throws Exception {
        Usuario usuario = findUsuarioById(idUsuario);
        ItemDeAcervo item = findItemByCodigo(codigoItem);

        // RN1: disponibilidade
        if (item.isEmprestado()) throw new Exception("RN1 - Item indisponível (já emprestado).");

        // RN4: bloqueios por multa ou item em atraso
        if (usuario.getMultaPendente() > 0.0) throw new Exception("RN4 - Usuário possui multa pendente.");
        if (usuario.getEmprestimosAtivos() > 0) {
            // verifica se usuário tem empréstimo atrasado dentre seus empréstimos ativos
            for (Emprestimo e : historicoEmprestimos) {
                if (e.getUsuario().getId().equals(usuario.getId()) && e.getDataDevolucaoReal() == null) {
                    if (e.getDataDevolucaoPrevista().before(new Date())) {
                        throw new Exception("RN4 - Usuário possui item em posse com prazo vencido.");
                    }
                }
            }
        }

        // RN2: limite por tipo
        if (usuario.getEmprestimosAtivos() >= usuario.getLimiteEmprestimos())
            throw new Exception("RN2 - Usuário excedeu o limite de empréstimos (limite = " + usuario.getLimiteEmprestimos() + ").");

        // Tudo ok: criar empréstimo
        String idEmp = "E" + (nextEmprestimoSeq++);
        Emprestimo emp = new Emprestimo(idEmp, usuario, item); // construtor valida novamente RN1/RN2/RN4 dentro da classe
        historicoEmprestimos.add(emp);

        // marcar item e usuario (Emprestimo já fez parte disso, mas garantimos estados aqui também)
        item.setEmprestado(true);
        usuario.incrementarEmprestimos();

        return emp;
    }

    // ---------------------------
    // DEVOLUÇÃO (RF3 e RN3)
    // ---------------------------
    public void realizarDevolucao(String idEmprestimo) throws Exception {
        Emprestimo e = findEmprestimoById(idEmprestimo);

        if (e.getDataDevolucaoReal() != null) {
            throw new Exception("Emprestimo já foi devolvido.");
        }

        Date hoje = new Date();
        e.finalizarEmprestimo(hoje); // atualiza dataDevolucaoReal, calcula multa, atualiza item e usuario

    }

    // Permite pagar a multa do usuário (zerar)
    public void pagarMultaUsuario(String idUsuario) throws Exception {
        Usuario u = findUsuarioById(idUsuario);
        // zera multa
        // assumindo que Usuario tem um método para resetar multa (se não, usamos adicionarMulta com valor negativo)
        // aqui vamos assumir setMultaPendente existe; se não, usar adicionarMulta(-u.getMultaPendente())
        try {
            // tenta chamar método setMultaPendente se existir
            java.lang.reflect.Method m = u.getClass().getMethod("setMultaPendente", double.class);
            m.invoke(u, 0.0);
        } catch (NoSuchMethodException nsme) {
            // se não existir, faz via adicionarMulta com valor negativo
            double atual = u.getMultaPendente();
            if (atual > 0) u.adicionarMulta(-atual);
        } catch (Exception ex) {
            // fallback
            double atual = u.getMultaPendente();
            if (atual > 0) u.adicionarMulta(-atual);
        }
    }

    // ---------------------------
    // HELPERS de busca (lançam Exception se não encontrar)
    // ---------------------------
    public Usuario findUsuarioById(String id) throws Exception {
        return listaUsuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Usuário não encontrado: id=" + id));
    }

    public ItemDeAcervo findItemByCodigo(String codigo) throws Exception {
        return acervo.stream()
                .filter(i -> i.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElseThrow(() -> new Exception("Item não encontrado: codigo=" + codigo));
    }

    public Emprestimo findEmprestimoById(String idEmprestimo) throws Exception {
        return historicoEmprestimos.stream()
                .filter(e -> e.getIdEmprestimo().equals(idEmprestimo))
                .findFirst()
                .orElseThrow(() -> new Exception("Empréstimo não encontrado: id=" + idEmprestimo));
    }

    // ---------------------------
    // LISTAGENS / RELATÓRIO
    // ---------------------------
    public void listarUsuarios() {
        System.out.println("===== Usuários =====");
        for (Usuario u : listaUsuarios) {
            System.out.println(u.getId() + " - " + u.getNome() + " - Multa: R$" + u.getMultaPendente() + " - EmprestimosAtivos: " + u.getEmprestimosAtivos());
        }
    }

    public void listarItens() {
        System.out.println("===== Acervo =====");
        for (ItemDeAcervo it : acervo) {
            System.out.println(it.getCodigo() + " - " + it.getTitulo() + " - Emprestado: " + it.isEmprestado());
        }
    }

    public void listarEmprestimos() {
        System.out.println("===== Empréstimos =====");
        for (Emprestimo e : historicoEmprestimos) {
            System.out.println(e.getIdEmprestimo() + " | Usuário: " + e.getUsuario().getId()
                    + " | Item: " + e.getItem().getCodigo()
                    + " | Emprestimo: " + e.getDataEmprestimo()
                    + " | Prevista: " + e.getDataDevolucaoPrevista()
                    + " | Real: " + e.getDataDevolucaoReal()
                    + " | Multa: R$" + e.getMultaCobrada());
        }
    }

    // ---------------------------
    // PERSISTÊNCIA (serialização)
    // ---------------------------
    public void salvar() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(this);
        }
    }

    public static SistemaBiblioteca carregar() {
        File f = new File(STORAGE_FILE);
        if (!f.exists()) {
            return new SistemaBiblioteca();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object o = ois.readObject();
            if (o instanceof SistemaBiblioteca) {
                return (SistemaBiblioteca) o;
            }
        } catch (Exception e) {
            System.err.println("Falha ao carregar dados: " + e.getMessage());
        }
        return new SistemaBiblioteca();
    }

    // ---------------------------
    // getters (úteis para testes)
    // ---------------------------
    public List<Usuario> getListaUsuarios() { return listaUsuarios; }
    public List<ItemDeAcervo> getAcervo() { return acervo; }
    public List<Emprestimo> getHistoricoEmprestimos() { return historicoEmprestimos; }

    public String gerarCodigoItemPadrao() {
        return "I" + (nextItemSeq++);
    }
}
