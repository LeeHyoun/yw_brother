package com.yw.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yw.bean.Word;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Autowired
	private SqlSessionTemplate sqlSession11;
	
	private static final String NS = "com.yw.mapper.SelectMapper.";
	
	public void setSqlSession11(SqlSessionTemplate sqlSession11) {
		this.sqlSession11 = sqlSession11;
	}

	/* 단어 list */
	@Override
	public List<Word> listAll() {
		return sqlSession11.selectList("listAll");
	}
	
	
	/* 해당 레코드 한건 */
	@Override
	public Word selectOne(int objectId) {
		return sqlSession11.selectOne(NS + "selectOne",objectId);
	}

	
	/* 단어 추가 */
	@Override
	public int insertOne(Word word) {
		return sqlSession11.insert(NS + "insertOne",word);

	}

	
	/* 단어 삭제 */
	@Override
	public int deleteOne(int objectId) {
		return sqlSession11.delete(NS + "deleteOne",objectId);

	}
	
	
	/* 단어 업데이트 */
	@Override
	public int updateOne(Word word) {
		return sqlSession11.update(NS + "updateOne",word);
	}

}
