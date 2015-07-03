package com.yw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yw.bean.Word;
import com.yw.service.BoardService;


@Controller
public class BoardMainController {
	
	@Autowired
	private BoardService boardService;
	
	
	/*
	 * classpath
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String boardMain() {
		
		return "home";
	}
	
	/*
	 * 단어 리스트 가져오기
	 */
	@RequestMapping(value="/boardList")
	@ResponseBody
	public List<Word> boardList(){
		
		System.out.println("리스트뽑아주오");
		
		//boardService.listAll();
		
		return boardService.listAll();
	}
	
	
	/*
	 * 단어 추가 페이지로 이동.
	 */
	@RequestMapping(value = "/boardInsertPage", method = RequestMethod.GET)
	public String boardInsertPage(){
		
		System.out.println("추가페이지 간다");
		
		return "boardInsertPage";
	}
	
	
	/*
	 * 단어 추가 하기
	 */
	@RequestMapping(value = "/boardInsert", method = RequestMethod.POST)	
	@ResponseBody
	public String boardInsert(@Valid Word word , Errors errors){
		
		System.out.println("추가 등록 간다");

		System.out.println("word : " + word.toString());
		if(errors.hasErrors()){
			System.out.println("insert errors");
			return "boardInsert";
		}
		
		
		boardService.insertOne(word);
		System.out.println("insert success");
		
		
		return "boardInsert";
	}
	
	
	/*
	 * 단어 삭제 페이지로 이동.
	 */
	@RequestMapping(value = "/boardDeletePage", method = RequestMethod.GET)
	public String boardDeletePage(@RequestParam int objectId ,Model model){
		
		System.out.println("삭제페이지 간다");
		model.addAttribute("word",boardService.selectOne(objectId));
		
		return "boardDeletePage";
	}
	
	
	/*
	 * 단어 삭제 하기
	 */
	@RequestMapping(value = "/boardDelete", method = RequestMethod.POST)	
	@ResponseBody
	public String boardDelete(@RequestParam int objectId){
		
		System.out.println("삭제 간다");
		System.out.println("wordNum = " + objectId);
		boardService.deleteOne(objectId);
		System.out.println("delete success");
		
		return "boardDelete";
	}
	
	
	/*
	 * 단어 수정 페이지로 이동.
	 */
	@RequestMapping(value = "/boardUpdatePage", method = RequestMethod.GET)
	public String boardUpdatePage(@RequestParam int objectId , Model model){
		
		System.out.println("수정페이지 간다");
		model.addAttribute("word",boardService.selectOne(objectId));
		
		return "boardUpdatePage";
	}
	
	
	/*
	 * 단어 수정 하기
	 */
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String boardUpdate(@Valid Word word){
		
		System.out.println("수정 간다");
		boardService.updateOne(word);
		System.out.println("update success");
		
		return "boardUpdate";
	}
	
	
	/*
	 * 검색할 단어 찾기
	 */
	@RequestMapping(value ="/searchWord", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Word> searchWord(@RequestParam String str){
		
		ArrayList<Word> res = new ArrayList<Word>();
		
		List<Word> word = boardService.listAll(); //전체 단어리스트 읽어오기
		
		String[] sector = str.split(" "); //공백을 구분문자로 잘라 가져오기  
		
				
		for (int i = 0; i < sector.length; i++) {
			
			sector[i] = sector[i].toLowerCase();  //전부 소문자로 변환.
		} 
		
		
		/* 단어명을 소문자로 바꾸어 다시 적재. */
		for (int i = 0; i < word.size(); i++) {
			
			String ddd = word.get(i).getWordName().toLowerCase();
			word.get(i).setWordName(ddd);
		}
		
		
		for (int i = 0; i < word.size(); i++) { //단어길이
			
			String dbname = word.get(i).getWordName(); //단어 하나를 꺼내 dbname 에 담기
			
			for (int j = 0; j < sector.length; j++) {  //검색한 단어만큼 돌아라. ex) 표준 단어 - 표준, 단어
				
				//단어 길이와 검색단어 길이 비교 
				for (int j2 = 0; j2 < dbname.length() - sector[j].length() + 1; j2++) {
					
					//검색단어와 dbname의 레퍼런스가 같으면 => res 에 추가.
					if (sector[j].equals( dbname.substring( j2, sector[j].length() + j2))){
                           res.add( word.get(i));        
                           break;
                    }
				}
			}
		}
		
		
		//res 에 들어있는 레코드 확인.
		for (int i = 0; i < res.size(); i++){
			System.out.println("res: " + res.get(i));
		}
				
		
		for (int j = 0; j < sector.length ; j ++) {
			
            for (int i = 1; i < res.size(); i++) {
	        
            	 if (res.get(i -1).getWordName().equals( res.get( i).getWordName())){
	                      res.remove( i);
	             }
            }
		}
     
		
		for (int i = 0; i < res.size(); i++) {
            //res.get( i).setWordName( res.get( i).getWordName().substring(0, 1).toUpperCase()+
			
			/* 검색 완료시 단어명의 첫 글자가 대문자로 변환되어 수정. */
			res.get( i).setWordName( res.get( i).getWordName().substring(0, 1) +
                          res.get( i).getWordName().substring(1, res.get(i ).getWordName().length()));
		}
		
		
		return res;
	}
}
