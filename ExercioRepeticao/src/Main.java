import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int qtdSenhas;
        do {
            System.out.print("Quantas senhas deseja gerar (1 a 20): ");
            qtdSenhas = scanner.nextInt();
        } while (qtdSenhas < 1 || qtdSenhas > 20);

        int tamanhoSenha;
        do {
            System.out.print("Tamanho das senhas (8 a 32): ");
            tamanhoSenha = scanner.nextInt();
        } while (tamanhoSenha < 8 || tamanhoSenha > 32);

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

        for (int i = 0; i < qtdSenhas; i++) {

            String senha;

            do {
                senha = gerarSenha(
                        tamanhoSenha,
                        usaMaiuscula,
                        usaMinuscula,
                        usaNumero,
                        usaEspecial,
                        random
                );
            } while (!validaSenha(
                    senha,
                    usaMaiuscula,
                    usaMinuscula,
                    usaNumero,
                    usaEspecial
            ));

            senhas.add(senha);
        }

        System.out.println("\nSenhas geradas:");
        for (String s : senhas) {
            exibirContagem(s);
        }

        scanner.close();
    }

    static String gerarSenha(int tamanho,
                             boolean usaMaiuscula,
                             boolean usaMinuscula,
                             boolean usaNumero,
                             boolean usaEspecial,
                             Random random) {

        String maiusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String minusculas = "abcdefghijklmnopqrstuvwxyz";
        String numeros = "0123456789";
        String especiais = "!@#$%¨&*()";

        String permitidos = "";

        if (usaMaiuscula) permitidos += maiusculas;
        if (usaMinuscula) permitidos += minusculas;
        if (usaNumero) permitidos += numeros;
        if (usaEspecial) permitidos += especiais;

        StringBuilder senha = new StringBuilder();

        // garantir pelo menos um caractere de cada grupo obrigatório
        if (usaMaiuscula) senha.append(maiusculas.charAt(random.nextInt(maiusculas.length())));
        if (usaMinuscula) senha.append(minusculas.charAt(random.nextInt(minusculas.length())));
        if (usaNumero) senha.append(numeros.charAt(random.nextInt(numeros.length())));
        if (usaEspecial) senha.append(especiais.charAt(random.nextInt(especiais.length())));

        // completar o restante da senha
        while (senha.length() < tamanho) {
            senha.append(permitidos.charAt(random.nextInt(permitidos.length())));
        }

        // embaralhar caracteres
        return senha.chars()
                .mapToObj(c -> (char) c)
                .sorted((a, b) -> random.nextInt(3) - 1)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    static boolean validaSenha(String senha,
                               boolean usaMaiuscula,
                               boolean usaMinuscula,
                               boolean usaNumero,
                               boolean usaEspecial) {

        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temEspecial = false;

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);

            // classificacao do caractere
            if (Character.isUpperCase(c)) temMaiuscula = true;
            else if (Character.isLowerCase(c)) temMinuscula = true;
            else if (Character.isDigit(c)) temNumero = true;
            else temEspecial = true;

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
