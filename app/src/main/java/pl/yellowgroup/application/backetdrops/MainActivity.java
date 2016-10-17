package pl.yellowgroup.application.backetdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import pl.yellowgroup.application.backetdrops.adapters.AdapterDrops;
import pl.yellowgroup.application.backetdrops.beans.Drop;
import pl.yellowgroup.application.backetdrops.widgets.BucketRecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    ImageView logo;
    Toolbar mToolbar;
    Button mBtnAdd;
    BucketRecyclerView mRecycler;
    Realm mRealm;
    RealmResults<Drop> mResults;
    View mEmptyView;
    AdapterDrops mAdapter;

    // create variable listener onClick
    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
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

    private void showDialogAdd() {
        DialogAddFragment dialog = new DialogAddFragment();
        dialog.show(getSupportFragmentManager(), "Add");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get instance DB realm
        mRealm = Realm.getDefaultInstance();
        mResults = mRealm.where(Drop.class).findAllAsync();

        // fields
        logo = (ImageView) findViewById(R.id.iv_logo);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mRecycler = (BucketRecyclerView) findViewById(R.id.rv_drops);
        mEmptyView = findViewById(R.id.empty_drops);

        // use data from adapter AdapterDrops () OR we can set this code in layout XML in activity_main.xml in RecyclerView app:layoutManager
        //LinearLayoutManager manager = new LinearLayoutManager(this); for first part comment
        //mRecycler.setLayoutManager(manager); for first part comment
        mAdapter = new AdapterDrops(this, mResults);
        mRecycler.setAdapter(mAdapter);
        //change view if
        mRecycler.hideIfEmpty(mToolbar);
        mRecycler.showIfEmpty(mEmptyView);

        // set listener on acction if click button
        mBtnAdd.setOnClickListener(mBtnAddListener);
        setSupportActionBar(mToolbar);
        initBackgroundImage();
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
