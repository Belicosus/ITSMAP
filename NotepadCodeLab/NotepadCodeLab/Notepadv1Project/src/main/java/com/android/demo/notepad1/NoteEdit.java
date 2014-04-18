package com.android.demo.notepad1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.demo.notepadv1.R;

/**
 * Created by Casper on 17-04-2014.
 */
public class NoteEdit extends Activity {

    private EditText mTitleText;
    private EditText mBodyText;
    private Long mCurrentRowid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Deliver View (MVC style)
        setContentView(R.layout.note_edit);

        setTitle(R.string.edit_note);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mCurrentRowid = null;
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            mCurrentRowid = extras.getLong(NotesDbAdapter.KEY_ROWID);
            String TitleText = extras.getString(NotesDbAdapter.KEY_TITLE);
            String BodyText = extras.getString(NotesDbAdapter.KEY_BODY);

            if(TitleText != null)
            {
                mTitleText.setText(TitleText);
            }
            if(BodyText != null)
            {
                mBodyText.setText(BodyText);
            }
        }

        confirmButton.setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View view)
                            {
                                String retTitle = mTitleText.getText().toString();
                                String retBody = mBodyText.getText().toString();

                                Bundle retBundle = new Bundle();

                                retBundle.putString(NotesDbAdapter.KEY_TITLE, retTitle);
                                retBundle.putString(NotesDbAdapter.KEY_BODY, retBody);
                                if(mCurrentRowid != null)
                                {
                                    retBundle.putLong(NotesDbAdapter.KEY_ROWID, mCurrentRowid);
                                }

                                Intent i = new Intent();
                                i.putExtras(retBundle);
                                setResult(RESULT_OK, i);
                                finish();

                            }
                        }
                        );
    }


}
