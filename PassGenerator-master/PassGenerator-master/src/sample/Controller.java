package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.Paint;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;



public class Controller implements Initializable{
    @FXML public TextArea idContent;
    @FXML public TextArea passContent;
    @FXML public TextArea idSize;
    @FXML public TextArea passSize;
    @FXML public Label errorMessage;
    
    private boolean idswitch = false;
    private boolean passswitch = false;
    
    

    //private static int length = 12; 
    @FXML
    public void idGenerate(ActionEvent actionEvent) {
    	try {
    		idgenerate(Integer.parseInt(idSize.getText()));
    	}catch(NumberFormatException e) {
    		idContent.setText("");
    		errorMessage.setText("文字数を入力してください。");
    	}
    }
    public void passGenerate(ActionEvent actionEvent) {
    	try {
    		passgenerate(Integer.parseInt(passSize.getText()));
    	}catch(NumberFormatException e) {
    		passContent.setText("");
    		errorMessage.setText("文字数を入力してください。");
    	}
    }
    
    public void idCopy(ActionEvent actionEvent) {
    	if(idContent.getText()!=null) {
    		String str = idContent.getText();
    		textCopy(str);
    	}else {
    		errorMessage.setText("コピー対象が見つかりません");
    	}
    }
    public void passCopy(ActionEvent actionEvent) {
    	if(idContent.getText()!=null) {
    		textCopy(passContent.getText());
    	}else {
    		errorMessage.setText("コピー対象が見つかりません");
    	}
    }
    
    public void textCopy(String str) {
    	Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
    	content.putString(str);
    	clipboard.setContent(content);
    }
    private static StringBuilder getDataSet(char start, char end, StringBuilder datalist) {
        datalist.setLength(0);
        for(char i = start; i < end; i++) {
            datalist.append(i);
        }
       // System.out.println(datalist);
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

    private void passgenerate(int length){
        // TODO Auto-generated method stub
        StringBuilder result = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        StringBuilder sign = new StringBuilder();

        sign.append((char)0x2d);
        sign.append((char)0x40);

        Random random = new SecureRandom();
        int snum = random.nextInt(sign.length())+1; 
        for(int i = 0;i < snum;i++) result.append(sign.charAt(random.nextInt(snum)));

        getDataSet((char)0x41, (char)0x5b, sb);
        int num = 3;
        int num1 = random.nextInt(length-snum-num);
        if(num1 == 0) {
            num1 += 1;
        }
        resultSet(num1, sb, result);

        getDataSet((char)0x61, (char)0x7b, sb);
        int num2 = random.nextInt(length-num1-num-1);
        if(num2 == 0) {
            num2 += 1;
        }
        resultSet(num2, sb, result);

        getDataSet((char)0x30, (char)0x3a, sb);
        int num3 = length-snum-num1-num2;
        resultSet(num3, sb, result);

        String pass = shuffle(result.toString());
        passContent.setText(pass);
    }
   
    private void idgenerate(int length){
        // TODO Auto-generated method stub
        StringBuilder result = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        Random random = new SecureRandom();
        //大文字のデータセットを取得
        getDataSet((char)0x41, (char)0x5b, sb);
        int num = 3;
        //小文字のデータセットを取得
        getDataSet((char)0x61, (char)0x7b, sb);
        int num2 = random.nextInt(length-num-1);
        if(num2 == 0) {
            num2 += 1;
        }
        resultSet(num2, sb, result);
        //0-9の数字のデータセットを取得
        getDataSet((char)0x30, (char)0x3a, sb);
        int num3 = length-num2;
        resultSet(num3, sb, result);

        String id = shuffle(result.toString());
        idContent.setText(id);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		idSize.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			  String str = idSize.getText();
			  if (!str.matches("^-?[1-9]*.?[0-9]+$") || Integer.parseInt(str) >= 50 || Integer.parseInt(str) < 3) {
				  errorMessage.setText("文字数を選択してください。");
			  }else {
				  idswitch = true;
				  errorMessage.setText(""); 
			  }
		});
		passSize.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			  String str = passSize.getText();
			  if (!str.matches("^-?[1-9]*.?[0-9]+$") || Integer.parseInt(str) > 30 || Integer.parseInt(str) < 4) {
				  errorMessage.setText("文字数を選択してください。");
			  }else {
				  passswitch = true;
				  errorMessage.setText(""); 
			  }
		});
	}
   
}