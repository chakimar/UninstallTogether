package name.chakimar.uninstalltogether;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.widget.ListView;

public class LoadPackageTask extends AsyncTask<Void, Void, List<PackageInfo>> {

	private MainActivity activity;
	private PackageManager pm;
	private ProgressDialog dialog;
	private String prefix;
	
	public LoadPackageTask(MainActivity activity, String prefix) {
		super();
		this.activity = activity;
		this.pm = activity.getPackageManager();
		this.prefix = prefix;
	}
	
	@Override
	protected List<PackageInfo> doInBackground(Void... arg0) {
		return filteringPakcage(pm.getInstalledPackages(0), prefix);
	}

	@Override
	protected void onPreExecute() {
		showProgress();
	}

	@Override
	protected void onPostExecute(List<PackageInfo> result) {
		dismissProgress();
		List<Model> models = populatePackageInfoToModel(result);
		ModelManager.getInstance().setModels(models);
		PackageNameAdapter adapter = new PackageNameAdapter(activity, R.layout.row_packagename, models);
		ListView lv = (ListView) activity.findViewById(R.id.lv_packageNames);
		lv.setAdapter(adapter);
	}

	private void showProgress() {
		if (dialog == null) {
			dialog = new ProgressDialog(activity);
		}
		dialog.setMessage("Loading...");
		dialog.show();
	}
	
	private void dismissProgress() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	private List<PackageInfo> filteringPakcage(List<PackageInfo> packages,
			String prefix) {
		List<PackageInfo> filteredPackageInfos = new ArrayList<PackageInfo>();
		for (PackageInfo packageInfo : packages) {
			String packageName = packageInfo.packageName;
			if (packageName.startsWith(prefix)) {
				filteredPackageInfos.add(packageInfo);
			}
		}
		return filteredPackageInfos;
	}

	private List<Model> populatePackageInfoToModel(List<PackageInfo> packages) {
		List<Model> populateList = new ArrayList<Model>();
		
		for (PackageInfo packageInfo : packages) {
			populateList.add(new Model(packageInfo));
		}
		return populateList;
	}
	
}
