package com.bookcase.library_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookIdDto {

    private String id;
    private String isbn;

    public static BookIdDto convert(String id, String isbn) {
        return new BookIdDto(id, isbn);
    }
}
