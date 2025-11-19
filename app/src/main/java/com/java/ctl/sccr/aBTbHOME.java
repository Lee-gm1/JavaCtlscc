
package com.java.ctl.sccr;

import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.*;
import android.view.*;
import android.view.View.*;
import android.content.*;

import java.io.*;
import android.graphics.drawable.*;
import android.graphics.*;
import android.widget.Toolbar;
import java.util.ArrayList;


    public class aBTbHOME extends Activity {
	
	public static String ExtPath, fpath, Bsel;
	
    public static String BpkPath, AppPath, AppName, Appdir;

	public static int AppPos;
	
    public static String bpath, fopath, finame, str;  

	public static String fpathSet;

	public static int Ttlcol=0;


	public static long Bgvc = 0xA8000000L;      // Xx 0xAA000000;
	public static int Bgcol = (int) (Bgvc+0x151000L);
	
	
	TextView nTView;
	
	Activity act = this;
	GridView gridView;

	List<String> bapps  = new ArrayList ();
	List<String> bapps1  = new ArrayList ();
    
		public class gridAdapter extends BaseAdapter{

		LayoutInflater inflater;

		public gridAdapter() {
			inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
			

		@Override
		public int getCount() {
			return bapps1.size();
		}

		@Override
		public Object getItem(int position) {

			return bapps1.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ithitem, parent, false);
			}

			   ImageView imageView = (ImageView) convertView
				.findViewById(R.id.imageView1);
	
			TextView textView = (TextView) convertView
				.findViewById(R.id.textView1);
	
		 Appdir  = findAppdir(position);	

			String Gpath = AppPath+"/"+Appdir+"/icon.png";
			
		try{
			File bbfilej = new File (Gpath); // "icon.jpg"
			InputStream is = new FileInputStream(bbfilej) ;
		Bitmap 	btmap = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(btmap);
			is.close();
	        textView.setText(AppName);
		
		}catch(Exception e)
		{
			Toast.makeText(act, " • icon File Read Err! • \n"+ Gpath, Toast.LENGTH_LONG).show();			
		}

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
		
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
			{

				Appdir  = findAppdir(position);	 // O.K! good work !!!
				
				Toast.makeText(act, Appdir+" • "+AppName, Toast.LENGTH_SHORT).show();		
				
				if (Appdir.equals("Home"))
				{
					
									
				}
				
			}
			
			
		});
		
			return convertView;

		}
	}
	

	public String findAppdir(int position)
	{
       String fstr = bapps1.get(position);
	   String Appdir = "";
	   String str1= "";
	   
	   int lstr = fstr.length();
	   int lcnt = 0;
	   while (lstr > lcnt)
	   {
		   str1 = fstr.substring(lcnt,lcnt+1);
		   
		   if (str1.equals(";"))
		   {
			  AppName = fstr.substring(lcnt+1, lstr);
			  break;
		   }
		   
		   Appdir = Appdir + str1;
		   
		   lcnt ++;
		   
	   }
	   
	return Appdir;
	}
	

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

		setContentView(R.layout.ithome);
		gridView = (GridView) findViewById(R.id.gridview);
		nTView = (TextView) findViewById(R.id.xTView);	

		setTitle("📝 ");
        ExtPath  =  Basic.getFilePath();
       
        fpath = ExtPath+"/Java/App/AppList.dat";

		bpath= ExtPath;
        fopath= bpath+"/Java/App";
		finame = fopath+ "/AppListSel.dat";

		AppPath = ExtPath+"/Java/App";

		fpathSet=ExtPath+"/Java/BattBSet.dat";

		File appdata = new File (fopath);
		if(!appdata.exists())
		{
			appdata.mkdirs();
		}
		

		String str1 = FreadList(fpath);

		gridView.setAdapter(new gridAdapter());
		

	
		File wmset = new File (fpathSet);
		if(!wmset.exists())
		{
			FwriteSet(fpathSet, Ttlcol, Bgvc);	 
		}	


		FreadSet(fpathSet);	


		if (Ttlcol == 1)
			getActionBar().setBackgroundDrawable(new ColorDrawable
                                                 (Color.parseColor("#ff4f186f"))); // 20 7f Blue  
		else
		if (Ttlcol == 2)
			getActionBar().setBackgroundDrawable(new ColorDrawable
												 (Color.parseColor("#ff705000"))); // 90 90 Yellow
		else
		if (Ttlcol == 3)
			getActionBar().setBackgroundDrawable(new ColorDrawable
												 (Color.parseColor("#ff00503f"))); // 70 4f Green
		else
		if (Ttlcol == 4)
			getActionBar().setBackgroundDrawable(new ColorDrawable
												 //			  (Color.parseColor("#ff20004f"))); // Blue Black
												 (Color.parseColor("#ff003f5f")));  
		else
		if (Ttlcol == 5)
			getActionBar().setBackgroundDrawable(new ColorDrawable
												 (Color.parseColor("#ff800000"))); // 90 Red

		
		Bgcol = (int) (Bgvc+0x151000L);
		nTView.setBackgroundColor(Bgcol); ///   Color(0xAAFFD060);  // 글자 노란색.
		
	
		
	} // onCr

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{	
		getMenuInflater().inflate(R.menu.ithome_menu, menu);
		return true;
	}	

    @Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{

		
			case R.id.act_bt3:  // Set
			    finish();
			
				return true;
				
			case R.id.act_bt6:  // 💍

				return true;		
				
			case R.id.act_bt7:  // 🔱 Ttl color
			// 🏆 💍 💫 ✨ 💦 ⚡ 🌙 
				Ttlcol++;
			if (Ttlcol == 1)
				getActionBar().setBackgroundDrawable(new ColorDrawable
						(Color.parseColor("#ff4f186f"))); // 20 7f Blue  
			else
				if (Ttlcol == 2)
				getActionBar().setBackgroundDrawable(new ColorDrawable
						 (Color.parseColor("#ff705000"))); // 90 90 Yellow
				else
				if (Ttlcol == 3)
				getActionBar().setBackgroundDrawable(new ColorDrawable
					     (Color.parseColor("#ff00503f"))); // 70 4f Green
				else
				if (Ttlcol == 4)
				getActionBar().setBackgroundDrawable(new ColorDrawable
				          (Color.parseColor("#ff003f5f")));  
				else
				if (Ttlcol == 5)
					getActionBar().setBackgroundDrawable(new ColorDrawable
						  (Color.parseColor("#ff800000"))); // 90 Red
				else
				{
					getActionBar().setBackgroundDrawable(new ColorDrawable
					      (Color.parseColor("#ff20004f"))); // Blue Black  
					Ttlcol	= 0;
                 }
				 
				 
				FwriteSet(fpathSet, Ttlcol, Bgvc);
				 
				 
				return true;
			case R.id.act_bt4:  // ➖ // Del
			
				Bgvc = Bgvc+0x08000000L;
				if (Bgvc >= 0xFF000000L)
					Bgvc =  0xFF000000L;

				Bgcol = (int) (Bgvc+0x151000L);
				nTView.setBackgroundColor(Bgcol); ///   Color(0xAAFFD060);  // 글자 노란색 지정.
	            FwriteSet(fpathSet, Ttlcol, Bgvc);

				delayed(500);

				return true;
				


			case R.id.act_bt5:  // ➕ // install
			
				Bgvc = Bgvc-0x08000000L;
				if (Bgvc <= 0x00000000L)
					Bgvc =  0x00000000L;

				Bgcol = (int) (Bgvc+0x151000L);
				
				nTView.setBackgroundColor(Bgcol);
	            FwriteSet(fpathSet, Ttlcol, Bgvc);

				delayed(500);
				return true;		

			default:
				return super.onOptionsItemSelected(item);
		}
	}  // menu Sel
	
	public String  FreadSet(String fpath)
	{
		String str= null;  // "";
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = null; // ""; // null;
			dstr = dbr.readLine();

			str = dstr;
			Ttlcol = Integer.parseInt(dstr);

			dstr = dbr.readLine();
			long bv = Integer.parseInt(dstr);
			Bgvc = (long) (bv * 0x1000000);


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
	}  // freadSet
	
	
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

	}  // fread

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

	
	public  void FwriteSet(String fpath, int ttl, long Bgv)
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(fpath, false)); // Over Write
			dbw.write(ttl+ "\n");
			dbw.write(Bgv/0x1000000+ "\n");
			dbw.close();
		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}

	}  // FwriteSet
	
	

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



	void delayed(long dlytime)
	{
		try
		{
			Thread.sleep(dlytime);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}  // delayed


	void delayedBk(long dlytime)
	{
		long stime= System.currentTimeMillis();			

		while (true)
		{
			long etime= System.currentTimeMillis();			

			if ((etime - stime) > dlytime)
			{
				break;
			}

		}

	} // delayedBk
	
	public String Lfpath= fpath;
	public  void FwriteList()
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(Lfpath, false));
			int lenlst = bapps.size();  // apps
			int lpnt = 0;
			String lstr= "";
			
			while (lenlst >0)
			{
				lstr = bapps.get(lpnt).toString(); // cpos);
				dbw.write(lstr+ "\n");	
				lenlst--;
				lpnt++;
			}

			dbw.close();
		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + Lfpath, Toast.LENGTH_SHORT).show();
		}

	}  // FwriteList
    
	public String  FreadList(String fpath)
	{
		String str= null;
		
		bapps1.clear();
		
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = null;  // ""; // null;
			while (((dstr = dbr.readLine()) != null))
			{	
				if ( dstr.length() <= 1)
					dstr = " ";	
				bapps1.add(dstr);
				str = dstr;	 
			}
			dbr.close();


		}
		catch (FileNotFoundException e)
		{
			Toast.makeText(this, " • File Not found! • \n" + fpath, Toast.LENGTH_LONG).show();
		}
		catch (IOException e)
		{
			Toast.makeText(this, " • File Read Err! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}
		return str;
	} // FreadList
	

}
