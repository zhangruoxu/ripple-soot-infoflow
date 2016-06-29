package soot.jimple.infoflow.reflection;

public class Option {
	private boolean isInferenceReflectionModel = false;
	private String appName;
	
	private static Option options;
	
	private Option() {}
	
	public static Option v() {
		if(options == null)
			options = new Option();
		return options;
	}
		
	public boolean isInferenceReflectionModel() {
		return isInferenceReflectionModel;
	}

	public void setInferenceReflectionModel(boolean isInferenceReflectionModel) {
		this.isInferenceReflectionModel = isInferenceReflectionModel;
	}

	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
