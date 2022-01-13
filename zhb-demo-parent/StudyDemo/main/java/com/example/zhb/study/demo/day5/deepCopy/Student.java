package com.example.zhb.study.demo.day5.deepCopy;

import java.util.List;

public class Student implements Cloneable{

	private String name;
	public List<Address> addressList;

	public Student(){
    }

	public Student(String name) {
		this.name = name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Object clone() {
		Student student = null;
		try {
			student = (Student) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return student;
	}
}