package soot.jimple.infoflow.reflection;

public class Option {
	private String appName;
	
	private static Option options;
	
	private Option() {}
	
	public static Option v() {
		if(options == null)
			options = new Option();
		return options;
	}
	
	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
