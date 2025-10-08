package edu.rit.swen262.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

/**
 * Searchable database of ingredients
 * Stores and provides ingredient data from a CSV file
 * 
 * @author David Martinez
 */
public class IngredientDatabase {
    private static final String DEFAULT_FILENAME = "src/main/java/edu/rit/swen262/data/ingredients.csv";

    /**
     * A 2D HashMap to mimic a database
     * Intended to provide accessible primitive ingredient data
     * 
     * Format:
     * {
     *   name: Ingredient
     * }
     */
    private final HashMap<String, Ingredient> ingredients;
    
    /**
     * The name of the CSV file
     */
    private String filename;

    /**
     * Constructor for Ingredient database
     * Uses default data/ingredients.csv file
     * @throws IOException
     */
    public IngredientDatabase() throws IOException {
        ingredients = new HashMap<>();
        this.filename = DEFAULT_FILENAME;
        initIngredients(DEFAULT_FILENAME);
    }

    /**
     * Constructor for IngredientDatabase
     * @param filename The name of the file
     * @throws IOException
     */
    public IngredientDatabase(String filename) throws IOException {
        ingredients = new HashMap<>();
        this.filename = filename;
        initIngredients(filename);
    }

    private void initIngredients(String filename) throws IOException {
        CsvListReader csvReader = new CsvListReader(new FileReader(filename), CsvPreference.STANDARD_PREFERENCE);
        csvReader.read(); // skip header
        List<String> row = csvReader.read(); // first row
        while (row != null) { // loop through all lines in csv
            String name = row.get(1);

            String caloriesString = row.get(3) != null ? row.get(3) : "0";
            String fatString = row.get(5) != null ? row.get(5) : "0.0";
            String proteinString = row.get(4) != null ? row.get(4) : "0.0";
            String fiberString = row.get(8) != null ? row.get(8) : "0.0";
            String carbsString = row.get(7) != null ? row.get(7) : "0.0";

            int calories = Integer.parseInt(caloriesString);
            float fat = Float.parseFloat(fatString);
            float protein = Float.parseFloat(proteinString);
            float fiber = Float.parseFloat(fiberString);
            float carbs = Float.parseFloat(carbsString);
            
            Ingredient ingredient = new Ingredient(name, calories, fat, protein, fiber, carbs);
            ingredients.put(name, ingredient);

            row = csvReader.read(); // next line
        }
        csvReader.close(); // prevent resource leak
    }

    /**
     * Searches the database for ingredients that contain the substring in its description
     * @param substring The search term
     * @return The ingredient data found through search. Empty HashMap if none found. Null if IOException occurs.
     */
    public HashMap<String, Ingredient> search(String substring) {
        HashMap<String, Ingredient> result = new HashMap<>();

        try {
            CsvListReader csvReader = new CsvListReader(new FileReader(this.filename), CsvPreference.STANDARD_PREFERENCE);
            csvReader.read(); // skip header
            List<String> row = csvReader.read();
            while (row != null) {
                String name = row.get(1);
                if (name.contains(substring.toUpperCase())) {
                    result.put(name, ingredients.get(name));
                }
                row = csvReader.read(); // next line
            }
            csvReader.close();
        } catch (IOException e) {
            return null;
        }

        @SuppressWarnings("unchecked")
        HashMap<String, Ingredient> clone = (HashMap<String, Ingredient>) result.clone();

        return clone;
    }

    public List<Ingredient> getAllIngredients() {
        return new ArrayList<>(ingredients.values());
    }
    

    public static void main(String[] args) throws IOException {
        IngredientDatabase db = new IngredientDatabase();

        String searchTerm = "TORTELLINI";
        HashMap<String, Ingredient> data1 = db.search(searchTerm);
        for (String name: data1.keySet()) {
            System.out.println(name);
        }
    }
}
