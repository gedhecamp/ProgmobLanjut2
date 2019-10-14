package com.hellohasan.sqlite_project.ShowTemanList;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.hellohasan.sqlite_project.TemanCreateListener;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class TemanListActivity extends AppCompatActivity implements TemanCreateListener {


    private List<Product> productList = new ArrayList<>();
    private ProductRepository productRepository;
    private TextView productListEmptyTextView;
    private RecyclerView recyclerView;
    private ProductListRecyclerViewAdapter productListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.productRecyclerView);
        productListEmptyTextView = (TextView) findViewById(R.id.emptyProductListTextView);
        productRepository = new ProductRepository(getApplicationContext());


        productRepository.getProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                productList.addAll(products);
                viewVisibility();
            }
        });

        FragmentManager fm = getSupportFragmentManager();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productListRecyclerViewAdapter = new ProductListRecyclerViewAdapter(this, productList,fm);
        recyclerView.setAdapter(productListRecyclerViewAdapter);
        Log.e("data ",productList.toString());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentCreateDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Yakin ingin menghapus semua Products?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            productRepository.deleteAllProduct();

                            productList.clear();
                            productListRecyclerViewAdapter.notifyDataSetChanged();
                            viewVisibility();
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(productList.isEmpty())
            productListEmptyTextView.setVisibility(View.VISIBLE);
        else
            productListEmptyTextView.setVisibility(View.GONE);
    }

    private void openStudentCreateDialog() {
        ProductCreateDialogFragment productCreateDialogFragment = ProductCreateDialogFragment.newInstance("Create Product", this);
        productCreateDialogFragment.show(getSupportFragmentManager(), "Create Product");
    }

    @Override
    public void onProductCreated(Product product) {
        productList.add(product);
        productListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(product.getProductName());
    }
}
