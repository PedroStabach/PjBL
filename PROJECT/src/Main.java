import Events.SistemaBiblioteca;

public class Main {
    public static void main(String[] args) {

        SistemaBiblioteca sistema = SistemaBiblioteca.carregar();

        System.out.println("ANTES:");
        sistema.listarUsuarios();

        // cadastrar um usuário de teste
        sistema.cadastrarAluno("MATHEUS", "Rua Tal", "123", "ADS");
        try {
            sistema.salvar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("DEPOIS:");
        sistema.listarUsuarios();
    }
}
