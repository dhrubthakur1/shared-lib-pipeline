package com.util;
//import groovy.yaml.YamlSlurper;

public class ParseFile{

	public ParseFile(){
		bat "echo ParseFile called";
	}
	public void readFile(){
		bat "echo ParseFile readFile called";
		//def config = new YamlSlurper().parseText("C:/Users/66480/.jenkins/workspace/shared-lib-spec/build.yml")
		def config = readYaml(file: "C:/Users/66480/.jenkins/workspace/shared-lib-spec/build.yml")
				 
		bat "echo ${config}"
	}
}
