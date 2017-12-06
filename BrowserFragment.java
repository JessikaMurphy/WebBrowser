package edu.temple.webbrowser;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowserFragment extends Fragment {

    WebView browser;
    String savedUrl;


    public BrowserFragment(){

    }


//    public static BrowserFragment newInstance(int position) {
//
//        Bundle args = new Bundle();
//        args.putInt("position",position);
//        BrowserFragment fragment = new BrowserFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_tab, container, false);
        browser = (WebView)v.findViewById(R.id.my_browser);
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(savedUrl!=null){
            Toast.makeText(getContext(), savedUrl, Toast.LENGTH_SHORT).show();
            browser.loadUrl(savedUrl);

        }else
            Toast.makeText(getContext(), "nothing there", Toast.LENGTH_SHORT).show();





        browser.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
//                Toast.makeText(getActivity(),"urlchange: "+url,Toast.LENGTH_SHORT).show();
                savedUrl = url;
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList();
//                Intent intent = getActivity().getIntent();
//                intent.putExtras(bundle);
                listener.passUrl(savedUrl);

                super.onPageFinished(view, url);
            }


        });



//        v.findViewById(R.id.enterSite);
        return v;
    }


    //    public static Fragment newInstance(String url){
//        BrowserFragment f=new BrowserFragment();
//        f.url=url;
//        return f;
//    }
//    public int getSomeIdentifier(){
//        return getArguments().getInt("position");
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listener = (urlChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void changeUrl(String url){
        if(url!=null){
            if(!(url.startsWith("http://")|| url.startsWith("https://"))){
                url = "http://" + url;

                browser.loadUrl(url);

            }else{
                browser.loadUrl(url);

            }
        }


    }
    public void loadSavedUrl(String url){
        browser.loadUrl(url);
    }


    public interface urlChangeListener{
        void passUrl(String savedUrl);

    }
    urlChangeListener listener;





}
