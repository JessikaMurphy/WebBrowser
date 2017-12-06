package edu.temple.webbrowser;

import android.app.Fragment;
import android.app.FragmentManager;

import android.net.Uri;
import android.provider.Browser;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;



import static android.R.attr.button;
import static android.support.v4.view.ViewPager.*;

public class MainActivity extends AppCompatActivity implements BrowserFragment.urlChangeListener {

    ViewPager pager;

    //array list of fragment type to hold fragments

    //ArrayList<Integer> sDataSet;
    ArrayList<BrowserFragment> fragments = new ArrayList<>();
    ArrayList<String> urlStrings = new ArrayList<>();
    BrowserFragment currentFragment; //store current fragment
    Logger log = Logger.getAnonymousLogger();
    int index; //which fragment is currently displayed

    //to add fragments to array
    EditText editText;
    Button button;
    myAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = (EditText) findViewById(R.id.enterSite);
        button = (Button) findViewById(R.id.go_button);
        //set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //set up view pager
        pager = (ViewPager) findViewById(R.id.vpPager);

        adapter = new myAdapter(getSupportFragmentManager(), fragments, urlStrings);
        pager.setAdapter(adapter);




        editText = (EditText) findViewById(R.id.enterSite);

        //Service other applications that call this app
        Uri data = getIntent().getData();
        if(data != null) {
            String url = data.toString();
            BrowserFragment fragment = new BrowserFragment();
            fragments.add(adapter.listIndex, fragment);
            adapter.NUM_FRAGS++;

            adapter.notifyDataSetChanged();
            currentFragment = fragment;
            pager.setCurrentItem(adapter.listIndex);
            Toast.makeText(getApplicationContext(),"url:"+url,Toast.LENGTH_LONG).show();
            editText.setText(url);
            currentFragment.savedUrl = url;

        }





        button = (Button) findViewById(R.id.go_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log.info("Go button pressed");
                String websiteUrl = editText.getText().toString();


                index = pager.getCurrentItem();
                currentFragment = fragments.get(index);

                currentFragment.changeUrl(websiteUrl);

                button.setVisibility(View.GONE);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_tab:
                button.setVisibility(View.VISIBLE);
                BrowserFragment fragment = new BrowserFragment();
                fragments.add(adapter.listIndex, fragment);
                adapter.NUM_FRAGS++;

                adapter.notifyDataSetChanged();
                currentFragment = fragment;
                pager.setCurrentItem(adapter.listIndex);

                editText.setText("");
                return true;
            case R.id.item_back:
                if ((pager.getCurrentItem() - 1) < 0)
                    Toast.makeText(getApplicationContext(), "cant go back further", Toast.LENGTH_SHORT).show();
                else {
                    //pressing <- will use the view pager's functionality to change the current fragment to previous
                    pager.setCurrentItem(pager.getCurrentItem() - 1);
                    //because the previous fragment is set to current we can set the text of the url with
                    String savedUrl = fragments.get(pager.getCurrentItem()).savedUrl;
                    editText.setText(savedUrl);


//                    fragments.remove(pager.getCurrentItem());
//                    currentFragment = new BrowserFragment();
//                    currentFragment.savedUrl = savedUrl;
//                    fragments.add(pager.getCurrentItem(),currentFragment);
//                    adapter.notifyDataSetChanged();
//                    currentFragment.changeUrl(savedUrl);

//                    fragments.get(pager.getCurrentItem()).loadSavedUrl(savedUrl);
                    button.setVisibility(View.GONE);
                }
                return true;
            case R.id.item_forward:
                if ((pager.getCurrentItem() + 1) < adapter.NUM_FRAGS) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                    String savedUrl = fragments.get(pager.getCurrentItem()).savedUrl;
                    editText.setText(savedUrl);
//                    fragments.get(pager.getCurrentItem()).loadSavedUrl(savedUrl);
                    button.setVisibility(View.GONE);


                }
                else
                    Toast.makeText(getApplicationContext(), "cant go forward further, try making a new tab", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    public void passUrl(String url){
        editText.setText(url);
//        Toast.makeText(getApplicationContext(),"passurl in MAIN: "+url,Toast.LENGTH_SHORT).show();
    }













}