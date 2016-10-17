package pl.yellowgroup.application.backetdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pl.yellowgroup.application.backetdrops.adapters.AdapterDrops;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    Toolbar mToolbar;
    Button mBtnAdd;
    RecyclerView mRecycler;

    // create variable listener onClick
    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showDialogAdd();
        }
    };

    private void showDialogAdd() {
        DialogAddFragment dialog = new DialogAddFragment();
        dialog.show(getSupportFragmentManager(),"Add");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = (ImageView) findViewById(R.id.iv_logo);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mRecycler = (RecyclerView) findViewById(R.id.rv_drops);

        // use data from adapter AdapterDrops () OR we can set this code in layout XML in activity_main.xml in RecyclerView app:layoutManager
        //LinearLayoutManager manager = new LinearLayoutManager(this);
        //mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(new AdapterDrops(this));

        // set variable onClick listener
        mBtnAdd.setOnClickListener(mBtnAddListener);

        setSupportActionBar(mToolbar);
        initBackgroundImage();
    }

    private void initBackgroundImage() {
        ImageView iv_background = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(iv_background);
    }

}
