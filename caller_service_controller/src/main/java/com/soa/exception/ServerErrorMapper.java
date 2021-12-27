package com.soa.exception;

import com.soa.dto.ExceptionDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerErrorMapper implements ExceptionMapper<RuntimeException> {

    public Response toResponse(RuntimeException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage("Произошла внутренняя ошибка сервера :с");

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(exceptionDTO)
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}