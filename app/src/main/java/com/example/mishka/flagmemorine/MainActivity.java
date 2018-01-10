package com.example.mishka.flagmemorine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //************************
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        //************************

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            View view  = getLayoutInflater().inflate(R.layout.layout_6_6, null);
            relativeLayout.addView(view);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(view.getId()){
                        case R.id.imageButton2:
                            Log.d(LOG_TAG, "click imageButton" + view.getTag());
                            break;
                        case R.id.imageButton3:
                            Log.d(LOG_TAG, "click imageButton" + view.getTag());
                            break;
                        case R.id.imageButton4:
                            Log.d(LOG_TAG, "click imageButton" + view.getTag());
                            break;
                        case R.id.imageButton5:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton6:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton7:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton8:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton9:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton10:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton11:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton12:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton13:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton14:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton15:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton16:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton17:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton18:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton19:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton20:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton21:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton22:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton23:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton24:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton25:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton26:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton27:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton28:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton29:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton30:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton31:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton32:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton33:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton34:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton35:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton36:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                        case R.id.imageButton37:
                            Log.d(LOG_TAG, "click imageButton" + view.getId());
                            break;
                    }
                }
            };
            imageButtonArrayList = new ArrayList<>(capacity6x6);
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton2));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton3));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton4));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton5));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton6));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton7));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton8));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton9));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton10));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton11));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton12));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton13));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton14));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton15));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton16));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton17));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton18));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton19));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton20));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton21));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton22));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton23));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton24));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton25));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton26));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton27));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton28));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton29));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton30));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton31));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton32));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton33));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton34));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton35));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton36));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton37));
//            ImageButton im = (ImageButton) view.findViewWithTag(1);
//            for (int i = 0; i < capacity6x6; i++) {
//                imageButtonArrayList.add((ImageButton) view.findViewWithTag((Integer) (i + 1)));
//            }
//            ImageButton im
            for (int i = 0; i < capacity6x6; i++) {
                imageButtonArrayList.get(i).setOnClickListener(onClickListener);
            }


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private RelativeLayout relativeLayout;
    private String LOG_TAG = "flagmemorine";
    private ArrayList<ImageButton> imageButtonArrayList;
    private int capacity6x6 = 36;
}
