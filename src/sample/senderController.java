package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Random;

public class senderController {
    public TextField senderMessage;
    public Button hammingButton;
    public Label hammingCodeLabel;
    public Button copyWithoutButton;
    public Button copyWithErrorButton;


    public void hammingButtonAction() {

        String myString = senderMessage.getText();
        int n = myString.length();

        if (n == 0) {
            hammingCodeLabel.setText("Empty Message!");
            return;
        }
        for (char c : myString.toCharArray()) {

            if (c != '0' && c != '1') {
                hammingCodeLabel.setText("Please Enter Only Binary Numbers");
                return;
            }
        }

        //Convert String To Integer Array
        int[] inputData = new int[n];
        for (int i = 0; i < n; i++)
            inputData[i] = Integer.parseInt(myString.charAt(i) + "");

        //Calculate Number Of Parity Bits
        int parityCount = 0;
        for (int i = 0; n + i + 1 > (int) Math.pow(2, i); i++)
            parityCount++;

        int messageLength = parityCount + n;
        int[] resultMessage = new int[32];
        int j = 0, k = 0;

        // Set All Parity Bits To Zero
        for (int i = 0; i < messageLength; i++) {

            if (i == ((int) Math.pow(2, k) - 1)) {
                resultMessage[i] = 0;
                k++;
            } else {
                resultMessage[i] = inputData[j];
                j++;
            }
        }
        // Calculate Value For All Parity
        for (int i = 0; i < parityCount; i++) {
            int position = (int) Math.pow(2, i);
            resultMessage[position - 1] = parityValue(resultMessage, position);
        }

        // Print Result On Label
        StringBuilder outputString = new StringBuilder();
        for (int i = 0; i < messageLength; i++)
            outputString.append(resultMessage[i]);

        hammingCodeLabel.setText(outputString.toString());
        copy();

    }

    private int parityValue(int[] resultMessage, int position) {
        int count = 0;
        int n = resultMessage.length;

        for (int i = position - 1; i < n; i += 2 * position)
            for (int j = i; j < i + position; j++)
                if (resultMessage[j] == 1)
                    count++;

        return count % 2; // XOR Concept => Return 1 If Number Of One's Is Odd, Else Return 0
    }

    private void copy() {

        copyWithoutButton.setVisible(true);
        copyWithErrorButton.setVisible(true);
        copyWithoutButton.setOnAction(actionEvent -> {
            StringSelection stringClip = new StringSelection( hammingCodeLabel.getText());
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            clip.setContents(stringClip, null);
        });

        copyWithErrorButton.setOnAction(actionEvent -> {
            Random random = new Random();
            char[] myArr = hammingCodeLabel.getText().toCharArray();
            int x = random.nextInt(myArr.length);
            System.out.println(x + 1);
            if (myArr[x] == '0')
                myArr[x] = '1';
            else
                myArr[x] = '0';

            StringBuilder myString = new StringBuilder();
            for (char c : myArr) myString.append(c);

            StringSelection stringClip = new StringSelection(myString.toString());
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            clip.setContents(stringClip, null);
        });

    }

}