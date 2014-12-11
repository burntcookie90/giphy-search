package io.dwak.giphysearch;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.dwak.giphysearch.network.GiphyService;
import io.dwak.giphysearch.network.model.Giphy;
import io.dwak.giphysearch.network.model.SearchResponse;
import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.container, new PlaceholderFragment())
                                       .commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            setSupportActionBar(toolbar);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final EditText searchBox = (EditText) rootView.findViewById(R.id.search_box);
            Button searchButton = (Button) rootView.findViewById(R.id.search_button);
            GridView gridView = (GridView) rootView.findViewById(R.id.list);
            final GiphyArrayAdapter giphyArrayAdapter = new GiphyArrayAdapter(getActivity(), R.layout.giphy_grid_item);
            gridView.setAdapter(giphyArrayAdapter);
            final ClipboardManager clipboard = (ClipboardManager)
                    getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String url = giphyArrayAdapter.getItem(position).getGiphyImage().getGiphyOriginal().getUrl();
                    clipboard.setPrimaryClip(ClipData.newPlainText("gif", url));
                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();
                }
            });
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint("http://api.giphy.com/")
                    .build();

            final GiphyService service = restAdapter.create(GiphyService.class);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    giphyArrayAdapter.clear();
                    service.search(String.valueOf(searchBox.getText()), "dc6zaTOxFJmzC", 5)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<SearchResponse>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(MainActivity.class.getSimpleName(), "complete");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(SearchResponse searchResponse) {
                                    giphyArrayAdapter.addAll(searchResponse.getGiphyList());

                                }
                            });
                }
            });
            return rootView;
        }
    }

    static class GiphyArrayAdapter extends ArrayAdapter<Giphy> {

        private final Context mContext;
        private final int mResource;

        public GiphyArrayAdapter(Context context, int resource) {
            super(context, resource);
            mContext = context;
            mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView == null){
                imageView = (ImageView) LayoutInflater.from(mContext).inflate(mResource, null);
            }
            else {
                imageView = (ImageView) convertView;
            }

            Glide.with(mContext)
                    .load(getItem(position).getGiphyImage().getGiphyFixedWidthDownSampled().getUrl())
                    .asGif()
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(imageView);
            return imageView;
        }


    }
}
