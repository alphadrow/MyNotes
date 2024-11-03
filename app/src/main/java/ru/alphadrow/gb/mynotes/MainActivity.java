package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;

import ru.alphadrow.gb.mynotes.observe.Publisher;


public class MainActivity extends AppCompatActivity {

    private Publisher publisher;
    private Navigation navigation;
    boolean isLandScape;

    public Navigation getNavigation() {
        return navigation;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notesContainer, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            showFragment(AboutFragment.newInstance());
        }
        if (item.getItemId() == R.id.action_settings) {
            showFragment(SettingsFragment.newInstance());
        }
        if (item.getItemId() == R.id.action_main) {
            showFragment(NoteFragment.newInstance());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        navigation = new Navigation(getSupportFragmentManager());
        setContentView(R.layout.activity_main);

        initDrawer(initToolBar());

        if (savedInstanceState == null) {
            navigation.addFragment(NoteFragment.newInstance(), false, "noteFragment");
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    private Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.about_note, R.string.about_title);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_about) {
                    showFragment(AboutFragment.newInstance());
                }
                if (item.getItemId() == R.id.action_settings) {
                    showFragment((SettingsFragment.newInstance()));
                }
                if (item.getItemId() == R.id.action_main) {
                    showFragment(NoteFragment.newInstance());
                }
                drawerLayout.close();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment backStackFragment = getSupportFragmentManager().findFragmentById(R.id.notesContainer);
        if (backStackFragment instanceof NotePropertiesFragment) {
            getOnBackPressedDispatcher().onBackPressed();
        }
    }
}