package com.example.benedict.attendanceapp;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

class Global {


    void textViewToEditText(TextView textView, EditText editText){
        editText.setText(textView.getText().toString().trim());
    }

    String getTextView_filtered(TextView textView){
        return textView.getText().toString().trim();
    }

    String getTextView_unfiltered(TextView textView){
        return textView.getText().toString().trim();
    }

    String filter(EditText editText){

        String text = editText.getText().toString().trim();

        if (text.contains("'")) text = text.replace("'","''");
        return text;
    } // trims, and duplicates single quote on edit texts for query purposes

    String getEditText_unfiltered(EditText editText){

        return editText.getText().toString().trim();
    } // trims edit texts


    String filterTextView(TextView textView){

        String text = textView.getText().toString().trim();

        if (text.contains("'")) text = text.replace("'","''");
        return text;
    } // trims, and duplicates single quote on edit texts for query purposes


    String getSVquery(SearchView searchView){

        String text = searchView.getQuery().toString().trim();

        if (text.contains("'")) text = text.replace("'","''");
        return text;
    } // trims, and duplicates single quote on edit texts for query purposes



    String getSpinner_filtered(Spinner spinner){

        return spinner.getSelectedItem().toString().trim().replace("'", "''");
    } // trims and filters spinners

    String getSpinner_unfiltered(Spinner spinner){

        return spinner.getSelectedItem().toString().trim();
    } // trims spinner


    Boolean isContainingDigit(EditText editText){

        String text = editText.getText().toString().trim();

        return text.contains("1") ||
               text.contains("2") ||
               text.contains("3") ||
               text.contains("4") ||
               text.contains("5") ||
               text.contains("6") ||
               text.contains("7") ||
               text.contains("8") ||
               text.contains("9") ||
               text.contains("0");
    } // returns true if edit texts contain digits


    String getButtonText(Button button){
        return button.getText().toString().trim();
    }
}
