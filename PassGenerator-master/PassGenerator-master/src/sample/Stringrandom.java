package sample;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stringrandom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 100;i++) passgenerate(100);

	}
	//使用する文字列の格納
	private static StringBuilder getDataSet(char start, char end, StringBuilder datalist) {
        for(char i = start; i < end; i++) {
            datalist.append(i);
        }
        return datalist;
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
	
	private static void passgenerate(int length){
        // TODO Auto-generated method stub
		StringBuilder result = new StringBuilder(length);
		StringBuilder num = new StringBuilder(10);
		StringBuilder small = new StringBuilder(26);
		StringBuilder big = new StringBuilder(26);
        StringBuilder sign = new StringBuilder(2);
        
        Random random = new SecureRandom();
        //記号(-と@)
        sign.append((char)0x2d);
        sign.append((char)0x40);
        //英字(大文字)を用意
        getDataSet((char)0x41, (char)0x5b, big);
        //英字(小文字)を用意
        getDataSet((char)0x61, (char)0x7b, small);
        //数字を用意
        getDataSet((char)0x30, (char)0x3a, num);
        
        while(result.length() < length) {
        	if(result.length() < length){
        		for(int i=0; i < random.nextInt(sign.length()) + 1;i++) {
            		result.append(sign.charAt(random.nextInt(sign.length())));
            	}
        	}
        	if(result.length() < length){
            	int smallnum = random.nextInt(length - result.length()) + 1;
	        	for(int i=0; i < smallnum;i++) {
	        		result.append(small.charAt(random.nextInt(small.length())));
	        	}
        	}else {
        		continue;
        	}
        	if(result.length() < length){
	        	int bignum = random.nextInt(length - result.length()) + 1;
	        	for(int i=0; i < bignum;i++) {
	        		result.append(big.charAt(random.nextInt(big.length())));
	        	}
        	}
        }
         System.out.println(shuffle(result.toString()));
    }
}
