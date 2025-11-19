
package com.java.ctl.sccr;
 
import android.app.*;
import android.os.*;

import java.io.*;
import android.widget.*;

import android.view.View;

public class EditSet extends Activity 
{
	
	String ExtPath, fpath, Bsel;
	private String bpath, fopath, finame, str;  
	// ++ iian. 220913Tu1050P, (221005We0831A, iian.)
	
	String Stini = 
	       "0x30000080\n3\n1\n0\n1\n2000";

	// 20221027Th0549P, 0555P+, iian.
	

	EditText ed3;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsetmain);
        ExtPath =  Basic.getFilePath();
        
		fpath = ExtPath+"/Java/COMM/data/EditText1Set.dat";
		// 20221111Fr0741P, ++ iian.
	
		bpath= ExtPath;
		fopath= bpath+"/Java/COMM/data";
		finame = fopath+ "/EditText1SetSel.dat";
	
		File shdata = new File (fopath);
		if(!shdata.exists())
		{
			shdata.mkdirs();
		}
		
	
		File shfile = new File (fpath);  // Set.dat
		if(!shfile.exists())
		{
			
			Fwrite(fpath, Stini);
		}
		else
		{
			str=  Fread4(fpath);  // 4...
			ed3= findViewById(R.id.edit3s);
			
			ed3.setText(str);
			
		}
	
	
		
    }  // onCreate
	
	
	
	public void onButtYes(View v) // Use...
	{
		
		
		ed3= findViewById(R.id.edit3s);
	    str = ed3.getText().toString();
		// 20221005We1221P, iian.
		   
		   
		Fwritedir(fopath, fpath, str);  	   
		
		Bsel = "1";
		Fwritedir(fopath, finame, Bsel);
		finish();
	}

	public void onButtNo(View v) 
	{
		Bsel = "2";
		Fwritedir(fopath, finame, Bsel);
		finish();
	}

	public void onButtDft(View v) 
	{
		Bsel = "3";
		ed3= findViewById(R.id.edit3s);
		ed3.setText(Stini);	
	}
	
		
	public String  Fread4(String fpath)
	{
		String str3= "";
		
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = "";
			
			dstr = dbr.readLine();  // color 0x30 000080
			str3 = str3+dstr+"\n";
			
					
			dstr = dbr.readLine();  // Ch size 1~ 5
			str3 = str3+dstr+"\n";
			
			dstr = dbr.readLine();  // Bg Pic. 0~ 4
			str3 = str3+dstr+"\n";
			
			dstr = dbr.readLine();  // Auto Chg : 1
			str3 = str3+dstr+"\n";
			
			dstr = dbr.readLine();  // Start Line : 1
			str3 = str3+dstr+"\n";
			
			dstr = dbr.readLine();  // End Line : 2000
			str3 = str3+dstr;  // +"\n";
			
			dbr.close();
		}
		catch (FileNotFoundException e)
		{
			Toast.makeText(this, " • File Not found! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}
		catch (IOException e)
		{
			Toast.makeText(this, " • File Read Err! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}
		return str3;
	} // Fread4
	

	public  void Fwritedir(String fopath, String finame, String str)
	{

		File folder = new File (fopath);
		if(!folder.exists())
		{
			folder.mkdirs();
		}

		try
		{		

			BufferedWriter dbw = new BufferedWriter(new FileWriter(finame, false));	
			dbw.write(str); 
			dbw.close();

		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + fopath + finame, Toast.LENGTH_LONG).show();
		}

	}  // Fwritedir
	
	
	public  void Fwrite(String fpath, String str)
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(fpath, false));
			dbw.write(str+ "\n");
			dbw.close();
		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}

	}  // Fwrite 

	public String  Fread(String fpath)
	{
		String str= "";  // null;
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = ""; // null;
			dstr = dbr.readLine();
			str = dstr;
			dbr.close();
		}
		catch (FileNotFoundException e)
		{
			Toast.makeText(this, " • File Not found! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}
		catch (IOException e)
		{
			Toast.makeText(this, " • File Read Err! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}
		return str;
	} // Fread
		
	
} // End
