package io;
import java.io.File;
import java.util.ArrayList;

public class FileOrganizer {
	private File dataDirectory;
	private ArrayList<File> salesSummaryFiles;
	public FileOrganizer(final File dataDirectory) {
		super();
		this.dataDirectory = dataDirectory;
		salesSummaryFiles = new ArrayList<File>();
		sortFilesForFolder(dataDirectory);
	}
	
	private void sortFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	    	if(fileEntry.getName().startsWith("SS"))
	    	{
	    		salesSummaryFiles.add(fileEntry);
	    	}
	    	
	    }
	}
	
	public File getDataDirectory()
	{
		return dataDirectory;
	}
	
	public ArrayList<File> getSalesSummaryFiles()
	{
		return salesSummaryFiles;
	}
}
