package com.example.task02;

import java.io.File;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.io.*;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;

    public SavedList(File file) {
        this.file = file;
    }

    @Override
    public E get(int index) {
        if(!file.exists()) return null;

        ArrayList<E> list = loadFromFile();
        if(list == null) return null;

        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        if(!file.exists()) return null;

        ArrayList<E> list = loadFromFile();
        if(list == null) return null;

        E el = list.set(index, element);

        saveToFile(list);
        return el;
    }

    @Override
    public int size() {
        if(!file.exists()) return 0;

        ArrayList<E> list = loadFromFile();
        if(list == null) return 0;

        return list.size();
    }

    @Override
    public void add(int index, E element) {
        ArrayList<E> list = loadFromFile();
        if(list == null || !file.exists()) list = new ArrayList<>();

        list.add(index, element);
        saveToFile(list);
    }

    @Override
    public E remove(int index) {
        if(!file.exists()) return null;

        ArrayList<E> list = loadFromFile();
        if(list == null) return null;

        E el = list.remove(index);
        saveToFile(list);

        return el;
    }

    private void saveToFile(ArrayList<E> list){
        try {
            file.createNewFile();

            try (OutputStream outputStream = new FileOutputStream(file)) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                    objectOutputStream.writeObject(list);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<E> loadFromFile(){
        try(InputStream inputStream = new FileInputStream(file)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                return (ArrayList<E>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            return null;
        }
    }
}
