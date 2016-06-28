package soot.jimple.infoflow.reflection;

public class Option {
	private String appName;
	// use inference reflection model or default reflection model
	private boolean inferenceReflectionModel = false;
	// modeling Android APIs invoked by reflection or not
	private boolean reflectionLibModel = false;
	private static Option options;
	
	private Option() {}
	
	public static Option v() {
		if(options == null)
			options = new Option();
		return options;
	}
	
	public boolean isInferenceReflectionModel() {
		return inferenceReflectionModel;
	}
	
	public void setInferenceReflectionModel(boolean isInferenceReflectionModel) {
		this.inferenceReflectionModel= isInferenceReflectionModel;
	}
	
	public boolean isReflectionLibModel() {
		return reflectionLibModel;
	}
	
	public void setReflectionLibModel(boolean reflectionLibModel) {
		this.reflectionLibModel = reflectionLibModel;
	}
	
	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
