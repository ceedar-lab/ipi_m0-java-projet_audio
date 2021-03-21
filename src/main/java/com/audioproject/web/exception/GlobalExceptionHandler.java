package com.audioproject.web.exception;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    final String NO_ELEMENT = "L'élément n'existe pas.";
    final String ELEMENT_EXISTS = "L'élément existe déjà.";
    final String WRONG_LABEL = "Veuillez entrer un nom d'artiste ou un titre d'album correct.";
    final String WRONG_PARAMETER = "Au moins un des paramètres est incorrect.";
    final String UNAUTHORIZED = "Méthode non autorisée.";

    /**
     * Renvoie un code 404 si l'entité n'a pas été trouvée.
     * @param e
     * @return
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoSuchElementException(NoSuchElementException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", NO_ELEMENT);
        modelAndView.addObject("status", HttpStatus.NOT_FOUND.value());
        return modelAndView;
    }

    /**
     * Renvoie un code 404 si l'entité n'a pas été trouvée.
     * @param e
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", NO_ELEMENT);
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    /**
     * Renvoie un code 409 si l'entité créée existe déjà.
     * @param e
     * @return
     */
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView handleEntityExistsException(EntityExistsException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.CONFLICT);
        modelAndView.addObject("error", ELEMENT_EXISTS);
        modelAndView.addObject("status", HttpStatus.CONFLICT.value());
        return modelAndView;
    }

    /**
     * Renvoie un code 400 si le champ est vide ou dépasse la longueur autorisée.
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("error", WRONG_LABEL);
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    /**
     * Renvoie un code 400 si la valeur du paramètre est incorrecte.
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("error", WRONG_PARAMETER);
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    /**
     * Renvoie un code 400 si le paramètre est incorrect.
     * @param e
     * @return
     */
    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handlePropertyReferenceException(PropertyReferenceException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("error", WRONG_PARAMETER);
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    /**
     * Renvoie un code 400 si le type du paramètre est incorrect.
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("error", WRONG_PARAMETER);
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST.value());
        return modelAndView;
    }

    /**
     * Renvoie un code 405 si l'utilisateur appelle une méthode interdite.'
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.METHOD_NOT_ALLOWED);
        modelAndView.addObject("error", UNAUTHORIZED);
        modelAndView.addObject("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        return modelAndView;
    }
}
