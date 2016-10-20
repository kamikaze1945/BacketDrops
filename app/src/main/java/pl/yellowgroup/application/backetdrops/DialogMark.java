package pl.yellowgroup.application.backetdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import pl.yellowgroup.application.backetdrops.adapters.CompliteListener;

/**
 * Created by darek on 18.10.2016.
 */

public class DialogMark extends DialogFragment {

    private ImageButton mBtnClose;
    private Button mBtnCompleted;
    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_completed:
                    //TODO handle the action here to mark the item as complete
                    markAsComplited();
                    break;
            }
            dismiss();
        }
    };
    private CompliteListener mListener;

    private void markAsComplited() {
        Bundle arguments = getArguments();

        if(mListener != null && arguments!= null && !arguments.isEmpty() ) {

            int position = arguments.getInt("POSITION");
            mListener.onComplited(position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mBtnCompleted = (Button) view.findViewById(R.id.btn_completed);
        mBtnClose.setOnClickListener(mBtnClickListener);
        mBtnCompleted.setOnClickListener(mBtnClickListener);
    }

    public void setCompleteListener(CompliteListener mCompleteListener) {
        mListener = mCompleteListener;
    }
}