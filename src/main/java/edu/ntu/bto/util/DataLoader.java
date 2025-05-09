package edu.ntu.bto.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.HDBManager;
import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;

/**
 * Utility class to load Excel data files using Apache POI.
 * 
 * For user files: Columns: 0: Name (ignored), 1: NRIC, 2: Age, 3: Marital
 * Status, 4: Password (ignored)
 * 
 * For project files: Columns: 0: Project Name, 1: Neighborhood, 2: Type 1, 3:
 * #units for Type 1, 4: Selling price for Type 1, 5: Type 2, 6: #units for Type
 * 2, 7: Selling price for Type 2, 8: Application opening date, 9: Application
 * closing date, 10: Manager, 11: Officer Slot, 12: Officer
 */
public class DataLoader {

	/**
	 * Loads an Excel workbook from a file in the data directory.
	 * 
	 * @param relativePath The filename of the Excel file located in the "data"
	 *                     directory.
	 * @return The loaded {@link Workbook}.
	 * @throws IOException if the file cannot be found or read.
	 */
	private static Workbook loadWorkbook(String relativePath) throws IOException {
		String filePath = Paths.get("data", relativePath).toString();
		return new XSSFWorkbook(new FileInputStream(filePath));
	}

	/**
	 * Safely retrieves an integer value from a cell. This handles numeric cells or
	 * numeric strings; other types will cause an IllegalStateException.
	 * 
	 * @param cell The Excel {@link Cell} containing an integer.
	 * @return The integer value of the cell.
	 */
	private static int getIntCellValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			return (int) cell.getNumericCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			String value = cell.getStringCellValue().trim();
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				System.err.println("Warning: Expected int but got: " + value);
				return 0;
			}
		} else {
			throw new IllegalStateException("Unexpected cell type for integer: " + cell.getCellType());
		}
	}

	/**
	 * Safely retrieves a double value from a cell. This handles numeric cells or
	 * numeric strings; other types will cause an IllegalStateException.
	 * 
	 * @param cell The Excel {@link Cell} containing a double.
	 * @return The double value of the cell.
	 */
	private static double getDoubleCellValue(Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			String value = cell.getStringCellValue().trim();
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
				System.err.println("Warning: Expected double but got: " + value);
				return 0.0;
			}
		} else {
			throw new IllegalStateException("Unexpected cell type for double: " + cell.getCellType());
		}
	}

	/**
	 * Extracts and returns the cell’s value as a {@code String}, handling different
	 * cell types.
	 * <p>
	 * If the cell is of type {@link CellType#STRING}, its trimmed string value is
	 * returned. If it is of type {@link CellType#NUMERIC} and formatted as a date,
	 * the date is formatted as {@code yyyy-MM-dd}. If it is numeric but not a date,
	 * the numeric value is converted to its string representation. For any other
	 * cell type (including blank, boolean, formula, or error), an empty string is
	 * returned.
	 * </p>
	 *
	 * @param cell the {@code Cell} from which to extract the value; must not be
	 *             {@code null}
	 * @return the cell’s contents as a {@code String}, or an empty string if the
	 *         cell type is unsupported or blank
	 * @throws NullPointerException if {@code cell} is {@code null}
	 */
	private static String getStringValue(Cell cell) {
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue().trim();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(date);
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		} else {
			return "";
		}
	}

	/**
	 * Loads all applicants from an Excel file. Each row in the Excel should contain
	 * applicant details. Name and password columns are ignored (default password is
	 * used).
	 * 
	 * @param fileName The filename of the Excel file containing applicant data.
	 * @return A list of {@link Applicant} objects loaded from the file.
	 */
	public static List<Applicant> loadApplicants(String fileName) {
		List<Applicant> applicants = new ArrayList<>();
		try (Workbook workbook = loadWorkbook(fileName)) {
			Sheet sheet = workbook.getSheetAt(0);
			boolean firstRow = true;
			for (Row row : sheet) {
				if (firstRow) {
					firstRow = false;
					continue;
				}
				Cell nricCell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell ageCell = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell maritalCell = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (nricCell != null && ageCell != null && maritalCell != null) {
					String nric = getStringValue(nricCell);
					int age = getIntCellValue(ageCell);
					String maritalStatus = getStringValue(maritalCell);
					applicants.add(new Applicant(nric, age, maritalStatus));
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading Applicants from " + fileName + ": " + e.getMessage());
		}
		return applicants;
	}

	/**
	 * Loads all HDB Managers from an Excel file. Each row should contain manager
	 * details including name. Password column is ignored (default used).
	 * 
	 * @param fileName The filename of the Excel file containing manager data.
	 * @return A list of {@link HDBManager} objects loaded from the file.
	 */
	public static List<HDBManager> loadManagers(String fileName) {
		List<HDBManager> managers = new ArrayList<>();
		try (Workbook workbook = loadWorkbook(fileName)) {
			Sheet sheet = workbook.getSheetAt(0);
			boolean firstRow = true;
			for (Row row : sheet) {
				if (firstRow) {
					firstRow = false;
					continue;
				}
				Cell nameCell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell nricCell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell ageCell = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell maritalCell = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (nricCell != null && ageCell != null && maritalCell != null) {
					String name = getStringValue(nameCell);
					String nric = getStringValue(nricCell);
					int age = getIntCellValue(ageCell);
					String maritalStatus = getStringValue(maritalCell);
					managers.add(new HDBManager(name, nric, age, maritalStatus));
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading Managers from " + fileName + ": " + e.getMessage());
		}
		return managers;
	}

	/**
	 * Loads all HDB Officers from an Excel file. Each row should contain officer
	 * details. Name and password are ignored (treated like applicants).
	 * 
	 * @param fileName The filename of the Excel file containing officer data.
	 * @return A list of {@link HDBOfficer} objects loaded from the file.
	 */
	public static List<HDBOfficer> loadOfficers(String fileName) {
		List<HDBOfficer> officers = new ArrayList<>();
		try (Workbook workbook = loadWorkbook(fileName)) {
			Sheet sheet = workbook.getSheetAt(0);
			boolean firstRow = true;
			for (Row row : sheet) {
				if (firstRow) {
					firstRow = false;
					continue;
				}
				Cell nricCell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell ageCell = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell maritalCell = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if (nricCell != null && ageCell != null && maritalCell != null) {
					String nric = getStringValue(nricCell);
					int age = getIntCellValue(ageCell);
					String maritalStatus = getStringValue(maritalCell);
					officers.add(new HDBOfficer(nric, age, maritalStatus));
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading Officers from " + fileName + ": " + e.getMessage());
		}
		return officers;
	}

	/**
	 * Loads all projects from an Excel file. Each row should contain project
	 * details as per the specified column format.
	 * 
	 * @param fileName The filename of the Excel file containing project data.
	 * @return A list of {@link Project} objects loaded from the file.
	 */
	public static List<Project> loadProjects(String fileName) {
		List<Project> projects = new ArrayList<>();
		try (Workbook workbook = loadWorkbook(fileName)) {
			Sheet sheet = workbook.getSheetAt(0);
			boolean firstRow = true;
			for (Row row : sheet) {
				if (firstRow) {
					firstRow = false;
					continue;
				}
				// Expected 13 columns.
				Cell projectNameCell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell neighborhoodCell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell type1Cell = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell unitsType1Cell = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell priceType1Cell = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell type2Cell = row.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell unitsType2Cell = row.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell priceType2Cell = row.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell openDateCell = row.getCell(8, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell closeDateCell = row.getCell(9, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell managerCell = row.getCell(10, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell officerSlotCell = row.getCell(11, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell officerCell = row.getCell(12, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

				if (projectNameCell == null || neighborhoodCell == null || type1Cell == null || unitsType1Cell == null
						|| priceType1Cell == null || type2Cell == null || unitsType2Cell == null
						|| priceType2Cell == null || openDateCell == null || closeDateCell == null
						|| managerCell == null || officerSlotCell == null || officerCell == null) {
					System.err.println("Skipping row " + row.getRowNum() + " due to missing cells.");
					continue;
				}

				String projectName = getStringValue(projectNameCell);
				String neighborhood = getStringValue(neighborhoodCell);
				String type1 = getStringValue(type1Cell);
				int unitsType1 = getIntCellValue(unitsType1Cell);
				double priceType1 = getDoubleCellValue(priceType1Cell);
				String type2 = getStringValue(type2Cell);
				int unitsType2 = getIntCellValue(unitsType2Cell);
				double priceType2 = getDoubleCellValue(priceType2Cell);
				String openDate = getStringValue(openDateCell);
				String closeDate = getStringValue(closeDateCell);
				String manager = getStringValue(managerCell);
				int officerSlot = getIntCellValue(officerSlotCell);
				String officer = getStringValue(officerCell);

				Project proj = new Project(projectName, neighborhood, type1, unitsType1, priceType1, type2, unitsType2,
						priceType2, openDate, closeDate, manager, officerSlot, officer);
				projects.add(proj);
			}
		} catch (IOException e) {
			System.err.println("Error loading Projects from " + fileName + ": " + e.getMessage());
		}
		return projects;
	}
}
