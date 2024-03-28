package br.maia.tiago.rinha;

import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Priority(1)
public class ConstraintViolationExceptionMapper
       implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // todo: return errors in the response body
        List<String> errors =
            exception.getConstraintViolations().stream().map(ConstraintViolation::toString).collect(Collectors.toList());
        return Response.status(422).type(MediaType.APPLICATION_JSON)
            .entity(errors)
            .build();
    }

}
