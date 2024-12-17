package com.bookcase.library_service.service;


import com.bookcase.library_service.client.BookServiceClient;
import com.bookcase.library_service.dto.AddBookRequest;
import com.bookcase.library_service.dto.LibraryDto;
import com.bookcase.library_service.entity.Library;
import com.bookcase.library_service.exception.LibraryNotFoundException;
import com.bookcase.library_service.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository  libraryRepository;
    private final BookServiceClient bookServiceClient;

    public LibraryDto getAllBooksInLibraryById(String id){
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by ID: " + id));
        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(bookServiceClient::getBookById)
                        .map(ResponseEntity::getBody)
                        .collect(Collectors.toList()));
        return libraryDto;
    }

    public LibraryDto createLibrary(){
        Library newLibrary = libraryRepository.save(new Library());
        return new LibraryDto(newLibrary.getId());
    }

    public void addBookToLibrary(AddBookRequest request) {
        String bookId = bookServiceClient.getBookByIsbn(request.getIsbn()).getBody().getId();

        Library library = libraryRepository.findById(request.getId())
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by ID: " + request.getId()));

        library.getUserBook().add(bookId);

        libraryRepository.save(library);
    }
}
