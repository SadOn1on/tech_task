package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.repository.BookRepository;
import com.ifortex.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultBookService implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public DefaultBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Map<String, Long> getBooks() {
        return bookRepository.getBooksGenres()
                .stream()
                .flatMap(Set::stream)
                // count genre occurrences
                .collect(
                        Collectors.groupingBy(
                                genre -> genre,
                                Collectors.counting()
                        ))
                // sort by count and put into Map with predictable iteration order (LinkedHashMap)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
