package com.bookcase.library_service.client;

import com.bookcase.library_service.exception.BookNotFoundException;
import com.bookcase.library_service.exception.ExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RetriveMessageErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        ExceptionMessage message = null;
        try (InputStream body = response.body().asInputStream()){
            message = new ExceptionMessage((String) response.headers().get("date").toArray()[0],
                    response.status(),
                    HttpStatus.resolve(response.status()).getReasonPhrase(),
                    IOUtils.toString(body, StandardCharsets.UTF_8),
                    response.request().url());

        }catch (IOException e){
            return new Exception(e.getMessage());
        }
        switch (response.status()){
            case 404:
                throw new BookNotFoundException(message);

            default:
                return errorDecoder.decode(s,response);
        }
    }
}
