package com.sist.dao;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MovieDAO dao =MovieDAO.newInstance();

		ArrayList<MovieVo> list = dao.movieListData(5, 2);
		//ArrayList<MovieVo> list = dao.test(2);
		for(MovieVo vo:list){
			System.out.println(vo.getTitle()+" "+vo.getScore());
		}
	}

}
