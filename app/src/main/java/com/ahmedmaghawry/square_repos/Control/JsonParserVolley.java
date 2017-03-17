package com.ahmedmaghawry.square_repos.Control;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Ahmed Maghawry on 3/17/2017.
 * Volley implementation which extends JsonParser its parameter is JsonArray which needed to parse
 * its output is the arrayList of Repos to diplay in recycleview
 */
public class JsonParserVolley extends JsonParser {

    public JsonParserVolley(JSONArray json) throws JSONException {
        fillArrays(json.toString());
        createRepos();
    }

    @Override
    public void dummy() {
        //Don't Do No thing Just to make the JsonParse class abstract
    }
}
