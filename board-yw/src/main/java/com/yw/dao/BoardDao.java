package com.yw.dao;

import java.util.List;

import com.yw.bean.Word;

public interface BoardDao {
	
	public List<Word> listAll();
	public Word selectOne(int objectId);
	public int insertOne(Word word);
	public int deleteOne(int objectId);
	public int updateOne(Word word);
}
