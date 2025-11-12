package Events;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import Models.*;
import Utils.*;
public class SistemaBiblioteca {

    private List<Usuario> listaUsuarios;
    private List<ItemDeAcervo> acervo;
    private List<Emprestimo> historicoEmprestimos;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public SistemaBiblioteca() {
        listaUsuarios = new ArrayList<>();
        acervo = new ArrayList<>();
        historicoEmprestimos = new ArrayList<>();
    }

    // ========================= MÉTODOS BÁSICOS =========================
    public void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public void adicionarItem(ItemDeAcervo item) {
        acervo.add(item);
    }

    // ========================= EMPRÉSTIMO =========================
    public Emprestimo realizarEmprestimo(String idUsuario, String codItem) {
        Usuario usuario = listaUsuarios.stream()
                .filter(u -> u.getId().equals(idUsuario))
                .findFirst()
                .orElse(null);

        ItemDeAcervo item = acervo.stream()
                .filter(i -> i.getCodigo().equals(codItem) && !i.isEmprestado())
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return null;
        }

        if (item == null) {
            System.out.println("Item não disponível ou não encontrado.");
            return null;
        }

        String idEmprestimo = UUID.randomUUID().toString();
        Date dataEmprestimo = new Date();
        Date dataPrevista = usuario.calculaPrazoDevolucao();

        Emprestimo emprestimo = new Emprestimo(idEmprestimo, usuario, item, dataEmprestimo, dataPrevista);
        item.setEmprestado(true);
        historicoEmprestimos.add(emprestimo);

        System.out.println("Empréstimo realizado com sucesso! ID: " + idEmprestimo);
        return emprestimo;
    }

    // ========================= DEVOLUÇÃO =========================
    public void realizarDevolucao(String idEmprestimo) {
        Emprestimo emprestimo = historicoEmprestimos.stream()
                .filter(e -> e.getIdEmprestimo().equals(idEmprestimo))
                .findFirst()
                .orElse(null);

        if (emprestimo == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }

        if (emprestimo.getDataDevolucaoReal() != null) {
            System.out.println("Item já devolvido.");
            return;
        }

        Date dataDevolucao = new Date();
        emprestimo.setDataDevolucaoReal(dataDevolucao);
        emprestimo.getItem().setEmprestado(false);

        double multa = emprestimo.calcularMulta(dataDevolucao);
        emprestimo.setMultaCobrada(multa);

        System.out.println("Devolução realizada com sucesso! Multa: R$" + multa);
    }

    // ========================= SALVAR DADOS =========================
    public void salvarDados(String arquivoUsuarios, String arquivoItens, String arquivoEmprestimos) {
        try {
            // Usuários
            try (PrintWriter pw = new PrintWriter(new File(arquivoUsuarios))) {
                for (Usuario u : listaUsuarios) {
                    if (u instanceof Aluno a) {
                        pw.println(String.join(",",
                                a.getId(),
                                a.getNome(),
                                a.getEndereco(),
                                a.getMatricula(),
                                a.getCurso(),
                                "Aluno"));
                    } else if (u instanceof Professor p) {
                        pw.println(String.join(",",
                                p.getId(),
                                p.getNome(),
                                p.getEndereco(),
                                p.getDepartamento(),
                                "Professor"));
                    }
                }
            }

            // Itens
            try (PrintWriter pw = new PrintWriter(new File(arquivoItens))) {
                for (ItemDeAcervo i : acervo) {
                    pw.println(String.join(",",
                            i.getCodigo(),
                            i.getTitulo(),
                            i.getAnoPublicacao(),
                            String.valueOf(i.isEmprestado()),
                            i.getClass().getSimpleName()));
                }
            }

            // Empréstimos
            try (PrintWriter pw = new PrintWriter(new File(arquivoEmprestimos))) {
                for (Emprestimo e : historicoEmprestimos) {
                    String dataDevPrev = sdf.format(e.getDataDevolucaoPrevista());
                    String dataDevReal = e.getDataDevolucaoReal() == null ? "" : sdf.format(e.getDataDevolucaoReal());
                    pw.println(String.join(",",
                            e.getIdEmprestimo(),
                            e.getUsuario().getId(),
                            e.getItem().getCodigo(),
                            sdf.format(e.getDataEmprestimo()),
                            dataDevPrev,
                            dataDevReal,
                            String.valueOf(e.getMultaCobrada())
                    ));
                }
            }

            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    // ========================= CARREGAR DADOS =========================
    public void carregarDados(String arquivoUsuarios, String arquivoItens, String arquivoEmprestimos) {
        try {
            // Usuários
            listaUsuarios.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(",");
                    String id = partes[0];
                    String nome = partes[1];
                    String endereco = partes[2];
                    String tipo = partes[partes.length - 1];

                    Usuario u;
                    if (tipo.equals("Aluno")) {
                        String matricula = partes[3];
                        String curso = partes[4];
                        u = new Aluno(id, nome, endereco, matricula, curso);
                    } else {
                        String siape = partes[3];
                        String departamento = partes[4];
                        u = new Professor(id, nome, endereco,siape,  departamento );
                    }
                    listaUsuarios.add(u);
                }
            }

            // Itens
            acervo.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(arquivoItens))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(",");
                    String codigo = partes[0];
                    String titulo = partes[1];
                    String ano = partes[2];

                    String tipo = partes[partes.length - 1];

                    ItemDeAcervo item;
                    if (tipo.equals("Livro")) {
                        String autor = partes[3];
                        String isbn = partes[4];
                        String edicao = partes[5];
                        item = new Livro(codigo, titulo, ano, autor, isbn, Integer.parseInt(edicao) );
                    } else {
                        String editora = partes[3];
                        String volume = partes[4];
                        String issn = partes[5];
                        item = new Revista(codigo, titulo, ano, editora, Integer.parseInt(volume), issn);
                    }
                    acervo.add(item);
                }
            }

            // Empréstimos
            historicoEmprestimos.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(arquivoEmprestimos))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(",");
                    String idEmprestimo = partes[0];
                    String idUsuario = partes[1];
                    String codItem = partes[2];
                    Date dataEmprestimo = sdf.parse(partes[3]);
                    Date dataDevPrev = sdf.parse(partes[4]);
                    Date dataDevReal = partes[5].isEmpty() ? null : sdf.parse(partes[5]);
                    double multa = Double.parseDouble(partes[6]);

                    Usuario usuario = listaUsuarios.stream()
                            .filter(u -> u.getId().equals(idUsuario))
                            .findFirst().orElse(null);

                    ItemDeAcervo item = acervo.stream()
                            .filter(i -> i.getCodigo().equals(codItem))
                            .findFirst().orElse(null);

                    Emprestimo e = new Emprestimo(idEmprestimo, usuario, item, dataEmprestimo, dataDevPrev);
                    e.setDataDevolucaoReal(dataDevReal);
                    e.setMultaCobrada(multa);

                    historicoEmprestimos.add(e);
                }
            }

            System.out.println("Dados carregados com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}
