package com.concretepage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.concretepage.dao.ArticleRepo_Dung_Cai_Nay_Hay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.concretepage.dao.IArticleDAO;
import com.concretepage.entity.Article;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService implements IArticleService {
    //	@Autowired
//	private IArticleDAO articleDAO;
    @Autowired
    private ArticleRepo_Dung_Cai_Nay_Hay articleDAO;

    @Override
    public Article getArticleById(int articleId) {
//		Article obj = articleDAO.getArticleById(articleId);
        Article obj = articleDAO.findOne(articleId);
        return obj;
    }

    @Override
    public List<Article> getAllArticles() {
//		return articleDAO.getAllArticles();
        Iterable<Article> iter = articleDAO.findAll();
        List<Article> list = new ArrayList<>();
        iter.forEach(a -> list.add(a));
        return list;
    }

    @Override
    public boolean addArticle(Article article) {
//       if (articleDAO.articleExists(article.getTitle(), article.getCategory())) {
//    	   return false;
//       } else {
//    	   articleDAO.addArticle(article);
//    	   return true;
//       }
    	   articleDAO.save(article);
        return true;
    }

    @Override
    public void updateArticle(Article article) {
//		articleDAO.updateArticle(article);
        articleDAO.save(article);
    }

    @Override
    public void deleteArticle(int articleId) {
        articleDAO.delete(articleId);
    }
}
