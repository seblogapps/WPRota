/**
 * Created by nisham on 14/11/16.
 */


package com.stognacci.worldpay;

public class getData {
}

/*
    //private static String max;
    private static int max=0;
    ArrayList<ArrayList<String>> employeesf = new ArrayList<>();
    public void getData() {
        BufferedReader br = null;

        try {
            String empData;
            br = new BufferedReader(new FileReader("/home/nisham/users/data.csv"));
            while ((empData = br.readLine()) != null) {
                System.out.println("Raw CSV data: " + empData);
                employeesf.add(convertCSVtoArrayList(empData));
                System.out.println("Converted ArrayList data: " + convertCSVtoArrayList(empData) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException readDataException) {
                readDataException.printStackTrace();

            }
        }
    }

    // Utility which converts CSV to ArrayList using Split Operation
    public static ArrayList<String> convertCSVtoArrayList(String csvFile) {
        ArrayList<String> employees = new ArrayList<String>();

        if (csvFile != null) {
            String[] splitData = csvFile.split("\\s*,\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0))
                    employees.add(splitData[i].trim());
                //System.out.println("result is " + result);
                //System.out.println("*******************" + splitData[i]);

            }
            max= Integer.parseInt(splitData[3]);

        }
        System.out.println("******************"+employees);
        return employees;
    }

    public void displayData(){
        for (Employee employee : employeesf) {
            System.out.println("employee = " + employee);
        }
        System.out.println("");
    }


}


*/