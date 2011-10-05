package name.chakimar.uninstalltogether;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class PackageNameAdapter extends ArrayAdapter<Model> {

	private PackageManager pm;
	private List<Model> items;
	private LayoutInflater inflater;

	public PackageNameAdapter(Context context, int textViewResourceId,
			List<Model> items) {
		super(context, textViewResourceId, items);
		this.pm = context.getPackageManager();
		this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		CheckBox cb = null;
        if(v == null){  
            //1行分layoutからViewの塊を生成
            v = inflater.inflate(R.layout.row_packagename, null);
        }
        final Model model = items.get(position);

        cb = (CheckBox) v.findViewById(R.id.cb_packagename);
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				model.setSelected(isChecked);
			}
		});
        cb.setChecked(model.isSelected());
        
        TextView tv = (TextView) v.findViewById(R.id.tv_packagename);
        tv.setText(model.info.applicationInfo.loadLabel(pm));
        
        if (position %2 == 0) {
        	v.setBackgroundColor(Color.DKGRAY);
        	tv.setTextColor(Color.WHITE);
        } else {
        	v.setBackgroundColor(Color.WHITE);
        	tv.setTextColor(Color.BLACK);
        }
        
		return v;
	}

}
