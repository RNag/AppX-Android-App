package appx_homescreen.appx;

/**
 * Created by Savita on 11/1/2015.
 */

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public final List<View> children = new ArrayList<View>();

    public Group(String string) {
        this.string = string;
    }

}