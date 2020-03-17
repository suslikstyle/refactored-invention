# refactored-invention #
The project consists of two modules. Each of which must perform the following tasks:
## csv_updater ##
The source of the Java application that works with the CSV file. In which three methods should be implemented:
1. Reads the data of their CSV file with a list of companies (name, tax number of 12 digits, phone number, address, name and surname of the director) and gives the result of the request in JSON format of a string
2. The method should make it possible to receive data in the request and add it to the CSV file.
3. The last method is to delete the line by company name or its tax number.
### Usage ###
Get executable .jar file.
##### To get a list of JSON string with data from a CSV file, use: #####
    java -jar csv_updater-0.1.jar -get
##### To add a data line to a CSV file, use: #####
    java -jar csv_updater-0.1.jar -add  
##### To delete a data line from a CSV file use: #####
    java -jar csv_updater-0.1.jar -delete <name_company>
or
    java -jar csv_updater-0.1.jar -delete <tax_number>
## service ##
The service should use сsv_updater to keep the MySQL database up to date.
### Usage ###
    java -jar service.jar -start
in a running application, the 'help' or 'h' command displays help.
###### I'm sorry for my English. ######