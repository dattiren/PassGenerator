package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    public TextArea content;
    private static int length = 12; //������

    public void passGenerate(ActionEvent actionEvent) {
//        long total = Runtime.getRuntime().totalMemory();
//        for(int i = 0; i < 30;i++) {
//            System.out.println(passgenerate());
//        }
//        long free = Runtime.getRuntime().freeMemory();
//        long used = total - free;
//        System.out.println("used memory = "+used /1024 +"KB");
        content.setText(passgenerate());
    }

    private static StringBuilder getDataSet(char start, char end, StringBuilder datalist) {
        datalist.setLength(0);
        for(char i = start; i < end; i++) {
            datalist.append(i);
        }
        return datalist;
    }

    private static void resultSet(int num, StringBuilder sb, StringBuilder result) {
        Random random = new SecureRandom();
        StringBuilder buffer = new StringBuilder();
        while(buffer.length() != num) {
            int m = random.nextInt(sb.length());
            if(buffer.toString().contains(String.valueOf(sb.charAt(m)))) {
                continue;
            }else {
                buffer.append(sb.charAt(m));
            }
        }
        result.append(buffer);
    }

    public static String shuffle(String result){
        List<Character> characters = new ArrayList<Character>();
        for(char c:result.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(result.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    private static String passgenerate(){
        // TODO Auto-generated method stub
        StringBuilder result = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        StringBuilder sign = new StringBuilder();
        String n="";

        //�L��(-,@)
        sign.append((char)0x2d);
        sign.append((char)0x40);

        //�L���̑I��
        Random random = new SecureRandom();
        int snum = random.nextInt(sign.length())+1; //1����L���̑����܂ł̗���
        for(int i = 0;i < snum;i++) result.append(sign.charAt(random.nextInt(snum)));

        //�啶���̑I��
        getDataSet((char)0x41, (char)0x5b, sb);
        int num = 3;
        int num1 = random.nextInt(length-snum-num);
        if(num1 == 0) {
            num1 += 1;
        }
        resultSet(num1, sb, result);

        //�������̑I��
        getDataSet((char)0x61, (char)0x7b, sb);
        int num2 = random.nextInt(length-num1-num-1);
        if(num2 == 0) {
            num2 += 1;
        }
        resultSet(num2, sb, result);

        //�����̑I��
        getDataSet((char)0x30, (char)0x3a, sb);
        int num3 = length-snum-num1-num2;
        resultSet(num3, sb, result);

        //�����񉻂ƕ����̓���ւ�
        String m = result.toString();
        n = shuffle(m);
        return n;
    }
}
