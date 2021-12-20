package com.checkout;

public class CheckOut {
  def steps
  
  public CheckOut(steps) {
    this.steps = steps
  }
  
  public void startBuild() {
    steps git url:"https://github.com/dhrubthakur1/shared-lib-pipeline.git", branch:"main"
  }
}