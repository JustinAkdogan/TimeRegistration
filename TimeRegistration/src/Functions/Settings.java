package Functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {
	
	final String settings_path = "C:\\Zeiterfassung\\Settings.ini";
	
	private String [] settingStructure = {
	"[Database]",
	"connection_type=MYSQL",
	"server=localhost",
	"database=zeiterfassungs_db",
	"username=root",
	"password=blowfish"
	};

	public void setStandardSettings() {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter (new FileWriter(settings_path, false));
			for (byte i=0; i < settingStructure.length; i++) {
				writer.write(settingStructure[i]);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
				
		}
	}
	
	public String [] readAndGetSettings() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(settings_path));
			String currentLine;
	        String [] settingLines = new String [getSettingsLength()];
	        int counter = 0;
	        while ((currentLine = reader.readLine()) != null) {
	        	if (counter > 0) {
	        		settingLines[counter] = currentLine.substring(currentLine.indexOf("=")+1, currentLine.length());
	        	}
	        	counter++;
	        }
			reader.close();
			return settingLines;
		} catch (IOException e) {
				
		}
		return null;
	}
	
	
	public int getSettingsLength() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(settings_path));
			String currentLine;
			int lineCount = 0;
	        while ((currentLine = reader.readLine()) != null) { lineCount++; }
			reader.close();
			return lineCount;
		} catch (IOException e) {
				
		}
		return 0;
	}
}
