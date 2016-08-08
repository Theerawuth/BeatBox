package com.augmentis.ayp.beatbox.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.augmentis.ayp.beatbox.BeatBox;
import com.augmentis.ayp.beatbox.R;
import com.augmentis.ayp.beatbox.Sound;

import java.util.List;

/**
 * Created by Theerawuth on 8/8/2016.
 */
public class BeatBoxFragment extends Fragment {

    BeatBox beatBox;


    //step1: newInstance for Singleton
    public static BeatBoxFragment newInstance() {

        Bundle args = new Bundle();

        BeatBoxFragment fragment = new BeatBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //step2
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beat_box, container, false);

        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.fragment_beat_box_recycle_view);

        recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycleView.setAdapter(new SoundAdapter(beatBox.getSounds()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beatBox.release();
    }

    //step3: new Class SoundHolder for Build ButtonSound

    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Sound sound;
        private Button button;

        SoundHolder(LayoutInflater layoutInflater, ViewGroup viewGroup){
            super(layoutInflater.inflate(R.layout.list_item_sound, viewGroup, false));

            button = (Button) itemView.findViewById(R.id.list_item_sound_button);
            button.setOnClickListener(this);

        }

        //set bind sound to button
        public void bindSound(Sound sound) {
            this.sound = sound;
            button.setText(sound.getName());
        }

        @Override
        public void onClick(View v) {
            beatBox.play(sound);
        }
    }



    //step4: new Class Adapter
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<Sound> soundList;

        SoundAdapter(List<Sound> sounds){
            soundList = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = soundList.get(position);
            holder.bindSound(sound);

        }

        @Override
        public int getItemCount() {
            return soundList.size();
        }
    }

    //step5: new OnCreate for Call Method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call Class BeatBox
        beatBox = new BeatBox(getActivity());
    }
}
