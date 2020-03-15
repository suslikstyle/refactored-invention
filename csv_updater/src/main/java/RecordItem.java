/**
 * Class structure of one record
 */
public class RecordItem {

    private String company_name, tax_number, phone_number, address, director_name, director_surname;

    /**
     * @param company_name Наименование компании
     * @param tax_number Налоговый номер
     * @param phone_number Номер телефона
     * @param address Адрес
     * @param director_name Имя директора
     * @param director_surname Фамилия директора
     */
    public RecordItem(String company_name, String tax_number, String phone_number,
                      String address, String director_name, String director_surname){
        // constructor
        this.company_name = company_name;
        this.tax_number = tax_number;
        this.phone_number = phone_number;
        this.address = address;
        this.director_name = director_name;
        this.director_surname = director_surname;
    }

    /**
     * @param line массив строк в определенной последовательности для создания объекта записи       !!
     */
    public RecordItem(String[] line){
        // constructor
        try{
            this.company_name = line[0];
            this.tax_number = line[1];
            this.phone_number = line[2];
            this.address = line[3];
            this.director_name = line[4];
            this.director_surname = line[5];
        } catch (ArrayIndexOutOfBoundsException e){
            Loger.log("Less parameters than expected.", Loger.log_type.Info);
        } catch (Exception e){
            Loger.log("Unknown error.", Loger.log_type.Error);
        }
    }

    /**
     * @return Getting data from the current object
     */
    public String[] getLine() {
        return new String[] {company_name, tax_number, phone_number, address, director_name, director_surname};
    }

    public boolean checkRecord(){
        return (this.company_name != null & this.tax_number != null);
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getTax_number() {
        return tax_number;
    }
}
