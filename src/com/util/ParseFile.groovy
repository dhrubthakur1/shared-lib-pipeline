package com.util;
//import groovy.yaml.YamlSlurper;

public class ParseFile{

	public ParseFile(){
		System.out.println("ParseFile called");
	}
	public void readFile(){
		System.out.println("ParseFile readFile called");
		//def config = new YamlSlurper().parseText("C:/Users/66480/.jenkins/workspace/shared-lib-spec/build.yml")
		def config = readYaml(file: "C:/Users/66480/.jenkins/workspace/shared-lib-spec/build.yml")
				 
		System.out.println(config);
	}
}
