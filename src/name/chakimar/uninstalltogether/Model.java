package name.chakimar.uninstalltogether;

import android.content.pm.PackageInfo;

public class Model {

	public PackageInfo info;
	private boolean selected = true;

	public Model(PackageInfo info) {
		super();
		this.info = info;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
	
	
}
