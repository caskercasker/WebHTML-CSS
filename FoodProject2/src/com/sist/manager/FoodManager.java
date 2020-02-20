package com.sist.manager;
import java.io.EOFException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

	public ArrayList<FoodHouseVO> foodAllData(){
		ArrayList<FoodHouseVO> flist = new ArrayList<FoodHouseVO>();
		try {
			ArrayList<CategoryVO> clist=categoryAllData();
			int k=1;
			for(CategoryVO cvo:clist){
				Document doc = Jsoup.connect(cvo.getLink()).get();
				Elements link=doc.select("div.info span.title a");
				System.out.println(cvo.getTitle());
				
	
				
				for (int j=0; j<link.size(); j++){
					try{
					String url="https://www.mangoplate.com/"+link.get(j).attr("href");
					
					Document doc2 = Jsoup.connect(url).get();
					
					Elements poster=doc2.select("figure.list-photo img.center-croping");
					String img="";
					for(int a=0; a<poster.size(); a++){
						//System.out.println(poster.get(a).attr("src"));
						img+=poster.get(a).attr("src")+"^";   //문자열 구분하기위해 ^ 끝줄에 표시 
					}
					img=img.substring(0,img.lastIndexOf("^")); //문자열 구분 기준 ^ 값까지를 가져옴 
					
					System.out.println("********************");
					Element title=doc2.selectFirst("span.title h1.restaurant_name"); // select("").get(0)
					Element score=doc2.selectFirst("span.title strong.rate-point span");
					Element address=doc2.select("table.info tr td").get(0);
					Element tel=doc2.select("table.info tr td").get(1);
					Element type=doc2.select("table.info tr td").get(2);
					Element price=doc2.select("table.info tr td").get(3);
					Element temp=doc2.selectFirst("script#reviewCountInfo");
					
					System.out.println("k="+k);
					System.out.println(title.text());
					System.out.println(score.text());
					System.out.println(address.text());
					System.out.println(tel.text());
					System.out.println(type.text());
					System.out.println(price.text());
					System.out.println(temp.data());
					System.out.println("=================================");
					
					FoodHouseVO vo = new FoodHouseVO();
					vo.setCno(cvo.getCateno());
					vo.setTitle(title.text());
					vo.setScore(Double.parseDouble(score.text()));
					vo.setAddress(address.text());
					vo.setTel(tel.text());
					vo.setType(type.text());
					vo.setPrice(price.text());
					vo.setImage(img);
					
					JSONParser parser=new JSONParser();   // [ ] 파싱한 결과 배열한 결과. 
					JSONArray arr=(JSONArray)parser.parse(temp.data());  //{ } 블록으로 구분된 Object  (key, value)
					
					System.out.println("JSON=>"+arr.toJSONString());
					/*
					 * 	{}
					 *  [{},{},{}]   , 타입 지정이 없다, [10, 'aaa'] 동적 언어 자바 스크립트 
					 * 
					 */
					long[] data = new long[3];
					
					
					for(int b=0; b<arr.size(); b++){
						JSONObject obj = (JSONObject)arr.get(b);
						System.out.println("Object=>"+obj.toJSONString());
						long count=(long)obj.get("count");
						data[b]=count;
						System.out.println("count="+count);
						}
						vo.setBad((int)data[0]);
						vo.setSoso((int)data[1]);
						vo.setGood((int)data[2]);
						
						flist.add(vo);
						k++;
					}catch (Exception ex){
						System.out.println(ex.getMessage());
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flist;
	}
	public static void main(String[] args){
		FoodManager fm = new FoodManager();
		//fm.foodAllData();
		ArrayList<FoodHouseVO> list = fm.foodAllData();
		FoodDAO dao=FoodDAO.newInstance();
		
		
		int k=1;
		for(FoodHouseVO vo:list){
			dao.FoodHouseInsert(vo);
			System.out.println("k="+k);
			k++;
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Save End...");
		//		ArrayList<CategoryVO> list = fm.categoryAllData();		
//		FoodDAO dao = FoodDAO.newInstance();
//		int k=1;
//		for(CategoryVO vo:list){
//			dao.CategoryInsert(vo);
//			System.out.println("k="+k);
//			k++;
//		}
//		System.out.println("Save End...");

	}
}
