package com.example.comparerooms;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.R.id.text1;
import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {
    ImageView upper;
    ImageView down;
    Button startBtn;
    Button againBtn;
    ListView listViewResults;
    TextView leftOver;
    //this string will contain a matrix element
    //Why matrix? The matrix is a set of numbers to make sure every picture is compared with each other
    String numCombination = "";
    //to add a new result to the results
    int newResult = 0;
    //every matrix element has 2 subelements for picture comparison, every subelement represents a picture
    String[] numCombinationList;
    //matrix element will be chosen randomly
    Random rand = new Random();
    Object obj1;
    Object obj2;
    //matrix values are calculated in the matrix() function and initialized in the matrixList[]
    ArrayList<String> matrixList = matrix();
    //list to add points according to which picture is chosen
    ArrayList<Integer> results = new ArrayList<>();
    //list that stores the imageID's
    Integer[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten, R.drawable.eleven};
    //list that stores the endresult and changes from integer to string (to be able to display it in listviewResults)
    ArrayList<String> endResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upper = findViewById (R.id.picOne);
        down = findViewById (R.id.picTwo);
        startBtn = findViewById(R.id.btnStart);
        leftOver = findViewById(R.id.leftOver);
        listViewResults = findViewById(R.id.results);
        againBtn = findViewById(R.id.btnAgain);
        //I work with one page layout, so I play with the visibility of the elements
        upper.setVisibility(View.INVISIBLE);
        down.setVisibility(View.INVISIBLE);
        startBtn.setVisibility(View.VISIBLE);
        againBtn.setVisibility(View.INVISIBLE);
        //each picture starts with a zero score
        for (int i=1; i<=11; i++) {
            results.add(0);
        }

        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextSize(30);
        textView.setText("Results");
        listViewResults.addHeaderView(textView);

        // For populating list data
//        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(getApplicationContext(),"You Selected "+countryNames[position-1]+ " as Country",Toast.LENGTH_SHORT).show();        }
//        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if clicked, the comparison activity will start
                startVisibles();
                comparisonDeterminer();
            }
        });

        againBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when the activity is done, you can do it again
                startVisibles();
                againBtn.setVisibility(View.INVISIBLE);
                //reinitialize the lists
                results.clear();
                endResults.clear();
                for (int i=1; i<=11; i++) {
                    results.add(0);
                }
                //all matrix elements are removed, so we need to add them again
                matrixList = matrix();
                comparisonDeterminer();
            }
        });
        upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matrixList.size() == 1){
                    resultCalculation(0);
                }
                else {
                    createComparison(0);
                }

            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the next if statement will determine if there are matrix elements leftover
                if (matrixList.size() == 1){
                    // calculate the end result
                    resultCalculation(1);
                }
                else {
                    //We still have matrix elements, so we need to create more comparisons.
                    createComparison(1);
                }

            }
        });


    }
    // this function will create a matrix to make sure every picture is compared
    public ArrayList<String> matrix(){
        //the matrix elements are numbers => 1 representing pictureOne, 2 representing pictureTwo, etc...
        ArrayList<String> listOne = new ArrayList<String>();
        ArrayList<String> listTwo = new ArrayList<String>();
        for (int i = 0; i<=10; i++){
            listOne.add(String.valueOf(i));
        }
        for (String j : listOne) {
            for (int i = 0; i <= 10; i++) {
                if (Integer.valueOf(j) != i && Integer.valueOf(j)<=i){
                    listTwo.add(j + "/" + i);
                }
            }
        }
        //it will return a list of 55 matrix elements 11*11 = 121 elements - duplicates (-11) - reversed duplicates (-55)
        return listTwo;
    }
    //the elements that need to be (in)visible when the activity starts
    public void startVisibles(){

        upper.setVisibility(View.VISIBLE);
        down.setVisibility(View.VISIBLE);
        leftOver.setVisibility(View.VISIBLE);
        startBtn.setVisibility(View.INVISIBLE);
        listViewResults.setVisibility(View.INVISIBLE);
    }
    public void createComparison(int position){
        //position is the clicked item. If the upper image is clicked, I will use 0, the down image is 0
        newResult = results.get(Integer.parseInt(numCombinationList[position]))+ 1;
        results.set(Integer.parseInt(numCombinationList[position]), newResult);
        matrixList.remove(numCombination);
        comparisonDeterminer();
    }
    public void resultCalculation(int position){
        //position is the clicked item. If the upper image is clicked, then I will use 0, the down image is 1. You can use true/false as well.
        //The image that is clicked will receive one point
        newResult = results.get(Integer.parseInt(numCombinationList[position]))+ 1;
        //store it in the results list
        results.set(Integer.parseInt(numCombinationList[position]), newResult);
        //the matrix list need to be emptied so we know all combinations are used
        matrixList.remove(numCombination);
        leftOver.setText("Remaining combinations: " + matrixList.size());
        upper.setVisibility(View.INVISIBLE);
        down.setVisibility(View.INVISIBLE);
        leftOver.setVisibility(View.INVISIBLE);
        listViewResults.setVisibility(View.VISIBLE);
        //we need to stringify the results so it can be displayed
        for (int resultId=0; resultId<results.size(); resultId++) {
            endResults.add("Room " + (resultId+1) + " = " + results.get(resultId));
        }
        // the results need to be displayed per row. 1st column has an image, 2nd column has the result
        CustomResults customResults = new CustomResults(this, endResults.toArray(new String[0]), images);
        listViewResults.setAdapter(customResults);
        againBtn.setVisibility(View.VISIBLE);
    }
    public void comparisonDeterminer(){
        try {
            numCombination = matrixList.get(rand.nextInt(matrixList.size()));
            //every matrix element has two subelements (5/4) => picture five compared with picture four
            numCombinationList = numCombination.split("/");
            //call the images
            obj1 = images[Integer.parseInt(numCombinationList[0])];
            obj2 = images[Integer.parseInt(numCombinationList[1])];
            upper.setImageResource((Integer) obj1);
            down.setImageResource((Integer) obj2);
        }
        catch (IllegalArgumentException e){
            //the app sometimes catches this error, but it doesn't affect the result.
            //try catch will prevent the app from shutting ddown

        }
        leftOver.setText("Remaining combinations: " + matrixList.size());
    }
}