package com.sist.dao;
import java.util.*;
public class EmpManager2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmpDAO2 dao=new EmpDAO2();
		ArrayList<EmpVO> list=dao.empAllData();		
		//출력
		for(EmpVO vo:list)
		{
			System.out.println(vo.getEmpno()+" "
					+vo.getEname()+" "
					+vo.getJob()+" "
					+vo.getMgr()+" "
					+vo.getHiredate().toString()+" "
					+vo.getSal()+" "
					+vo.getComm()+" "
					+vo.getDeptno());
		}
		
		Scanner scan=new Scanner(System.in);
//		System.out.println("사번 입력:");
//		int empno=scan.nextInt();
//
//		EmpVO vo=dao.empDetailData(empno);
//		System.out.println("************검색결과**************");
//		System.out.println(vo.getEmpno()+" "
//					+vo.getEname()+" "
//					+vo.getJob()+" "
//					+vo.getMgr()+" "
//					+vo.getHiredate().toString()+" "
//					+vo.getSal()+" "
//					+vo.getComm()+" "
//					+vo.getDeptno());
//		
//		
//		System.out.println("검색할 이름 입력:");
//		String ename=scan.next();
//		list=dao.empFindDate(ename.toUpperCase());
//		System.out.println("************검색결과**************");
//		for(EmpVO vo1:list) {
//		System.out.println(vo1.getEmpno()+" "
//					+vo1.getEname()+" "
//					+vo1.getJob()+" "
//					+vo1.getMgr()+" "
//					+vo1.getHiredate().toString()+" "
//					+vo1.getSal()+" "
//					+vo1.getComm()+" "
//					+vo1.getDeptno());
//			}
//		
		System.out.println("년도 입력:");
		int year=scan.nextInt();
		list=dao.empRangeData(year);
		System.out.println("****검색결과******");
		for(EmpVO vo1:list) {
		System.out.println(vo1.getEmpno()+" "
					+vo1.getEname()+" "
					+vo1.getJob()+" "
					+vo1.getMgr()+" "
					+vo1.getHiredate().toString()+" "
					+vo1.getSal()+" "
					+vo1.getComm()+" "
					+vo1.getDeptno());
			}
	}
}