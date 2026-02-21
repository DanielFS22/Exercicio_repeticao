# Gerador e Validador de Senhas em Java

Este projeto é um programa em Java que permite ao usuário cadastrar múltiplas senhas, aplicando regras de segurança definidas por ele mesmo.

## Funcionalidades

* Definir quantas senhas deseja criar
* Escolher quais grupos de caracteres a senha deve conter:

  * Letras maiúsculas
  * Letras minúsculas
  * Números
  * Símbolos especiais
* Exigir no mínimo **2 grupos de caracteres**
* Validar cada senha digitada com regras de segurança
* Armazenar as senhas válidas em uma lista
* Exibir a contagem de caracteres de cada senha

## Regras de Validação

* Tamanho entre **8 e 32 caracteres**
* Deve conter os grupos selecionados
* Não permite **3 caracteres iguais seguidos**
* Não permite **sequências numéricas** (ex: 123 ou 321)

## Como executar

1. Compile o arquivo:

   ```
   javac Main.java
   ```
2. Execute o programa:

   ```
   java Main
   ```

## Observação

O projeto foi desenvolvido com foco em lógica, validação de strings e uso de estruturas básicas da linguagem Java.
