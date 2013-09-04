package org.homemotion.ui.admin.tree;
 
public enum ModeSelection {
   ALL("Alle"),
   TODAY("Heute"),
   WEEK("Diese Woche"),
   MONTH("Dieser Monat"),
   YEAR("Dieses Jahr"),
   NEXT_WEEK("Nächste Woche"),
   NEXT_14_DAYS("Nächste 14 Tage"),
   NEXT_MONTH("Nächster Monat"),
   NEXT_3_MONTHS("Nächste 3 Monate"),
   NEXT_6_MONTHS("Nächstes Halbjahr");
   
   private String name;
   
   private ModeSelection(String name) {
	this.name = name;
   }
   
   public String toString(){
	   return this.name;
   }
   
}
