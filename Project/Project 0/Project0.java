import java.util.ArrayList;
import java.util.Scanner;

public class Project0 {
    static ArrayList<Token> tokens = new ArrayList<Token>();
    static String input = "";
    
    public ArrayList<Token> get_tokens(String input) {
        /*TODO: Split the input into the seperate tokens */
        Token currentToken;
        int j = 0;
        int decimalCount = 0;
        String temp = "";
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= '0' && input. charAt(i) <= '9') {
                j = 0;
                while (input.charAt(i + j) >= '0' && input.charAt(i + j) <= '9'
                           || input.charAt(i + j) == '.' 
                           || (i + j + 1) == temp.indexOf('.')) {
                    temp += input.charAt(i + j);
                    j++;
                    if ((i + j) >= input.length())
                        break;
                }
                i += j - 1;
                for (int k = 0; k < temp.length(); k++) {
                    if (temp.charAt(k) == '.')
                        decimalCount++;
                }
                if (!temp.contains(".")) {
                    int tempInt = Integer.parseInt(temp);
                    currentToken = new Token(11, tempInt);
                    tokens.add(currentToken);
                }
                else if (!temp.substring(temp.length() - 1, 
                                         temp.length()).equals(".") 
                             && decimalCount == 1) { 
                    float tempFloat = Float.parseFloat(temp);
                    currentToken = new Token(12, tempFloat);
                    tokens.add(currentToken);
                    decimalCount = 0;
                }
                else if (temp.substring(temp.length() - 1, 
                                        temp.length()).equals(".") 
                             && decimalCount == 1) {  
                    int tempInt = 
                        Integer.parseInt(temp.substring(0, temp.length() - 1));
                    currentToken = new Token(11, tempInt);
                    tokens.add(currentToken);
                    currentToken = new Token(27, 0);
                    tokens.add(currentToken);
                }
                else if (decimalCount > 1) {
                    int decimal = 0;
                    String tempDecimal = "";
                    String afterDecimal = "";
                    for (int m = 0; m < temp.length(); m++) {
                        if (decimal < 2) {
                            while (decimal < 2) {
                                tempDecimal += temp.charAt(m);
                                if (temp.charAt(m) == '.') {
                                    decimal++;
                                }
                                break;
                            }
                        }
                        else 
                            afterDecimal += temp.charAt(m);
                    }
                    if (tempDecimal.substring(tempDecimal.
                                                  length() - 2).equals("..")) {
                        int tempInt = 
                            Integer.parseInt(
                                             tempDecimal.substring(0, 
                                                                   tempDecimal.indexOf('.')));
                        currentToken = new Token(11, tempInt);
                        tokens.add(currentToken);
                        currentToken = new Token(27, 0);
                        tokens.add(currentToken);
                        currentToken = new Token(27, 0);
                        tokens.add(currentToken);
                    }
                    else {
                        float tempFloat = 
                            Float.parseFloat(tempDecimal.
                                                 substring(0,
                                                           tempDecimal.length() - 1));
                        currentToken = new Token(12, tempFloat);
                        tokens.add(currentToken);
                        currentToken = new Token(27, 0);
                        tokens.add(currentToken);
                    }
                    get_tokens(afterDecimal);
                }
                temp = "";
            }
            else {
                if (input.charAt(i) == '?') {
                    currentToken = new Token(20, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == '(') {
                    currentToken = new Token(21, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == ')') {
                    currentToken = new Token(22, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == '+') {
                    currentToken = new Token(23, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == '-') {
                    currentToken = new Token(24, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == '*') {
                    currentToken = new Token(25, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == '/') {
                    currentToken = new Token(26, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == '.') {
                    currentToken = new Token(27, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == ';') {
                    currentToken = new Token(28, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) == '=') {
                    currentToken = new Token(29, 0); 
                    tokens.add(currentToken);
                }
                else if (input.charAt(i) >= 48 && input. charAt(i) <= 57) {
                    currentToken = new Token(11, Integer.parseInt(input)); 
                    tokens.add(currentToken);
                }
            }
        }
        return tokens;
    }
    
    public String read_input(Scanner in) {
        /*TODO: Read input until a '?' is found */
        String a = "";
        while (in.hasNext()) {
            a = in.next();
            if (a.contains("?")) {
                input += a.substring(0, a.indexOf("?") + 1);
                break;
            }
            else
                input += a;
            input += " ";
        }
        return input;
    }
    
    public void print_tokens(ArrayList<Token> tokens) {
        /*TODO: Print all the tokens before and including the '?' token
         *      Print tokens from list in the following way, 
         *      "(token,tokenValue)"
         * */
        for (Token p : tokens) {
            if (p.getType() == 'i')
                System.out.print("(" + p.getToken() + "," 
                                     + p.getValue_i() + ")");
            else if (p.getType() == 'f')
                System.out.print("(" + p.getToken() + "," 
                                     + p.getValue_f() + ")");
            else if (p.getType() == 's')
                System.out.print("(" + p.getToken() + "," 
                                     + p.getValue_s() + ")");
        }
    }
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Project0 p0 = new Project0();
        input = p0.read_input(in);
        tokens = p0.get_tokens(input);
        p0.print_tokens(tokens);
        
    }
}
