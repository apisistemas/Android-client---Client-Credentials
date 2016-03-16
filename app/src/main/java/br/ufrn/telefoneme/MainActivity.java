package br.ufrn.telefoneme;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TelefoneDTO> telefones;
    private ProgressDialog progressDialog;
    private EditText editText;
    private ImageButton searchButton;

    public MainActivity(){
        telefones = new ArrayList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(telefones, MainActivity.this);
        recyclerView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (ImageButton) findViewById(R.id.search_button_tel);

        searchButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Create a new progress dialog
                progressDialog = ProgressDialog.show(MainActivity.this, null, "Carregando dados da aplicação...", true);

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        //Initialize a LoadViewTask object and call the execute() method
                        String text = OltuJavaClient.getResource(editText.getText().toString());
                        telefones = new ArrayList<TelefoneDTO>();
                        if(!text.equalsIgnoreCase("")){
                            try {
                                JSONArray array = new JSONArray(text);
                                for(int i = 0; i < array.length(); ++i){
                                    JSONObject jsonList = array.getJSONObject(i);

                                    telefones.add(new TelefoneDTO(jsonList.getString("numero"),
                                            jsonList.getString("descricao"),
                                            jsonList.getString("localizacao"),
                                            jsonList.getString("setor")));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                progressDialog.dismiss();
                                adapter = new RecyclerAdapter(telefones, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }).start();

            }
        });
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
}
