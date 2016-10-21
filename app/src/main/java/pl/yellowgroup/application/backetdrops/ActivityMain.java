package pl.yellowgroup.application.backetdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import pl.yellowgroup.application.backetdrops.adapters.AdapterDrops;
import pl.yellowgroup.application.backetdrops.adapters.AddListener;
import pl.yellowgroup.application.backetdrops.adapters.CompliteListener;
import pl.yellowgroup.application.backetdrops.adapters.Divider;
import pl.yellowgroup.application.backetdrops.adapters.MarkListener;
import pl.yellowgroup.application.backetdrops.adapters.SimpleTouchCallback;
import pl.yellowgroup.application.backetdrops.beans.Drop;
import pl.yellowgroup.application.backetdrops.widgets.BucketRecyclerView;

public class ActivityMain extends AppCompatActivity {

    public static final String TAG = "VIVZ";
    Toolbar mToolbar;
    Button mBtnAdd;
    BucketRecyclerView mRecycler;
    Realm mRealm;
    RealmResults<Drop> mResults;
    View mEmptyView;
    AdapterDrops mAdapter;
    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showDialogAdd();
        }
    };
    private AddListener mAddListener = new AddListener() {
        @Override
        public void add() {
            showDialogAdd();
        }
    };

    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            Log.d(TAG, "onChange: was called");
            mAdapter.update(mResults);
        }
    };

    private MarkListener mMarkListener = new MarkListener() {
        @Override
        public void onMark(int position) {
            showDialogMark(position);
        }
    };

    private CompliteListener mCompleteListener = new CompliteListener() {
        @Override
        public void onComplited(int position) {
            Log.d(TAG, "Position " + position);

            mAdapter.onComplete(position);
        }
    };

    private void showDialogAdd() {
        DialogAddFragment dialog = new DialogAddFragment();
        dialog.show(getSupportFragmentManager(), "Add");
    }

    private void showDialogMark(int position) {
        DialogMark dialog = new DialogMark();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        dialog.setArguments(bundle);
        dialog.setCompleteListener(mCompleteListener);
        dialog.show(getSupportFragmentManager(), "Mark");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(mBtnAddListener);
        mRealm = Realm.getDefaultInstance();
        mResults = mRealm.where(Drop.class).findAllAsync();
        mEmptyView = findViewById(R.id.empty_drops);
        mRecycler = (BucketRecyclerView) findViewById(R.id.rv_drops);
        mRecycler.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        mRecycler.hideIfEmpty(mToolbar);
        mRecycler.showIfEmpty(mEmptyView);
        mAdapter = new AdapterDrops(this, mRealm, mResults, mAddListener, mMarkListener);
        mRecycler.setAdapter(mAdapter);
        SimpleTouchCallback callback = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecycler);
        initBackgroundImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                showDialogAdd();
                return true;
            case R.id.action_sort_ascending_date:
                mResults = mRealm.where(Drop.class).findAllSortedAsync("when");
                mResults.addChangeListener(mChangeListener);
                return true;
            case R.id.action_sort_descending_date:
                mResults = mRealm.where(Drop.class).findAllSortedAsync("when", Sort.DESCENDING);
                mResults.addChangeListener(mChangeListener);
                return true;
            case R.id.action_show_complete:
                mResults = mRealm.where(Drop.class).equalTo("completed",true).findAllAsync();
                mResults.addChangeListener(mChangeListener);
                return true;
            case R.id.action_show_incomplete:
                mResults = mRealm.where(Drop.class).equalTo("completed",false).findAllAsync();
                mResults.addChangeListener(mChangeListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mResults.addChangeListener(mChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mResults.removeChangeListener(mChangeListener);
    }

    private void initBackgroundImage() {
        ImageView background = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
    }
}
