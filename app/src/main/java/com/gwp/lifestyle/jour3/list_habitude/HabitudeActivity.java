package com.gwp.lifestyle.jour3.list_habitude;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.ActivityHabitudeBinding;

import java.util.ArrayList;

public class HabitudeActivity extends AppCompatActivity implements OnHabitudeItemListener {
    private RecyclerView recyclerView;
    private ActivityHabitudeBinding ui;
    private HabitudeAdaptateur adaptateur;
    ArrayList<Habitude> habitudes = new ArrayList<>();
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ui = ActivityHabitudeBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        recyclerView = ui.listHabitude;
        toolbar = ui.topAppBar;
        assert toolbar != null;
        toolbar.setOnMenuItemClickListener(
                item -> {

                    if(item.getItemId()==R.id.refresh_list){
                        Toast.makeText(this,"C'est bon",Toast.LENGTH_LONG).show();
                        return true;
                    }
                    return true;

                }
        );
//        toolbar.setNavigationOnClickListener();
        setupRecyclerView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.habitude_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.refresh_list){
            Toast.makeText(this,"C'est bon",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private ArrayList<Habitude> getData(){

        for (int i = 0; i < 100; i++) {
            habitudes.add(new Habitude("Lecture quotidienne", "Consacrer chaque jour du temps à la lecture, que ce soit des livres, des articles ou des blogs."));
            habitudes.add(new Habitude("Méditation matinale", "Commencer la journée avec une séance de méditation pour apaiser l'esprit."));
            habitudes.add(new Habitude("Faire de l'exercice chaque jour", "Prendre du temps chaque jour pour une activité physique comme la course ou le yoga."));
            habitudes.add(new Habitude("Prendre un petit déjeuner nutritif", "Manger un petit déjeuner équilibré pour bien commencer la journée."));
            habitudes.add(new Habitude("Lecture quotidienne", "Consacrer chaque jour du temps à la lecture, que ce soit des livres, des articles ou des blogs."));
            habitudes.add(new Habitude("Méditation matinale", "Commencer la journée avec une séance de méditation pour apaiser l'esprit."));
            habitudes.add(new Habitude("Faire de l'exercice chaque jour", "Prendre du temps chaque jour pour une activité physique comme la course ou le yoga."));
            habitudes.add(new Habitude("Prendre un petit déjeuner nutritif", "Manger un petit déjeuner équilibré pour bien commencer la journée."));

            habitudes.add(new Habitude("Lecture quotidienne2", "Consacrer chaque jour du temps à la lecture, que ce soit des livres, des articles ou des blogs."));
            habitudes.add(new Habitude("Méditation matinale2", "Commencer la journée avec une séance de méditation pour apaiser l'esprit."));
            habitudes.add(new Habitude("Faire de l'exercice chaque jour2", "Prendre du temps chaque jour pour une activité physique comme la course ou le yoga."));
            habitudes.add(new Habitude("Prendre un petit déjeuner nutritif2", "Manger un petit déjeuner équilibré pour bien commencer la journée."));

            habitudes.add(new Habitude("Lecture quotidienne3", "Consacrer chaque jour du temps à la lecture, que ce soit des livres, des articles ou des blogs."));
            habitudes.add(new Habitude("Méditation matinale3", "Commencer la journée avec une séance de méditation pour apaiser l'esprit."));
            habitudes.add(new Habitude("Faire de l'exercice chaque jour3", "Prendre du temps chaque jour pour une activité physique comme la course ou le yoga."));
            habitudes.add(new Habitude("Prendre un petit déjeuner nutritif3", "Manger un petit déjeuner équilibré pour bien commencer la journée."));
        }

        return habitudes;
    }

    private void setupRecyclerView(){
        adaptateur = new HabitudeAdaptateur(this,getData(), this);
        recyclerView.setAdapter(adaptateur);
//        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
    }


    @Override
    public void onHabitudeClick(int position) {
        habitudes.remove(position);
        adaptateur.notifyDataSetChanged();
        Toast.makeText(this,"Click sur Item"+position,Toast.LENGTH_LONG).show();
    }
}