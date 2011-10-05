package name.chakimar.uninstalltogether;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private static final int ITEM_ID_ALL_CHECK = 0;
	private static final int ITEM_ID_ALL_UNCHECK = 1;
	private EditText et_search;
	private ImageButton btn_search;
	private ImageButton btn_uninstall;
	private TextView tv_search_key;
	private ListView lv_package;
	private String key = "";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		et_search = (EditText) findViewById(R.id.et_search);
		btn_search = (ImageButton) findViewById(R.id.btn_search);
		btn_uninstall = (ImageButton) findViewById(R.id.btn_uninstall);
		tv_search_key = (TextView) findViewById(R.id.tv_search_key);
		lv_package = (ListView) findViewById(R.id.lv_packageNames);
		
		btn_search.setOnClickListener(this);
		btn_uninstall.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		LoadPackageTask task = new LoadPackageTask(this, key);
		task.execute();
	}

	@Override
	public void onClick(View view) {
		if (view == btn_search) {
			key = et_search.getText().toString();
			String text = getString(R.string.label_search_result, key);
			tv_search_key.setText(text);
			
			LoadPackageTask task = new LoadPackageTask(this, key);
			task.execute();
		} else if (view == btn_uninstall) {
			ListAdapter adapter = lv_package.getAdapter();
			for (int i=0;i<adapter.getCount();i++) {
				Model model = (Model)adapter.getItem(i);
				if (model.isSelected()) {
					startUninstallActivity(model.info.packageName);
				}
			}
		}
	}

	private void startUninstallActivity(String packageName) {
		Uri uri = Uri.fromParts("package",packageName ,null);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ITEM_ID_ALL_CHECK, 1, R.string.all_check).setIcon(R.drawable.check);
		menu.add(0, ITEM_ID_ALL_UNCHECK, 2, R.string.all_uncheck).setIcon(R.drawable.denied);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == ITEM_ID_ALL_CHECK) {
			changeAllSelector(true);
		} else if (itemId == ITEM_ID_ALL_UNCHECK) {
			changeAllSelector(false);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void changeAllSelector(boolean selected) {
		final PackageNameAdapter adapter = (PackageNameAdapter) lv_package.getAdapter();
		for (int i=0;i<adapter.getCount();i++) {
			Model model = (Model)adapter.getItem(i);
			model.setSelected(selected);
		}
		
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				adapter.notifyDataSetChanged();
				lv_package.invalidateViews();
			}
		});
	}
}