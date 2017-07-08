package appx_homescreen.appx.Settings;
import appx_homescreen.appx.*;



        import android.content.Context;
        import android.graphics.Color;
        import android.view.Gravity;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.List;

/**
 * Created by sueparks on 11/24/15.
 */

public class SettingsAdapter extends ArrayAdapter<SettingsData> {

    private Context mContext;
    private List<SettingsData> mValues;

    public SettingsAdapter(Context context,
                           int textViewResourceId, List<SettingsData> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mValues = objects;
    }

    @Override
    public int getCount(){
        return mValues.size();
    }

    @Override
    public SettingsData getItem(int position){
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //This is for the first item before dropdown or default state.
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        //label.setText(" " + mValues.get(position).getName());
        label.setHeight(50);
        label.setGravity(Gravity.LEFT | Gravity.CENTER );
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        //This is when user click the spinner and list of item display
        // beneath it
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        //label.setText(" " + mValues.get(position).getName());
        label.setHeight(70);
        label.setGravity(Gravity.LEFT | Gravity.CENTER );

        return label;
    }
}

