package pl.yellowgroup.application.backetdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pl.yellowgroup.application.backetdrops.R;

/**
 * Created by darek on 17.10.2016.
 */

public class AdapterDrops extends RecyclerView.Adapter<AdapterDrops.DropHolder> {
    private LayoutInflater  mInflanter;
    private ArrayList<String> mItems = new ArrayList<>();

    public AdapterDrops(Context context) {
        mInflanter = LayoutInflater.from(context);
        mItems = generateValue();
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
        return holder;
    }

    @Override
    public void onBindViewHolder(DropHolder holder, int position) {
        holder.mTextWhat.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;
        public DropHolder(View itemView) {
            super(itemView);

            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        }
    }
}
