package com.augmentis.ayp.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theerawuth on 8/8/2016.
 */
public class BeatBox {
    //Step1: Handle Asset
    private static final String TAG = "BeatBox";

    private static final String SOUND_FOLDER = "sample_sounds";

    private static final int MAX_SOUNDS = 5;

    private AssetManager assets;
    private List<Sound> sounds = new ArrayList<>();
    private SoundPool soundPool;

    //build constructor
    public BeatBox(Context context){
        assets = context.getAssets();
        soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        //call sound
        loadSounds();
    }

    //Step2: use read file
    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = assets.list(SOUND_FOLDER);

            Log.d(TAG, "found " + soundNames.length + " sound");
        }
        catch (IOException ioe)
        {
            Log.d(TAG, "Could not list assets", ioe);
            return;
        }

        //step3 : get list of file name
        for(String filename : soundNames){
            try{
                String assetPath = SOUND_FOLDER + File.separator + filename;
                Sound sound = new Sound(assetPath);
                load(sound);

                //add sound to List
                sounds.add(sound);
            }catch (IOException e){
                e.printStackTrace();

                // do something
            }

        }
    }
    //step4:
    public List<Sound> getSounds(){
        return sounds;

    }
    //step5: Build load for set SoundId and sent to SoundPool
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = assets.openFd(sound.getAssetPath());
        int soundId = soundPool.load(afd, 1);
        sound.setSoundId(soundId);

    }

    //step6: bulid play for call to play sound
    public void play(Sound sound){
        Integer soundId = sound.getSoundId();

        if(soundId ==  null){
            return;
        }

        soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //step7: use clean memory SoundPool
    public void release(){
        soundPool.release();
    }

}
