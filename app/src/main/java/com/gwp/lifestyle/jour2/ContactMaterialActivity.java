package com.gwp.lifestyle.jour2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.ActivityContactMaterialBinding;

public class ContactMaterialActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityContactMaterialBinding ui;
    private MaterialButton btnCree, btnCancel;
    private TextInputEditText nom, prenom, matricule,email,session;
    private final String TAG = "ContactMaterialActivity";
    private MaterialRadioButton sexeM, sexeF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //  instanciation de ui
        ui = ActivityContactMaterialBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        initFormField();
        initFormValues();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contact_material), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initFormField(){
        nom = ui.nom;
        prenom = ui.prenom;
        matricule = ui.matricule;
        email = ui.email;
        session = ui.session;
        sexeF = ui.sexeF;
        sexeM = ui.sexeM;
        btnCree = ui.btnCreer;
        btnCancel = ui.btnAnnuler;
        sexeM.setChecked(true);
        btnCree.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


//        btnCree.setOnClickListener(v->{
//            if(validateInputs()){
//                String sex_value = "M";
//                if(sexeF.isChecked()) sex_value ="F";
//            }else{
//                Toast.makeText(this,getString(R.string.error_form_contact),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private boolean validateInputs(){
        if (nom.getText().toString().isEmpty() ||
                prenom.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() ||
                matricule.getText().toString().isEmpty() ||
                session.getText().toString().isEmpty()) {
//            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initFormValues(){
        nom.setText(R.string.paterne_guiffo);
        prenom.setText(R.string.paterne);
        email.setText(R.string.my_email);
        matricule.setText(R.string.exemple_matricule);
        session.setText(R.string.exemple_Session);
        sexeM.setChecked(true);
        sexeF.setChecked(false);

    }

    @Override
    public void onClick(View v) {

        int id_view = v.getId();
        if(btnCree.getId()==id_view){
            if(validateInputs()){
                setupDialogue("Resultats du Formulaire",resultForm(),
                        (dialog, which) -> {
                            Intent intent = new Intent(this, ContactActivity.class);
                            startActivity(intent);
                            finish();
                        },
                        (dialog,which)->{

                        }
                        ).show();
            }else{
                Toast.makeText(this,getString(R.string.error_form_contact),Toast.LENGTH_SHORT).show();
            }
        }
        if(id_view==btnCancel.getId()){
            setupDialogue("Confirmation de Reinitialisation","Voulez-vous reinitialiser?",
                    (dialog,which)->{
                        initFormValues();
                        Toast.makeText(this,getString(R.string.sucess_init_form),Toast.LENGTH_SHORT).show();
                    },(dialog,which)->{
                        dialog.dismiss();
                    }).show();
        }
    }
    private AlertDialog.Builder setupDialogue(String title, String message,
                                              DialogInterface.OnClickListener positiveAction,
                                              DialogInterface.OnClickListener negativeAction
    ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok,positiveAction)
                .setNegativeButton(R.string.cancel,negativeAction);
        return builder;
    }

    private String resultForm(){
        String sex_value = sexeF.isChecked() ? "F" : "M";
        return "Formulaire soumis : " +
                "\nNom : " + nom.getText().toString() +
                "\nPrenom : " + prenom.getText().toString() +
                "\nEmail : " + email.getText().toString() +
                "\nSexe : " + sex_value +
                "\nMatricule : " + matricule.getText().toString() +
                "\nSession : " + session.getText().toString();
    }

    private void handleSubmit() {
            Toast.makeText(this, resultForm(), Toast.LENGTH_LONG).show();
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CycleDeVie", "onStart appele");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CycleDeVie", "onResume appele");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CycleDeVie", "onPause appele");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CycleDeVie", "onStop appele");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.
    }
}