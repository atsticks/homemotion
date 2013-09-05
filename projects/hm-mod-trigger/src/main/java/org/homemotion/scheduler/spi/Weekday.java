package org.homemotion.scheduler.spi;

public enum Weekday {
  Mo(2),
  Tu(3),
  We(4),
  Th(5),
  Fr(6),
  Sa(7),
  Su(1);
  
  private int val;
  
  private Weekday(int val){
	  this.val = val;
  }
  
  public int getValue(){
	  return val;
  }
}
