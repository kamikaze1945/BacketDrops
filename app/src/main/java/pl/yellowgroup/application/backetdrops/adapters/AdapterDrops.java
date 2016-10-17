package pl.yellowgroup.application.backetdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmResults;
import pl.yellowgroup.application.backetdrops.R;
import pl.yellowgroup.application.backetdrops.beans.Drop;

/**
 * Created by darek on 17.10.2016.
 */

public class AdapterDrops extends RecyclerView.Adapter<AdapterDrops.DropHolder> {
    public static final String TAG = AdapterDrops.class.getSimpleName();

    private LayoutInflater  mInflanter;
    private RealmResults<Drop> mResults;

    public AdapterDrops(Context context, RealmResults<Drop> result) {
        mInflanter = LayoutInflater.from(context);
        update(result);
    }

    /**
     * Update date object when add new data
     * @param result
     */
    public void update(RealmResults<Drop> result) {
        mResults = result;
        notifyDataSetChanged(); // we need add this method because refresh data on list recycler View when we add new position
    }

    public static ArrayList<String> generateValue() {
        ArrayList<String> dummyValues = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            dummyValues.add("Item: " + i);
        }

        return dummyValues;
    }

    @Override
    public DropHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflanter.inflate(R.layout.drop_row, parent, false);
        DropHolder holder = new DropHolder(view);
        Log.d(TAG, "onCreateViewHolder: ");
        return holder;
    }

    @Override
    public void onBindViewHolder(DropHolder holder, int position) {
        Drop drop = mResults.get(position);
        holder.mTextWhat.setText(drop.getWhat());
        Log.d(TAG, "onBindViewHolder: " + position);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;
        public DropHolder(View itemView) {
            super(itemView);

            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        }
    }
}
