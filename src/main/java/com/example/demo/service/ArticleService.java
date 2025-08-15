package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ArticleService {

    private final ArticleRepository repository;
    private final AuthorService authorService;

    public ArticleService(ArticleRepository repository, AuthorService authorService) {
        this.repository = repository;
        this.authorService = authorService;
    }

    public Article findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Iterable<Article> findAll() {
        return repository.findAll();
    }

    public Article save(Article article) {
        var saved = repository.save(article);
        var author = authorService.findById(saved.getAuthor().getId());
        saved.setAuthor(author);
        return saved;
    }

    public void delete(Article article) {
        repository.delete(article);
    }
}
