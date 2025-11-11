import Models.*;

public class Main {
    public static void main(String[] args) {
        Usuario aluno = new Aluno(123, "asd", "123" , "123", "Matematica");
        Usuario professor = new Professor(123, "Professor", "123" , "123", "Matematica");

        System.out.println(aluno.getNome() + " deve devolver até: " + aluno.calculaPrazoDevolucao());
        System.out.println(professor.getNome() + " deve devolver até: " + professor.calculaPrazoDevolucao());

    }
}