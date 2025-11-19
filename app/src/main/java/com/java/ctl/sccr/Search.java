
/// Copyright (C) 2010 - 2019 Paul Laughton

package com.java.ctl.sccr;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Search extends Activity {
	private String originalText;               // The original text
	private EditText rText;				// The replace text
    private String replaceText;
    private EditText sText;				// The search text
    private String searchText;
    private Button nextButton;			// The buttons
    private Button doneButton;
    private Button replaceButton;
    private Button replaceAllButton;
    private EditText theTextView;		//The EditText TextView
    private int Index;					//Indexes into the text
    private int nextIndex;

	private boolean mChanged = false;
	private boolean isSearchCaseSensitive = false;		// TODO: provide a control to set case-sensitivity

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)  {
    	// if BACK key restore original text
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
             Editor_b.DisplayText = originalText;
             Editor_b.mText.setText(Editor_b.DisplayText);
             Editor_b.mText.setSelection(Editor_b.selectionStart, Editor_b.selectionEnd);
             finish();
             return true;
        }

        return super.onKeyUp(keyCode, event);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Settings1.getSreenOrientation(this));
		originalText = Editor_b.DisplayText;		// Save in case of BACK Key

		setContentView(R.layout.search);			// Layouts xmls exist for both landscape or portrait modes

		rText = (EditText) findViewById(R.id.replace_text);				// The replace text
		sText = (EditText) findViewById(R.id.search_text);				// The search for text

		nextButton = (Button) findViewById(R.id.next_button);			// The buttons
		replaceButton = (Button) findViewById(R.id.replace_button);
		replaceAllButton = (Button) findViewById(R.id.replace_all_button);
		doneButton = (Button) findViewById(R.id.done_button);

		theTextView = (EditText) findViewById(R.id.the_text);			// The text display area

		InputFilter[] filters = theTextView.getFilters();				// some devices (Samsung) have a filter that limits EditText size
		if (filters.length != 0) {
			theTextView.setFilters(new InputFilter[0]);					// if there are any filters, remove them
		}

		theTextView.setText(Editor_b.DisplayText);						// The Editor_b's display text

		Basic.TextStyle style = Basic.defaultTextStyle;					// Get text color from Settings1
		theTextView.setTextColor(style.mTextColor);
		theTextView.setBackgroundColor(style.mBackgroundColor);
		theTextView.setHighlightColor(style.mHighlightColor);
		rText.setTextColor(style.mTextColor);
		rText.setBackgroundColor(style.mBackgroundColor);
		sText.setTextColor(style.mTextColor);
		sText.setBackgroundColor(style.mBackgroundColor);

		theTextView.setTextSize(1, Settings1.getFont(this));

		// If there is a block of text selected in the Editor_b then make that
		// block of text the search for text

		if (Editor_b.selectionStart != Editor_b.selectionEnd) {
			int s = Editor_b.selectionStart;
			int e = Editor_b.selectionEnd;
			if (e < s) {
				s = e;
				e = Editor_b.selectionStart;
			}
			sText.setText(Editor_b.DisplayText.substring(s, e));
		}

		Index = -1;			// Current Index into text, set for nothing found
		nextIndex = 0;		// next Index

		nextButton.setOnClickListener(new OnClickListener() {			// ***** Next Button ****

			public void onClick(View v) {
				Editor_b.DisplayText = theTextView.getText().toString();	// Grab the text that the user is seeing
																		// She may have edited it
				if (nextIndex < 0) nextIndex = 0;						// If nextIndex <0 then a previous search
																		// search has finished. Start next search
																		// from the start
				if (!doNext()) {										// If this next found something, return
					nextIndex = -1;										// else indicate not found (Index also -1 now)
					String toast = getString(R.string.WHATARG_not_found, searchText);
					Basic.toaster(Search.this, toast);					// and tell the user
				}
				return;
			}
		});

		replaceButton.setOnClickListener(new OnClickListener() {		// ***** Replace Button ****

			public void onClick(View v) {
				if (Index < 0) {										// If nothing has been found
					String toast = getString(R.string.Nothing_found_to_replace);
					Basic.toaster(Search.this, toast);					// tell the user
				} else {
					doReplace();										// else replace what was found
				}
				return;
			}
		});

		replaceAllButton.setOnClickListener(new OnClickListener() {		// ******* Replace All Button *****

			public void onClick(View v) {
				doReplaceAll();
			}
		});

		doneButton.setOnClickListener(new OnClickListener() {			// **** Done Button ****

			public void onClick(View v) {
				Editor_b.DisplayText = theTextView.getText().toString();	// Grab the text that the user is seeing
				Editor_b.mText.setText(Editor_b.DisplayText);				// Set the Editor_b's EditText TextView text
				if (!mChanged) {
					mChanged = !originalText.equals(Editor_b.DisplayText);// She may have edited it
				}
				if (mChanged) {
					Editor_b.Saved = false;
				}
				if (nextIndex < 0 ) nextIndex = 0;						// If nextIndex indicates done, then set to start
				if (Index < 0) {
					Index = Editor_b.selectionStart;						// If Index indicates not found, restore position before search
					nextIndex = Editor_b.selectionEnd;
				} else
				if (nextIndex < Index) {
					int ni = nextIndex;
					nextIndex = Index;
					Index = ni;
				}
				Editor_b.mText.setSelection(Index, nextIndex);			// Set the cursor or selection highlight
				finish();												// Done with this module
				return;
			}
		});

	} // onCreate

	@Override
	protected void onResume() {
		super.onResume();
		sText.requestFocus();
	}

    private boolean doNext(){										// Find the next occurrence of the search for string
 	   Index = nextIndex;											// Position from last search becomes start of this search
	   searchText = sText.getText().toString();						// Get the search text from the dialog box
	   String MirrorText = "" + Editor_b.DisplayText;
		if (!isSearchCaseSensitive) {
			searchText = searchText.toLowerCase();
			MirrorText = MirrorText.toLowerCase();
		}
	   Index = MirrorText.indexOf(searchText, Index);				// Search for the text
	   if (Index < 0) return false;									// If not found, return false
	   nextIndex= Index + searchText.length();						// Set nextIndex to the end of the found text
	   theTextView.setSelection(Index, nextIndex);					// Highlight the selection
	   theTextView.requestFocus();									// Set focus on the text display area
	   return true;

    }
    
    private void doReplace(){										// Replace the text that was found
       if (nextIndex <0 || Index<0) return;							// Make sure we have valid indexes
 	   replaceText = rText.getText().toString();					// Get the replace text from the dialog
	   StringBuilder S = new StringBuilder(Editor_b.DisplayText);		// Schlepp text around to get needed method
	   S.replace(Index, nextIndex, replaceText);					// do the replace
	   mChanged = true;												// mark changed (even if it really didn't)
	   Editor_b.DisplayText = S.toString();							// Schlepp the text back
	   theTextView.setText(Editor_b.DisplayText);						
	   nextIndex= Index + replaceText.length();						// Set nextIndex after the replaced text
	   theTextView.setSelection(Index, nextIndex);					// Show the selection highlighted
	   theTextView.requestFocus();
    	
    }
    
    private void doReplaceAll(){
    	int count = 0;
    	Index = 0;
    	nextIndex = 0;
 	   	searchText = sText.getText().toString();				// Get the search text from the dialog box
 	   	int sl = searchText.length();
 	   	replaceText = rText.getText().toString();				// Get the replace text from the dialog
 	   	int rl = replaceText.length();
		String workingText = ("" + Editor_b.DisplayText);
		String mirrorText = workingText;
		StringBuilder S = new StringBuilder("");				// Workspace for building text with replacement

		if (!isSearchCaseSensitive) {
			searchText = searchText.toLowerCase();
			mirrorText = mirrorText.toLowerCase();
		}

 	   	do{
 	   		nextIndex = Index;									// Save last Index
			Index = mirrorText.indexOf(searchText,Index);		// Search
 	   		if (Index >=0){										// If found
				S.append(workingText.substring(nextIndex, Index)); // copy up to search text
				S.append(replaceText);							// insert replacement text
 	   			count = count + 1;								// Increment count
 	   			Index = Index + sl;								// Set new index
 	   		} 
 	   	}while (Index >= 0);									// Loop until not found

		if (count > 0) { mChanged = true; }						// Mark changed
		else           { rl = 0; }								// else set highlight length 0
		
		Index = S.length();										// End of last thing selected
		S.append(workingText.substring(nextIndex));				// Get the rest of the original string

		nextIndex = Index;										// Location in new string
		Index -= rl;											// of last thing selected

		Basic.toaster(this, count + " items replaced");			// Tell the user how many replaced

		Editor_b.DisplayText = S.toString();						// Schlepp the text back
	    theTextView.setText(Editor_b.DisplayText);
	    theTextView.setSelection(Index, nextIndex);				// Select last thing selected
		theTextView.requestFocus();
    }

}
