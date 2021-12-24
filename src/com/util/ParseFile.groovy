package com.util;

public class ParseFile{

	public ParseFile(){
	}
	public void readFile(){
		 def datas = readYaml file: 'build.yml',
		System.out.println(datas);
	}
}