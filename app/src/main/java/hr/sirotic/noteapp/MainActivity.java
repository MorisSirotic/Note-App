package hr.sirotic.noteapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import hr.sirotic.noteapp.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startNewFragment(new MainFragment());

    }

    public <T extends Fragment> void startNewFragment(T t) {
        int id = findViewById(R.id.fragContainer).getId();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(id, t)
                .commit();
    }


}


