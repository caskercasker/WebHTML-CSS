package com.sist.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.MovieDAO;
import com.sist.vo.MovieVo;
import com.sist.vo.NewsVO;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/*//�����ϰ� �����ϴ� �ڵ� �κ�
 * <div class="info_tit">
		<em class="ico_movie ico_15rating">15���̻������</em>
			<a href="/moviedb/main?movieId=122091" class="name_movie #title">������ �����</a>
	</div>
 */

/*
 * <div class="wrap_news">
	<ul class="list_line #list">

							<li>
				<a href="http://v.movie.daum.net/v/20200218165207877" class="thumb_line bg_noimage2 @1">
				<span class="thumb_img" style="background-image:url(//img1.daumcdn.net/thumb/S320x200/?fname=https://t1.daumcdn.net/news/202002/18/starnews/20200218165207830ydsf.jpg);"></span>
				</a>
				<span class="cont_line">
					<strong class="tit_line"><a href="http://v.movie.daum.net/v/20200218165207877" class="link_txt @1">유아인·공효진, 코로나19로 버버리 패션쇼 입장 금지? "사실무근" </a></strong>
					<a href="http://v.movie.daum.net/v/20200218165207877" class="desc_line @1">
						[스타뉴스 김미화 기자]유아인, 공효진 / 사진=스타뉴스 배우 유아인 공효진 등이 코로나19 여파로 영국 버버리 패션쇼 참석을 거부당했다는 보도가 나온 가운데 양쪽 배우 모두 "사실이 아니다"라는 입장을 밝혔다. 18일 한 매체는 배우 유아인 공효진이 17일 영국 런던에서 열린 버버리 컬렉션 쇼에 참석할 예정이었으나, 코로나19 안전 대책이 마련되지 않아
					</a>
					<span class="state_line">
						스타뉴스<span class="txt_dot">・</span><span class="screen_out">발행일자</span>20.02.18
					</span>
				</span>
			</li>
 * 
 * 
 * 
 * 
 */

public class MovieManager {
	MovieDAO dao = MovieDAO.newInstance(); //싱글턴 개체 생성 
	public ArrayList<MovieVo> movieListData(){
		ArrayList<MovieVo> list = new ArrayList<MovieVo>();
		//MovieDAO dao = MovieDAO.newInstance(); //싱글턴 개체 생성 
		try {
			int k=1;
			//for(int i=1; i<=7; i++)
			{
				Document doc = Jsoup.connect("https://movie.daum.net/boxoffice/yearly").get();
				Elements link = doc.select("div.info_tit a");
				for (int j=0; j<link.size(); j++) {
					try {

					Element elem = link.get(j);
					//System.out.println((i)+"-"+elem.attr("href"));
					String mLink = "https://movie.daum.net"+elem.attr("href");
					Document doc2 = Jsoup.connect(mLink).get();

					Element title = doc2.selectFirst("div.subject_movie strong.tit_movie");
					System.out.println(title.text());
					//Element score = doc.selectFirst("a.raking_grade em.emph_grade");
					Element score = doc2.selectFirst("div.subject_movie em.emph_grade");
					System.out.println(score.text());
					Element genre = doc2.selectFirst("div.movie_summary dd.txt_main");
					System.out.println(genre.text());
					//Element regdate = doc2.selectFirst("dl.list_movie dd.text_main:eq(1)");
					Element regdate = doc2.select("dl.list_movie dd.txt_main").get(1);
					System.out.println(regdate.text());
					Element time = doc2.select("dl.list_movie dd").get(3); //���� ���� ���� String�� ����
					//System.out.println(time.text());

					String temp = time.text();
					StringTokenizer st = new StringTokenizer(temp,",");
					String strTime = st.nextToken();
					String strGrade = st.nextToken().trim();

					System.out.println(strTime);
					System.out.println(strGrade);

					Element grade;
					Element director = doc2.select("dl.list_movie dd.type_ellipsis").get(0);
					System.out.println(director.text());
					Element actor = doc2.select("dl.list_movie dd.type_ellipsis").get(1);
					System.out.println(actor.text());
					Element story = doc2.selectFirst("div.desc_movie p");
					System.out.println(story.text());
					Element poster= doc2.selectFirst("a.area_poster img.img_summary");

					MovieVo mv = new MovieVo();
					//mv.setMno(i);
					mv.setTitle(title.text());
					mv.setScore(Double.parseDouble(score.text()));
					mv.setGenre(genre.text());
					mv.setRegdate(regdate.text());
					mv.setGrade(strGrade);
					mv.setTime(strTime);
					mv.setDirector(director.text());
					mv.setActor(actor.text());
					mv.setStory(story.text());
					mv.setPoster(poster.attr("src"));
					mv.setType(5);  //2번 실행
					//dao.insertValue(mv);
					
					dao.movieInsert(mv); 
					System.out.println("k="+k);
					k++;
					}catch(Exception ex) {}
				}
			}
			System.out.println("DATABase Insert END");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return list;
	}
	
	
	public ArrayList<NewsVO> newsALLData(){
		ArrayList<NewsVO> list = new ArrayList<NewsVO>();
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //20200218
		String today=sdf.format(date);
		
		try {
			for(int i=1; i<=18; i++){
			Document doc=Jsoup.connect("https://movie.daum.net/magazine/new?tab=nws&regdate="+today+"&page="+i).get();     //html 도큐먼트 가져오기 
			Elements title = doc.select("ul.list_line strong.tit_line a");
			Elements poster = doc.select("ul.list_line a.thumb_line span.thumb_img");
			Elements link = doc.select("ul.list_line strong.tit_line a");
			Elements temp = doc.select("span.cont_line span.state_line");
			Elements content = doc.select("span.cont_line a.desc_line");
				for(int j=0; j<title.size(); j++){
					System.out.println(title.get(j).text());
					
					String ss=poster.get(j).attr("style");
					
					ss=ss.substring(ss.indexOf("(")+1,ss.lastIndexOf(")"));
					//System.out.println(poster.get(j).attr("style"));
					System.out.println(ss);
					System.out.println(link.get(j).attr("href"));
					String str=temp.get(j).text();
					String author=str.substring(0,str.indexOf("・"));
					
					String regdate=str.substring(str.lastIndexOf("자")+1);
					
					System.out.println(author);
					System.out.println(regdate);
					//System.out.println(temp.get(j).text());
					System.out.println(content.get(j).text());
					System.out.println("=====================================");
					
					NewsVO vo = new NewsVO();
					vo.setTitle(title.get(j).text());
					vo.setLink(link.get(j).attr("href"));
					vo.setContent(content.get(j).text());
					vo.setPoster(ss);
					vo.setAuthor(author);
					vo.setRegdate(regdate);
					//오라클에 전송
					dao.NewsInsert(vo);
					Thread.sleep(200);
				}
			}
			System.out.println("Save End....");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MovieManager mm = new MovieManager();
		//mm.movieListData();
		mm.newsALLData();
	}
}
