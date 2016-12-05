package com.root.cyber.d_liknesms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by root on 03/04/16.
 */
public class ProsesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses);
    }

    public class SubstringExample {

        public void main(String args[]) {
            String str = new String("Javacodegeeks");

            System.out.println("Initial string is: " + str);

            System.out.println("Start position=4 and no end position specified: "
                    + str.substring(4));

            System.out.println("Start position=2 and end position=11: "
                    + str.substring(2, 11));

            System.out.println("Start position=3 and end position=3: "
                    + str.substring(3, 3).isEmpty());
  System.out.println("Start position=-2 and end position=5: "
                    + str.substring(-2, 5));
            System.out.println("Start position=5 and end position=2: "
                    + str.substring(5, 2));
        }

    }
}
