package pl.yellowgroup.application.backetdrops;

import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
