package ashwin.examples.com.resizingwebviewdialogactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void show(View view) {
        String url = ((EditText) findViewById(R.id.url_edittext)).getText().toString().trim();
        if (url.isEmpty() || !url.startsWith("http")) {
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url), MainActivity.this, WebViewDialogActivity.class);
            startActivity(intent);
        }
    }

}
