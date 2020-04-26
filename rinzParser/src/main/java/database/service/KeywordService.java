package database.service;

import database.dao.KeywordDao;
import database.model.Keyword;

import java.util.List;

public class KeywordService {
    private KeywordDao keywordDao = new KeywordDao();

    public KeywordService() {
    }

    public Keyword findKeyword(int id) {
        return keywordDao.findById(id);
    }

    public void saveKeyword(Keyword keyword) {
        keywordDao.save(keyword);
    }

    public void deleteKeyword(Keyword keyword) {
        keywordDao.delete(keyword);
    }

    public void updateKeyword(Keyword keyword) {
        keywordDao.update(keyword);
    }

    public List<Keyword> findAllKeywords() {
        return keywordDao.findAll();
    }

    public Keyword findByKeyword(String keyword) {
        return keywordDao.findByKeyword(keyword);
    }

    public void openConnection() {
        keywordDao.openConnection();
    }

    public void closeConnection() {
        keywordDao.closeConnection();
    }
}
