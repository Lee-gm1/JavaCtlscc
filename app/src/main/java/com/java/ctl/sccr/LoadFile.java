
/// Copyright (C) 2010 - 2019 Paul Laughton

package com.java.ctl.sccr;

import static com.java.ctl.sccr.Editor_b.GO_UP;
import static com.java.ctl.sccr.Editor_b.addDirMark;
import static com.java.ctl.sccr.Editor_b.isMarkedDir;
import static com.java.ctl.sccr.Editor_b.stripDirMark;
import static com.java.ctl.sccr.Editor_b.getDisplayPath;
import static com.java.ctl.sccr.Editor_b.goUp;
import static com.java.ctl.sccr.Editor_b.quote;

import com.java.ctl.sccr.Basic.ColoredTextAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import android.content.*;

public class LoadFile extends ListActivity {
	private static final String LOGTAG = "Load File";

private Basic.ColoredTextAdapter mAdapter;
	private Toast mToast = null;

	/// private String mProgramPath;										// load file directory path
	public static String mProgramPath;										// load file directory path
	// for Chk, 20221201Th1146A, ++ iian.
	
	private ArrayList<String> FL = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setRequestedOrientation(Settings1.getSreenOrientation(this));

		mProgramPath = Editor_b.ProgramPath;							// set Load path to current program path
		updateList();												// put file list in FL

mAdapter = new ColoredTextAdapter(this, FL, Basic.defaultTextStyle);	// Display the list
		setListAdapter(mAdapter);
		ListView lv = getListView();
		lv.setTextFilterEnabled(false);
		lv.setBackgroundColor(mAdapter.getBackgroundColor());

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// User has selected a line.
				// If the selection is the top line, ignore it.
				if (position == 0)		return;

				// If the selection is a directory, change the program path
				// and then display the list of files in that directory.
				// Otherwise load the selected file and tell the Editor_b it was loaded.

				String fileName = FL.get(position);
				Log.i(LOGTAG, "Selection: " + quote(fileName));
				if (!SelectionIsFile(fileName)) {
					updateList();
				} else {
					
					
		/// Size String Cut... 20221123We1152A, ++ iian.

					int fln = fileName.length();
					int flcnt = fln;
					int cnt = 0;
					String fnm = "";

					while (flcnt > 0)
					{

						String s1 = fileName.substring(cnt, cnt+1);

						if (s1.equals("\n"))
						{
							fileName = fnm;
	
							break;
						}
						fnm = fnm+s1;

						flcnt--;
						cnt++;
					}
					
			/// 20221123We1228P, ++ iian.		
					
					
					
					FileLoader(fileName);
					setResult(RESULT_OK);
					if (mToast != null) { mToast.cancel(); mToast = null; }
					finish();									// LoadFile is done
					return;
				}
			}
		});

	}

	private void updateList() {

		File dir = new File(mProgramPath);
		dir.mkdirs();

		String[] fileList = dir.list();							// Get the list of files in this dir
		if (fileList == null) {
			String msg = dir.exists() ? getString(R.string.File_not_directory)
									  : getString(R.string.Source_directory_does_not_exist);
			msg = getString(R.string.System_Error_MSGARG, msg);
			if (mToast != null) { mToast.cancel(); }
mToast = Basic.toaster(this, msg);
			return;
		}

		// Go through the list of files and mark directories with "(d)".
		// Display only files with the .bas extension.
		ArrayList<String> dirs = new ArrayList<String>();
		ArrayList<String> files = new ArrayList<String>();

		String absPath = dir.getAbsolutePath() + '/';
		for (String s : fileList) {
			File test = new File(absPath + s);
			if (test.isDirectory()) {							// If file is a directory, add "(d)"
				dirs.add(addDirMark(s));						// and add to display list
			} else {
			///	if (s.toLowerCase(Locale.getDefault()).endsWith(".bas")) { // Only put files ending in
		
			
		/// 20221123We1143A, ++ iian.
	    long fsz = test.length();
		String fszs= String.valueOf(fsz);
		int fszln = fszs.length();
		int flcnt = fszln;
	    int i3=0, i1=0;
		String nfszs, nf1;
		nfszs = ""; nf1="";

		for (int ii=fszln; ii > 0; ii--)
		{
			i3++;
			nf1= fszs.substring(ii-1, ii);

		    nfszs = nf1+nfszs;
			if (i3 == 3)
			{
			  if (fszln > 3)
			  {
				  nfszs= ","+ nfszs;
			  }
			} // 3
			if (i3 == 6)
			{
				if (fszln > 6)
				{
					nfszs= ","+ nfszs;
				}
			}
			if (i3 == 9)
			{
				if (fszln > 9)
				{
					nfszs= ","+ nfszs;
				}
			}
		
			
		} // for
		
		
		flcnt = nfszs.length();
		while (flcnt < 30)  // 32 max ? V
		{
			nfszs = " "+ nfszs;
			flcnt++; 
		}
	
				    files.add(s+"\n"+ nfszs);	
			}
			
		/// 20221123We1143A, ++ iian.
			
		}  // ...
		
		Collections.sort(dirs);									// sort the directory list
		Collections.sort(files);								// sort the file list

		FL.clear();
		FL.add("Path: " + quote(getDisplayPath(mProgramPath))); // put the path at the top of the list - will not be selectable
		FL.add(GO_UP);											// put  the ".." above the directory contents
		FL.addAll(dirs);										// copy the directory list to the adapter list
		FL.addAll(files);										// copy the file list to the end of the adapter list

		if (mAdapter != null) { mAdapter.notifyDataSetChanged(); }

		if (mToast != null) { mToast.cancel(); }				// tell the user what to do using Toast
mToast = Basic.toaster(this, getString(R.string.Select_File_To_Load));
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {						// If back key pressed
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {	// and the key is the BACK key
			setResult(RESULT_CANCELED);
			if (mToast != null) { mToast.cancel(); mToast = null; }
			finish();															// then done with LoadFile
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	private boolean SelectionIsFile(String fileName) {
		
		// Test to see if the user selection is a file or a directory
		// If is directory, then change the path so that the files
		// in that directory will be displayed.
		
		if (fileName.equals(GO_UP)) {							// if GoUp is selected
			mProgramPath = goUp(mProgramPath);					// change path to its parent directory
			return false;										// report this is not a file
		}
																// Not UP, is selection a dir
		if (isMarkedDir(fileName)) {							// if has (d), then is directory
			fileName = stripDirMark(fileName);					// remove the (d)
			mProgramPath = new File(mProgramPath, fileName).getPath();	// add dir to path
			return false;										// and report not a file
		}

		
		  return true;											// if none of the above, it is a file
	}

	 private void FileLoader(String aFileName) 
	
	{					// The user has selected a file to load, load it.

Basic.clearProgram(); 				// Clear the old program
		Editor_b.DisplayText = "";			// Clear the display text buffer
		 
		Editor_b.ProgramPath = mProgramPath;  // Modify public static, ++ iian.
		   // "null/"
		   
		Editor_b.ProgramFileName = aFileName;

		String FullFileName = new File(mProgramPath, aFileName).getPath();

		ArrayList<String> lines = new ArrayList<String>();
int size = Basic.loadProgramFileToList(true, FullFileName, lines);	// is full path to the file to load
		if (size == 0) {					// File not found - this should never happen
			// Turn the program file into an error message
			// and act as if we loaded a file.
			String msg = "! Load Error: " + quote(aFileName) + " not found";
			lines.add(msg);
			size = msg.length() + 1;
		}

		// The file is now loaded into a String ArrayList. Next we need to move
		// the lines into the Editor_b display buffer.

Editor_b.DisplayText = Basic.loadProgramListToString(lines, size);
		Editor_b.InitialProgramSize = Editor_b.DisplayText.length();		// Save the initial size for changed check
		Editor_b.Saved = true;
		if (Editor_b.mText == null) {
			throw new RuntimeException("LoadFile: Editor_b.mText null");
		}
		Editor_b.mText.setText(Editor_b.DisplayText);
	}

///	public static void FileLoader1(String aFileName) 
	public static void FileLoaderJv(String aPath, String aFileName) 
	// Checked & Modify, 20221202Fr0340P, ++ iian.

	{					// The user has selected a file to load, load it.

		Basic.clearProgram(); 				// Clear the old program
		Editor_b.DisplayText = "";			// Clear the display text buffer
		Editor_b.ProgramPath = mProgramPath;  // ??? Modify public static, ++ iian.
		// "null/"

	///	Editor_b.ProgramFileName = aPath + "/"+ aFileName;
		Editor_b.ProgramFileName = aFileName;  // aFileName = full path
		// full path => filename
		// 20221202Fr0343P, iian.

	//	String FullFileName = new File(mProgramPath, aFileName).getPath();  // org.
	//  20221202Fr0122A, iian.
		
	//	String FullFileName = aFileName;  // O.K! All ways Good Work!!!
	//  Check to Good Work!, 20221202Fr0119A, ++ iian.
		
	///	String FullFileName = aFileName; 
		String FullFileName = aPath + "/"+ aFileName;
	// Modify, 20221202Fr0349P, iian.	
		
		ArrayList<String> lines = new ArrayList<String>();
		int size = Basic.loadProgramFileToList(true, FullFileName, lines);	// is full path to the file to load
		if (size == 0) {					// File not found - this should never happen
			// Turn the program file into an error message
			// and act as if we loaded a file.
			String msg = "! Load Error: " + quote(aFileName) + " not found";
			lines.add(msg);
			size = msg.length() + 1;
		}

		// The file is now loaded into a String ArrayList. Next we need to move
		// the lines into the Editor_b display buffer.

		Editor_b.DisplayText = Basic.loadProgramListToString(lines, size);
		Editor_b.InitialProgramSize = Editor_b.DisplayText.length();		// Save the initial size for changed check
		Editor_b.Saved = true;
		if (Editor_b.mText == null) {
			throw new RuntimeException("LoadFile: Editor_b.mText null");
		}
		Editor_b.mText.setText(Editor_b.DisplayText);
		
        Editor_b.ProgramPath = Basic.getSourcePath(null);  // O.K! Normal Work!
		
		Editor_b.ProgramFileName =  aFileName;
		// Modify, 20221202Fr0351P, iian.
		// 20221202Fr0354P, iian.
		////////////////////////////////////////////////////
		
	}  //  FileLoader1 => FileLoadJv
		
	
}
