package com.concretepage.controller;

import com.concretepage.entity.Article;
import com.concretepage.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Article> getAllArticles() {
        List<Article> list = articleService.getAllArticles();
        return list;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Article getArticleById(@PathVariable("id") Integer id) {
        Article article = articleService.getArticleById(id);
        return article;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean addArticle(@RequestBody Article article) {
        return articleService.addArticle(article);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public boolean updateArticle(@RequestBody Article article) {
        articleService.updateArticle(article);
        return true;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
        return true;
    }
} 