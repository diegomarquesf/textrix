package br.com.diegomarques.textrix.validators;

import java.util.InputMismatchException;

public class CpfValidator implements Validator<String> {

    @Override
    public boolean isValid(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf == null || cpf.length() != 11)
            return false;

        // Verifica se todos os números são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}"))
            return false;

        try {
            // Calcula os dígitos verificadores
            int digito1 = calculateDigit(cpf.substring(0,9), true);
            int digito2 = calculateDigit(cpf.substring(0,9) + digito1, false);

            // Compara os dígitos calculados com os dígitos do CPF
            return cpf.charAt(9) - '0' == digito1 && cpf.charAt(10) - '0' == digito2;
        } catch (InputMismatchException ignored) {
            return false;
        }
    }

    private static int calculateDigit(String str, boolean isFirstDigit) {
        int total = 0;
        int peso = isFirstDigit ? 10 : 11;
        for (int i = 0; i < str.length(); i++) {
            total += (str.charAt(i) - '0') * peso--;
        }
        int resto = total % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    @Override
    public String getErrorMessage() {
        return "O campo 'CPF' está inválido!";
    }
}
