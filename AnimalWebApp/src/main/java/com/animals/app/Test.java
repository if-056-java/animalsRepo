package com.animals.app;

import java.util.Random;

/**
 * Created by oleg on 09.09.2015.
 */
public class Test {

    public static void main(String[] args) {
        int n = 15; //оголошуємо і ініцілізуємо змінну з розміром масиву
        int Array[] = new int[n]; //оголошуємо і ініціалізуємо масив
        Random generator = new Random(); // створюємо генератор випадковиx чисел
        int gn; //змінна в яку буде записуватися згенероване генератором число

        int start[] = new int[n];   //step 1
        int sorted[] = new int[n];  //step 2
        int step3[] = new int[n];   //step 3
        int step4[] = new int[n];   //step 4
        int step5[] = new int[n];   //step 5

        /***заповнюємо масив випадковими числами***/
        for (int i = 0; i < n; i++) { //проходимось по рядках
            gn = generator.nextInt(5); //генерація випадкового числа від 0 до 5;
            gn++;
            Array[i] = gn; //записуємо згенероване випадкове число
        }

        /***Виводимо результат***/
        for (int i = 0; i < n; i++) {
            System.out.print(Array[i] + " ");
            start[i] = Array[i];
        }

        /***сортуємо методом бульбашки***/
        System.out.print("\n");
        for (int i = Array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (Array[j] > Array[j + 1]) {
                    int tmp = Array[j];
                    Array[j] = Array[j + 1];
                    Array[j + 1] = tmp;
                }
            }
        }

        for (int i = 0; i < Array.length; i++) {
            System.out.print(Array[i] + " ");
            sorted[i] = Array[i];
        }
        System.out.println();
        int k = 1;
        int l = 1;
        for (int i = 0; i < Array.length; i++) {
            if (Array[i] == k) {
                step3[i] = l;
                System.out.print(l + " ");
                k++;
                l++;
            } else {System.out.print(" "); step3[i] = 0; }
        }

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        for (int i = 0; i < Array.length; i++) {
            if (Array[i] == 1) {
                a++;
            } else if (Array[i] == 2) {
                b++;
            } else if (Array[i] == 3) {
                c++;
            } else if (Array[i] == 4) {
                d++;
            } else if (Array[i] == 5) {
                e++;
            }
        }
        System.out.println();
        System.out.print(a + " ");
        System.out.print(b + " ");
        System.out.print(c + " ");
        System.out.print(d + " ");
        System.out.print(e + " ");
        step4[0] = a;
        step4[1] = b;
        step4[2] = c;
        step4[3] = d;
        step4[4] = e;
        System.out.println();
        int h = 0;
        for (int i = 0; i < 1; i++) {
            h = h + a;
            step5[0] = h;
            System.out.print(h + " ");
            h = h + b;
            step5[1] = h;
            System.out.print(h + " ");
            h = h + c;
            step5[2] = h;
            System.out.print(h + " ");
            h = h + d;
            step5[3] = h;
            System.out.print(h + " ");
            h = h + e;
            step5[4] = h;
            System.out.print(h + " ");
        }

        System.out.println();
        System.out.println();
        System.out.println("*********************************");

        System.out.println("*1* *2* *3* *4* *5*");
        for(int i = 0; i < n; i++){
            System.out.println(start[i] + "   " + sorted[i] + "   " + (step3[i] == 0 ? " " : step3[i]) + "   " + (step4[i] == 0 ? " " : step4[i]) + "   " + (step5[i] == 0 ? " " : step5[i]));
        }

        System.out.println("*********************************");
    }

}
