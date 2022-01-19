package com.example.cm07project;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private int REQUEST_CODE = 111;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private GoogleMap map,googlemap;
    private Geocoder geocoder;
    private double selectedlat, selectedlng;
    private List<Address> addressList;
    private String string;
    private SearchView searchView;
    private DatabaseReference reference;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },REQUEST_CODE);
        }

        searchView = view.findViewById(R.id.sv_location);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));

                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap Map){
        map = Map;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!= null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            map = googleMap;
                            googlemap = googleMap;
                            getAddress(location.getLatitude(),location.getLongitude());

                            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    connectionCheck();
                                    if(networkInfo.isConnected() && networkInfo.isAvailable()){

                                        selectedlat = latLng.latitude;
                                        selectedlng = latLng.longitude;

                                        getAddress(selectedlat,selectedlng);

                                    }else {
                                        Toast.makeText(getActivity(), "Connection check", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }else {
            Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void connectionCheck(){
        connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

    }
    private void getAddress(double mlat, double mlng){
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        if(mlat != 0){
            try {
                addressList = geocoder.getFromLocation(mlat,mlng,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList != null){
                String maddress = addressList.get(0).getAddressLine(0);

                string = maddress;

                if(maddress != null){
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(mlat,mlng);
                    markerOptions.position(latLng).title(string);
                    map.addMarker(markerOptions).showInfoWindow();
                    googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    googlemap.addMarker(markerOptions).showInfoWindow();
                }else {
                    Toast.makeText(getActivity(), "Something error", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(), "Something error", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "LatLng null", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

    List<Places> placesList = new ArrayList<>();
    Places places;

    void getDataFromFirebase(GoogleMap googleMap){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events");
        placesList.clear();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String,String> map = (Map<String, String>) snapshot.getValue();

                places = new Places();
                places.setCountry(map.get("country"));
                places.setState(map.get("state"));
                places.setStreetAdress(map.get("streetadress"));
                places.setEvent_name(map.get("name"));
                places.setOrg(map.get("org"));
                places.setDate(map.get("date"));
                placesList.add(places);

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setPadding(20,20,20,200);


                LatLng address = null;

                for (int i = 0; i<placesList.size();i++){
                    try {
                        String adr = placesList.get(i).getStreetAdress()+ ","+
                                placesList.get(i).getState()+","+
                                placesList.get(i).getCountry()+",";
                        //Funtion no final do codigo
                        address=getLatLongFromAdress(getActivity(),adr);

                        googleMap.addMarker(new MarkerOptions()
                                .position(address)
                                .title(placesList.get(i).getEvent_name())
                                .snippet(placesList.get(i).getOrg()+", " + placesList.get(i).getDate()));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address,10));

                        //Quando se carrega no marker mostra uma InfoWindow Custom
                        googleMap.setInfoWindowAdapter(new CustomInfoWindowForGoogleMap(getActivity()));


                    }catch (Exception e){
                    }
                }



                // adding on click listener to marker of google maps.
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        // on marker click we are getting the title of our marker
                        // which is clicked and displaying it in a toast message.
                        String markerName = marker.getTitle();
                        //Toast.makeText(getActivity(), "Evento Cliked Teste", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                // adding on click listener to WindowInfo of google maps
                //Vai para o EventsDetailsFragments correspondent
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String markerName = marker.getTitle();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                                    final String a = snapshot.child("name").getValue().toString();

                                    if (markerName.toString().equals(a)){

                                        final String n1 =  snapshot.child("id").getValue().toString();
                                        Bundle bundle = new Bundle();
                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        EventsDetailsFragment llf = new EventsDetailsFragment();
                                        bundle.putString("message", n1.toString());
                                        llf.setArguments(bundle);
                                        ft.replace(R.id.container, llf);
                                        ft.addToBackStack("tag");
                                        ft.commit();

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    LatLng getLatLongFromAdress(Context context, String Stradress){
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        LatLng latLng = null;
        try {
            address=geocoder.getFromLocationName(Stradress,2);
            if (address==null){
                return null;
            }
            Address loc= address.get(0);
            latLng = new LatLng(loc.getLatitude(), loc.getLongitude());

        }catch (Exception e){

        }

        return latLng;
    }
}