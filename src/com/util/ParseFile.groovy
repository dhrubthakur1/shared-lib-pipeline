package com.util;

public class ParseFile{

	public ParseFile(){
		echo "ParseFile called"
	}
	public void readFile(){
		echo "ParseFile readFile called"
		 def datas = readYaml file: 'build.yml',
		System.out.println(datas);
	}
}
