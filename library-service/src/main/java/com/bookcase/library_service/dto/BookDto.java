package com.bookcase.library_service.dto;

import lombok.Data;

@Data
public class BookDto {

    private String id;
    private String title;
    private String author;
    private Integer bookYear;
    private String pressName;
    private String isbn;


}
