package com.augmentis.ayp.beatbox.Activity;
import android.support.v4.app.Fragment;
import com.augmentis.ayp.beatbox.Fragment.BeatBoxFragment;
import com.augmentis.ayp.beatbox.Fragment.SingleFragmentActivity;


public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return BeatBoxFragment.newInstance();
    }
}
