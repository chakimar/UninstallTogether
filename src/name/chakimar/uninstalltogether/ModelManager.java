package name.chakimar.uninstalltogether;

import java.util.List;

public class ModelManager {

	private static final ModelManager singleton = new ModelManager();
	
	private List<Model> models;
	
	private ModelManager() {
		
	}
	
	public static ModelManager getInstance() {
		return singleton;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

	public List<Model> getModels() {
		return models;
	}
}
