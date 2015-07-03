package com.yw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yw.bean.Word;
import com.yw.dao.BoardDao;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<Word> listAll() {
		return boardDao.listAll();
	}

	@Override
	public Word selectOne(int objectId) {
		return boardDao.selectOne(objectId);
	}

	@Override
	public int insertOne(Word word) {
		return boardDao.insertOne(word);

	}

	@Override
	public int deleteOne(int wordNum) {
		return boardDao.deleteOne(wordNum);

	}

	@Override
	public int updateOne(Word word) {
		return boardDao.updateOne(word);

	}

}
