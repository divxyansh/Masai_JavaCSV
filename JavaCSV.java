import java.util.*;

public class JavaCSV {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter the number of columns:");
        int cols = sc.nextInt();
        sc.nextLine(); // Consume the remaining newline

        String[][] csvData = new String[rows][cols];

        // Reading CSV input from the user
        System.out.println("Enter the CSV data row by row (use '=' for formulas):");
        for (int i = 0; i < rows; i++) {
            csvData[i] = sc.nextLine().split(",");
        }

        // Processing the CSV data
        String[][] processedData = processCSVData(csvData);

        // Displaying the output
        System.out.println("Processed CSV output:");
        for (String[] row : processedData) {
            System.out.println(String.join(",", row));
        }

        sc.close();
    }

    public static String[][] processCSVData(String[][] csvData) {
        int rows = csvData.length;
        int cols = csvData[0].length;
        String[][] result = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String cell = csvData[i][j];
                if (cell.startsWith("=")) {
                    result[i][j] = evaluateFormula(cell, result, csvData, i, j);
                } else {
                    result[i][j] = cell;
                }
            }
        }
        return result;
    }

    public static String evaluateFormula(String formula, String[][] result, String[][] originalData, int row, int col) {
        formula = formula.substring(1); // Remove the "="
        String[] tokens = formula.split("\\+");
        int sum = 0;

        for (String token : tokens) {
            if (Character.isLetter(token.charAt(0))) {
                int r = token.charAt(1) - '1'; // Row index
                int c = token.charAt(0) - 'A'; // Column index
                if (result[r][c] != null) {
                    sum += Integer.parseInt(result[r][c]);
                } else {
                    sum += Integer.parseInt(originalData[r][c]);
                }
            } else {
                sum += Integer.parseInt(token);
            }
        }

        return String.valueOf(sum);
    }
}