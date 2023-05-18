package com.qnecesitas.elreteninventario.auxiliary;

import java.util.Random;

public class IDCreater {


    public static String generate(){

        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int randomNumber = random.nextInt(max - min +1)+min;

        return String.valueOf(randomNumber);
    }

}
