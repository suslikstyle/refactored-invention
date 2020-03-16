import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    enum Headers {
        company, tax_num, phone, address, first_name, surname
    }

    private String csvPath = null;
    private List<String[]> data;

    public CSV(String filePath) {
        // constructor
        if (filePath != null && !filePath.equals("")) {
            this.csvPath = filePath;
            FileReader fileReader;
            CSVReader csvFileReader;
            try {
                fileReader = new FileReader(this.csvPath);
                csvFileReader = new CSVReader(fileReader);
                data = csvFileReader.readAll();

            } catch (FileNotFoundException e) {
                Loger.log("Error. CSV file not found.", Loger.log_type.Error);
                System.exit(1);
            } catch (IllegalArgumentException e){
                Loger.log("Not enough company data. skipping");
            } catch (IOException e) {
                Loger.log("Error reading CSV file.", Loger.log_type.Error);
                System.exit(2);
            }
        }
    }

    public List<RecordItem> getRecords(){
        List<RecordItem> all_rec = new ArrayList<RecordItem>();
        for (String[] line : data){
            try {
                if (line[0] != null && line[1] != null) {
                    all_rec.add(new RecordItem(line));
                }
            } catch (ArrayIndexOutOfBoundsException e){
                Loger.log("Missing data, skip.");
            }
        }
        return all_rec;
    }

    public boolean addRecord(RecordItem record){
        String company = record.getCompany_name();
        String tax = record.getTax_number();
        if(!record.checkRecord()){return false;}
        if (checkExistence(company) >= 0 || checkExistence(tax) >= 0){
            Loger.log("Record already exists.", Loger.log_type.Error);
            return false;
        }
        this.data.add(record.getLine());
        if (writeDataToCsv()){
            Loger.log( String.format("The company \"%s\" was added to the CSV file.", company));
            return true;
        } else {Loger.log( "Failed to write file.");}
        return false;
    }

    public boolean addRecord(RecordItem record, boolean allowDuplicate){
        String company = record.getCompany_name();
        String tax = record.getTax_number();
        if(!record.checkRecord()){return false;}
        if (!allowDuplicate && (checkExistence(company) >= 0 || checkExistence(tax) >= 0)){
            Loger.log("Record already exists.", Loger.log_type.Error);
            return false;
        }
        this.data.add(record.getLine());
        if (writeDataToCsv()){
            Loger.log( String.format("The company \"%s\" was added to the CSV file.", company));
            return true;
        } else {Loger.log( "Failed to write file.");}
        return false;
    }


    public boolean deleteRecord(String value){
        boolean result = false;
        int index = checkExistence(value);
        if (index >= 0) {
            this.data.remove(index);
            if (writeDataToCsv()){
                Loger.log( String.format("Record with a company name or tax number of \"%s\" has been deleted.", value));
                result = true;
            } else {Loger.log( "Failed to write file.");}
        } else { Loger.log("Record not found."); }
        return result;
    }


    public boolean deleteRecord(int index){
        boolean result = false;
        if (index >= 0) {
            this.data.remove(index);
            if (writeDataToCsv()){
                result = true;
            } else {Loger.log( "Failed to write file.");}
        }
        return result;
    }

    /**
     * @return Get a list of data from a CSV file. Each list item represents
     * string array whose size corresponds to the number of columns in the CSV file.
     */
    public List<String[]> getData() {
        return data;
    }

    /**
     * @param value The value to find in the CSV file.
     * @return При нахождении записи, в которой имя компании ИЛИ налоговый номер совпадет со значением
     * value вернет целое число, соответсвующее номеру строки (начиная с 0) в CSV файле
     */
    private int checkExistence(String value){
        for(int i = 1; i < this.data.size(); ++i){
            String[] line = this.data.get(i);
            try {
                if (value.equals(line[0]) || value.equals(line[1])) {return i;}
            } catch (ArrayIndexOutOfBoundsException e){
                Loger.log("Missing data, delete and skip.", Loger.log_type.Info);
                deleteRecord(i);
            }
        }
        return -1;
    }

    /**
     * @return Status. If data was written to a file, returns true.
     */
    private boolean writeDataToCsv() {
        boolean result = false;
        FileWriter sw;
        CSVWriter writer = null;
        try {
            sw = new FileWriter(this.csvPath);
            writer = new CSVWriter(sw);
            writer.writeAll(this.data);
            result = true;
        } catch (IOException e) {
            Loger.log("IO error in write csv method. "+e.getMessage(), Loger.log_type.Error);
        }finally {
            try {
                if(writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                Loger.log("IO error in close csv. "+e.getMessage(), Loger.log_type.Error);
            }
        }
        return result;
    }
}
