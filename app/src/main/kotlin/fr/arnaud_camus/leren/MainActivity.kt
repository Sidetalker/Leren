package fr.arnaud_camus.leren

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import fr.arnaud_camus.leren.ui.DictionaryFragment
import fr.arnaud_camus.leren.ui.LearnFragment
import fr.arnaud_camus.leren.ui.PracticeFragment
import fr.arnaud_camus.leren.utils.DismissKeyboardDrawerListener

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var navigationView: NavigationView? = null
    var selectedTab = R.id.nav_learn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        drawer.addDrawerListener(DismissKeyboardDrawerListener())
        toggle.syncState()

        navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView?.setNavigationItemSelectedListener(this)
        navigationView?.setCheckedItem(R.id.nav_learn)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, LearnFragment())
                .commit();
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        var fragment: Fragment? = null;

        if (id == R.id.nav_learn) {
            fragment = LearnFragment()
        } else if (id == R.id.nav_practice) {
            fragment = PracticeFragment()
        } else if (id == R.id.nav_dictionary) {
            fragment = DictionaryFragment()
        }

        if (fragment != null) {
            if (id != selectedTab) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
            selectedTab = id
        } else {
            Toast.makeText(this, "Not yet supported!", Toast.LENGTH_SHORT).show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
