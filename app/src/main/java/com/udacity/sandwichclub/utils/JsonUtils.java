package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    //JSON keys
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String AKA = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_SRC = "image";
    private static final String INGREDIENTS = "ingredients";

/*


*/
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        //sandwichJson contains the entire Sandiwich object in Json Format
        JSONObject sandwichJson = new JSONObject(json);
        //nameObject contains the mainName string and the alsoKnownAs Array of Strings
        JSONObject nameObject = sandwichJson.getJSONObject(NAME);
        // sandwichObject contains the Sandwich object we will be assigning data to and returning
        Sandwich sandwichObject = new Sandwich();

        //Find keys in the json string and set values to Sandwich object
        sandwichObject.setMainName(nameObject.getString(MAIN_NAME));
        sandwichObject.setPlaceOfOrigin(sandwichJson.getString(PLACE_OF_ORIGIN));
        sandwichObject.setDescription(sandwichJson.getString(DESCRIPTION));
        sandwichObject.setImage(sandwichJson.getString(IMAGE_SRC));

        //assign all aka strings to sandwich object as a string list
        JSONArray akaArray = nameObject.getJSONArray(AKA);
        List<String> akaList = new ArrayList<>();
        for ( int i = 0; i < akaArray.length(); i++){
            akaList.add(akaArray.getString(i));
        }
        sandwichObject.setAlsoKnownAs(akaList);

        //assign all ingredients strings to sandwich object as a string list
        JSONArray ingredients = sandwichJson.getJSONArray(INGREDIENTS);
        List<String> ingredientsList = new ArrayList<>();
        for (int i = 0; i < ingredients.length(); i++){
            ingredientsList.add(ingredients.getString(i));
        }
        sandwichObject.setIngredients(ingredientsList);

        return sandwichObject;
    }
}
