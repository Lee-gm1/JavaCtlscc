
// Copyright (C) 2010 - 2019 Paul Laughton
// Localization added 2018 by Hirokazu Yamazaki (puziro).
// Line number, zoom, more added 2024, iian.


package com.java.ctl.sccr;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

// 20220713We1029P, iian.
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.AssetManager;

import android.widget.*;
import java.io.*;

import java.lang.*;   // java.lang.Math;    


import java.util.*;

import java.util.concurrent.*;   // Array.fill(Array, 0);

import android.app.ActivityManager;
import android.graphics.drawable.Drawable; // 221120Su0341A, iian.

import android.view.WindowManager; // 221120Su0341A, iian.
/// 20240514Tu0527A, This ++ Resume for Key Hide... , iian.

public class Editor_b extends Activity {
	private static final String LOGTAG = "Editor";

	// Things to save and restore if the system kills us.
	private static final String STATE_PROGRAM_PATH = "programPath";
	private static final String STATE_PROGRAM_FILE_NAME = "programFileName";
	private static final String STATE_INITIAL_SIZE = "initialSize";
	private static final String STATE_SAVED = "isSaved";
	private static final String STATE_ERROR_DISPLACEMENT = "errorDisplacement";
	private static final String STATE_MTEXT_DATA = "theText";
public static final String EXTRA_RESTART = "internal.rfobasic.zin.doRestart";
public static final String EXTRA_LOADPATH = "internal.rfobasic.zin.initLoadPath";

	// Since Android 4.2, "external storage" may be emulated and the path may included the user id.
	// There is another path that uses "legacy" instead of the user id.
	// E.g., getExternalStorageDirectory() may return /storage/emulated/0",
	// but then the "/sdcard link would resolve to "/storage/emulated/legacy".
	// The default basePath and filePath come from getExternalStorageDirectory().
	// For comparisons with other canonical paths we need the "legacy" version.
	// mAltHomePath and mAltBasePath, if non-null, use the "legacy" paths.

	private static String mBasePathAndSep;				// canonical base path (WITH trailing separator)
	private static String mAltBasePathAndSep;			// if non-null, this is mBasePath with "legacy"
	private static String mHomePath;					// canonical base path + app path (NO trailing separator)
	private static String mAltHomePath;					// if non-null, this is mHomePath with "legacy"
	public static String ProgramPath;					// canonical path to ProgramFileName used by LOAD/SAVE
	public static String ProgramFileName;				// set when program loaded or saved
	public static final String SEPARATOR = "" + File.separatorChar; // '/' in a String
	public static final String DIR_MARK = "(d)";		// marker that indicates a file name refers to a directory
	public static final String GO_UP = "..";			// "file name" that means move up the directory tree (toward root)

	private static final int LOAD_FILE_INTENT = 1;

	public static LinedEditText mText;					// The Editors display text buffers
 public static String DisplayText = Basic.mDefaultFirstLine;
	public static int SyntaxErrorDisplacement = -1;

	public static int selectionStart;
	public static int selectionEnd;
	public static int InitialProgramSize;				// Used to determine if program has changed
	public static boolean Saved = true;

	private Menu mMenu = null;
	private enum Action { NONE, CLEAR, LOAD, RUN, LOAD_RUN, EXIT }

	private Bundle mSavedInstanceState = null;			// carry saved state across callbacks
	
	////////// Start ...
	public static int TextSz20 = 17;  // 20;  /// Err ? No ... iian ++ 220713We0235P.
	public static int TextSz20bk = 0; // w, h ...
	
	public static double touchpos_X  = 0; // X position.
	public static double touchpos_Y  = 0; // Y position.
	
	// Board_Disp. On/Off 1: On.
	// Disp_Pic number. 00~09
	// Zoom_Edit On/Off 1: On
	// Zoom_Sz End time size: 1 else init size(18).
	
	/// public static String Board_Disp = "1";
	public static String Board_Disp = "0"; // default.
	// 20220721Th1226A, iian.
	
	public static String Disp_Pic = "00";
	public static String Zoom_Edit = "1";
	
	public static int Zoom_Sz = 1; // 1: End time Size, else init 18.
	// End ...  20220715Fr0955P, iian.
	
	private static int Linenum[] = new int [100000]; // 100,000: 200KB ++ Memory.
	// 100K Lines Max, 20221106Su0957A, ++ iian.
	
	private static int gotolnum = 0;  // 221106Su0457A, ++ iian.
	private static int mxfln = 0, gotofl = 0;  // for Reasum, 221116We0319A ++ iian.
	
	public static int Exitfl = 0;  
    // Run at Exit, onStop.
	// Editor Stop, Filag 1 Set, 20221120Su0253A, iian.
	
    
    public static int mBaseline =0;  /// getBaseline();
    
 /////////////////////////////

 
	
	// ****************** Class for drawing Lined Edit Text *******************

	public static class LinedEditText extends EditText {	// Part of the edit screen setup
		private Rect mRect;
		private Paint mPaint;
		
		private Rect rect;  // ++
		private Paint paint; //++
			
		
		
		private boolean mLinesSetting;						// Lines preference setting for onDraw
		private boolean mLineWrapSetting;					// Line-wrap preference setting

		private Scroller mScroller;							// The scroller object
		private VelocityTracker mVelocityTracker;			// The velocity tracker
		private int mScrollY = 0;							// The current scroll location
		private float mLastMotionY;							// Start of last movement
		private int mMinScroll;								// Minimum scroll distance
		private int mFlingV;								// Minimum velocity for fling
		public static int sHeight;							// Screen height minus the crap at top
		public static boolean didMove;			// Determines if super called on UP


		public LinedEditText(Context context, AttributeSet attrs) {
			super(context, attrs);

			mRect = new Rect();
			mPaint = new Paint();
			mPaint.setStyle(Paint.Style.FILL); // STROKE);
	//		mPaint.setColor(0x800000FF);
			mPaint.setColor(0xFF00FFFF);
			

			
			InitScroller(context);			
			
		}
		

		@Override
		protected void onTextChanged(CharSequence  text, int start, int before, int after) {

			// Here we are monitoring for text changes so that we can set Saved properly
			

			int i = text.length();							// When the text is changed
			if (i > 0 && i != InitialProgramSize) {			// Make sure it is a real change
				Saved = false;								// then indicate not saved
			}

			super.onTextChanged(text, start, before, after);
		}

		@Override
		protected void onDraw(Canvas canvas) 
		{
	
	///////////////////////////////////////////////
	//  20221102We1126P, 20221103Th1142P, 20221104Fr1237A, ++ iian.	
	
	//	private static int linenum[] = new int [100000]; // 100,000: 200KB ++ Memory.
		// 100K Lines Max, 20221106Su0957A, ++ iian.
		
	
	    int linenum = 1;
		
	    if (linenum == 1)
		{
			int baseline1 = getBaseline();
            
			
			Rect r1 = mRect;
			Paint paint1 = mPaint;
			int count1 = getLineCount();		
			
			
			float tsize1 = getTextSize();
			mPaint.setTextSize(tsize1);  // ++ 40, 50
			mPaint.setColor(0xFF2F9FFF); // Blue...
            
		 
			for (int i = 0; i < count1; i++) 
			{
				canvas.drawText("" + (i+1), r1.left, baseline1, paint1); // O.K !!!
        
				baseline1 += getLineHeight();
						
			}
			
	    setPadding((int)tsize1 * 3 + 10, getPaddingTop(),				 
	    getPaddingRight(), getPaddingBottom());
	
		}  // linenum
		
	// 20221103Th1142P, 20221104Fr1237A, ++ iian.	
	// Line num On-Off & goto Line Num ... Menu+ need...
    /////////////////////////////////////////////////////
			if (mLinesSetting) 
			{
				Rect r = mRect;
				Paint paint = mPaint;
				int count = getLineCount();		// Draw the lines under each line of text

				for (int i = 0; i < count; i++) 
				{
                    
					int baseline = getLineBounds(i, r);
					canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
				}
			}  // mLinesSetting

			
			sHeight = getHeight();			// This is where we get the screen height
			
			super.onDraw(canvas);
			

    
		}  // onDraw
		
		
		
		// ********************** Methods for scrolling ***********************

		public void InitScroller(Context context) {
			mScroller = new Scroller(context);					// Get a scroller object
			mScrollY = 0;										// Set beginning of program as top of screen.
    		mMinScroll = 1;			// Set minimum scroll distance
			mFlingV = 750;		// Minimum fling velocity
		}
		
					
		
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			super.onTouchEvent(event);

			if (mVelocityTracker == null) {						// If we do not have velocity tracker
				mVelocityTracker = VelocityTracker.obtain();	// then get one
			}
			mVelocityTracker.addMovement(event);				// add this movement to it

			final int action = event.getAction();				// Get action type
			final float y = event.getY();				// Get the displacement for the action

			switch (action) 
			{

				case MotionEvent.ACTION_DOWN:					// User has touched screen
					if (!mScroller.isFinished()) 
					{				// If scrolling, then stop now
						mScroller.abortAnimation();
					}
					mLastMotionY = y;							// Save start (or end) of motion
					mScrollY = this.getScrollY();				// Save where we ended up
					mText.setCursorVisible(true);
					didMove = false;
				break;

				case MotionEvent.ACTION_MOVE: // The user finger is on the move			
				if(event.getPointerCount() == 2 && Zoom_Edit.equals("1")) 
										
					{ 
						double nowpos_X = Math.abs(event.getX(0) - event.getX(1));
						double nowpos_Y = Math.abs(event.getY(0) - event.getY(1));

						if(touchpos_X < nowpos_X && touchpos_Y < nowpos_Y) 
						{ 
						 				
							TextSz20 ++;
                            
                            delayed02(300);
                           
							if (TextSz20 >=28)
							{
								TextSz20= 28;
							}
							setTextSize(1, TextSz20);
							
						}  // 2.

						if(touchpos_X > nowpos_X && touchpos_Y > nowpos_Y) 
						{  
						 				 
							TextSz20 --;
                            
                            delayed02(300);

							if (TextSz20 <= 10)
							{
								TextSz20= 10;
							}
							setTextSize(1, TextSz20);		
							
						} // 3.
						touchpos_X = Math.abs(event.getX(0) - event.getX(1));
						touchpos_Y = Math.abs(event.getY(0) - event.getY(1));

					break;	
			 	} // 1.
					
								
					didMove = true;
					final int deltaY = (int) (mLastMotionY - y);	// Calculate distance moved since last report
					mLastMotionY = y;								// Save the start of this motion

					if (deltaY < 0) 
					{							// If user is moving finger up screen
						if (mScrollY > 0) 
						{						// and we are not at top of text
							int m = mScrollY - mMinScroll;		// Do not go beyond top of text
							if (m < 0) 
							{
								m = mScrollY;
							} else m = mMinScroll;

							scrollBy(0, -m);					// Scroll the text up
						}
					} 
					else
					if (deltaY > 0) 
					{							// The user finger is moving up
						int max = getLineCount() * getLineHeight() - sHeight;	// Set max up value
						if (mScrollY < max - mMinScroll) 
						{
							scrollBy(0, mMinScroll);			// Scroll up
						}
					}
					break;
					

				case MotionEvent.ACTION_UP:						// User finger lifted up
					final VelocityTracker velocityTracker = mVelocityTracker;		// Find out how fast the finger was moving
					velocityTracker.computeCurrentVelocity(mFlingV);
					int velocityY = (int) velocityTracker.getYVelocity();

					if (Math.abs(velocityY) > mFlingV) {							// if the velocity exceeds threshold
						int maxY = getLineCount() * getLineHeight() - sHeight;		// calculate maximum Y movement
						mScroller.fling(0, mScrollY, 0, -velocityY, 0, 0, 0, maxY);	// Do the filng
						
	
						
					} else
					{
						if (mVelocityTracker != null)
					   {								// If the velocity less than threshold
							mVelocityTracker.recycle();								// recycle the tracker
							mVelocityTracker = null;
						}
					}
					break;
			}

			mScrollY = this.getScrollY();			// Save where we ended up
			return true ;										// Tell caller we handled the move event
		}

		@Override
		public void computeScroll() {					// Called while flinging to execute a fling step
			if (mScroller.computeScrollOffset()) {
				mScrollY = mScroller.getCurrY();		// Get where we should scroll to
				scrollTo(0, mScrollY);					// and do it
				postInvalidate();				// the redraw the sreem
			}
		}

		private void getPreferences(Context context) 
		{
            Basic.TextStyle style = Basic.defaultTextStyle;
			mText.setTextColor(style.mTextColor);
		
			int Bcol= style.mBackgroundColor | 0xC0000000;
			mText.setBackgroundColor( Bcol );
			
			
			mText.setHighlightColor(style.mHighlightColor);
			mPaint.setColor(style.mLineColor);
			
			setTextSize(1, Settings1.getFont(context));
			mLinesSetting = Settings1.getLinedEditor(context);
			mLineWrapSetting = Settings1.getEditorLineWrap(context);
			
			if (TextSz20bk == 1) // ++ iian.
			{
			setTextSize(1, TextSz20);
				TextSz20bk = 0;
			}	
		}
		
	} // 
	
	
    static void delayed02(long dlytime)
    {
        try
        {
            Thread.sleep(dlytime);
        } 
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
	} /// delayed02 
    
    
	
	// ************************ End of LinedEdit Class ************************

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOGTAG, "onCreate");
		
		
		super.onCreate(savedInstanceState);			// Setup and the display the text to be edited

		
		mSavedInstanceState = savedInstanceState;				// preserve for onResume
		Intent intent = getIntent();
		if (savedInstanceState == null) {		// if no state from system
		
mSavedInstanceState = intent.getBundleExtra(EXTRA_RESTART); // look for state from Basic Activity
		
		}
		
		Run.Exit = false; 										// Clear this in case it was set last time BASIC! exited.
		
		/*
		 * Open up the view.
		 * 
		 * The LinedEdit Class code will get control at this point and draw the blank screen.
		 * 
		 * When that is done, the rest of the code here will be execute.
		 */
		setContentView(R.layout.editor_b);

mHomePath = Basic.getFilePath();
		mBasePathAndSep = new File(mHomePath).getParent() + SEPARATOR;
		mAltHomePath = mAltBasePathAndSep = null;

		File base = new File(mBasePathAndSep);
		File sdBase = new File(SEPARATOR + "sdcard");
		try { sdBase = sdBase.getCanonicalFile(); }
		catch (IOException ex) { }
		if (sdBase.getName().equals("legacy")) {				// if /sdcard points to legacy
			base = new File(base.getParent(), "legacy");		// replace base leaf with "legacy"
			if (sdBase.getPath().equals(base.getPath()) &&
					base.exists() && sdBase.exists()) {
				// We're as sure as we can be that base and sdBase are the same.
				mAltBasePathAndSep = sdBase.getPath() + SEPARATOR;
mAltHomePath = new File(sdBase, Basic.AppPath).getPath();
			}
		}

		String initLoadPath = intent.getStringExtra(EXTRA_LOADPATH);
		if (initLoadPath == null) { initLoadPath = ""; }
		// These two fields may be overwritten from mSavedInstanceState in onResume().
ProgramPath = Basic.getSourcePath(initLoadPath);
		ProgramFileName = "";

		mText = (LinedEditText)findViewById(R.id.basic_text);	// mText is the TextView Object
		mText.setTypeface(Typeface.MONOSPACE);

		InputFilter[] filters = mText.getFilters();				// some devices (Samsung) have a filter that limits EditText size
		if (filters.length != 0) {
			mText.setFilters(new InputFilter[0]);				// if there are any filters, remove them
		}
       
		mText.setMinLines(4096);
		mText.setCursorVisible(true);
		mText.setText(DisplayText);		// Put the text lines into Object
		InitialProgramSize = DisplayText.length();
		Saved = true;
		String SetZm = mHomePath+"/Set/SetZm.dat"; 		
		File SetZm1 = new File (SetZm); // "/Set/SetZm.dat"
		if(!SetZm1.exists()) // 처음 시작 때는, 파일이 없음~ !!!
		{
            /// +++
            String Rwdir= mHomePath+"/Set";
            File fm = new File(Rwdir);

            if(!fm.exists())
            {
                fm.mkdirs();

  /// +++ end...
  /// 20240421Su1212A, iian.
  
            }
                       
			Fwritei(SetZm,TextSz20);
            
		}
		else
		{
		 
		TextSz20=  Fread(SetZm);  // O.K!!!
		}

		if (Exitfl == 1)  
		{
	 	}	
		
		
		
	} // onCr
	

   public static void ExitEditor()  // for Chk.

   { 
	 android.os.Process.killProcess(android.os.Process.myPid());
   }
	


	
	////////////////////////////////////////////////////////
	//  Start •••
	//  Snippet Add. 20220714Th1130A, by iian.
	//
	////////////////////////////////////////////////////////
	
	
	public void  Fread4w(String Setd, String SetEd )
    {

		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(SetEd));

			String dstr = "";		
		
			Board_Disp = dbr.readLine();
			Disp_Pic   = dbr.readLine();
			Zoom_Edit  = dbr.readLine();
			     dstr  = dbr.readLine();		 		
			dbr.close();
		
			if (dstr != null)
			{
				Zoom_Sz = Integer.valueOf(dstr); // 1 or 0
			}
			else
			{
				Zoom_Sz = 0; // Restart to init 17 size.
			}
			
			int dnum1 = Integer.valueOf(Disp_Pic);
			    dnum1 ++;
				
				if (dnum1 > 9)
				{
					dnum1 = 0;			
				}		
				
				
			String dnums = String.valueOf(dnum1);
			dnums = "0"+ dnums;			
			
	String		BBpathp = mHomePath+"/Set/BBD"+ dnums+ ".png";
	String		BBpathj = mHomePath+"/Set/BBD"+ dnums+ ".jpg";	
	
			File bbfilej = new File (BBpathj); // "/Set/BBD00.jpg"
			if(!bbfilej.exists()) // mHomePath+"/Set/BBD00.jpg"
			{
				File bbfilep = new File (BBpathp); // "/Set/BBD00.png"
				if(!bbfilep.exists()) // mHomePath+"/Set/BBD00.png"
				{
					
					dnums= "00";
					
				}
				
			}	
			
			Fwritedir4(Setd , SetEd, dnums);
			

		}
		catch (FileNotFoundException e)
		{
			Toast.makeText(this, " • File Not found! • \n" + SetEd, Toast.LENGTH_LONG).show();
		}
		catch (IOException e)
		{
			Toast.makeText(this, " • File Read Err! • \n" + SetEd, Toast.LENGTH_SHORT).show();
		}

	}  // Fread4w
	
	

	private int  Fread(String fpath)
    {
		   int idat1= 0;
		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));

			String dstr = "";		
			dstr = dbr.readLine();
			dbr.close();
			
			if (dstr != null)
			{		
			idat1 = Integer.valueOf(dstr);
			}
			else
			{
			  idat1 = 17;  // init value.

		// 20220716Sa1242A, iian.
				
			}

		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}

		return idat1;
	}  // Fread

	
	public  void Fwritedir4(String dir, String finame, String dnum)
	{

		File dirs = new File (dir);
		if(!dirs.exists())
		{
			dirs.mkdir();
		}

		try
		{		
	
			BufferedWriter dbw = new BufferedWriter(new FileWriter(finame, false));	
			dbw.write( Board_Disp+ "\n"); // "1\n"); // BackBoard Disp. on : 0 : off.
			if (dnum.equals("") || Board_Disp.equals("0")) 
			   dbw.write( "00\n"); // "/Set/BBD00.jpg" or png.
			else
			   dbw.write( dnum+ "\n"); // ++ Disp num.
			
			dbw.write(Zoom_Edit+"\n"); //  "1\n"); // Editor Zoom On. : 0 : Zoom Off.	
			dbw.write(Zoom_Sz+ "\n");
			dbw.write("\n\n");
			
		dbw.write("% 1st: Back ground picture display, 1: On, 0: Off.\n");
		dbw.write("% 2nd: Display picture file number to next time (00~09 auto).\n");
		dbw.write("% 3rd: Zoom up/down, switch to editor, 1: On, 0: Off.\n");
		dbw.write("% 4th: View size, 1: Set last time, 0: Set init size 17.\n");
		
        dbw.write("%\n% If you don't need a background picture and zoom function,");
        dbw.write("\n% the contents of the ../Set/SetEd.dat file above,");
        dbw.write("\n\n0\n00\n0\n1\n\n");
        dbw.write("% Writing with, will stop all extension functions,");
        dbw.write("\n% in ../Set/SetZm.dat file, display text size,");
        dbw.write("\n% You can use  directly by specifying it (without background, zoom).\n");
	
		dbw.write("%\n% Have a good time~ 20220717Su1113A, iian.\n");
		
		// Korean.
		dbw.write("%\n%\n% 지정 내용, 한글 설명. (Korean letter).\n%\n");
		
		dbw.write("% 1번: 배경 화면 표시 여부, 1: 켬, 0: 끔.\n");
		dbw.write("% 2번: 다음 번에 표시될 배경 화면 번호. (00~09 자동 반복 됨).\n");
		dbw.write("% 3번: 에디터의 확대/축소, 줌 작동. 1: 켬, 0: 끔.\n");
		dbw.write("% 4번: 시작 때 글자 크기, 1: 마지막 크기 유지, 0: 크기 17 로, 초기화.\n");
		
	    dbw.write("%\n% 만약, 배경 사진과, 줌 기능이 필요 없을 때는,");
		dbw.write("\n% 위의 ../Set/SetEd.dat 파일의 내용을,");
		dbw.write("\n\n0\n00\n0\n1\n\n");
		dbw.write("% 로 기록 하면, 확장 기능 작동이 모두 중단 되므로,");
		dbw.write("\n% ../Set/SetZm.dat 파일에서, 표시 글자 크기를,");
		dbw.write("\n% 직접 지정해서, (배경, 줌 없이) 사용 할수도 있음.");

		dbw.write("\n%\n% 좋은 시간 되시길 바랍니다~ 220717Su1113A, 이안 iian\n");
			
			
			dbw.close();

		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + dirs + finame, Toast.LENGTH_LONG).show();
		}

	}  // Fwritedir4
	
	
	

	public  void Fwritedir(String dir, String finame, String str)
	{

		File dirs = new File (dir);
		if(!dirs.exists())
		{
			dirs.mkdir();
		}

		try
		{		

			BufferedWriter dbw = new BufferedWriter(new FileWriter(finame, false));	
			dbw.write(str+ "\n");
			dbw.close();

		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + dirs + finame, Toast.LENGTH_LONG).show();
		}

	}  // Fwritedir


	// public  void Fwrite(String fpath, String str)
	public  void Fwritei(String fpath, int ival)
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(fpath, false));
			dbw.write(ival+ "\n");
			dbw.close();
		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + fpath, Toast.LENGTH_LONG).show();
		}

	}  // Fwrite 


	public  void Syncwrite(String fpath, String str)
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(fpath, false));
			dbw.write(str+ "\n");
			dbw.close();
		}
		catch (Exception e)
		{
			Toast.makeText(this, " • File Write Err! • \n" + fpath, Toast.LENGTH_LONG).show();
		}

	}  // Syncwrite 

	
	////////////////////////////////////////////////////////
	//  End •••
	//  Snippet Add. 20220714Th1130A, by iian.
	//
	////////////////////////////////////////////////////////
	


	long ktime1, ktime2;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ktime2 = System.currentTimeMillis();
			if(ktime2 - ktime1 < 2000)
			{
				finish();
			}
			else
			if (ktime2 - ktime1 > 3000)
			{

		Toast.makeText(this, "◀ ◀   EXIT",Toast.LENGTH_SHORT).show();			
		// 20221117Th0920A, iian.		
			}

		    ktime1 = System.currentTimeMillis();
			return true;									// Do not allow backing out of Editor
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return true;									// Do not allow backing out of Editor
		}

		// Do on the fly formatting upon ENTER
		if (!Settings1.getAutoIndent(this)) return false;	// Don't do the formatting if the user does not want it

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getRepeatCount() == 0) {
            int selection = mText.getSelectionEnd();								// Split the text into two parts
            String theText = mText.getText().toString();							// The text before the ENTER
            String fText = "";														// and the Text after the ENTER
            if (selection > 1)
            	fText = theText.substring(0, selection - 1);
            String eText = "";
            eText = theText.substring(selection);

            int lineStart = 0;														// Backtrack over the before text
            if (selection - 2 > 0) {												// to find the start of the last
            	for (lineStart = selection - 2; lineStart > 0 ; --lineStart) {		// before ENTER line
            		char c = theText.charAt(lineStart);
            		if (c == '\n') break;
            	}
            	if (lineStart > 0) ++lineStart;
            } else lineStart = 0;


            String blanks = "";														// Now, count the leading blanks
            for (int i = lineStart; i < selection - 1; ++i) {						// in the last before ENTER line
            	char c = fText.charAt(i);
            	if (c != ' ')
            		if (c != '\t') break;
            	blanks = blanks + " ";
            }

            if (fText.endsWith("#")) {												// If formatting of line was wanted
            	String theLine = fText.substring(lineStart, fText.length() - 1);	// go format the line
            	String newLine = Format.ProcessKeyWords(theLine);
            	String aLine = fText.substring(0, lineStart);
            	fText = aLine + newLine;
            }

            theText = fText + "\n" + blanks + eText;								// Put together the final text
            mText.setText(theText);													// and set the selection after the blanks
            mText.setSelection(fText.length() + 1 + blanks.length(), fText.length() + 1 + blanks.length());
        	return true;
        }


		return super.onKeyUp(keyCode, event);
	} // onKeyUp


	
	@Override
	   protected void onRestart()
    {	
		if (Exitfl == 1)
		{
		}		
	    	super.onRestart();
	}
	
	@Override
	protected void onPause()
    {	
		if (Exitfl == 1)
		{
		}		
		super.onPause();
	}
	
	  public void ExitEdit1()  
   // for Chk. No Use, 20221120Su0314P, iian.
	{
		   onStop();
	}
	
	
	
	@Override
     protected void onStop()
    {	
		if (Exitfl == 1)
		{
		}		
	       super.onStop();
	}
	
	void delayedBk(long dlytime)  // ++ iian : 220725Mo0449P
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

	}
	
	
	
	@Override
	protected void onResume() 
	{
		
		if (Exitfl == 1)
		{

		}
		
		
		
		
		if (gotofl == 1)
		{
            String intpath =  Basic.getFilePath();
			String ifpath = intpath+ "/Java/COMM/data/Edit1.dat";
			String ifopath= intpath+"/Java/COMM/data";
			String ifiname = ifopath+ "/Edit1sel.dat";
			
			gotofl = 0; // 221116We0323A, ++ iian.
			
			int kin= 	Fread(ifiname);  // Edit1sel.dat
			if (kin == 1)  // goto Btn.
			{
				gotolnum = 	Fread(ifpath);  // Edit1.dat	


				if (gotolnum > mxfln || gotolnum == 0)
				{
					gotolnum = mxfln;
					// 20221107Mo0421A, ++ iian.
				}


				int goln1 =  Linenum[gotolnum];        // 25* gotolnum;
				int maxpos = mText.length();
				if (goln1 >= maxpos)
					goln1 = maxpos;


				mText.setSelection(goln1);

			}	// goto Btn.		  				
			
		}  // gotofl == 1
		
		
		String SetZm = mHomePath+"/Set/SetZm.dat";  	
		
		if (Zoom_Sz != 1 && TextSz20bk == 0 )  // O.K!!!
		{	
			TextSz20 = 17;
		}
        if (Zoom_Edit.equals("1"))
		    Fwritei(SetZm,TextSz20);  // init value...
		
		super.onResume();
		
	//	String SetZm = mHomePath+"/Set/SetZm.dat";  // ++ iian.
	// 20220716Sa0139A, iian.

     if (Basic.getContextManager() == null) 
	  {				// if we have lost context then restart Basic Activity
			Log.e(LOGTAG, "onCreate: lost Context. Restarting BASIC!.");
            Intent intent = new Intent(getApplicationContext(), Basic.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       if (mSavedInstanceState != null) 
	      {					// send saved state so Basic can send it back
				mSavedInstanceState.putString(STATE_MTEXT_DATA, mText.getText().toString());
				intent.putExtra(EXTRA_RESTART, mSavedInstanceState);
			}
			startActivity(intent);				
		 
			finish();
			return;
		}

		if (mSavedInstanceState != null) {
			Log.d(LOGTAG, "onResume: found savedInstanceState");
			ProgramPath = mSavedInstanceState.getString(STATE_PROGRAM_PATH);
Run.running_bas = Basic.getRelativePath(ProgramPath, Basic.getSourcePath(null));
			ProgramFileName = mSavedInstanceState.getString(STATE_PROGRAM_FILE_NAME);
			String text = mSavedInstanceState.getString(STATE_MTEXT_DATA);
			if (text != null) { mText.setText(text); }
			InitialProgramSize = mSavedInstanceState.getInt(STATE_INITIAL_SIZE);
			SyntaxErrorDisplacement = mSavedInstanceState.getInt(STATE_ERROR_DISPLACEMENT);
			Saved = mSavedInstanceState.getBoolean(STATE_SAVED);
			mSavedInstanceState = null;
			
		}

		if (Settings1.changeBaseDrive) {
			doBaseDriveChange();
		}

		if (Run.Exit) {		// Somebody told Run to exit, so exit Editor, too.

			
			finish();		// Do not clear Exit here; it might still be seen by another Activity
			return;			// Instead, clear it in onCreate() the next time the Editor starts
		}

            if (Basic.DoAutoRun) {
			Log.e(LOGTAG, "onResume: AutoRun is set. Shutting down.");
			

			finish();  // 1st.
			
		} else {
            
      ///////////////////////////////////////////       
            
			setTitle(ProgramFileName);
			menuItemsToActionBar(mMenu);

			mText.getPreferences(this);

     ///  Loading Prg ...
      mBaseline++;
     if (mBaseline >= 3)  
        mBaseline = 0;
     
    String s0 = String.valueOf(mBaseline);
    int sl1 = s0.length();
    
    if (sl1 >= 1)
    {
    String s1=  s0.substring(sl1-1,sl1);
          ///  .
            if (s1.equals("0"))
            {
            }
            else
            if (s1.equals("1"))
            {
           }
            else
            {
                mBaseline = 0;  /// dumy...
                
            }
          
            
      }  ///         
            
    ////////////////////////////////////////////////
    
			int SO = Settings1.getSreenOrientation(this);
			setRequestedOrientation(SO);
			mText.setHorizontallyScrolling(!mText.mLineWrapSetting);		
            // set scrolling per Preferences

            
            
			if (SyntaxErrorDisplacement >= 0 &&
				SyntaxErrorDisplacement < AddProgramLine.lineCharCounts.size()) 
            {	// If run ended in error, select error line

				int end = AddProgramLine.lineCharCounts.get(SyntaxErrorDisplacement);  
                // Get selection end
				if (end >= DisplayText.length()) end = DisplayText.length();
				int start = end - 1;										
                // back up over the new line

				for (start = end - 1; start > 0 ; --start) 
                {				// Scan for previous nl or start
					char c = DisplayText.charAt(start);
					if (c == '\n') {
						start = start + 1;
						break;
					}
				}

				if (start >= 0 && end >= 0 && start <= end &&				
                // make sure values are not crash bait
					end <= mText.length()) {								
                // Note: if RUN command, DisplayText does not match mText. TODO: FIX THIS?
					mText.setSelection(start, end);							
                // Set the selection
				}
				mText.setCursorVisible(true);
				SyntaxErrorDisplacement = -1;								
                // Reset the value
			}
		}
        
        
     getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);     
           
		
	} // onResume
    
    
  
    

	@Override
	public void setTitle(CharSequence programName) {
		CharSequence title = getString(R.string.editor_name) + " - " +
							 (((programName != null) && !programName.equals(""))
									 ? programName : getString(R.string.unnamed_program));
		super.setTitle(title);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) 
	{
		savedInstanceState.putString(STATE_PROGRAM_PATH, ProgramPath);
		savedInstanceState.putString(STATE_PROGRAM_FILE_NAME, ProgramFileName);
		savedInstanceState.putInt(STATE_INITIAL_SIZE, InitialProgramSize);
		savedInstanceState.putBoolean(STATE_SAVED, Saved);
		savedInstanceState.putInt(STATE_ERROR_DISPLACEMENT, SyntaxErrorDisplacement);
		
        String SetZm = mHomePath+"/Set/SetZm.dat";  
		if (Zoom_Edit.equals("1"))
	     Fwritei(SetZm,TextSz20);  // ++ iian.
		 
		 TextSz20bk = 1;  // 화면 전환 때만 setting...
		
		
		super.onSaveInstanceState(savedInstanceState);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// Returning from LoadFile Activity for LOAD_RUN menu selection.
		if (requestCode == LOAD_FILE_INTENT) {
			if (resultCode == RESULT_OK) { Run(); }		// user loaded a program; run it
		}
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void menuItemsToActionBar(Menu menu) {
		if (menu == null) return;
		if (Build.VERSION.SDK_INT < 11) return;				// no action needed

		if (Settings1.menuItemsToActionBar(this, menu)) {
			invalidateOptionsMenu();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {			// When the user presses Menu
		super.onCreateOptionsMenu(menu);					// set up and display the Menu
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.editor, menu);
		mMenu = menu;
		Settings1.menuItemsToActionBar(this, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{	// A menu item has been selected
		switch (item.getItemId())
		{

            case R.id.pathrun:  // point path to load & Run.



                // 20230816We1010P, Home Menu ++ iian.
                Intent intenthome = new Intent(this, aBTbHOME.class);   

                startActivity(intenthome);          

                return true;
				
				
				
				
	////////////////			
				
			case R.id.run:									// RUN
				if (Saved) {								// If current program has been saved
					Run();									// then run the program
				} else {
					doSaveDialog(Action.RUN);				// Ask if the user wants to save before running
				}
				return true;

			case R.id.load:									// LOAD
				if (Saved) {								// If current program has been saved
					loadFile(false);						// then load the program, but don't run it
				} else {
					doSaveDialog(Action.LOAD);				// Ask if the user wants to save before loading
				}
				return true;

			case R.id.save:									// SAVE
				askNameSaveFile(Action.NONE);				// Just do it; no action needed after Save
				return true;

				
			case R.id.goto1:				
		String fpath = 	Editor_b.ProgramPath+"/"+Editor_b.ProgramFileName;
		
		   mxfln= FreadFgo(fpath);  				
		// O.K!, 20221116We0316A, ++ iian.			
		
				gotolnum = 1010;  // O.K !!!
		// 20221107Mo0338A, Chk O.K!, ++ iian.
		
		        gotofl = 1;   // for Resume, 221116We0322A, ++ iian.
		
		Intent intent = new Intent(this, Edit1.class); 
		startActivity(intent);			// to the Edit1
		// 20221106We0139A, iian.
			
		return(true);
				
			case R.id.clear:								// CLEAR
				if (Saved) {								// If program has been saved
					clearProgram();							// then clear the Editor
				} else {
					doSaveDialog(Action.CLEAR);				// Ask if the user wants to save before clearing
				}
				return(true);
                
			case R.id.search:								// SEARCH
				if (mText == null) {
					throw new RuntimeException("Editor: attempt to Search with null mText");
				}
				DisplayText = mText.getText().toString();
				selectionStart = mText.getSelectionStart();
				selectionEnd = mText.getSelectionEnd();
				startActivity(new Intent(this, Search.class));	// Start the search activity
				return true;

			case R.id.load_run:								// LOAD and RUN
				if (Saved) {								// If program has been saved
					loadFile(true);							// then load the program, and run it
				} else {
					doSaveDialog(Action.LOAD_RUN);			// Ask if the user wants to save before clearing
				}
				return true;

			case R.id.save_run:								// SAVE and RUN
				if (Saved) {
					Run();									// no change, just run the program
				} else if (ProgramFileName.equals("")) {	// if no file name...
					askNameSaveFile(Action.RUN);			// ... get a name, save the program and run it
				} else {									// else have a file name
					writeTheFile(ProgramFileName);			// save the program, overwriting existing file
					Run();									// run the program
				}
				return true;


			case R.id.delete:								// DELETE
				DisplayText = mText.getText().toString();	// get the text being displayed

				// First make sure that the SD Card is present and can be written to

				// if the SD Card is not available or writable pop some toast and do
				// not call Delete
	
				startActivity(new Intent(this, Delete.class));	// Go to Delete Activity
				return true;
				
			case R.id.settings:								// Settings1
				startActivity(new Intent(this, Settings1.class));// Start the Setting activity
				return true;
                
			case R.id.help:									// COMMANDS
				startActivity(new Intent(this, Help.class));	// Start the Help activity
				return true;
                
			case R.id.about:								// ABOUT
				startActivity(new Intent(this, About_b.class));	// Start the About activity
				return true;

				
			case R.id.exit:									// EXIT

				String SetZm = mHomePath+"/Set/SetZm.dat";  	
				if (Zoom_Sz != 1)
				{	
					TextSz20 = 17;
				}
				if (Zoom_Edit.equals("1"))
				  Fwritei(SetZm,TextSz20);  // init value...
						
				if (Saved) 
				{								// If program has been saved
					finish();								// exit immediately
				} 
				else 			
				{
					doSaveDialog(Action.EXIT);				// Ask if the user wants to save before exiting
				}
				return true;

			default:
				return true;
		}
	}

	void delayed(long dlytime)  // ++ iian : 220725Mo0449P
	{
		
		try
		{
			Thread.sleep(dlytime);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
			e.toString();
		}
	
	}
	
	
	private int  FreadFgo(String fpath)
	{
		String str= ""; // null;
		int lcnt = 0;
		int lnum = 1;  //  0;  //  1; 1: O.K! 221106Su0203A, iian.
		int lnum0 = 1;
		String Lnum = "";

		int lnlen = 0;

		Arrays.fill(Linenum,0);
		// 20221106Su0839A, ++ iian.


		try
		{
			BufferedReader dbr = new BufferedReader(new FileReader(fpath));
			String dstr = "";

			Lnum= String.valueOf(lnum)+"\n";

			while (((dstr = dbr.readLine()) != null))
			{	
				lcnt ++;
				lnum ++;
				lnum0 = lnum;
				if (lnum0 >= 100000)
					lnum0 =  100000 -1;

				lnlen = lnlen + dstr.length()+1;

				Linenum[lnum0] = lnlen;
				// 20221106Su0739A, ++ iian.

				if (lcnt <= 40) // Fixed Bug. O.K! 221106Su0217A, iian.
				// 50: 25 Sec., 40:22~ 24 Sec. : 700KB File.
				// 20221031Mo0455P, ++ iian.
				{
					str = str+ dstr+"\n";
					Lnum= Lnum+ String.valueOf(lnum)+"\n";
				}
				if (lcnt == 40)
				{
					str = "";
					Lnum= "";

					lcnt = 0;
				}


			} // while
			dbr.close();
			if (lcnt < 40)
			{
			}

		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
			
			Toast.makeText(this, " • File Read Err! • \n" + fpath, Toast.LENGTH_SHORT).show();
			
		}
		return lnum0;   // str;
	}  // freadF

	
	
	private void doSaveDialog(final Action afterSave) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(getString(R.string.Current_Program_Not_Saved))
			.setCancelable(true)										// Allow user to BACK key out of the dialog

			.setPositiveButton(getString(R.string.save),
							   new DialogInterface.OnClickListener() {	// User says to save first
				public void onClick(DialogInterface dialog, int id) {
					askNameSaveFile(afterSave);							// Tell the saver what to do after the save is done
				}
			})

			.setNegativeButton(getString(R.string.Continue),
							   new DialogInterface.OnClickListener() {	// User says Do not save
				public void onClick(DialogInterface dialog, int id) {
					doAfterSave(afterSave);								// Finish what the Save dialog interrupted
				}
			})

			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface arg0) {			// User has canceled save
					return;												// done
				}
			});

		alert.show();
 	}


	private void doFormatDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(getString(R.string.Format_your_program))
			.setCancelable(true)

			.setPositiveButton(getString(R.string.format),
							   new DialogInterface.OnClickListener() {	// User says to do the format
				public void onClick(DialogInterface dialog, int id) {
					DisplayText = mText.getText().toString();
					startActivity(new Intent(Editor_b.this, Format.class));			// Start the format activity
					Saved = false;
				}
			})

			.setNegativeButton(getString(R.string.cancel_button_label),
							   new DialogInterface.OnClickListener() {	// User says to cancel
				public void onClick(DialogInterface dialog, int id) {
					return;
				}
			})

			.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface arg0) {			// User has canceled format
					return;												// done
				}
			});

		alert.show();
	}

	private void Run() {

		/* Run a program
* Create a new Basic.lines object and then copy
		 * the display text buffer to it.
		 * 
		 * The display buffer is one big string. We need
		 * to step through it looking for \n (newline) characters.
* Each \n marks a new line for Basic.lines.
		 */

		DisplayText = mText.getText().toString();
Basic.loadProgramFromString(DisplayText, null);			// build program in Basic.lines

if (Basic.lines.size() == 0) {							// If the program is empty
Basic.lines.add(new Run.ProgramLine("@@@"));		// add Nothing to run command
		}

		SyntaxErrorDisplacement = -1;
		startActivity(new Intent(this, Run.class));				// now go run the program
	}

	private void loadFile(boolean doRun) 
	{
		Intent intent = new  Intent(this, LoadFile.class);
			// Go to the LoadFile Activity. If doRun, catch returned intent and run the loaded program.
			startActivityForResult(intent, doRun ? LOAD_FILE_INTENT : -1);
	}

	private void clearProgram() {
Basic.clearProgram();									// then do the clear
		ProgramFileName = "";
		setTitle(ProgramFileName);
		Saved = true;
		InitialProgramSize = DisplayText.length();
		mText.setText(DisplayText);
	}

	private void askNameSaveFile(final Action afterSave) 
	{

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);	// get the filename from user
		final EditText input = new EditText(this);
		input.setText(ProgramFileName);										// if the program has a name put it in the dialog box
		alert.setView(input);
		alert.setCancelable(true);											// allow the dialog to be canceled

		String path = getDisplayPath(ProgramPath);							// get the save path to display in the dialog box
if (path.endsWith(Basic.SAMPLES_DIR)) { path = goUp(path); }		// don't offer to save in sample programs directory
		alert.setTitle(getString(R.string.Save_to_FILENAMEARG, path));
		alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface arg0) {					// user has canceled save
				return;														// done
			}
		});

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {// have a filename
			public void onClick(DialogInterface dialog, int whichButton) {
				String theFilename = input.getText().toString().trim();
		// 20221127Su0823A, iian.
		
			
				writeTheFile(theFilename);									// write the program to a file
				
				doAfterSave(afterSave);										// and finish what was interrupted by Save dialog
			}});

		alert.show();
	}

	private ArrayList<String> captureProgram() {
		ArrayList<String> lines = new ArrayList<String>();
		DisplayText = mText.getText().toString();				// get the text being displayed
		String line = "";
		boolean LineAdded = false;
		for (int k = 0; k < DisplayText.length(); ++k) {		// move the display text to a String array
			if (DisplayText.charAt(k) == '\n') {
				lines.add(line);
				line = "";
				LineAdded = true;
			} else {
				line += DisplayText.charAt(k);
				LineAdded = false;
			}
		}
		if (!LineAdded) {										// Special case for line
			lines.add(line);									// without \n
		}
		return lines;
	}

	private void writeTheFile(String fileName) {

		String seps = "^" + File.separatorChar + "+";				// regex for leading slashes
		fileName = fileName.replaceFirst(seps, "");					// remove leading slashes
		String path = "";
		int k = fileName.lastIndexOf(File.separatorChar);			// does name contain a path separator?
		if (k > 0) {												// form is "path/file"
			path = fileName.substring(0, k);						// path is part before last separator
			fileName = fileName.substring(k + 1);					// the filename is the part after the last separator
		}
		if (fileName.length() == 0) {								// if no file name
			fileName = "default.bas";								// use the default
		} 
		else 
		if (!fileName.endsWith(".bas")) 
		{					// if the filename does not
		}															// then add it

		File dir;
		if (path.length() == 0) {									// not changing directory
			path = ProgramPath;										// get absolute path
		} else {													// directory change
			dir = new File(ProgramPath, path);						// add new path element(s) to absolute path
			try { path = dir.getCanonicalPath(); }					// resolve new path
			catch (IOException e) { path = dir.getAbsolutePath(); }	// if error just fix slashes
		}
		dir = new File(path);

		File file = new File(path, fileName);

		// Now dir and file are File objects
		// for Strings path and path/fileName.

		boolean success = false;
		IOException ex = null;
		if (dir.exists() && dir.canWrite()) {						// good to go
			ArrayList<String> lines = captureProgram();				// copy the program to a String array

			FileWriter writer = null;
			try {
				file.createNewFile();
				writer = new FileWriter(file);						// write the program to the file
				for (String line : lines) {
					writer.write(line + '\n');
				}
				success = true;
			} catch (IOException e) {
				ex = e;
			} finally {
				if (writer != null) {
					try { writer.flush(); } catch (IOException e) { ex = e; }
					try { writer.close(); } catch (IOException e) { ex = e; }
				}
			}
		}

		if (success) {
			// Save bas path after making it relative to rfo-basic/source
Run.running_bas = Basic.getRelativePath(file.getPath(), Basic.getSourcePath(null));

			ProgramPath = path;									// record new path
			ProgramFileName = fileName;							// and file name

			String display = getDisplayPath(file.getPath());	// notify the user
Basic.toaster(this, getString(R.string.Saved_FILENAMEARG, display));
			setTitle(ProgramFileName);
			InitialProgramSize = mText.length();				// reset initial program size
			Saved = true;										// indicate the program has been saved
		} else {
			String msg = getString(R.string.File_not_saved) +
					     ((ex == null) ? path : ex.getMessage());
Basic.toaster(this, msg);
		}
	} // writeTheFile

	private void doAfterSave(Action afterSave) {
		switch (afterSave) {
		case NONE:											// No action needed
			break;
		case LOAD:											// if diverted from Doing Load
			loadFile(false);								// then go do load, but don't run
			break;
		case RUN:											// if diverted from Doing Run
			Run();											// then go do run
			break;
		case LOAD_RUN:										// if diverted from Doing LOAD_RUN
			loadFile(true);									// then go do load, then run
			break;
		case CLEAR:											// if diverted from Doing Clear
			clearProgram();									// then go do clear
			break;
		case EXIT:											// if diverted from Doing Exit
			finish();										// then exit
			break;
		}
	}

	private void doBaseDriveChange() {
		Settings1.changeBaseDrive = false;

		String newBaseDrive = Settings1.getBaseDrive(this);

		if (newBaseDrive.equals("none")) return;
if (newBaseDrive.equals(Basic.getBasePath())) return;

		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);		// using a dialog box.

		alt_bld.setMessage("When BASIC! restarts the new Base Drive will be used.\n\n" +
							"Restart BASIC! Now\n" +
							"or Wait and restart BASIC! yourself.")
		.setCancelable(false)												// Do not allow user BACK key out of dialog

		// The click listeners *****

		.setPositiveButton("Restart Now", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
Intent restart = new Intent(getApplicationContext(), Basic.class);
				startActivity(restart);
				finish();
			}
		})

		.setNegativeButton("Wait", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				waitMessage();
			}
		});

		// End of click listeners *****

		AlertDialog alert = alt_bld.create();								// Display the dialog
		alert.setTitle("Base Drive Changed");
		alert.show();
	}

	private void waitMessage() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);		// using a dialog box.

		alt_bld.setMessage("When ready to resart with new base drive:\n\n " +
							"Tap Menu -> Exit and then\n" +
							"Restart BASIC!")
		.setCancelable(false)												// Do not allow user BACK key out of dialog

		// The click listeners ***

		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				return;
			}
		});

		// End of click listeners ***

		AlertDialog alert = alt_bld.create();								// Display the dialog
		alert.setTitle("Restart Later");
		alert.show();
	}

	// *************************** Static utilities for LOAD/SAVE/DELETE **************************

	public static String getDisplayPath(String path) {
		File pathFile = new File(path);
		try { path = pathFile.getCanonicalPath(); }
		catch (IOException ex) { path = pathFile.getAbsolutePath(); }
		// mHomePath is absolute path to "rfo-basic",
		// mAltHomePath is absolute path through "legacy" (if applicable)
		// mBasePathAndSep is its parent plus a trailing "/".
		if (path.startsWith(mHomePath)) {					// if starts with home path
			path = path.substring(mBasePathAndSep.length());	// clip off base path and "/"
		} else if ((mAltHomePath != null) && path.startsWith(mAltHomePath)) {
			path = path.substring(mAltBasePathAndSep.length());	// ditto alt path
		}														// else leave absolute path
		return path;
	}

	public static boolean isMarkedDir(String name) {
		return name.endsWith(DIR_MARK);
	}

	public static String addDirMark(String name) {
		return name + DIR_MARK;
	}

	public static String stripDirMark(String name) {
		int k = name.length() - DIR_MARK.length();
		return name.substring(0, k);
	}

	
	public static String goUp(String path) {					// change path to go up the directory tree
		String path1 = new File(path).getParent();
		if (path1 == null) { path = SEPARATOR; }	// no parent: assume absolute path and set to root
		int le1 = path.length();
		String pa1 = path.substring(le1-6, le1);
		if (pa1.equals("/files"))  /// +++
		  {
			path1 = path;
			
		  }
		return path1;  /// O.K. Limitted...
	/// 20240123Tu1145P, ++ iian.
	}
	//////////////////////////////


	public static String quote(String str) {
		return '\"' + str + '\"';
	}
}
