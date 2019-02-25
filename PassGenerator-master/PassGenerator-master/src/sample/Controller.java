package sample;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.net.URL;
import java.security.SecureRandom;
import java.util.*;

public class Controller implements Initializable{
    @FXML public TextArea idContent;
    @FXML public TextArea passContent;
    @FXML public TextArea idSize;
    @FXML public TextArea passSize;
    @FXML public Label errorMessage;
    @FXML public Button idButton;
	@FXML public Button passButton;


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
		StringBuilder result = new StringBuilder(length);
		StringBuilder num = new StringBuilder(10);
		StringBuilder small = new StringBuilder(26);
		StringBuilder big = new StringBuilder(26);
		StringBuilder sign = new StringBuilder(2);

		Random random = new SecureRandom();
		//ascii 16進数
		//記号('_', '.', '@', '-')
		sign.append((char)0x2d);
		sign.append((char)0x40);
		sign.append((char)0x5F);
		sign.append((char)0x2E);
		//英字(大文字)を用意
		getDataSet((char)0x41, (char)0x5b, big);
		//英字(小文字)を用意
		getDataSet((char)0x61, (char)0x7b, small);
		//数字を用意
		getDataSet((char)0x30, (char)0x3a, num);

		int smallCount= random.nextInt(length - 3) + 1;
		int bigCount = random.nextInt((length - 2) - smallCount) + 1;
		int numCount = random.nextInt((length - 1) - bigCount - smallCount) + 1;
		int signCount = length - smallCount - bigCount - numCount;

		for (int i = 0; i < smallCount; i++) {
			result.append(small.charAt(random.nextInt(small.length())));
		}

		for (int i = 0; i < bigCount; i++) {
			result.append(big.charAt(random.nextInt(big.length())));
		}

		for (int i = 0; i < numCount; i++) {
			result.append(num.charAt(random.nextInt(num.length())));
		}

		for (int i = 0; i < signCount; i++) {
			result.append(sign.charAt(random.nextInt(sign.length())));
		}

		String pass = shuffle(result.toString());
		passContent.setText(pass);
	}

	private void idgenerate(int length){
		// TODO Auto-generated method stub
		StringBuilder result = new StringBuilder(length);
		StringBuilder num = new StringBuilder(10);
		StringBuilder small = new StringBuilder(26);
		StringBuilder big = new StringBuilder(26);

		Random random = new SecureRandom();
		//英字(大文字)を用意
		getDataSet((char)0x41, (char)0x5b, big);
		//英字(小文字)を用意
		getDataSet((char)0x61, (char)0x7b, small);
		//数字を用意
		getDataSet((char)0x30, (char)0x3a, num);

		int smallCount= random.nextInt(length - 2) + 1;
		int bigCount = random.nextInt((length - 1) - smallCount) + 1;
		int numCount = length - smallCount - bigCount;

		for (int i = 0; i < smallCount; i++) {
			result.append(small.charAt(random.nextInt(small.length())));
		}

		for (int i = 0; i < bigCount; i++) {
			result.append(big.charAt(random.nextInt(big.length())));
		}

		for (int i = 0; i < numCount; i++) {
			result.append(num.charAt(random.nextInt(num.length())));
		}

		String id = shuffle(result.toString());
		idContent.setText(id);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		idSize.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			  String str = idSize.getText();
			  if (!str.matches("^-?[1-9]*.?[0-9]+$") || Integer.parseInt(str) >= 50 || Integer.parseInt(str) < 3) {
				  errorMessage.setText("3文字以上50文字以下で文字数を入力してください");
				  idButton.setDisable(true);
			  }else {
				  idswitch = true;
				  idButton.setDisable(false);
				  errorMessage.setText("");
			  }
		});
		passSize.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			  String str = passSize.getText();
			  if (!str.matches("^-?[1-9]*.?[0-9]+$") || Integer.parseInt(str) > 30 || Integer.parseInt(str) < 4) {
				  errorMessage.setText("4文字以上30文字以下で文字数を入力してください");
				  passButton.setDisable(true);
			  }else {
				  passswitch = true;
				  passButton.setDisable(false);
				  errorMessage.setText("");
			  }
		});
	}

}
