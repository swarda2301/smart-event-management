
package eventmanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImportService {

    public void importPeople(String filePath, InvitationService service) {

        int imported = 0;
        int duplicates = 0;
        int invalid = 0;

        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Excel file not found.");
            return;
        }

        if (!file.getName().toLowerCase().endsWith(".xlsx")) {
            System.out.println("Please select an Excel .xlsx file.");
            return;
        }

        try (
            FileInputStream input = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(input)
        ) {

            if (workbook.getNumberOfSheets() == 0) {
                System.out.println("The workbook contains no sheets.");
                return;
            }

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            if (sheet.getRow(0) == null) {
                System.out.println("The worksheet is empty.");
                return;
            }

            System.out.println(
                "\nReading worksheet: " + sheet.getSheetName()
            );

            // Row 0 contains column headings.
            // Data starts from row 1.
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String personId = formatter
                    .formatCellValue(row.getCell(0)).trim();

                String fullName = formatter
                    .formatCellValue(row.getCell(1)).trim();

                String email = formatter
                    .formatCellValue(row.getCell(2)).trim();

                String role = formatter
                    .formatCellValue(row.getCell(3)).trim();

                // Skip completely empty rows.
                if (personId.isEmpty()
                        && fullName.isEmpty()
                        && email.isEmpty()
                        && role.isEmpty()) {
                    continue;
                }

                // Validate required fields and email.
                if (personId.isEmpty()
                        || fullName.isEmpty()
                        || email.isEmpty()
                        || role.isEmpty()
                        || !email.matches(
                            "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"
                        )) {

                    invalid++;
                    System.out.println(
                        "Row " + (i + 1)
                        + " skipped: missing or invalid data."
                    );
                    continue;
                }

                Person person;

                if (role.equalsIgnoreCase("Student")
                        || role.equalsIgnoreCase("Participant")) {

                    person = new Participant(
                        personId, fullName, email
                    );

                } else if (role.equalsIgnoreCase("Teacher")) {

                    person = new Teacher(
                        personId, fullName, email
                    );

                } else {
                    invalid++;

                    System.out.println(
                        "Row " + (i + 1)
                        + " skipped: unsupported role '"
                        + role + "'."
                    );
                    continue;
                }

                // addRecipient returns true when added
                // and false when the ID already exists.
                boolean added = addRecipientSafely(service, person);

                if (added) {
                    imported++;
                } else {
                    duplicates++;
                    System.out.println(
                        "Row " + (i + 1)
                        + " skipped: duplicate person ID "
                        + personId
                    );
                }
            }

            System.out.println("\n===== EXCEL IMPORT REPORT =====");
            System.out.println("Imported successfully: " + imported);
            System.out.println("Duplicate IDs skipped: " + duplicates);
            System.out.println("Invalid rows skipped: " + invalid);
            System.out.println("================================");

        } catch (IOException e) {

            System.out.println(
                "Unable to read the Excel file: " + e.getMessage()
            );

        } catch (RuntimeException e) {

            System.out.println(
                "Could not process the workbook. "
                + "Check that it is a valid, unencrypted .xlsx file."
            );
        }
    }

    // Works whether addRecipient currently returns void
    // or boolean, provided the method accepts a Person.
    private boolean addRecipientSafely(
            InvitationService service, Person person) {

        for (Person existing : service.getRecipients()) {
            if (existing.getPersonId().equalsIgnoreCase(
                    person.getPersonId())) {
                return false;
            }
        }

        service.addRecipient(person);
        return true;
    }
}
