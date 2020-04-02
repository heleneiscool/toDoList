package com.example.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {
    public static final String FILENAME = "listinfo.dat";
    public static final String FILENAME_SELECTED = "selectedlistinfo.dat";

    public static void writeData(ArrayList<String> items, Context context){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> readData(Context context, int file){
        ArrayList<String> itemsList = null;
        try {
            FileInputStream fis;
            if(file==0) {
                fis = context.openFileInput(FILENAME);
            }
            else{
                fis = context.openFileInput(FILENAME_SELECTED);
            }
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemsList = (ArrayList<String>) ois.readObject();

        }
        catch (FileNotFoundException e) {
            itemsList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return itemsList;
    }

}
