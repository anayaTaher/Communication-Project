package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class receiverController {
    public Button detectButton;
    public Label detectErrors;
    public Label correctErrors;
    public TextField receiverMessage;

    public void detectButtonAction() {
        String myString = receiverMessage.getText();
        int n = myString.length();

        if (n == 0) {
            detectErrors.setText("Empty Message!");
            return;
        }

        if (n < 3) {
            detectErrors.setText("The Received Message Should Be Contain At Least 3 Bits");
            return;
        }
        if (n == 4 || n == 8 || n == 16) {
            detectErrors.setText("The Received Message Cannot Consist Of " + n + " Bits");
            return;
        }
        for (char c : myString.toCharArray()) {
            if (c != '0' && c != '1') {
                detectErrors.setText("Please Enter Only Binary Numbers");
                return;
            }
        }

        int parityCount = 0;
        for (int i = 0; n + i + 1 > (int) Math.pow(2, i); i++)
            parityCount++;

        int[] message = new int[32];
        for (int i = 0; i < n; i++)
            message[i] = Integer.parseInt(myString.charAt(i) + "");

        int errorPosition = 0;
        for (int i = 0; i < parityCount; i++) {
            int position = (int) Math.pow(2, i);
            if (parityValue(message, position) != 0)
                errorPosition += position;
        }
        if (errorPosition == 0) {
            StringBuilder myStr = new StringBuilder("The Received Message Is Correct\nThe Actual Message Is: ");

            for (int i = 0; i < n; i++)
                if (i != 0 && i != 1 && i != 3 && i != 7 && i != 15 && i != 31)
                    myStr.append(message[i]);

            myStr.append("\nError Percentage: 0%");

            detectErrors.setText(myStr.toString());


        } else {
            StringBuilder myStr = new StringBuilder("Error At Bit Position: " + errorPosition + "\nThe Actual Message After Correct: ");
            if (message[errorPosition - 1] == 0)
                message[errorPosition - 1] = 1;
            else
                message[errorPosition - 1] = 0;

            for (int i = 0; i < n; i++)
                if (i != 0 && i != 1 && i != 3 && i != 7 && i != 15 && i != 31)
                    myStr.append(message[i]);

            myStr.append("\nError Percentage: ").append((100 / n)).append("%");
            detectErrors.setText(myStr.toString());
        }
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

}
