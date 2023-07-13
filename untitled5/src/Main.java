import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Character, Integer> RIMSK = new HashMap<>();

    static {
        RIMSK.put('I', 1);
        RIMSK.put('V', 5);
        RIMSK.put('X', 10);
        RIMSK.put('L', 50);
        RIMSK.put('C', 100);
        RIMSK.put('D', 500);
        RIMSK.put('M', 1000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();
        String result = calc(input);
        System.out.println("Результат: " + result);
    }

    public static String calc(String input) {
        try {
            String[] tokens = input.split(" ");
            String num1 = tokens[0];
            String operator = tokens[1];
            String num2 = tokens[2];

            boolean isRomanNumeral = isRomanNumeral(num1) && isRomanNumeral(num2);

            int decimalNum1 = isRomanNumeral ? toDecimalNumeral(num1) : Integer.parseInt(num1);
            int decimalNum2 = isRomanNumeral ? toDecimalNumeral(num2) : Integer.parseInt(num2);

            if (decimalNum1 < 1 || decimalNum1 > 10 || decimalNum2 < 1 || decimalNum2 > 10) {
                throw new IllegalArgumentException("Числа должны быть от 1 до 10");
            }

            int result = switch (operator) {
                case "+" -> decimalNum1 + decimalNum2;
                case "-" -> decimalNum1 - decimalNum2;
                case "*" -> decimalNum1 * decimalNum2;
                case "/" -> decimalNum1 / decimalNum2;
                default -> throw new IllegalArgumentException("Неподдерживаемая операция");
            };

            return isRomanNumeral ? toRomanNumeral(result) : String.valueOf(result);
        } catch (Exception e) {
            return "Что-то пошло не так: " + e.getMessage();
        }
    }

    private static boolean isRomanNumeral(String input) {
        return input.matches("[IVXLCDM]+");
    }

    private static int toDecimalNumeral(String romanNumeral) {
        int result = 0;
        int prevValue = 0;
        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            int value = RIMSK.get(romanNumeral.charAt(i));
            result += value * (value < prevValue ? -1 : 1);
            prevValue = value;
        }
        return result;
    }

    private static String toRomanNumeral(int decimalNumeral) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : RIMSK.entrySet()) {
            char symbol = entry.getKey();
            int value = entry.getValue();
            while (decimalNumeral >= value) {
                sb.append(symbol);
                decimalNumeral -= value;
            }
        }
        return sb.toString();
    }
}