/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.demo.notepad1;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import com.android.demo.notepadv1.R;

public class Notepadv1 extends ListActivity {

    public static final int ADDNOTE_ID = Menu.FIRST;

    private int mNoteNumber = 1;
    private NotesDbAdapter mDbHelper;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notepad_list);      //Set the view to be used
        mDbHelper = new NotesDbAdapter(this);       //Create instance of adapter to DB
        mDbHelper.open();                           //Open or Create a DB
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean blnReturn = super.onCreateOptionsMenu(menu);
        menu.add(0, ADDNOTE_ID, 0, R.string.menu_insert);
        return blnReturn;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean blnResult = super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case ADDNOTE_ID:
                createNote();
                blnResult = true;
        }
        return blnResult;
    }

    private void createNote() {
        String noteName = "Note " + mNoteNumber++;
        mDbHelper.createNote(noteName, "body number: " + mNoteNumber);
        fillData();
    }

    private void fillData() {
        Cursor crsDbCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(crsDbCursor);

        String[] from = new String[] { NotesDbAdapter.KEY_TITLE};       //Where do we want our resources from?
        int[] to = new int[] {R.id.text1};                              //What (in the view) do we want to "bind" our data to

        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.notes_row, crsDbCursor, from, to);
        setListAdapter(notes);
    }


}
