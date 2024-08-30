
import java.text.DecimalFormat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HeLi
 */
public class Test {
    public void main(String[] args) {
        DecimalFormat scoreFomat = new DecimalFormat("#,###");
        int score = 1000000000;
        System.out.println(scoreFomat.format(score));
    }
}
