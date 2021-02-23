package com.example.acctask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    ListView listView1;
    ArrayList<String> arrayListSongName = new ArrayList<>();
    ArrayList<String> arrayListSongUrl = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        jcPlayerView = findViewById(R.id.jcplayer);
        listView1 = findViewById(R.id.listview1);
        retriveSongs();
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jcPlayerView.playAudio(jcAudios.get(i));
            }
        });
    }

    private void retriveSongs() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Songs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MusicGetSet musicGetSetObj = ds.getValue(MusicGetSet.class);
                    arrayListSongName.add(musicGetSetObj.getName());
                    arrayListSongUrl.add(musicGetSetObj.getUrl());
                    jcAudios.add(JcAudio.createFromURL(musicGetSetObj.getName(), musicGetSetObj.getUrl()));
                }
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayListSongName) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setSingleLine(true);
                        textView.setMaxLines(1);
                        textView.setGravity(Gravity.CENTER);
                        return view;
                    }
                };
                jcPlayerView.initPlaylist(jcAudios, null);
                listView1.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        jcPlayerView.kill();
        super.onBackPressed();
        this.finish();
    }
}