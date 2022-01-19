package com.example.cm07project;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;


public class CreateItemFragment extends Fragment {
    private EditText mtitle;
    private EditText mdesc;
    private EditText mquant;
    private String category;
    private Spinner spinner;
    private Button create;
    private Button gallery;
    private Button photo;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userid;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    private ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String id;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_create_item, null);

        spinner = (Spinner) root.findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        reference = FirebaseDatabase.getInstance().getReference("Products");


        create = root.findViewById(R.id.createItemButton);
        photo = root.findViewById(R.id.takephoto);
        gallery = root.findViewById(R.id.btnChoose);
        imageView = root.findViewById(R.id.imgView);

        mtitle = root.findViewById(R.id.itemtitleedit);
        mdesc = root.findViewById(R.id.itemdescedit);
        mquant = root.findViewById(R.id.itemquant);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItem();

            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrir camera

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2000);
                }
                else {
                    startGallery();
                }
            }
        });


        return  root;


    }

    private void startGallery() {
        // Defining Implicit Intent to mobile gallery
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmapImage);
            }
        }
        //Uri returnUri;
        //returnUri = data.getData();
    }

    public void UploadImage() {

        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                           // databaseReference.child("imageid").setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(getContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    private void createItem() {
        String title = mtitle.getText().toString();
        String desc = mdesc.getText().toString();
        category = spinner.getSelectedItem().toString();

        if(title.isEmpty()) {
            Toast.makeText(getActivity(), "Título obrigatório", Toast.LENGTH_SHORT).show();

        } else if(desc.isEmpty()) {
            Toast.makeText(getActivity(), "Descrição obrigatória", Toast.LENGTH_SHORT).show();

        } else if(category.isEmpty()) {
            Toast.makeText(getActivity(), "Categoria obrigatória", Toast.LENGTH_SHORT).show();

        } else if(mquant.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Descrição obrigatória", Toast.LENGTH_SHORT).show();

        } else {
            int quant = Integer.parseInt(mquant.getText().toString());

            Products itemP = new Products(UUID.randomUUID().toString(), userid, title,desc, category,quant);

            FirebaseDatabase.getInstance().getReference("Products")
                    .push()
                    .setValue(itemP).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getActivity(), "Item registered", Toast.LENGTH_SHORT).show();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();

                        ProfileFragment llf = new ProfileFragment();

                        ft.replace(R.id.container, llf);
                        ft.addToBackStack("tag");
                        ft.commit();

                    }else {
                        Toast.makeText(getActivity(), "Item Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}