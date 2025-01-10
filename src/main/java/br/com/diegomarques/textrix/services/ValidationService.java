package br.com.diegomarques.textrix.services;

import br.com.diegomarques.textrix.services.exceptions.DataIntegrityException;
import br.com.diegomarques.textrix.validators.CnpjValidator;
import br.com.diegomarques.textrix.validators.CpfValidator;
import br.com.diegomarques.textrix.validators.Validator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationService {

    private final Map<Class<?>, Validator<?>> validators = new HashMap<>();

    public ValidationService() {
        validators.put(CpfValidator.class, new CpfValidator());
        validators.put(CnpjValidator.class, new CnpjValidator());
    }

    public <T> void validate(Class<? extends Validator<T>> validatorClass, T value) {
        Validator<T> validator = (Validator<T>) validators.get(validatorClass);
        if (validator == null) {
            throw new IllegalArgumentException("Validador não encontrado para " + validatorClass.getSimpleName());
        }
        if (!validator.isValid(value)) {
            throw new DataIntegrityException(validator.getErrorMessage());
        }
    }
}
