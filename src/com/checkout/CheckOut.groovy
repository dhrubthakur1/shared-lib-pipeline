package com.checkout;

public class CheckOut {
  def steps
  
  public CheckOut(steps) {
    this.steps = steps
  }
  
  public void startBuild() {
    steps.echo "Hi......${steps}"
    git url:"https://github.com/dhrubthakur1/spring4-mvc-example.git", branch:"main"
  }
}
