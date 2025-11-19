
package com.java.ctl.sccr;

import android.app.*;
import android.os.*;

import java.io.*;
import android.widget.*;

import android.view.View;
import java.util.concurrent.*;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import   android.view.ViewParent;
  
import android.view.*;
import java.util.*;
import java.lang.String.*;
import java.lang.ref.*;
import java.lang.reflect.*;
import android.service.autofill.*;

import android.content.Intent;


public class EditText1 extends Activity 
{
	
	public static int goline=1;  // Line
	public static int gopos=0;   // Line * length()
	public static int golnbt=1;    // goto Btn On
	
    public static String ExtPath, fpath, Bsel, fpathSet;
	// App Down Fixed, 20230520Sa1206A ++ iian. 
    
	 public static String bpath, fopath, finame, str;  
	// ++ iian. 220913Tu1050P, (221020Th0319P, iian.)
	
	private String Stini = "/sdcard/Text.txt";
	private String fullpath = Stini;  // "";
	
	private EditText ed3;
	private TextView  ed3n;
	private static int pos1= 0, Avrln = 25;
	
	public static  int TextSz20 = 14; 
	public static  int BgcolorEdt = 0x30000080; 
	// 221111Fr0806P, ++ iian.
	public static  int BgpicEdt = 1;
	public static  int BgAutoEdt = 0;
	
	
	private int [] lposd = new int [500000]; // max lines.
	
	// Menu to Set, 20221112Sa1252A, ++ iian.
	public static String Bgpic= null, Selc= null;
	public static String bgco, disp, bgpi, Fname1;
	public static int Bgcolr = 0;
	public static int modlst=3; //3: 17sp : 13, 15, 17, 19, 21
	public static long BgcolrL = 0x30000080L; //  0x800000F0L;
	private static String atfl = "1";
	
	private static int Edfilelen= 0; // 221113Su0301A, iian.
	public static int fofl = 0;
	
    public static int iTfl = 0;  // 0 : Run FileLists.class
	
	private static int StartLine = 1;
	private static int EndLine = 2000;
	private static String Stln = "1";
	private static String Edln = "2000";
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext1);
		
        ExtPath=  Basic.getFilePath(); 
		fpath = ExtPath+"/Java/COMM/data/EditText1.dat";
        fpathSet = ExtPath+"/Java/COMM/data/EditText1Set.dat";
        
		// 20220917Sa0147A, ++ iian, (221020Th0327P, iian.)
		bpath= ExtPath;
		fopath= bpath+"/Java/COMM/data";
		finame = fopath+ "/EditText1Sel.dat";

		File ed1data = new File (fopath);
		if(!ed1data.exists())
		{
			ed1data.mkdirs();
		}
        File ed1set = new File(fpathSet);
        if(!ed1set.exists())
        {
    String StiniSet = "0x30000080\n2\n1\n0\n1\n20000\n";
           Fwrite(fpathSet, StiniSet); // EditText1Set.dat              
		}

		ed3 = findViewById(R.id.edit51);
		ed3n= findViewById(R.id.edit50);

		ed3.setTextSize(TextSz20);
		ed3n.setTextSize(TextSz20);

		BkColorSet();
		ed3. setBackgroundColor((int)BgcolrL);
		ed3n.setBackgroundColor((int)BgcolrL);
		// 20221112Sa0404P, ++ iian.
		
		////////////////////  20221114Mo0355P, Move This, iian.
		
		TextView tv1 =  findViewById(R.id.ed1txt51 );  // 	ed1txt51
		// 221102We0643A, ++ iian. 

		fofl = 0; // 20221115Tu0720A, ++ iian.
		
		 // Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
	    String type = intent.getType();
        
		if (Intent.ACTION_SEND.equals(action) && type != null) 
		{
			
	String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
					
	Toast.makeText(this, " • intent Type (1) ! • \n" + sharedText, Toast.LENGTH_LONG).show();
			
	Toast.makeText(this, " • intent Type (2) ! • \n" + type, Toast.LENGTH_LONG).show();
			String 	fullp ="";
			int fll =0;
		     if (sharedText == null)
		     {
			
	    	fullp = intent.getDataString(); // full file path
	            // file:///stroage/...		
	        fll = fullp.length();		
	
     fullpath = fullp.substring(6, fll);  // ???
	
     Toast.makeText(this, " • intent Type (3) ! • \n" + fullp + "\n"+ fll, Toast.LENGTH_LONG).show();
		      }  // null
			  else
			  {
				  
				  fofl = 1;
				  ed3.append(sharedText);
				  // 20221115Tu0658A, ++ iian.
			  }
	
		
		}
		
		else  // intent ...
		{
		
		fullpath = Fread1(fpath);  // EditText1.dat
		
		}
		Edfilelen = ed3.length(); // 221113Su0309A, iian.

		int ll = fullpath.length();

		String tt1 = "";
		int ct1 = ll;
		while (ct1 > 0)
		{
			ct1--;
			tt1 = fullpath.substring(ct1,ct1+1);
			if (tt1.equals("/"))
				break;
		}  // while
		Fname1 = fullpath.substring(ct1+1, ll); // Edfilelen); //  ll);
		setTitle(Fname1+ " • "+ TextSz20);
		
	/////////////////////////////////////////	

		File ed1file = new File (fpath);
		if(!ed1file.exists())
		{

			Fwrite(fpath, Stini); // EditText1.dat
			
			// 20221114Mo0356P, ++ iian.
			Fwrite(fullpath, "\n"); 
			
			str = FreadF(fullpath);    // Full path
			// set Edit...
			
		}
		
		
		else
	{  
		
	
		if (iTfl == 0)
		{
	       Intent intent2 = new Intent(this, FileLists.class); // /Sdcard/...
     
     //    20230520Sa0240P, iian.
		startActivity(intent2);			// to the List // Edit1
		}
		else
		{
			
			if (fofl == 0)
			{

				str = FreadF(fullpath);    // Full path

				fofl = 1;
			}	
		}
		
	    iTfl = 0;
		
	} // else
		
    }  // onCr
	
    void handleSendText(Intent intent)
	{
		String data= "";
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		
	    data = intent.getDataString(); // full file path
		
		if (sharedText != null) 
		{
			ed3.append(sharedText);
			ed3.setSelection(0);
			
	Toast.makeText(this, " • intent 2 ! • \n" + sharedText.length(), Toast.LENGTH_SHORT).show();				
		}
		
	else
	{
		ed3.append(data);
		ed3.setSelection(0);	
	 }
		
  }
	
   

	
	// 20221112Sa1228A, iian.
	public void BkColorSet()
    {
		String Selpath = fopath+"/EditText1SetSel.dat";
		String Setpath = fopath+"/EditText1Set.dat";

		File Selfl= new File (Selpath);  // finame); //Selpath);
		if (Selfl.exists())
		{


			Selc= Fread(Selpath);  // finame); // Selpath);
			
			String Cc = Selc.substring(0,1); 
			int Cci = Integer.parseInt(Cc);
		
			if (Cci == 1) 
			{
				String strs= Freads4(Setpath); // fpath); // Setpath);
				modlst = Integer.parseInt(disp);  // O.K.
		
			String BgHx=  bgco.replaceFirst("(?i)^0x", "");
			int bgl= bgco.length();
			String BgHx1 = bgco.substring(4,bgl); // 81 Xx ...
				
				Bgcolr = Integer.parseInt(("00"+ BgHx1),16);  // O.K. => 80 Down !!!
			String BgHx2 = ("00"+ BgHx).substring(2,4); // 80 Down 대처...
			long Bgh2 = Integer.parseInt(BgHx2,16); 
				Bgh2 = Bgh2 * 0x1000000L;
		BgcolrL = Bgh2 + (long) Bgcolr;
			String BgHex= String.valueOf(BgcolrL);

		Bgpic = bgpi+".jpg";
	
	   StartLine = Integer.parseInt(Stln);
	   EndLine   = Integer.parseInt(Edln);
	   // 20221115Tu0111P, ++ iian.
	   
	if (golnbt != 1 )  // 20221113Su0936P, iian.
	{
		if (disp.equals("1"))
		{
			TextSz20 = 13;
		}
		else if  (disp.equals("2"))
		{
			TextSz20 = 15;
		}
		else if  (disp.equals("3"))
		{
			TextSz20 = 17;
		}
		else if  (disp.equals("4"))
		{
			TextSz20 = 19;
		}
		else if  (disp.equals("5"))
		{
			TextSz20 = 21;
		}
	    else
		{
			TextSz20 = 14; 
		}

	}  // golnbt != 1 
    
        }

		} // (Selfl.exists())  
		else
		{

			Fwritedir(fopath, Selpath, "1"); // SetSel.dat

			File Setfl= new File (Setpath); // fpath); // Setpath);
			if (!Setfl.exists())
			{
				Fwrite(fpath, Stini); // Set.dat
			}

		} // Selfl.exists
		
	} // BkColorSet
	
	private String  Freads4(String fpath)
	{
		bgco = ""; disp=""; bgpi= "";
		String str= null; //  "";
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			bgco = dbr.readLine();  // FList Back Color...
			disp = dbr.readLine();  // Ch Size, 1~5
			bgpi = dbr.readLine();  // Back Pic Num 0~4
			atfl = dbr.readLine();  // Auto Chg 1, Fix 0
			Stln = dbr.readLine();  // Start Line, 1
			Edln = dbr.readLine();  // End   Line, 2000
			dbr.close();
			str = bgpi;
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
	} // Freads4
	
	private String  Fread(String fpath)
	{
		String str= null;  // "";
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = null; // ""; // null;
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
		
	}  // Fread
	
	long ktime1, ktime2;
	@Override
	public void onBackPressed()
	{
		ktime2 = System.currentTimeMillis();
		if(ktime2 - ktime1 < 2000)
		{
			finish();
		}
		else
		if (ktime2 - ktime1 > 3000)
		{
			Toast.makeText(this, "Exit   ◀ ◀",Toast.LENGTH_SHORT).show();   
			// 20221119Sa0442A, iian.
		}
		ktime1 = System.currentTimeMillis();
	
		// 20221119Sa0456A, iian.
	}
		
	@Override
	protected void onRestart()
	{
		
		super.onRestart();
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();

	}
	
	
	@Override
	protected void onResume()
	
	{
		super.onResume();
		
		BkColorSet();
		
		ed3. setBackgroundColor((int)BgcolrL);
		ed3n.setBackgroundColor((int)BgcolrL);
		// 20221112Sa0454P, ++ iian.
	
	{
		ed3.setTextSize(TextSz20);
		ed3n.setTextSize(TextSz20);
		// 20221112Sa0954P, ++ iian.
		
		setTitle(Fname1+ " • "+ TextSz20);
		// 20221113Su0631A, ++ iian.
	}
		
	if (golnbt==1 )
  {
	/////////////////////////////////////////////	
	// 20221109We0330A, ++ iian. 
		
		int tlen=   ed3.length();
	    if (goline >= EndLine)
		    goline = EndLine -1; // 20221115Tu1259P, ++ iian.
	if (goline <= 0)
	{
		goline = 0;
		gopos = 0;  // 1;
	}
	else if (goline == 1)
		{
			gopos = 1;
		}
    else  // 2 >= goline
	{
		gopos = lposd[goline-2]; // 20221109We0346A, ++ iian.
	}

		// 20221106Su0815A, ++ iian.

		if (gopos > tlen | gopos == 0)  // goline > maxline
			gopos = tlen;
        else if (gopos == 1)  
	// 20221109We0446A, ++ iian. Very Hard work.
		{
			gopos= 0;
		}
		

		ed3.setSelection(gopos);  // move o.k!  
		// 20221109We0330A, ++ iian.  
		
  }  // golnbt== 1

	}  // onResume
	
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
	}

	@Override
	protected void onStop()
	{
	
		super.onStop();
	}



	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		
	}
	

	
	// ++ iian. 220913Tu0937A.	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{	
		getMenuInflater().inflate(R.menu.edt_menu, menu);
		return true;
	}	


	// ++ iian. 220913Tu0938A, 220915Th0237A++.		
    @Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{


			case R.id.act_bt1:  // 📝  : goto // Dir

	    	   goline = 1;  // 0;  // 1010; 
			   
			golnbt=1;  // 20221113Su0951P, ++ iian.
		 
		Intent intent = new Intent(this, Edit1.class); 
		startActivity(intent);			// to the Edit1
		

				return true;
			case R.id.act_bt2:  //  💾  : save   // Chk
			
			String str = ed3.getText().toString();
			int lls = str.length();
			String Svpath =  fullpath;
			
			{	
			
			Fwrite(Svpath, str);
			Edfilelen = lls; 
			// 20221113Su0446A, iian.
			
				Toast.makeText(this, " • Save File • EDIT • "+ Edfilelen+ " Bytes •\n" + Svpath, Toast.LENGTH_LONG).show();
               }

				return true;
			case R.id.act_bt3:  // 🔍  : find  // Copy


				return true;
			case R.id.act_bt4:  // ➖ : zoom out   // Del
			
				TextSz20 --;

				if (TextSz20 <= 1)  // 10)
				{
					TextSz20= 1;    // 10;
				}
				ed3.setTextSize(TextSz20);
				ed3n.setTextSize(TextSz20);
			setTitle(Fname1+ " • "+ TextSz20);

				return true;


			case R.id.act_bt5:  // ➕ : zoom in // Ren
				TextSz20 ++;

				if (TextSz20 >=30)  // 50)  // 28)
				{
					TextSz20= 30;   // 50;   // 28;
				}

				ed3.setTextSize(TextSz20);
				ed3n.setTextSize(TextSz20);
			setTitle(Fname1+ " • "+ TextSz20);
				return true;
			case R.id.act_bt6:  // Exit
			
			int ll = ed3.length();
			if (Edfilelen != ll)
			{
				// Chk Save...
			}
	
				finish();
				return true;
			case R.id.act_bt7:  // Save As // Mov

				return true;
			case R.id.act_bt8:  //  Open // New Dir

		    	return true;
			case R.id.act_bt9:  // New Text // ✔ All

		    	return true;

			case R.id.act_bt10: // New Dir // Share id

		    	return true;
			case R.id.act_bt11:  // Help // Help

		    	return true;	
			case R.id.act_bt12:  // About // Text Rd : Toggle

		    	return true;	
			case R.id.act_bt13:  // Set // Text Wr
			golnbt = 0;  // 20221113Su0952P, ++ iian.
			
			Intent intents = new Intent(this, EditSet.class); 
			startActivity(intents);			// to the EditSet				
				
		    	return true;	

			default:
				return super.onOptionsItemSelected(item);
		}
	}	
	
	
	
	
	
	


	
	
	public void onButtSave(View v) // Use...
	// save1Btn
	{

		ed3= findViewById(R.id.edit51);

		// str= ed3.getText();	// Err. ^^
		// ed3.getText();	 
	    str = ed3.getText().toString();
		// 20221005We1221P, iian.


	//	Fwritedir(fopath, fpath, str); // EditText1.dat 
		Fwritedir(fopath, fullpath, str); // full path 

		Bsel = "1";
		Fwritedir(fopath, finame, Bsel); // EditText1Sel.dat
		
		finish();
	}

	public void onButtNone(View v) 
	{
		Bsel = "2";
		Fwritedir(fopath, finame, Bsel); // EditText1Sel.dat
		finish();
	}
	
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
			dbw.write(str);  // + "\n");
			dbw.close();

		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + fopath + finame, Toast.LENGTH_LONG).show();
		}

	}  // Fwritedir
	
	private String  FreadF_Tst(String fpath)
	{
		String str= ""; // null;

		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = ""; // null;  // ""; // null;

			while (((dstr = dbr.readLine()) != null))
			{	
				//	if ( dstr.length() <= 1)
				//		dstr = " ";	

				//	str = str+ dstr;
				//	str = str+ dstr+"\n";

				str = dstr+ "\n";
				ed3.append(str); // 221031Mo0222P, ++ iian.

				/// ed3.setText(str);	

				// 20221020Th0720P, ++ iian.
				// 20221021Fr0330A, ++ iian. 


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
	}  // freadF
	
	/////////////////////////////
	
	private String  FreadF(String fpath)
	{
		String str= ""; // null;
		int lcnt = 0;
		int lnum = 0; // 1;  //  0;  //  1; 1: O.K! 221106Su0203A, iian.
		int lnum0 = 0; //  1;
		String Lnum = "";
		
		int lnlen = 0;
		
		Arrays.fill(lposd,0);
		// 20221106Su0839A, ++ iian.
		// lposd[].
		
		/// File fr = new File(fpath);
		/// long flenf = fr.length();
		// 20221106Su0731A, ++ iian.
		

		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = ""; // null;  // ""; // null;
			
	        /// int lposd[] = lposd[(int)flenf];
	        // int [] lposd = new int[(int)flenf];
			
			
		//	ed3n.append(""+lnum+"\n");
		//  Lnum= String.valueOf(lnum)+"\n";
		    Lnum= String.valueOf(lnum+1)+"\n";
			// 20221109We0340A, ++ iian.
			lnum0 = lnum;
			// 20221109We0421A, ++ iian.
			
			while (((dstr = dbr.readLine()) != null))
			{	
			//	if ( dstr.length() <= 1)
			//		dstr = " ";	
			lcnt ++;
			
		//	lnum ++;
		//	lnum0 = lnum;
		//	if (lnum0 >= 500000)
		//		lnum0 =  500000 -1;
			
			lnlen = lnlen + dstr.length()+1;
	
			lposd[lnum0] = lnlen;
			
			// 20221109We0421A, ++ iian.
				lnum ++;
				lnum0 = lnum;
				
		//		if (lnum0 >= 500000)
		//			lnum0 =  500000 -1;
						
			
		// 20221106Su0739A, ++ iian.
			
		//	ed3n.append(""+lnum+"\n");
			
	///		if (lcnt < 40)  // Bug?? 
			if (lcnt <= 40) // Fixed Bug. O.K! 221106Su0217A, iian.
			// 50: 25 Sec., 40:22~ 24 Sec. : 700KB File.
			// 20221031Mo0455P, ++ iian.
			{
			  str = str+ dstr+"\n";
		// 	Lnum= Lnum+ String.valueOf(lnum)+"\n";  
		    Lnum= Lnum+ String.valueOf(lnum+1)+"\n";
			// 20221109We0340A, ++ iian. 
			
			} // if
			if (lcnt == 40)
			{
			///	ed3.setText(str);
				ed3.append(str);
				ed3n.append(Lnum);
				str = "";
				Lnum= "";
				
				lcnt = 0;
		///		delayed(2000);
			} // if
				
				if (lnum0 >= EndLine)    // 2000)
				{
					lnum0 =  EndLine -1; // 2000 -1;
					break;
				}
			// 20221114Mo0517A, ++ iian.			
			
			
			
		//	str = str+ dstr;
		//	str = str+ dstr+"\n";
		
	//	 if (lcnt > 100)
	//	 {
	//		str = dstr+ "\n";
	//		ed3.append(str); // 221031Mo0222P, ++ iian.
	//	  }
		/// ed3.setText(str);	

	   // 20221020Th0720P, ++ iian.
	   // 20221021Fr0330A, ++ iian. 


		} // while
			dbr.close();
			
	 if (lcnt > 0)
	 {
		 
	 
	//	ed3n.append(""+lnum+"\n");
		if (lcnt < 40)
		{
			ed3.append(str);
			ed3n.append(Lnum);  // for Chk Off. 221111Fr1125P, iian.
			
			str = "";
			Lnum= "";
			
		}
		
		Avrln = ed3.length() / lnum;
		// 20221106Su0643A, ++ iian.
		
	 } //  if (lcnt > 0)
	 else
	 {
	///	 lnum = 0;
	///	 ed3.append("\n");
	 }
	 
	///////////////////////////////////////////////////////
	
		int lineadd = lnum/10;  // 5  // ++ 10% Number add, Test.	
		// 20221111Fr1130P, ++ iian.	
	//	lnum = lnum0;
	//	int lineadd = lnum/10;  // 5  // ++ 10% Number add, Test.	
		// 20221115Tu1023A, ++ iian.
		
		if (lineadd > 100)
			lineadd = 100;
		else
		if (lineadd < 10)  // 221111Fr1140P, ++ iian.
			lineadd = 10;
			
		
		while (lineadd > 1)  //  0)
		{
			
			lineadd --;
			lnum ++;
			Lnum= Lnum+ String.valueOf(lnum+1)+"\n";
			// 20221111Fr1124, for Check, ++ iian. 	
		//	lnum ++;
			
		}
		
			ed3n.append(Lnum);  // for Chk Off. 221111Fr1126P, iian.
			
	///////////////////////////////////////////////////////
			
		ed3.setSelection(0);		    // O.K!
		
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
	}  // freadF
	
	
	public String  Fread1(String fpath)
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
	} // Fread1

	public  void Fwrite(String fpath, String str)
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(fpath, false));
			dbw.write(str);
			dbw.close();
		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + fpath, Toast.LENGTH_SHORT).show();
		}

	}  // Fwrite 
	
	void delayed(long dlytime)  // ++ iian : 220725Mo0449P
	{

		try
		{
			Thread.sleep(dlytime);   // ++ iian. 20220726Tu0345A.
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	} // delayed
	
	
} // EditText1


