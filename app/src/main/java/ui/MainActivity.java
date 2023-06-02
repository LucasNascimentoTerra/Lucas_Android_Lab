package ui;

import static android.app.ProgressDialog.show;
import static android.opengl.ETC1.getHeight;
import static android.opengl.ETC1.getWidth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.nasc0014.databinding.ActivityMainBinding;
import data.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());




        variableBinding.textview.setText(model.editString.getValue());




        variableBinding.mybutton.setOnClickListener(click  -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has: " + s);
        });



        variableBinding.myimagebutton.setOnClickListener(view ->{
            String message = "width = " + variableBinding.myimagebutton.getWidth() + " height = " + variableBinding.myimagebutton.getHeight();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });



        //checkbox
        variableBinding.checkbox.setOnCheckedChangeListener((btn, isChecked) -> {
            model.getIsSelected().postValue(isChecked);
        });

        // radio button
        variableBinding.radiobutton.setOnCheckedChangeListener((btn, isChecked) -> {
            model.getIsSelected().postValue(isChecked);
        });

        // switch button
        variableBinding.switchbutton.setOnCheckedChangeListener((btn, isChecked) -> {
            model.getIsSelected().postValue(isChecked);
        });



        model.isSelected.observe(this, isSelected -> {
            variableBinding.checkbox.setChecked(isSelected);
            variableBinding.radiobutton.setChecked(isSelected);
            variableBinding.switchbutton.setChecked(isSelected);
                });


    }
}