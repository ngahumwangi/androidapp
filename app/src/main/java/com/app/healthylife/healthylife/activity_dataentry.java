package com.app.healthylife.healthylife;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by MOSES on 2/25/2016.
 */
public class activity_dataentry extends Activity implements View.OnClickListener {
    private EditText editrecipename;
    private EditText editingredient;
    private Spinner categories;
    private EditText editinginsturuction;
    private Button ButtonSendFeedback;
    private Button ButtonDelete;


    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataentry);

        createDatabase();


        editrecipename = (EditText) findViewById(R.id.EditTextName);
        editingredient = (EditText) findViewById(R.id.Editingredient);
        categories=(Spinner) findViewById(R.id.SpinnerFeedbackType);
        editinginsturuction=(EditText) findViewById(R.id.EditTextFeedbackBody);
        ButtonSendFeedback= (Button) findViewById(R.id.ButtonSendFeedback);
        ButtonDelete = (Button) findViewById(R.id.ButtonDelete);

        ButtonSendFeedback.setOnClickListener(this);
        ButtonDelete.setOnClickListener(this);
    }
    protected void createDatabase(){
        db=openOrCreateDatabase("StoreDB", Context.MODE_PRIVATE, null);
         db.execSQL("CREATE TABLE IF NOT EXISTS category(category_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,category_name);") ;
         db.execSQL("CREATE TABLE IF NOT EXISTS Items(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,category_id INTEGER, recipe_name VARCHAR,ingredients VARCHAR,instructions TEXT);");
    }
    protected void insertIntoDB(){
        try {
        String name = editrecipename.getText().toString().trim();
        String ingr = editingredient.getText().toString().trim();
        String categ = categories.getSelectedItem().toString();
        String instruction= editinginsturuction.getText().toString().trim();
        if(name.equals("") || ingr.equals("")|| instruction.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        String query = "INSERT INTO Items(category_id,recipe_name,ingredients,instructions) VALUES((SELECT category_id FROM category WHERE category_name='"+categ+"') ,'"+name+"','"+ingr+"','"+instruction+"');";

            db.execSQL(query);

        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
            editrecipename.setText("");
            editingredient.setText("");
            editinginsturuction.setText("");

          }
          finally {
          }
    }
    protected void deleteFromDB()
    {
        String categ = categories.getSelectedItem().toString();
        String name = editrecipename.getText().toString().trim();
        String query2 = "DELETE FROM Items WHERE category_id=(SELECT category_id FROM category WHERE category_name='"+categ+"')or recipe_name='"+name+"' ";
        db.execSQL(query2);
        Toast.makeText(getApplicationContext(),"Data deleted successfully", Toast.LENGTH_LONG).show();
    }
    protected void insert_Bread() {

        db.execSQL("INSERT INTO category(category_name) SELECT * FROM (SELECT 'Bread') AS tmp WHERE NOT EXISTS( SELECT category_name FROM category WHERE category_name ='Bread') LIMIT 1;");
        Toast.makeText(getApplicationContext(),"Bread Added successfully", Toast.LENGTH_LONG).show();
    }
    protected void insert_Fruits() {

        db.execSQL("INSERT INTO category(category_name) SELECT * FROM (SELECT 'Fruits') AS tmp WHERE NOT EXISTS( SELECT category_name FROM category WHERE category_name ='Fruits') LIMIT 1;");
        Toast.makeText(getApplicationContext(),"Fruits Added successfully", Toast.LENGTH_LONG).show();
    }
    protected void insert_Vegetables() {

        db.execSQL("INSERT INTO category(category_name) SELECT * FROM (SELECT 'Vegetables') AS tmp WHERE NOT EXISTS( SELECT category_name FROM category WHERE category_name ='Vegetables') LIMIT 1;");
        Toast.makeText(getApplicationContext(),"Vegetables added successfully", Toast.LENGTH_LONG).show();
    }
    protected void insert_Soup() {

        db.execSQL("INSERT INTO category(category_name) SELECT * FROM (SELECT 'Soup') AS tmp WHERE NOT EXISTS( SELECT category_name FROM category WHERE category_name ='Soup') LIMIT 1;");
        Toast.makeText(getApplicationContext(),"Soup added successfully", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View v) {
        if(v ==ButtonSendFeedback ){
            insert_Bread();
            insert_Fruits();
            insert_Vegetables();
            insert_Soup();
          insertIntoDB();
        }
        if(v ==ButtonDelete){
            deleteFromDB();
        }
    }
}
