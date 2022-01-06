package kimp.where.is.a.cat.org;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SearchPlace searchPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPlace = new SearchPlace(this);
        setContentView(searchPlace);

        getSupportActionBar().hide();
    }
}