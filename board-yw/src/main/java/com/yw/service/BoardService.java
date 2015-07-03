package com.yw.service;

import java.util.List;

import com.yw.bean.Word;

public interface BoardService {
	
	public List<Word> listAll();
	public Word selectOne(int objectId);
	public int insertOne(Word word);
	public int deleteOne(int objectId);
	public int updateOne(Word word);
	

}
