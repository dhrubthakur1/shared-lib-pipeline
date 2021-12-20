package com.build;

public class MVNBuild{
  def steps
  
  public MVNBuild(steps) {
    this.steps = steps
  }
  
  public void startBuild() {
    steps.bat "mvn clean package"
  }
}