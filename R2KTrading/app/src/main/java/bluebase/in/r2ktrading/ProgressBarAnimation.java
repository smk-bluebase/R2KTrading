package bluebase.in.r2ktrading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

public class ProgressBarAnimation {
    private AnimationDrawable animationDrawable;
    private ImageView mProgressBar;
    private Dialog dialog;

    public ProgressBarAnimation(Context context){
        dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.progress_bar);

        mProgressBar = dialog.findViewById(R.id.progressBar);
        mProgressBar.setBackgroundResource(R.drawable.loading_animation);
        animationDrawable = (AnimationDrawable) mProgressBar.getBackground();
    }

    public void show(){
        mProgressBar.setVisibility(View.VISIBLE);
        animationDrawable.start();
        dialog.show();
    }

    public void dismiss(){
        mProgressBar.setVisibility(View.GONE);
        animationDrawable.stop();
        dialog.dismiss();
    }

}