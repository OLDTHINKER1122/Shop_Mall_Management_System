package com.example.business.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.business.Adapter.PopularListAdapter;
import com.example.business.Domain.PopularDomain;
import com.example.business.R;

import java.util.ArrayList;

public class user extends AppCompatActivity {

    private RecyclerView.Adapter adapterPupolar;
    private RecyclerView recyclerViewPopular;
    TextView user_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_name=findViewById(R.id.textView4);
        main();
        initRecyclerview();
    }

    public void gotoShopMail(View v) {
        startActivity(new Intent(this, CartActivity.class));

    }

    private void initRecyclerview(){
        ArrayList<PopularDomain> items=new ArrayList<>();
        items.add(new PopularDomain("MacBook Pro 13 M2 chip", "Discover the MacBook Pro 13 featuring the\n" +
                " powerful M2 chip. This cutting-edge laptop\n" +
                " redefines performance and portability. With its\n" +
                " sleek design and advanced technology, the\n" +
                " MacBook Pro 13 M2 chip is your ultimate\n" +
                " companion for productivity, creativity, and\n" +
                " entertainment. Experience seamless multitasking,\n" +
                " stunning visuals on the Retina display, and\n" +
                " enhanced security with Touch ID. Take your\n" +
                " computing experience to the next level with the\n" +
                " MacBook Pro 13 M2 chip." ,"pic1", 15, 4, 500));
        items.add(new PopularDomain("Ps-5 Digital", "Discover the MacBook Pro 13 featuring the\n" +
                " powerful M2 chip. This cutting-edge laptop\n" +
                " redefines performance and portability. With its\n" +
                " sleek design and advanced technology, the\n" +
                " MacBook Pro 13 M2 chip is your ultimate\n" +
                " companion for productivity, creativity, and\n" +
                " entertainment. Experience seamless multitasking,\n" +
                " stunning visuals on the Retina display, and\n" +
                " enhanced security with Touch ID. Take your\n" +
                " computing experience to the next level with the\n" +
                " MacBook Pro 13 M2 chip." , "pic2", 10, 4.5, 450));
        items.add(new PopularDomain("IPhone 14", "Discover the MacBook Pro 13 featuring the\n" +
                " powerful M2 chip. This cutting-edge laptop\n" +
                " redefines performance and portability. With its\n" +
                " sleek design and advanced technology, the\n" +
                " MacBook Pro 13 M2 chip is your ultimate\n" +
                " companion for productivity, creativity, and\n" +
                " entertainment. Experience seamless multitasking,\n" +
                " stunning visuals on the Retina display, and\n" +
                " enhanced security with Touch ID. Take your\n" +
                " computing experience to the next level with the\n" +
                " MacBook Pro 13 M2 chip." , "pic3", 13, 4.2, 800));

        recyclerViewPopular=findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        adapterPupolar=new PopularListAdapter(items);
        recyclerViewPopular.setAdapter(adapterPupolar);
    }
    public void main()  {
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("name");
        user_name.setText(name);



    }
}

