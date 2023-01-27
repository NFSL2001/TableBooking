package wia2007.project.tablebooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import wia2007.project.tablebooking.entity.UserStatus;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get admin preference
        SharedPreferences sharedPref = getSharedPreferences("admin", Context.MODE_PRIVATE);


        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        // get bottom nav
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        if (sharedPref.getBoolean(UserStatus.IS_ADMIN,false)){
            // if admin change navigation map to R.nav.admin_nav
            NavHostFragment adminHost = NavHostFragment.create(R.navigation.admin_navigation);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.NHFMain, adminHost)
                    .commit();
            bottomNav.setEnabled(false);
            bottomNav.setVisibility(View.GONE);
        } else {
            NavController navController = host.getNavController();
            NavigationUI.setupWithNavController(bottomNav, navController, false);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.TVMainAct);
        setSupportActionBar(toolbar);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            System.out.println("Reload");
            getSupportFragmentManager().beginTransaction().replace(R.id.NHFMain,new AdminBookingList()).commit();
        }
    }

}