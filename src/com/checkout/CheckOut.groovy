package com.checkout;
import com.util.*;

public class CheckOut {
  def steps
  
  public CheckOut(steps) {
    this.steps = steps
  }
  
  public void startBuild(def conf = [:]) {
    steps.echo '"CheckOut Startbuild is called"'
    steps.echo "${steps} ${conf.url}  ${conf.branch}"    
    steps.checkout([
                    $class: 'GitSCM',
                    branches: [[name:  conf.branch ]],
                    userRemoteConfigs: [[ url: conf.url ]]
                  ])
    steps.bat 'dir'
    def datas = readYaml file: 'build.yml'
    steps.echo "${datas}"   
    
    //steps.git url:conf.url, branch:conf.branch
    //steps.git url:"https://github.com/dhrubthakur1/spring4-mvc-example.git", branch:"main"   
  }
}
