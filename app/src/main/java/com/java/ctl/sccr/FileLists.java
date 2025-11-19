
package com.java.ctl.sccr;

import android.app.*;
import android.os.*;

import android.view.View;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import java.util.List;
import java.io.*;
import android.widget.*;

import android.view.*;
import java.util.*;
import java.lang.String.*;

import android.view.ViewGroup;

import android.content.Context;
import android.content.Intent;

 public class FileLists extends Activity
 {
    private TextView textView;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> items; 
    private String rootPath = "";
	private String editPath = "";
    private String nextPath = ""; // data/...
    private String prevPath = "";   
    private String currentPath = "";
    private TextView messageView;
	
	 public static String[] fileList;
	/// public static ArrayList<String> fileList = new ArrayList<String>();
	
	ArrayList<String> dirs = new ArrayList<String>();
	ArrayList<String> files = new ArrayList<String>();
	
	ArrayList<String> dirs1 = new ArrayList<String>();
	ArrayList<String> files1 = new ArrayList<String>();
	
	public static int Nestdir = 0;
	
	public String ExtPath= "", fpath = ""; // ++
	public String fopath= "", finame = "", 
	              namepath = "sdcard/Text.txt"; // ++
	/// String editpath = currentPath+"/"+ namepath;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
  //    setContentView(R.layout.activity_main);
		setContentView(R.layout.filelists_main);
        textView = (TextView)findViewById(R.id.tv_schk); //     textView);
        listView = (ListView)findViewById(R.id.LView);   //    listView);

		///////////////////////////////////////
		// Contact Editor ++, 20221127Su0101A, iian.
        
        ExtPath =  Basic.getFilePath();
        
		fpath = ExtPath+"/Java/COMM/data/EditText1.dat";
		fopath= ExtPath+"/Java/COMM/data";
		finame = fopath+ "/EditText1.dat";
		
		
        items = new ArrayList ();
        listAdapter = new ArrayAdapter<String>(this, 
			 R.layout.filelists_textview3, items); 

		editPath = ExtPath;
		
		boolean result = Init(editPath);  // data/...
		// 20230520Sa0446A, 0454A, ++ iian.

        if ( result == false )
			return;

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
				{
					currentPath = textView.getText().toString();
		            String path = "";
					String p1 = items.get(position).toString();
					int lep1 = p1.length();
					if (p1.equals("..") == false)
					{
						if (p1.substring(lep1-3,lep1).equals("(d)"))
				// dirs1.add(dirs.get(i));
				// Dir List Modify, 20221126Sa0553P, ++ iian.
						path = p1.substring(0,lep1-3); 	
						
					    else   // file, name ...
						{
						   path = p1.substring(14,lep1); 
						   namepath = path;
						}
				// 20221126Sa0647P, ++ iian.
					}
					else
					path = p1;  // ".."
					
					if (path.equals("..")) 
					{
				     if (Nestdir > 0)      // = 0;  // root
					// 20221126Sa0859P, ++ iian.			 
						 prevPath(path);
					 else
		Toast.makeText(FileLists.this, "/SDCARD  (No more)", Toast.LENGTH_SHORT).show();
					} 
					else 
					{
						nextPath(path);
					}
					
				} // onItemClick
				
			}); // setOnItemClickListener
			
    } // onCr

    public boolean Init(String rootPath)    
	{
        File fileRoot = new File(rootPath);
        if(fileRoot.isDirectory() == false)       
		{
			listView.setSelection(0);  // ??
            return false;
        }
        textView.setText(rootPath);
		
		Nestdir = 4;  /// 0;  // root
		// 20221126Sa0859P, ++ iian.

		fileList = fileRoot.list();
		
        if ( fileList == null )        
		{
			Toast.makeText(this, "Could not find List" , Toast.LENGTH_SHORT).show();
			listView.setSelection(0);  // ??
            return false;
        }
		
        items.clear();
		dirs.clear();
		files.clear();
		dirs1.clear();
		files1.clear();
		
		
		dirs.add(".."); // root at Down!!!
	// 20221126Sa0837P, iian.
		
        for ( int i = 0; i < fileList.length; i++ )    
		{
		File fl = new File(rootPath+"/"+fileList[i]); /// ++
			
		   if (fl.isDirectory()== true)  ///
		   {
			dirs.add(fileList[i] + "(d)");
		   }
		   else	
		   {
			files.add(fileList[i]);
		   }
        }
		
		Collections.sort(dirs);
		Collections.sort(files);
		
		String sfsz = "";
		
		for ( int i = 0; i < files.size(); i++ )    
		{
			File fl = new File(rootPath+"/"+files.get(i)); /// ++
			
			long fsz = fl.length();
			     sfsz= String.valueOf(fsz);		
	////////////////////
	// 20221126Sa0206A, ++ iian.	
			int fszln = sfsz.length();  // ++
			int i3=0;
			String nfszs, nf1;
			nfszs = ""; nf1="";
			// "123456", 6
			
			
			for (int ii=fszln; ii > 0; ii--)
			{
				i3++;
				nf1= sfsz.substring(ii-1, ii);
				
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
			
			sfsz = nfszs;
			
		int scnt = 14 - sfsz.length();	
			
		////////////////////	
		// 20221126Sa0217A, ++ iian.	
			
			
			
			while (scnt > 0)
			{
				sfsz = sfsz + " ";			
			    scnt --;		
			}
			
			files1.add(sfsz+ files.get(i));
        }
			
		for ( int i = 0; i < dirs.size(); i++ )    
		{
			dirs1.add(dirs.get(i));
	// Dir List Modify, 20221126Sa0536P, ++ iian.
			
			
		}
		items.addAll(dirs1);
		items.addAll(files1);
		
        listAdapter.notifyDataSetChanged();
		listView.setSelection(0);  // ??

	// Good Work but No use, 20221127Su1247A, iian.
        return true;
    }    

    public void nextPath(String str)    
	{
        prevPath = currentPath;

        nextPath = currentPath + "/" + str;
        File file = new File(nextPath);
        if ( file.isDirectory() == false )        
		{
			String editpath = currentPath+"/"+ namepath;
			
	              EditText1.fofl = 0;  // file Read at Edit Resume.
				  // 20221127Su0157P, ++ iian.
	              EditText1.iTfl = 1;
	
			Fwritedir(fopath,finame,editpath);
			
			Intent intent2 = new Intent(this, EditText1.class); 

	// 20221127Su0424A, iian.
   		
			startActivity(intent2);			// to the List // Edit1
				
		  	finish();

			listView.setSelection(0);  // ??
            return;
        }

		Nestdir ++;  // = 0;  // root
		// 20221126Sa0859P, ++ iian.
		fileList = file.list();
        items.clear();
		dirs.clear();
		files.clear();		
		dirs1.clear();
		files1.clear();		
		
		dirs.add("..");

        for ( int i = 0; i < fileList.length; i++ )  
		{
			File fl = new File(nextPath+"/"+fileList[i]); /// ++
			

			if (fl.isDirectory()== true)  ///
			    dirs.add(fileList[i] + "(d)");
			else			
			
			files.add(fileList[i]);
        }
		Collections.sort(dirs);
		Collections.sort(files);
		
		String sfsz = "";

		for ( int i = 0; i < files.size(); i++ )    
		{
			File fl = new File(nextPath+"/"+files.get(i)); /// ++
			long fsz = fl.length();
			sfsz= String.valueOf(fsz);		
            
			////////////////////
			// 20221126Sa0206A, ++ iian.	
			int fszln = sfsz.length();  // ++
			int i3=0;
			String nfszs, nf1;
			nfszs = ""; nf1="";
			// "123456", 6


			for (int ii=fszln; ii > 0; ii--)
			{
				i3++;
				nf1= sfsz.substring(ii-1, ii);
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

			sfsz = nfszs;
			int scnt = 14 - sfsz.length();	

			////////////////////	
			// 20221126Sa0217A, ++ iian.	
				
			

			while (scnt > 0)
			{
				sfsz = sfsz + " ";			
				scnt --;
			}

			files1.add(sfsz+ files.get(i));
       }

		for ( int i = 0; i < dirs.size(); i++ )    
		{
			dirs1.add(dirs.get(i));
		// Dir List Modify, 20221126Sa0536P, ++ iian.
			
		}
			

		items.addAll(dirs1);
		items.addAll(files1);
		
        textView.setText(nextPath);
        listAdapter.notifyDataSetChanged();
		listView.setSelection(0);  // ??

    }

    public void prevPath(String str)    
	{
        nextPath = currentPath;
        prevPath = currentPath;

        int lastSlashPosition = prevPath.lastIndexOf("/");

        prevPath = prevPath.substring(0, lastSlashPosition);
        File file = new File(prevPath);

        if ( file.isDirectory() == false)        
		{
			
			String editpath = currentPath+"/"+ namepath; 
			
			EditText1.fofl = 0;  // file Read at Edit Resume.
			// 20221127Su0157P, ++ iian.
			
			EditText1.iTfl = 1;
			
			Fwritedir(fopath,finame,editpath);
			
			Intent intent2 = new Intent(this, EditText1.class); 
			startActivity(intent2);			// to the List // Edit1		
			
			finish();		
			
            Toast.makeText(this, "Not a Directory" , Toast.LENGTH_SHORT).show();
    		listView.setSelection(0);  // ??
			return;
        }

		Nestdir --;  // = 0;  // root
		// 20221126Sa0859P, ++ iian.
		
		  fileList = file.list();
		
        items.clear();
		dirs.clear();
		files.clear();			
		dirs1.clear();
		files1.clear();			
		
		dirs.add(".."); 

        for( int i = 0; i < fileList.length; i++ )        
		{
			File fl = new File(prevPath+"/"+fileList[i]); /// ++

			if (fl.isDirectory()== true)  ///
			    dirs.add(fileList[i] + "(d)");
			else	
			files.add(fileList[i]);
        }

		Collections.sort(dirs);
		Collections.sort(files);
		
		String sfsz = "";
		for ( int i = 0; i < files.size(); i++ )    
		{
			File fl = new File(prevPath+"/"+files.get(i)); /// ++
			long fsz = fl.length();
			sfsz= String.valueOf(fsz);		
			
			////////////////////
			// 20221126Sa0206A, ++ iian.	
			int fszln = sfsz.length();  // ++
			int i3=0;
			String nfszs, nf1;
			nfszs = ""; nf1="";
			// "123456", 6


			for (int ii=fszln; ii > 0; ii--)
			{
				i3++;
				nf1= sfsz.substring(ii-1, ii);
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

			sfsz = nfszs;
			int scnt = 14 - sfsz.length();	

			////////////////////	
			// 20221126Sa0217A, ++ iian.		
			
			

			while (scnt > 0)
			{
				sfsz = sfsz + " ";			
				scnt --;
			}

			files1.add(sfsz+ files.get(i));
		}

		for ( int i = 0; i < dirs.size(); i++ )    
		{
			dirs1.add(dirs.get(i));
		// Dir List Modify, 20221126Sa0536P, ++ iian.
			
		}
			

		items.addAll(dirs1);
		items.addAll(files1);
		
		
        textView.setText(prevPath);
        listAdapter.notifyDataSetChanged();
		listView.setSelection(0);  // ??
    } 
	
//////////////////////////////////////////////
	

	public  void Fwritedir(String fopath, String finame, String str)
	{

		File fold= new File (fopath);
		if(!fold.exists())
		{
			fold.mkdirs();
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
			String dstr = null;
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
	

	public  void FwriteList2(String Lfpath)
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(Lfpath, false));
			int lenlst = items.size();
			int lpnt = 0;
			String lstr = "";
			while (lenlst >0)
			{
				lstr = items.get(lpnt); // cpos);
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

	}  // FwriteList2
	
	public  void FwriteList(String Lfpath)
	{
		try
		{
			BufferedWriter dbw = new BufferedWriter(new FileWriter(Lfpath, false));
			int lenlst = items.size();
			int lpnt = 0;
			String lstr = "";
			while (lenlst >0)
			{
				lstr = items.get(lpnt); // cpos);
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
	

}  // end
