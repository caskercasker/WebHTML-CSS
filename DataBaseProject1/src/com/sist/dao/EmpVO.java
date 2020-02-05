package com.sist.dao;
/*
 EMPNO                                              NUMBER	=> int
 ENAME                                              VARCHAR2(10) => String
 JOB                                                VARCHAR2(9) => String
 MGR                                                NUMBER(4)	=> int
 HIREDATE                                           DATE		=> Date
 SAL                                                NUMBER(7,2)	=> int
 COMM                                               NUMBER(7,2)	=> int
 DEPTNO                                             NUMBER		=> int

	문자형 char,varchar2,clob
	숫자형 number(4),number => int 		number(7,2) => double, int
	date Date => java.util.Date
 *
 */
import java.util.*;
public class EmpVO {
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private Date hiredate;
	private int sal;
	private int comm;
	private int deptno;
	
	//get -> 출력
	public int getEmpno() {
		return empno;
	}
	//오라클에서 값 바
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getMgr() {
		return mgr;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public int getSal() {
		return sal;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	public int getComm() {
		return comm;
	}
	public void setComm(int comm) {
		this.comm = comm;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	
	
}
