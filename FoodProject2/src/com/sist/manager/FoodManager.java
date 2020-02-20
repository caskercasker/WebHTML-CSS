package com.sist.manager;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.CategoryVO;

import com.sist.dao.*;
public class FoodManager {
	public ArrayList<CategoryVO> categoryAllData(){
		ArrayList<CategoryVO> list = new ArrayList<CategoryVO>();
		
		try{
			Document doc = Jsoup.connect("https://www.mangoplate.com/").get();
			Elements title = doc.select("div.info_inner_wrap span.title");
			Elements poster = doc.select("ul.list-toplist-slider img.center-croping"); //요소 분석시 & 구문 존재, 오라클이 실행 됬을시 input으로 인식하기 때문에 변경 해야 함. 
			Elements subject = doc.select("div.info_inner_wrap p.desc");
			Elements link = doc.select("ul.list-toplist-slider li a");
		
			for(int i=0; i<12; i++)	{
				System.out.println(title.get(i).text());
				System.out.println(subject.get(i).text());
				System.out.println(link.get(i).attr("href"));
				System.out.println(poster.get(i).attr("data-lazy"));
				
				CategoryVO vo = new CategoryVO();
				vo.setCateno(i+1);
				vo.setTitle(title.get(i).text());
				vo.setSubject(subject.get(i).text());
				vo.setLink("https://www.mangoplate.com"+link.get(i).attr("href"));
				String temp = poster.get(i).attr("data-lazy");
				temp = temp.replace("&","@"); //주소 몬자열에서 & 바꾸기
				vo.setPoster(temp);
				
				list.add(vo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void main(String[] args){
		FoodManager fm = new FoodManager();
		ArrayList<CategoryVO> list = fm.categoryAllData();		
		FoodDAO dao = FoodDAO.newInstance();
		int k=1;
		for(CategoryVO vo:list){
			dao.CategoryInsert(vo);
			System.out.println("k="+k);
			k++;
		}
		System.out.println("Save End...");
	}
}
