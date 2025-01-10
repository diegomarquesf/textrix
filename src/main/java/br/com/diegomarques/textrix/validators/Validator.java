package br.com.diegomarques.textrix.validators;

public interface Validator<T> {
    boolean isValid(T value);
    String getErrorMessage();
}
