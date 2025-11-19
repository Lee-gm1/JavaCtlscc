
package com.java.ctl.sccr;

import android.app.*;
import android.os.*;

import java.io.*;
import android.widget.*;

import android.view.View;

public class Edit1 extends Activity 
{
	
	String ExtPath, fpath, Bsel;
	private String bpath, fopath, finame, str;  
	// ++ iian. 220913Tu1050P, (221005We0831A, iian.)
	
	String Stini = "100";
	
	
	EditText ed33;
	TextView tv1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit1);
        ExtPath  =  Basic.getFilePath();
        
        
		fpath = ExtPath+"/Java/COMM/data/Edit1.dat";

		// 20220917Sa0147A, ++ iian, (221005We0831A, iian.)
		bpath= ExtPath;
		fopath= bpath+"/Java/COMM/data";
		finame = fopath+ "/Edit1sel.dat";
	     findViewById(R.id.editll5 ).
	     setBackgroundColor(0xff202020);
	
		File shdata = new File (fopath);
		if(!shdata.exists())
		{
			shdata.mkdirs();
		}
		
	
		File shfile = new File (fpath);
		if(!shfile.exists())
		{
			
			Fwrite(fpath, Stini);
		}
		else
		{
			str=  Fread3(fpath);  // 3...
			ed33= findViewById(R.id.edit3m);
        
        /// 20221005We1010A, iian.
			
		}
	
	
		
    }  // onCreate
	
	
	
	public void onButtYes(View v) // Use...
	{
		
		ed33= findViewById(R.id.edit3m);
		
		tv1 = findViewById(R.id.edit3mt); 
	
		str = null;
	 if (ed33.length() >= 1)  // 221108Tu0720P, ++ iian.
	    str = ed33.getText().toString();
		// 20221005We1221P, iian.
        
		if (str != null)
		{
		    tv1.setText(str);
			
			EditText1.golnbt=1;  // goto Line
		 
			EditText1.goline= Integer.parseInt(str);
		
		Fwritedir(fopath, fpath, str);  	   
		
		Bsel = "1";
		Fwritedir(fopath, finame, Bsel);
		}
		
		else
		{
			EditText1.golnbt=0; 
			
		// 20221218Su0656A, ++ iian.
		    str = "0";
			Fwritedir(fopath, fpath, str); 
			Bsel = "1";
			Fwritedir(fopath, finame, Bsel);		
			
		}
		finish();
	}

	public void onButtNo(View v) 
	{
		Bsel = "2";
		Fwritedir(fopath, finame, Bsel);
		
		EditText1.golnbt=0; 
		
		finish();
	}

	@Override
	public void onBackPressed()
	{
		
		EditText1.golnbt=0; 
		finish();

    }
	
	
	public void onButtDft(View v) 
	{
		Bsel = "3";
		ed33= findViewById(R.id.edit3m);
		ed33.setText(Stini);	

	}
	
	
	
	
	public String  Fread3(String fpath)
	{
		String str3= "";
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = "";
			
			dstr = dbr.readLine();
			str3 = dstr;
		
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
	} // Fread3
	
	
	

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
			dbw.write(str+ "\n");
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
		String str= null;
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
