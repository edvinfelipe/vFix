package corporation.app.menchus.com.vfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class popciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popciones);
    }

    public  void productos(View view){
        Intent miIntent= new Intent(this,productos.class);
        startActivity(miIntent);
    }
}
