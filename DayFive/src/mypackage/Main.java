package mypackage;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");
		
		String csvFile = workingDir + "/src/resources/students.csv";
		String delimiter = ",";
		
		List<Map<String, String>> listOfDicts = new ArrayList<>();
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String line;
			br.readLine();
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split(delimiter);
				
				// current dictionary
				Map<String, String> currDict = new HashMap<>();
				currDict.put("\"id\"", "\"" + values[0] + "\"");
				currDict.put("\"name\"", "\"" + values[1] + "\"");
				currDict.put("\"course\"", "\"" + values[2]+ "\"" );
					
				listOfDicts.add(currDict);
				
				
				//System.out.println(Arrays.toString(values));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		String jsonLike = listOfDicts.toString().replace("=", ":");
		
		System.out.println(jsonLike);
		// writing
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(workingDir + "/src/resources/students.json"))) {
			bw.write(jsonLike);
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
		
	}

}
