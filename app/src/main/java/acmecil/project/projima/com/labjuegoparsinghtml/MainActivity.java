package acmecil.project.projima.com.labjuegoparsinghtml;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.ConsoleHandler;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    String[] imageSources;
    String[] movieTitles;
    public String htmlText;
    QuizManager quizManager;
    ArrayAdapter adapter;
    ListView listView;
    MediaPlayer mediaPlayer;
    boolean valid;
    int attemps;
    TextView m;

    /*
    public void DownloadButtonClicked(View view){

        // https://media.gq.com/photos/5a341350b491742d4078b7b9/3:2/w_560/chewbacca-porg-friend.jpg
        ImageDownloader imageDownloader = new ImageDownloader();
        try {
            Bitmap image = imageDownloader.execute("https://media.gq.com/photos/5a341350b491742d4078b7b9/3:2/w_560/chewbacca-porg-friend.jpg").get();

            imageView.setImageBitmap(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("button", "clicked");

    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        imageView = findViewById(R.id.imageViewContainer);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.uragirimono);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        valid =true;
        attemps = 0;
        m = findViewById(R.id.pregunta);

        /*
        String html = null;
        try {
            html = new DownloadTask().execute("https://listas.20minutos.es/lista/top-20-peliculas-de-superheroes-433492/").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        m.setText("");
        imageView.setTranslationY(900);
        imageView.setAlpha(0f);


        imageView.animate().translationY(350).setDuration(7000).alpha(1);



        final Handler handler = new Handler();
        final Thread r = new Thread() {
            public void run() {
                // DO WORK
                if(!valid && attemps == 0) {
                    updateData();
                    attemps ++;
                }else{
                    valid = !valid;
                }
                handler.postDelayed(this, 9000);
            }
        };
        r.start();




    }

    private void updateData(){
        Log.i("HEEHEE","requiem");
        Ion.with(getApplicationContext()).load("https://aminoapps.com/c/anime-es/page/blog/top-10-los-stands-mas-poderosos/bNec_ou8QE0KzE55zo0rx2k27o35gvV").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                htmlText = result;
                if(!mediaPlayer.isPlaying()) mediaPlayer.start();
                parseHTML();
            }
        });
    }

    private void parseHTML() {
        Document doc = Jsoup.parse(htmlText);
        Elements imgsrcs = doc.select("div .image-container");

        movieTitles = new String[imgsrcs.size()];
        imageSources = new String[imgsrcs.size()];

        for(int i = 0; i < imgsrcs.size(); i++){
            movieTitles[i] = imgsrcs.get(i).text();
            imageSources[i] = imgsrcs.get(i).select("img").attr("src");
            Log.i("movie", movieTitles[i]);
            Log.i("Image Source",imageSources[i]);
        }
        quizManager = new QuizManager(imageSources, movieTitles);
        generateNextQuestion();

    }

    private void generateNextQuestion(){
        Bundle b = quizManager.getNextQuestion();
        final List<String> arrayList = b.getStringArrayList("standOptions");
        String imgPath = "http:" + b.getString("imagePath");
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        Picasso.with(getApplicationContext()).load(imgPath).into(imageView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (quizManager.validateAnswer(arrayList.get(position))){
                    Toast.makeText(getApplicationContext(),"Respuesta Correcta",Toast.LENGTH_SHORT).show();
                    generateNextQuestion();
                }else{
                    Toast.makeText(getApplicationContext(),"Respuesta Incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView.setTranslationY(0);
        m.setText("¿Cuál es el stand de la imagen?");
        listView.setAdapter(adapter);
    }






}
