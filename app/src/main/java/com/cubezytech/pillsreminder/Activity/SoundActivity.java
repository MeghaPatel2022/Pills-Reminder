package com.cubezytech.pillsreminder.Activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.cubezytech.pillsreminder.Adapter.DeviceToneAdapter;
import com.cubezytech.pillsreminder.Adapter.PillsToneAdapter;
import com.cubezytech.pillsreminder.Model.Song;
import com.cubezytech.pillsreminder.Pref.SharedPrefrance;
import com.cubezytech.pillsreminder.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoundActivity extends AppCompatActivity {

    private static String NOTIFICATION_TONE = "";
    private final ArrayList<String> nameList = new ArrayList<>();
    private final ArrayList<String> actualNameList = new ArrayList<>();
    @BindView(R.id.rv_tones)
    RecyclerView rv_tones;
    @BindView(R.id.rv_device_tones)
    RecyclerView rv_device_tones;
    @BindView(R.id.segmented)
    SegmentedButtonGroup segmented;
    @BindView(R.id.tv_save)
    TextView tv_save;
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer1;
    private ArrayList<Song> songList = new ArrayList<>();
    private PillsToneAdapter pillsToneAdapter;
    private DeviceToneAdapter deviceToneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sound);

        ButterKnife.bind(SoundActivity.this);

        songList.clear();
        songList = getNotificationSounds();
        getData();

        NOTIFICATION_TONE = SharedPrefrance.getNotificationTone(SoundActivity.this);

        // Pills Tone Adapter
        rv_tones.setLayoutManager(new LinearLayoutManager(SoundActivity.this, RecyclerView.VERTICAL, false));
        pillsToneAdapter = new PillsToneAdapter(nameList, actualNameList, SoundActivity.this);
        rv_tones.setAdapter(pillsToneAdapter);
        pillsToneAdapter.setAdapterItemClickListener(pos -> playAndStopPillsRingtone(actualNameList.get(pos)));

        // Device Tune Adapter
        rv_device_tones.setLayoutManager(new LinearLayoutManager(SoundActivity.this, RecyclerView.VERTICAL, false));
        deviceToneAdapter = new DeviceToneAdapter(songList, SoundActivity.this);
        rv_device_tones.setAdapter(deviceToneAdapter);
        deviceToneAdapter.setAdapterItemClickListener(pos -> playAndStopDeviceRingtone(songList.get(pos)));

        segmented.setOnPositionChangedListener(position -> {
            // Handle stuff here
            if (segmented.getPosition() == 0) {
                rv_device_tones.setVisibility(View.GONE);
                rv_tones.setVisibility(View.VISIBLE);
            } else {
                rv_tones.setVisibility(View.GONE);
                rv_device_tones.setVisibility(View.VISIBLE);
            }
            Log.e("LLLLL_Segment_Pos: ", String.valueOf(position));
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefrance.setNotificationTone(SoundActivity.this, NOTIFICATION_TONE);
                onBackPressed();
            }
        });
    }

    private void getData() {
        actualNameList.clear();
        actualNameList.add("drops");
        actualNameList.add("grame_take_your_pill");
        actualNameList.add("medicine_time");
        actualNameList.add("pill_bottle_shake");
        actualNameList.add("pill_time");
        actualNameList.add("pills_shake");
        actualNameList.add("take_your_pill");

        nameList.clear();
        nameList.add("Pills Drops");
        nameList.add("Grame Take Your Pill");
        nameList.add("Medicine Time");
        nameList.add("Pill Bottle Shake");
        nameList.add("Pill time");
        nameList.add("Pill Shake");
        nameList.add("Take Your Pill");
    }

    public ArrayList<Song> getNotificationSounds() {
        RingtoneManager manager = new RingtoneManager(SoundActivity.this);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        ArrayList<Song> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.add(new Song(id, name, uri));
        }

        return list;
    }

    private void playAndStopPillsRingtone(String songName) {

        try {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            int resID = getResources().getIdentifier(songName, "raw", getPackageName());

            mediaPlayer = MediaPlayer.create(SoundActivity.this, resID);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            } else {
                mediaPlayer.start();
            }
//            NOTIFICATION_TONE = "android.resource://com.example.waterreminder/raw/" + songName;
            NOTIFICATION_TONE = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/" + songName;
            Log.e("LLLLL_SongNMAme: ", NOTIFICATION_TONE);
//                    database_helper.updateTone(Uri.parse("android.resource://com.example.waterreminder/raw/" + songName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playAndStopDeviceRingtone(Song song) {
        String path = song.getUri() + "/" + song.getId();
        Log.e("LLLLL_Path: ", path + "    " + song.getName());
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse(path));
        r.play();
        NOTIFICATION_TONE = path;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
        }
        if (mediaPlayer1 != null) {
            if (mediaPlayer1.isPlaying())
                mediaPlayer1.pause();
        }

    }
}