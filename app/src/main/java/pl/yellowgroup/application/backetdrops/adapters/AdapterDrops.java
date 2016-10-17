package pl.yellowgroup.application.backetdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.RealmResults;
import pl.yellowgroup.application.backetdrops.R;
import pl.yellowgroup.application.backetdrops.beans.Drop;

/**
 * Created by darek on 17.10.2016.
 */

public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = AdapterDrops.class.getSimpleName();

    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    private LayoutInflater mInflater;
    private RealmResults<Drop> mResults;
    private AddListener mAddListener;

    public AdapterDrops(Context context, RealmResults<Drop> results) {
        mInflater = LayoutInflater.from(context);
        update(results);
    }

    public AdapterDrops(Context context, RealmResults<Drop> results, AddListener listener) {
        mInflater = LayoutInflater.from(context);
        update(results);
        mAddListener = listener;
    }

    public void update(RealmResults<Drop> results) {
        mResults = results;
        // we need add this method because refresh data on list recycler View when we add new position
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //We return an item if results are null or if the position is within the bounds of the results
        if (mResults == null || position < mResults.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new FooterHolder(view, mAddListener);
        } else {
            View view = mInflater.inflate(R.layout.drop_row, parent, false);
            return new DropHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mResults.get(position);
            dropHolder.mTextWhat.setText(drop.getWhat());
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size() + 1;
    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;

        public DropHolder(View itemView) {
            super(itemView);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mBtnAdd;
        AddListener mListener;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
        }

        public FooterHolder(View itemView, AddListener listener) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.add();
        }
    }
}