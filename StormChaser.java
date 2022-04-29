/*
 * Class:   StormChaser.java
 * Purpose: Main program to read hurricane data file, create Storm objects,
 *          and keep those that are category 3 or higher.
 */

import java.io.*;
import java.util.Scanner;

public class StormChaser {
    public static void main(String[] args)
    {
      // Constants
    final int MAX_STORMS = 300;

      // array of Storms
    Storm CurrentStorm;      // storm returned by GetStorm
    int nStorms = 0;         // number in array List
    int totalStorms = 0;     // total number of storms in the input file
    Storm[] List = new Storm[MAX_STORMS];           //array of storms
    
    Scanner fileInput;
    
   // Openning hurricane data file
   try{
   System.out.println("Openning hurricane data file...");
   fileInput = new Scanner(new File("hurricanedata1950to2015.txt"));
   }
   catch(FileNotFoundException e){
       System.err.println("FileNotFoundException: " + e.getMessage());
       return;
   }
   System.out.println( "File opened successfully...");
   System.out.println( "Reading file..." );
   
   // Read Storm data from file until EOF
   while( fileInput.hasNextLine() ) 
   {
       CurrentStorm = GetStorm(fileInput);
	++totalStorms;
        // If Storm i category 3 or higher, add to the array
	if( CurrentStorm.getCategory() >= 3 )
	{
            List[nStorms] = CurrentStorms;
            nStorms++;
	}
    }
    System.out.println( "Number of storms: " + totalStorms );
    System.out.println( "Hurricanes with category 3 and above: " + nStorms );
    DisplayStorms( "First Twenty Storms", List, 20 );
    Sort( List, nStorms );
    DisplayStorms( "Top Twenty Storms", List, 20 );
    fileInput.close();
}

public static Storm GetStorm( Scanner in ) 
{
    // Create constants as array indexes for data elements
    final int STORMID = 0;
    final int NAME = 1;
    final int MAXRECORDS = 2;
    final int BEGINDATE = 0;
    final int PRESSURE = 7;
    
    // Declare variables
    
    int wind = 0, pressure = 0, maxRecords = 0;        
    int beginDate = 0, duration = 0;
    String name;
    String stormID;
    String header;
    String data;
  
  
    Storm NewStorm; //creates references/object to storm object
    
    
    // Read the Storm header information
    
    header = fileInput.nextLine();
    
    
    // Tokenize the header
   String[] headerElements = header.split(".");
   stormID = headerElements[STORMID].trim();
   name = headerElements[NAME].trim();
   maxRecords = Integer.parseInt(headerElements[MAXRECORDS].trim());
        
    // Read first row of Storm data
    data = fileInput.nextLine();
        
    // Tokenize the Storm data
    String[] dataElements = data.split(",");
        
    // Extract the data elements
    beginDate = Integer.parseInt(dataElements[BEGINDATE].trim());
    wind = Integer.parseInt(dataElements[WIND].trim());
    pressure = Integer.parseInt(dataElements[PRESSURE].trim()); //converts pressure values to ints
    duration = 6;    
    
    // Create Storm object
    NewStorm = new Storm(stormid, beginDate, duration, name, wind, pressure);    
        
    for( int i = 1; i < maxRecords ; i++ ) 
    {
        // Read next row of Storm data
        data = fileInput.nextLine();
        
        // Tokenize the Storm data
        dataElements = data.split(",");
        
        // Extract the data elements
        wind = Integer.parseInt(dataElements[WIND].trim());
        pressure = Integer.parseInt(dataElements[PRESSURE].trim()); //converts pressure values to ints
        duration += 6; 
        
        // Update Storm object
        NewStorm.setPressure(pressure);
        NewStorm.setWind(wind);
        NewStorm.setDuration(duration);
    }
    // Return the new storm object
    return NewStorm;      
}

public static void DisplayStorms( String title, Storm[] List, int NStorms ) 
{
    // display NStorms storms
    // print some title and column headings
    System.out.println(title);
    System.out.println(
   "           Begin    Duration                        Maximum     Minimum  ");
    System.out.println(
   "Storm ID   Date      (hours)    Name    Category  Winds (mph) Press. (mb)");
    System.out.println(
   "-------------------------------------------------------------------------");
    for( int k = 0; k < NStorms; k++ )
    {
        // Print out one Storm
        System.out.print(List[k].toString());
    }
    System.out.println ("\n");
}

public static void Sort( Storm StormList[], int n ) 
{
    // bubble sort the list of Storms
    int pass = 0, k, switches;
    Storm temp;
    switches = 1;
    while( switches != 0 ) 
    {
	switches = 0;
	pass++;
	for( k = 0; k < n - pass; k++ ) 
	{
		if( StormList[k].getCategory() < StormList[k+1].getCategory() )
		{
                    temp = StormList[k];
                    StormList[k] = StormList[k+1];
                    StormList[k+1] = temp;
                    switches = 1;
		}
	}
    }
}
}