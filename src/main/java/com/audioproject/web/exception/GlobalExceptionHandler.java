package com.audioproject.web.exception;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Renvoie un code 404 si l'entité n'a pas été trouvée.
     * @param e
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException e) {
        return e.getMessage();
    }

    /**
     * Renvoie un code 409 si l'entité créée existe déjà.
     * @param e
     * @return
     */
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEntityExistsException(EntityExistsException e) {
        return e.getMessage();
    }

    /**
     * Renvoie un code 400 si le champ est vide.
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return "Ce champ ne peut être vide, et ne doit pas commencer par un espace.";
    }

    /**
     * Renvoie un code 400 si la paramètre est incorrect.
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return "Erreur 400 : Le paramètre '" +e.getParameterName()+ "' est incorrect.";
    }

    /**
     * Renvoie un code 400 si la valeur du paramètre est incorrecte.
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) { return e.getMessage(); }

    /**
     * Renvoie un code 400 si le type du paramètre est incorrect.
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String wantedType;
        if (e.getRequiredType().getName().contains("Integer")) wantedType = "un nombre";
        else wantedType = "une chaine de caractères";
        return "Erreur 400 : Le paramètre " +e.getName()+ " de valeur '" +e.getValue()+ "' doit être " +wantedType+ ".";
    }

    /**
     * Renvoie un code 400 si la propriété n'existe pas.
     * @param e
     * @return
     */
    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePropertyReferenceException(PropertyReferenceException e) {
        return "Erreur 400 : La propriété '" +e.getPropertyName()+ "' n'existe pas.";
    }
}
