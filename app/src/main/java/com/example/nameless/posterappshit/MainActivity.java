package com.example.nameless.posterappshit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private DatabaseReference postReference;

    private ViewPager mViewPager;
    private static int position;
    //public TextView capturadora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postReference = LoginActivity.firebaseDatabase.getReference(BDCaminhos.POSTS_ANONIMOS);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Postar Mensagem");

                View customView = getLayoutInflater().inflate(R.layout.dialog_layout, null, false);
                ImageButton accept = customView.findViewById(R.id.accept_dialog_btn);
               // ListView listDialogContact = customView.findViewById(R.id.list_view_contacts_dialog);
                final EditText text = customView.findViewById(R.id.input_text_dialog_post);


                //listDialogContact.setAdapter(PlaceholderFragment.userAdapter);

                alertBuilder.setView(customView);
                final AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Post post = new Post();
                        post.setDate(System.currentTimeMillis());
                        post.setSender(LoginActivity.firebaseUser.getDisplayName());
                        post.setText(text.getEditableText().toString());

                        postReference.child(new Date(post.getDate()).toString()).setValue(post);

                        alertDialog.hide();
                        text.setText("");

                    }
                });


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        private DatabaseReference usersReference, anonimousPostReference;
        ModeloDadosRecycler modeloUser= new ModeloDadosRecycler();
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ListView listContacts;
        public static UserAdapter userAdapter;
        private LinearLayout feedLayout;
        public TextView capturadora;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            usersReference = firebaseDatabase.getReference(BDCaminhos.USER);
            anonimousPostReference = firebaseDatabase.getReference(BDCaminhos.POSTS_ANONIMOS);

            //listContacts = rootView.findViewById(R.id.list_view_contact);

            userAdapter = new UserAdapter(inflater);
           // listContacts.setAdapter(userAdapter);
            feedLayout = rootView.findViewById(R.id.feeding_area);

            anonimousPostReference.addValueEventListener(new ValueEventListener() {



                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Post post = data.getValue(Post.class);

                        capturadora = new TextView(inflater.getContext());
                      //  modeloUser.setNomeUsuario();
                        feedLayout.addView(capturadora);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            usersReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        User value = data.getValue(User.class);

                        Log.d("Initial DB Reading", "Value of key is: " + dataSnapshot.getChildren());
                        Log.d("Initial DB Reading", "Value of capturador is: " + value);
                        if (!userAdapter.getList().contains(value)) {
                            userAdapter.add(value);
                        } else {
                            userAdapter.update(value);
                        }

                        Log.d("Initial DB Reading", "Map value creation :" + userAdapter.getList());
                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Initial DB Reading", "Failed to read value.", error.toException());
                }
            });

            if (position == 1) {

                listContacts.setVisibility(View.VISIBLE);
                feedLayout.setVisibility(View.GONE);
            }
            if (position == 2) {
                feedLayout.setVisibility(View.VISIBLE);
                listContacts.setVisibility(View.GONE);
            }

            if (position == 3) {
                feedLayout.setVisibility(View.GONE);
                listContacts.setVisibility(View.GONE);
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            setPosition(position);
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

}
