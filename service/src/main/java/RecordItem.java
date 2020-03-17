import java.beans.PropertyEditorSupport;

public class RecordItem {

    private String company_name = "";
    private String tax_number = "";
    private String phone_number = "";
    private String address = "";
    private String director_name = "";
    private String director_surname = "";

    public RecordItem(String company_name, String tax_number, String phone_number,
                      String address, String director_name, String director_surname){
        this.company_name = company_name;
        this.tax_number = tax_number;
        this.phone_number = phone_number;
        this.address = address;
        this.director_name = director_name;
        this.director_surname = director_surname;
    }

    public boolean compare(RecordItem r_item){
        return this.company_name.equals(r_item.company_name) && this.tax_number.equals(r_item.tax_number);
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getTax_number() {
        return tax_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    public String getDirector_name() {
        return director_name;
    }

    public String getDirector_surname() {
        return director_surname;
    }
}
