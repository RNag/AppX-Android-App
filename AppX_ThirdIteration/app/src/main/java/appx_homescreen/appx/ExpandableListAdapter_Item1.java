package appx_homescreen.appx;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ExpandableListAdapter_Item1 extends BaseExpandableListAdapter {
    View v1, v2 = null;
    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;
    //public static EditText text = null;
    public static Spinner[] staticSpinner2 = new Spinner[6];
    public static ArrayAdapter<CharSequence>[] staticAdapter = new ArrayAdapter[6];
    public boolean testit[] = new boolean[2], idIsSet = false;
    int previousItem = -1;
    public static EditText editWhere, editWhen, edit_toWhen, edit_fromWhen, edit_AddressLine, edit_cityName, edit_state = null;

    public static View editWhen_this;
    public static ViewGroup.LayoutParams expandableView_lp;
    public static ExpandableListView expandableView;

    public static TextView editCheck;

    public ExpandableListAdapter_Item1(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
        expandableView = (ExpandableListView) activity.findViewById(R.id.expandableList);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final View children = (View) getChild(groupPosition, childPosition);
        int itemType = getChildType(groupPosition, childPosition);

                //LayoutInflater inflatere = (LayoutInflater) new LayoutInflater();




        switch (groupPosition) {
                    case 0:

                        if (v1 == null) {
                            v1 = inflater.inflate(R.layout.listgroup_when_details, null);
                            //v1.setTag(itemType);
                            staticSpinner2[0] = (Spinner) v1.findViewById(R.id.fromHourSpinner);
                            staticSpinner2[1] = (Spinner) v1.findViewById(R.id.fromMinSpinner);
                            staticSpinner2[2] = (Spinner) v1.findViewById(R.id.fromPeriodSpinner);
                            staticSpinner2[3] = (Spinner) v1.findViewById(R.id.toHourSpinner);
                            staticSpinner2[4] = (Spinner) v1.findViewById(R.id.toMinSpinner);
                            staticSpinner2[5] = (Spinner) v1.findViewById(R.id.toPeriodSpinner);

                            edit_fromWhen = (EditText) v1.findViewById(R.id.edit_fromWhen);
                            edit_toWhen = (EditText) v1.findViewById(R.id.edit_toWhen);

                            staticAdapter[0] = staticAdapter[3] = ArrayAdapter
                                    .createFromResource(v1.getContext(), R.array.HourValues,
                                            android.R.layout.simple_spinner_item);
                            staticAdapter[1] = staticAdapter[4] = ArrayAdapter
                                    .createFromResource(v1.getContext(), R.array.MinValues,
                                            android.R.layout.simple_spinner_item);
                            staticAdapter[2] = staticAdapter[5] = ArrayAdapter
                                    .createFromResource(v1.getContext(), R.array.PeriodValues,
                                            android.R.layout.simple_spinner_item);

                            // Specify the layout to use when the list of choices appears

                            // Apply the adapter to the spinners
                            for (int i = 0; i < 6; i++) {
                                staticAdapter[i].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                staticSpinner2[i].setAdapter(staticAdapter[i]);
                                staticSpinner2[i].setOnItemSelectedListener(new AddEvent.SpinnerActivity());
                            }
                        }

                        return v1;

                        //break;
                    case 1:

                        if(v2 == null){
                            v2 = inflater.inflate(R.layout.listgroup_where_details, null);

                            edit_AddressLine = (EditText) v2.findViewById(R.id.edit_AddressLine);
                            edit_cityName = (EditText) v2.findViewById(R.id.edit_cityName);
                            edit_state = (EditText) v2.findViewById(R.id.editState);
                       }
                        return v2;
                    default:
                        v1 = inflater.inflate(R.layout.listgroup_when_details, null);
                        return v1;

                }

    }


@Override
public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
        }

@Override
public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
        }

@Override
public int getGroupCount() {
        return groups.size();
        }

@Override
public void onGroupCollapsed(int groupPosition) {

    expandableView_lp =  expandableView.getLayoutParams();
    expandableView_lp.height -= 400;
    expandableView.setLayoutParams(expandableView_lp);
    super.onGroupCollapsed(groupPosition);
        }

@Override
public void onGroupExpanded(int groupPosition) {

    expandableView_lp =  expandableView.getLayoutParams();
    expandableView_lp.height += 400;
    expandableView.setLayoutParams(expandableView_lp);
    super.onGroupExpanded(groupPosition);
        }

@Override
public long getGroupId(int groupPosition) {
        return 0;
        }

@Override
public View getGroupView(final int groupPosition, boolean isExpanded,
        View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listgroup_style2, null);
            }

        Group group = (Group) getGroup(groupPosition);
    EditText focusOnInput2 = null;
    switch (groupPosition){

        case 0:


            editWhen = (EditText) convertView.findViewById(R.id.editText3);
             focusOnInput2 = editWhen;
            break;
        case 1:
            if (idIsSet == false){
                editWhere = (EditText) convertView.findViewById(R.id.editText3);
                editWhere.setId(123);
                idIsSet = true;
            }
             focusOnInput2 = editWhere;
            break;
    }

    final EditText focusOnInput = focusOnInput2;


    activity.findViewById(R.id.editWhat).setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub

            if (event.getAction() == EditorInfo.IME_ACTION_DONE || (keyCode == KeyEvent.KEYCODE_ENTER)) {

                v.clearFocus();
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editWhen.setFocusable(true);
                        editWhen.setFocusableInTouchMode(true);
                        editWhen.requestFocus();
                    }
                }, 1);
            }
            return false;
        }
    });

if (focusOnInput != null) {
    focusOnInput.setOnKeyListener(new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub

            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                focusOnInput.clearFocus();
                focusOnInput.setFocusable(false);
                if (groupPosition == 0) {
                    focusOnInput.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editWhere.setFocusable(true);
                            editWhere.setFocusableInTouchMode(true);
                            editWhere.requestFocus();
                        }
                    }, 1);
                } else {
                    focusOnInput.postDelayed(new Runnable() {
                        public void run() {
                            activity.findViewById(focusOnInput.getNextFocusDownId()).requestFocus();
                        }
                    }, 1);
                }

            }
            return false;
        }
    });

    focusOnInput.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                switch (groupPosition) {
                    case 0:
                        editWhere.setFocusable(false);
                        break;
                    case 1:
                        editWhen.setFocusable(false);
                        break;
                }
                focusOnInput.setFocusable(true);
                focusOnInput.setFocusableInTouchMode(true);
                final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

                focusOnInput.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        focusOnInput.requestFocus();
                        imm.showSoftInput(focusOnInput, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, 100);

                focusOnInput.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!focusOnInput.isFocused())
                            focusOnInput.setFocusable(false);
                    }
                }, 5000);
            }
            return false;
        }
    });


    focusOnInput.setFocusable(false);
}
    //((LinearLayout) convertView.findViewById(R.id.HeaderLayout)).requestFocus();
    //((LinearLayout) convertView.findViewById(R.id.HeaderLayout)).clearChildFocus(convertView.findViewById(R.id.editText3));
    ((CheckedTextView) convertView.findViewById(R.id.textView1)).setText(group.string);
    ((CheckedTextView) convertView.findViewById(R.id.textView1)).setChecked(isExpanded);

        return convertView;
        }

@Override
public boolean hasStableIds() {
        return false;
        }

@Override
public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
        }
        }