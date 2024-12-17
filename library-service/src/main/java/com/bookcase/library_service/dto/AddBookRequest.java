package com.bookcase.library_service.dto;

import lombok.Data;

@Data
public class AddBookRequest {

    private String id;
    private String isbn;
}
