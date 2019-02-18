package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView dImageIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        Sandwich sandwich = new Sandwich();
        try {
            // Reads all sandwiches json from string.xml
            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            //
            String json = sandwiches[position];
            sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
        }catch (Exception e){
            e.printStackTrace();
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(dImageIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sammy) {
        TextView dAlsoKnownAsLabelTv = findViewById(R.id.also_known_label_tv);
        TextView dAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView dIngredientsTv = findViewById(R.id.ingredients_tv);
        TextView dDescriptionTv = findViewById(R.id.description_tv);
        TextView dOriginLabelTv = findViewById(R.id.origin_label_tv);
        TextView dOriginTv = findViewById(R.id.origin_tv);

        List<String> alsoKnownValues = sammy.getAlsoKnownAs();
        List<String> ingredientsValues = sammy.getIngredients();
        String descriptionValue = sammy.getDescription();
        String originValue = sammy.getPlaceOfOrigin();

        // append strings in aka  and append strings to the text view
        for (int i = 0; i < alsoKnownValues.size(); i++){
            dAlsoKnownAsTv.append(alsoKnownValues.get(i));
            // do not append a comma to the final value
            if ( i != alsoKnownValues.size() - 1){
                dAlsoKnownAsTv.append(", ");
            }
        }
        // If aka List is empty, hide the aka Label
        if (alsoKnownValues.size() == 0){
            dAlsoKnownAsLabelTv.setVisibility(View.INVISIBLE);
        }else{
            dAlsoKnownAsLabelTv.setVisibility(View.VISIBLE);
        }

        // iterate over ingredients array and append strings to text view
        for (int i = 0; i < ingredientsValues.size(); i++){
            dIngredientsTv.append("\t -" + ingredientsValues.get(i));
           // do not append a newline code to the last ingredient
            if ( i != ingredientsValues.size() - 1){
                dIngredientsTv.append("\n");
            }
        }
        // set Description Text View
        dDescriptionTv.setText(descriptionValue);
        // Hide Place of Origin Label if there is no place of origin given
        if ( originValue.isEmpty() ){   //
            dOriginLabelTv.setVisibility(View.INVISIBLE);

        }else{ //set the place of origin and make it visible
            dOriginLabelTv.setVisibility(View.VISIBLE);
            dOriginTv.setText(originValue);
        }
    }
}
