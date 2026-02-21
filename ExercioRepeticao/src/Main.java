import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int qtdSenhas;
        do {
            System.out.print("Quantas senhas deseja criar (1 a 20): ");
            qtdSenhas = scanner.nextInt();
        } while (qtdSenhas < 1 || qtdSenhas > 20);

        boolean usaMaiuscula, usaMinuscula, usaNumero, usaEspecial;
        int gruposEscolhidos;

        do {
            gruposEscolhidos = 0;

            System.out.print("Usar letras maiúsculas? (s/n): ");
            usaMaiuscula = scanner.next().equalsIgnoreCase("s");
            if (usaMaiuscula) gruposEscolhidos++;

            System.out.print("Usar letras minúsculas? (s/n): ");
            usaMinuscula = scanner.next().equalsIgnoreCase("s");
            if (usaMinuscula) gruposEscolhidos++;

            System.out.print("Usar números? (s/n): ");
            usaNumero = scanner.next().equalsIgnoreCase("s");
            if (usaNumero) gruposEscolhidos++;

            System.out.print("Usar símbolos especiais? (s/n): ");
            usaEspecial = scanner.next().equalsIgnoreCase("s");
            if (usaEspecial) gruposEscolhidos++;

            if (gruposEscolhidos < 2) {
                System.out.println("Você deve escolher pelo menos 2 grupos!");
            }

        } while (gruposEscolhidos < 2);

        List<String> senhas = new ArrayList<>();

        scanner.nextLine(); // limpar buffer

        for (int i = 1; i <= qtdSenhas; i++) {

            String senha;
            boolean senhaValida;

            do {
                System.out.print("Digite a senha " + i + ": ");
                senha = scanner.nextLine();

                senhaValida = validaSenha(
                        senha,
                        usaMaiuscula,
                        usaMinuscula,
                        usaNumero,
                        usaEspecial
                );

                if (!senhaValida) {
                    System.out.println("Senha inválida. Tente novamente.");
                }

            } while (!senhaValida);

            senhas.add(senha);
        }

        System.out.println("\nSenhas cadastradas:");
        for (String s : senhas) {
            exibirContagem(s);
        }

        scanner.close();
    }

    static boolean validaSenha(String senha,
                               boolean usaMaiuscula,
                               boolean usaMinuscula,
                               boolean usaNumero,
                               boolean usaEspecial) {

        // tamanho da senha
        if (senha.length() < 8 || senha.length() > 32) return false;

        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temEspecial = false;

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);

            // classificacao do caractere
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            } else if (Character.isLowerCase(c)) {
                temMinuscula = true;
            } else if (Character.isDigit(c)) {
                temNumero = true;
            } else {
                temEspecial = true;
            }

            // repeticao de 3 caracteres iguais
            if (i >= 2 &&
                    senha.charAt(i) == senha.charAt(i - 1) &&
                    senha.charAt(i) == senha.charAt(i - 2)) {
                return false;
            }

            // sequencia numerica crescente ou decrescente
            if (i >= 2 &&
                    Character.isDigit(senha.charAt(i)) &&
                    Character.isDigit(senha.charAt(i - 1)) &&
                    Character.isDigit(senha.charAt(i - 2))) {

                int a = senha.charAt(i - 2) - '0';
                int b = senha.charAt(i - 1) - '0';
                int cNum = senha.charAt(i) - '0';

                if ((a + 1 == b && b + 1 == cNum) ||
                        (a - 1 == b && b - 1 == cNum)) {
                    return false;
                }
            }
        }

        // verificacao dos grupos obrigatorios
        if (usaMaiuscula && !temMaiuscula) return false;
        if (usaMinuscula && !temMinuscula) return false;
        if (usaNumero && !temNumero) return false;
        if (usaEspecial && !temEspecial) return false;

        return true;
    }

    static void exibirContagem(String senha) {

        int maiuscula = 0, minuscula = 0, numero = 0, especial = 0;

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);

            // contagem dos caracteres
            if (Character.isUpperCase(c)) maiuscula++;
            else if (Character.isLowerCase(c)) minuscula++;
            else if (Character.isDigit(c)) numero++;
            else especial++;
        }

        System.out.println(
                senha + " | Maiusculas: " + maiuscula +
                        " | Minusculas: " + minuscula +
                        " | Numeros: " + numero +
                        " | Simbolos: " + especial
        );
    }
}
