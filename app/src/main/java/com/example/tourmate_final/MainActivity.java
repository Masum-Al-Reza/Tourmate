package com.example.tourmate_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate_final.pojos.UserInformationPojo;
import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.example.tourmate_final.viewmodel.Loginviemodel;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LocationViewmodel locationViewmodel;
    private Loginviemodel loginviemodel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private TextView navheadname,navheademail;
    private boolean isExit = false;
    private boolean isBack = false;
    private TabLayout tabLayout;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationViewmodel = ViewModelProviders.of(this).get(LocationViewmodel.class);
        loginviemodel = ViewModelProviders.of(this).get(Loginviemodel.class);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(nalistener);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navheadname=headerView.findViewById(R.id.name_nav);
        navheademail=headerView.findViewById(R.id.email_nav);
        navigationView.setNavigationItemSelectedListener(this);
        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        drawerLayout = findViewById(R.id.drawerID);

        ActionBarDrawerToggle troggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(troggle);
        troggle.syncState();

        isLocationPermissionGranted();


//tab_layout
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Event List"));
        tabLayout.addTab(tabLayout.newTab().setText("New Event"));
        tabLayout.setSelected(true);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
      int index=tab.getPosition();
      if (index==0){
          Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.eventlistfragment);

      }
                if (index==1){
                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.add_event_fragment);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        loginviemodel.statelivedata.observe(this, new Observer<Loginviemodel.Authenticationstate>() {
            @Override
            public void onChanged(Loginviemodel.Authenticationstate authenticationstate) {
                switch (authenticationstate){
                    case AUTHENTICATIONSTATED:
                        loginviemodel.getuserinfo();
                        loginviemodel.userinfold.observe(MainActivity.this, new Observer<UserInformationPojo>() {
                            @Override
                            public void onChanged(UserInformationPojo userInformationPojo) {

                            }

                        });
                        break;
                    case UNAUTHENTICATIONSTATED:
                        break;

                }
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.eventlistfragment:
                        isExit = true;
tabLayout.getTabAt(0);
bottomNavigationView.setVisibility(View.GONE);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                tabLayout.setVisibility(View.VISIBLE);
                                tabLayout.getTabAt(0).select();
                                toolbar.setVisibility(View.VISIBLE);

                            }
                        });
break;
                    case R.id.add_event_fragment:
                        tabLayout.getTabAt(1).select();
                        tabLayout.setVisibility(View.VISIBLE);
                        isExit=false;
                        isBack=true;
                        break;
                    case R.id.login_fragments:
                        isExit = true;
                        toolbar.setVisibility(View.GONE);
                        bottomNavigationView.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.GONE);
                        break;
                    case R.id.registration_fragment:
                        isExit = false;
                        break;
                    case R.id.event_details:
                        bottomNavigationView.getMenu().findItem(R.id.botton_nav_expense).setChecked(true);
                        tabLayout.setVisibility(View.GONE);
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        isBack=true;
                        isExit=false;
                        break;
                    case R.id.moment_fragment:
                        bottomNavigationView.setVisibility(View.VISIBLE);
tabLayout.setVisibility(View.GONE);
                        isBack = true;
                        isExit = false;
                        break;
                        default:
                            bottomNavigationView.setVisibility(View.GONE);
                            tabLayout.setVisibility(View.GONE);
isExit=false;
                            break;
                }
            }
        });
    }

//bottom/-nav
    BottomNavigationView.OnNavigationItemSelectedListener nalistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


            switch (menuItem.getItemId()){
                case R.id.bottom_nav_event:
                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.eventlistfragment);
                    break;

                case R.id.bottom_nav_moment:
                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.moment_fragment);

                    break;
                case R.id.botton_nav_expense:
                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.event_details);

                    break;
                default:
                    break;

            }
            return true;
        }
    };


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (isExit){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }else {
                super.onBackPressed();
            }
        }
    }

    private boolean isLocationPermissionGranted() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
            locationViewmodel.getcurrentlocation();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.eventlistfragment);

                break;
            case R.id.nav_event:
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.eventlistfragment);

                break;
            case R.id.nav_nearby:
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.locationFragment);

                break;
            case R.id.nav_compass:
                break;
            case R.id.nav_exit:
                break;
            case R.id.nav_logout:
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                loginviemodel.logout();
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.login_fragments);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.nav_weather:
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.weather);

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

}
