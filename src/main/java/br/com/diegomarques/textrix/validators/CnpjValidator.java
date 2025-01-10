package br.com.diegomarques.textrix.validators;

import java.util.InputMismatchException;

public class CnpjValidator implements Validator<String>{
    @Override
    public boolean isValid(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ tem 14 dígitos
        if (cnpj == null || cnpj.length() != 14)
            return false;

        // Verifica se todos os números são iguais (CNPJ inválido)
        if (cnpj.matches("(\\d)\\1{13}"))
            return false;

        try {
            // Calcula os dígitos verificadores
            int digito1 = calculateDigit(cnpj.substring(0,12), true);
            int digito2 = calculateDigit(cnpj.substring(0,12) + digito1, false);

            // Compara os dígitos calculados com os dígitos do CNPJ
            return cnpj.charAt(12) - '0' == digito1 && cnpj.charAt(13) - '0' == digito2;
        } catch (InputMismatchException ignored) {
            return false;
        }
    }

    private static int calculateDigit(String str, boolean isFirstDigit) {
        int total = 0;
        int peso = isFirstDigit ? 5 : 6;
        for (int i = str.length() - 1; i >= 0; i--) {
            total += (str.charAt(i) - '0') * peso--;
            if (peso < 2) peso = 9;
        }
        int resto = total % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    @Override
    public String getErrorMessage() {
        return "O campo 'CNPJ' está inválido!";
    }
}
