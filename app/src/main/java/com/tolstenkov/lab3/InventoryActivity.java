package com.tolstenkov.lab3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class InventoryActivity extends AppCompatActivity {
    Inventory inventory;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Intent intent = this.getIntent();
        position = intent.getIntExtra("position", -1);

        Bundle Invent;
        if (intent.getParcelableExtra("inventory") != null)
            Invent = intent.getParcelableExtra("inventory");
        else Invent = null;

        inventory = new Inventory();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = new InventoryFragment(Invent);
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
    // додаємо до меню елемент типу “done”, який описано у файлі add_item.xml (див. далі п.10)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // реалізуємо функціональність при натисканні на певний елемент меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        // при натисканні на елемент action_save (“done”) формується інтент у відповідь
        if (item.getItemId() == R.id.action_save) {
            InventoryFragment fragment = (InventoryFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            inventory = fragment.getmInventory();
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.putExtra("action", "save");
            intent.putExtra("title", inventory.getmTitle());
            intent.putExtra("id", inventory.getmId());
            intent.putExtra("date", inventory.getmDate());
            intent.putExtra("status", inventory.ismSolved());
            setResult(Activity.RESULT_OK,intent);
            finish();
            return true;
        }else
            // при натисканні на елемент action_del (“delete”) формується інтент у відповідь
        if(item.getItemId() == R.id.action_del){
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.putExtra("action", "delete");
            setResult(Activity.RESULT_OK,intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
