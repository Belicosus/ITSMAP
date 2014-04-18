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
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.android.demo.notepadv1.R;

public class Notepadv1 extends ListActivity {

    private static final int ADDNOTE_ID = Menu.FIRST;
    private static final int DELETENOTE_ID = Menu.FIRST + 1;

    private int mNoteNumber = 1;
    private NotesDbAdapter mDbHelper;
    private Cursor mNotesCursor;

    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notepad_list);      //Set the view to be used
        mDbHelper = new NotesDbAdapter(this);       //Create instance of adapter to DB
        mDbHelper.open();                           //Open or Create a DB
        fillData();

        registerForContextMenu(getListView());
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETENOTE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case DELETENOTE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteNote(info.id);
                fillData();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void createNote() {
        String noteName = "Note " + mNoteNumber++;
        mDbHelper.createNote(noteName, "body number: " + mNoteNumber);
        fillData();

        Intent i = new Intent(this, NoteEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0)
        {
            Bundle intentExtras = data.getExtras();
            switch(requestCode)
            {
                case ACTIVITY_CREATE:
                    String strTitle = intentExtras.getString(NotesDbAdapter.KEY_TITLE);
                    String strBody = intentExtras.getString(NotesDbAdapter.KEY_BODY);
                    mDbHelper.createNote(strTitle, strBody);

                    break;
                case ACTIVITY_EDIT:
                    Long mRowId = intentExtras.getLong(NotesDbAdapter.KEY_ROWID);
                    if(mRowId != null)
                    {
                        String strRetTitle = intentExtras.getString(NotesDbAdapter.KEY_TITLE);
                        String strRetBody = intentExtras.getString(NotesDbAdapter.KEY_BODY);
                        mDbHelper.updateNote(mRowId, strRetTitle, strRetBody);
                    }
                    break;
            }
            fillData();
        }
    }


    private void fillData() {
        Cursor crsDbCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(crsDbCursor);

        String[] from = new String[] { NotesDbAdapter.KEY_TITLE};       //Where do we want our resources from?
        int[] to = new int[] {R.id.text1};                              //What (in the view) do we want to "bind" our data to

        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.notes_row, crsDbCursor, from, to);
        setListAdapter(notes);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mNotesCursor;
        c.moveToPosition(position);

        Intent i = new Intent(this, NoteEdit.class);
        i.putExtra(NotesDbAdapter.KEY_ROWID, id);

        //Get title from DB:
        int intTitleColumnIndex = c.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE);
        String strTitle = c.getString(intTitleColumnIndex);
        i.putExtra(NotesDbAdapter.KEY_TITLE, strTitle);

        int intBodyColumnIndex = c.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY);
        String strBody = c.getString(intBodyColumnIndex);
        i.putExtra(NotesDbAdapter.KEY_BODY, strBody);

        startActivityForResult(i, ACTIVITY_EDIT);
    }
}
