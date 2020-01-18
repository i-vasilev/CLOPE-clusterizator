package ru.vasilev;

import ru.vasilev.IO.FileReader;
import ru.vasilev.entity.Clope;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length > 1) {
                FileReader fileReader = new FileReader(args[0]);
                final double repulsion = Double.parseDouble(args[1]);
                Clope clope = new Clope(fileReader, repulsion);
                System.out.println(String.format("Count of clusters: %d", clope.getCountClusters()));
                System.out.println(clope);
            } else {
                System.out.println("Mandatory arguments are missing. Usage: program_name data_file repulsion");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
