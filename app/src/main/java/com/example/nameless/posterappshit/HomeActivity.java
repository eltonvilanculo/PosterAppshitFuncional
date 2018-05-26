package com.example.nameless.posterappshit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class HomeActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference usersReference, anonimousPostReference, publicPostRef;
    LinearLayout layoutprincipal ;
    FrameLayout frameLayout ;
    public  TextView capturador;
    public static UserAdapter userAdapter;
    private LinearLayout feedLayout;
    private  final  int IMAGEMGALERIA=1;
    private  final  int IMAGEMCAMERA=2;
    // Inicializacao do local onde ira ficar a foto carregada
    
    // Cloud Storage

    private StorageReference mStorageRef;
    

    
    // --------------------------------------
    ImageView imagemCarregada ;


    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Cloud Storage 

        mStorageRef = FirebaseStorage.getInstance().getReference();
        
        // Toolbar config

        
        layoutprincipal = (LinearLayout) findViewById(R.id.layoutPrincipal);
        setContentView(R.layout.activity_home);
        android.support.v7.widget.Toolbar toolbar =(android.support.v7.widget.Toolbar) findViewById(R.id.toolBarFeed);
        toolbar.setTitle(" FAJ FAJ");
        //toolbar.setTitleTextColor(R.color.colorT);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(10f);
            toolbar.setFocusable(true);
        }
        toolbar.inflateMenu(R.menu.menu_toolbar_config);
        // Framelayout


        // codigo para linkar

        //Adapter e RVIew
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        final MeuAdapter meuAdapter = new MeuAdapter(this);
        recyclerView.setAdapter(meuAdapter);

        //Layout Manager e RVIew

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        // codigo para linkar Post

        //Adapter e RVIew
        RecyclerView recycleFeed = (RecyclerView) findViewById(R.id.recycleFeed);
        final PostNewAdapter postNewAdapter = new PostNewAdapter(this);
        recycleFeed.setAdapter(postNewAdapter);

        RecyclerView recycle5 = (RecyclerView) findViewById(R.id.recycle5);
        final PostNewAdapter postNewAdapter5 = new PostNewAdapter(this);
        recycle5.setAdapter(postNewAdapter);
        //Layout Manager e RVIew

        LinearLayoutManager linearLayoutManagerFeed = new LinearLayoutManager(this);
        linearLayoutManagerFeed.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager linearLayoutManagerFeed5 = new LinearLayoutManager(this);
        linearLayoutManagerFeed5.setOrientation(LinearLayoutManager.VERTICAL);
        recycleFeed.setLayoutManager(linearLayoutManagerFeed);
        recycle5.setLayoutManager(linearLayoutManagerFeed5);

        recycleFeed.setItemAnimator(new DefaultItemAnimator());
        recycle5.setItemAnimator(new DefaultItemAnimator());




        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec aba1 = tabHost.newTabSpec("PRIMEIRA");
        aba1.setContent(R.id.feeding_area);
        aba1.setIndicator("FAJ NOTICIAS");



        TabHost.TabSpec aba2 = tabHost.newTabSpec("SEGUNDA");
        aba2.setContent(R.id.SEGUNDA);
        aba2.setIndicator("FAJ AMIGOS");

        TabHost.TabSpec aba3 = tabHost.newTabSpec("TERCEIRA");
        aba3.setContent(R.id.feeding5);
        aba3.setIndicator("FAJ 5");

        tabHost.addTab(aba1);
        tabHost.addTab(aba2);
        tabHost.addTab(aba3);


        usersReference = firebaseDatabase.getReference(BDCaminhos.USER);
        anonimousPostReference = firebaseDatabase.getReference(BDCaminhos.POSTS_ANONIMOS);

        //listContacts = findViewById(R.id.list_view_contact);

        //userAdapter = new UserAdapter(getLayoutInflater());
        //listContacts.setAdapter(userAdapter);
      //  feedLayout = findViewById(R.id.feeding_area);

        anonimousPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Post post = data.getValue(Post.class);

                 meteNoSQL(post);


                    if (!postNewAdapter.getList().contains(post)) {
                        postNewAdapter.add(post);
                    } else {
                        //listaUsuarios.update(value);
                    }
                    //capturador = new TextView(HomeActivity.this);
                    //capturador.setText("De: "+post.getSender()+"\n Mensagem:    " + post.getText().toString() + "\n Data:   " + new Date(post.getDate()).toString());
                  //  feedLayout.addView(capturador);
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
                    if (!meuAdapter.getList().contains(value)) {
                        meuAdapter.add(value);
                    } else {
                        //listaUsuarios.update(value);
                    }

                    Log.d("Initial DB Reading", "Map value creation :" + meuAdapter.getList());
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Initial DB Reading", "Failed to read value.", error.toException());
            }
        });


    }

    private void meteNoSQL(Post post) {
        /**
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         * */
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void verificarPerfil(MenuItem item) {

        Toast.makeText(HomeActivity.this, "Estou chateado", Toast.LENGTH_SHORT).show();
    }
    public void verificarSettings(MenuItem item) {
        Toast.makeText(HomeActivity.this,"Comming soon",Toast.LENGTH_LONG).show();
    }
    public void verificarUs(MenuItem item) {
        Toast.makeText(HomeActivity.this,"Comming soon",Toast.LENGTH_LONG).show();
    }
    public void verificarLogout(MenuItem item) {
        Toast.makeText(HomeActivity.this,"Comming soon",Toast.LENGTH_LONG).show();
    }



    public void postarMensagem(View view) {



        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertBuilder.setTitle("FAJ aqui !! ");

        // Estou a meter tudo num objecto de View para depois por no dialog

        View customView = getLayoutInflater().inflate(R.layout.dialog_layout, null, false);
        // Inicializacao do imageView que ficara a imagem carregada
        imagemCarregada = customView.findViewById(R.id.imagemCarregada);

       // Inicializacao dos botoes do post
        ImageButton accept = customView.findViewById(R.id.accept_dialog_btn);
        ImageButton acessoGaleria = customView.findViewById(R.id.acessoGaleria);
        ImageButton acessoCamera = customView.findViewById(R.id.acessoCamera);
        //ListView listDialogContact = customView.findViewById(R.id.list_view_contacts_dialog);
        final EditText text = customView.findViewById(R.id.input_text_dialog_post);


        //listDialogContact.setAdapter(userAdapter);

        alertBuilder.setView(customView);
        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Post post = new Post();
                post.setDate(System.currentTimeMillis());
                post.setSender(getString(R.string.usuarioAnonimo));
                // post.setSender(LoginActivity.firebaseUser.getDisplayName());
                post.setText(text.getEditableText().toString());

                anonimousPostReference.child(new Date(post.getDate()).toString()).setValue(post);

                alertDialog.hide();
                text.setText("");
            }
        });


        acessoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intentCamera.resolveActivity(getPackageManager())!=null){

                startActivityForResult(intentCamera,IMAGEMCAMERA);
                }
            }
        });

        acessoGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGaleria,IMAGEMGALERIA);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode == 1){

            Uri uriImagem = data.getData();

            String caminhoImagem [] = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uriImagem,caminhoImagem,null,null,null);
            cursor.moveToFirst();
            int indiceDoCaminho = cursor.getColumnIndex(caminhoImagem[0]);
            String caminhoFoto = cursor.getString(indiceDoCaminho);
            cursor.close();
            Bitmap scannerImagem =(BitmapFactory.decodeFile(caminhoFoto)) ;
            imagemCarregada.setImageBitmap(scannerImagem);

        }

        if (requestCode==IMAGEMCAMERA && resultCode == RESULT_OK){
          Bundle bundle = data.getExtras();
          Bitmap bitmap = (Bitmap) bundle.get("data");
          imagemCarregada.setImageBitmap(bitmap);

        }
        
        
        
    }
    
   public void uploadFireBase (){


       Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
       StorageReference storageRef = null;
       StorageReference riversRef = storageRef.child("images/rivers.jpg");

       riversRef.putFile(file)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       // Get a URL to the uploaded content
                       Uri downloadUrl = taskSnapshot.getDownloadUrl();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception exception) {
                       // Handle unsuccessful uploads
                       // ...
                   }
               });
        
   }
   
   void capturaImageFireBase () throws IOException {


       File localFile = File.createTempFile("images", "jpg");
       mStorageRef.getFile(localFile)
               .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                       // Successfully downloaded data to local file
                       // ...
                   }
               }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception exception) {
               // Handle failed download
               // ...
           }
       });
   }
}
