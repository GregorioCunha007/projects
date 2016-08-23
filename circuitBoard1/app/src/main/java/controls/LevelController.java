package controls;

 public class LevelController{
	
	public static int level = 1;
	public static int maxLevel;
	public static int currentPack=0;
	
	public final static String Tutorial = "tutoriallevels";
	public final static String Begginner = "begginnerlevels";
	public final static String Crazy = "crazylevels";
	
	public static boolean getBegginnerEnable(){
		return currentPack == 2;
	}
	
	public static boolean getCrazyEnabled(){
		return currentPack == 3;
	}
	

 }
