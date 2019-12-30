package com.android.learningapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.learningapp.Adapters.SensorRecViewAdapter;
import com.android.learningapp.R;
import com.android.learningapp.UPIPayment;

import java.util.ArrayList;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class FragmentTask extends Fragment {
    private TourGuide tourGuide;
    private RecyclerView recyclerView;
    private Button portal;
    private SensorRecViewAdapter recyclerViewAdapter;
    private Context context;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> Title = new ArrayList<String>();
    private ArrayList<String> Purpose = new ArrayList<String>();
    private ArrayList<String> Description = new ArrayList<String>();
    private ArrayList<String> Image = new ArrayList<String>();
    private CardView sensor_week;
    private View v;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            tourGuide = (FragmentTask.TourGuide) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again.");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    private void getValues(){
        Title.add("LED");
        Title.add("Switch");
        Title.add("Potentiometer");
        Purpose.add("Emits Light.");
        Purpose.add("Makes/breaks circuit");
        Purpose.add("Varies Resistance.");
        Description.add("A light-emitting diode is a semiconductor light source that emits light when current flows through it.");
        Description.add("A switch is used to make or break an electric circuit when required. ");
        Description.add("A potentiometer is a three-terminal resistor with a sliding or rotating contact that forms an adjustable voltage divider");
        Image.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTIsyQW1aDXV7X-oQmZytXjtYfj3NeApr0HmB0Zy-J7Y0CRgsN&s");
        Image.add("https://firebasestorage.googleapis.com/v0/b/silk-iot.appspot.com/o/Project%20Resources%2FSensors%20Images%2Fdpdt-500x500.jpg?alt=media&token=aa4f2f8c-5648-457c-91c7-5c3308643022");
        Image.add("https://upload.wikimedia.org/wikipedia/commons/0/0a/Electronic-Component-Potentiometer.jpg");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        getValues();
        context = getContext();
        portal = view.findViewById(R.id.portal);
        recyclerView = view.findViewById(R.id.recyclerViewSensor);
        recyclerViewAdapter = new SensorRecViewAdapter(context, Title, Purpose, Description, Image);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        sensor_week = view.findViewById(R.id.sensor_of_the_week);

        portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UPIPayment.class));
            }
        });
        return view;
    }
    public void guide() {
        Log.d("Inside the guide","Task/-guide");
         // half second between each showcase view
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), "Task");
        sequence.addSequenceItem(new MaterialShowcaseView.Builder(getActivity())
                .setTarget(sensor_week)
                .setGravity(0)
                .setDismissText("OK")
                .setDelay(500)
                .setContentText("You will get tasks on different sensors every week")
                .withRectangleShape()
                .build());
        sequence.addSequenceItem(new MaterialShowcaseView.Builder(getActivity())
                .setTarget(recyclerView.getLayoutManager().findViewByPosition(0))
                .setGravity(0)
                .setTargetTouchable(true)
                .setDismissText("OK")
                .setContentText("Click on LED to view the task")
                .withRectangleShape()
                .setDelay(500)
                .build());
        sequence.addSequenceItem(new MaterialShowcaseView.Builder(getActivity())
                .setTarget(recyclerView.getLayoutManager().findViewByPosition(0))
                .setGravity(0)
                .setDismissText("OK")
                .setContentText("Complete these tasks to earn points")
                .withRectangleShape()
                .setDelay(2500)
                .build());


        sequence.start();
        sequence.setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
            @Override
            public void onDismiss(MaterialShowcaseView itemView, int position) {
                if (position == 2) {
                    tourGuide.startTourSocial();

                }
            }
        });
    }
    public void updateTour(int pos){
        Log.d("Inside the fragment","Task"+pos);
        if(pos==2){
            guide();
        }

    }

    public interface TourGuide {
        void startTourSocial( );

    }
}
