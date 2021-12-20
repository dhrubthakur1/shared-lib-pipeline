package com.checkout;

public class CheckOut {
  def steps
  
  public CheckOut(steps) {
    this.steps = steps
  }
  
  public void startBuild(def conf = [:]) {
    steps.echo "${steps} ${conf.url}  ${conf.branch}"    
    checkout([$class: 'GitSCM',
          branches: [[name: 'master']],
          extensions: [],
          userRemoteConfigs: [[url: 'https://github.com/jenkinsci/git-plugin']]])
    //steps.git url:conf.url, branch:conf.branch
    //steps.git url:"https://github.com/dhrubthakur1/spring4-mvc-example.git", branch:"main"   
  }
}
