
package com.ushahidi.android.app.helpers;

import android.annotation.SuppressLint;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ushahidi.android.app.R;
import com.ushahidi.android.app.ui.tablet.ListMapFragment;

@SuppressLint("NewApi")
public class ActionModeHelper implements ActionMode.Callback, AdapterView.OnItemLongClickListener {
   
    private ListMapFragment listMapFragment;

    private ActionMode activeMode;

    private ListView modeView;

    private int lastPosition = -1;

    public ActionModeHelper(ListMapFragment listMapFragment, ListView listView) {
        this.listMapFragment = listMapFragment;
        this.modeView = listView;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> view, View row, int position, long id) {
        lastPosition = position;
        modeView.clearChoices();
        modeView.setItemChecked(lastPosition, true);

        if (activeMode == null) {
            if (listMapFragment != null) {
                activeMode = listMapFragment.getActivity().startActionMode(this);
            }
        }

        return (true);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
       
        if (listMapFragment != null) {
            MenuInflater inflater = listMapFragment.getActivity().getMenuInflater();

            inflater.inflate(R.menu.list_map_context, menu);
            listMapFragment.getActivity().setTitle(R.string.map);
        }

        return (true);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return (false);
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        boolean result = false;
        
        if( listMapFragment !=null)
            result = listMapFragment.performAction(item, lastPosition);
        /*
         * if (item.getItemId() == R.id.remove) { activeMode.finish(); }
         */

        return (result);
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        activeMode = null;
        modeView.clearChoices();
    }
}
