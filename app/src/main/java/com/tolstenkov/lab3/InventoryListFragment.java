package com.tolstenkov.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

public class InventoryListFragment extends Fragment {
    private ArrayList<Inventory> inventories;
    private InventoryAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inventories = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // зв’язуємо з відповідним макетом
        View view = inflater.inflate(R.layout.fragment_inventory_list, container, false);
        ListView listInventories = (ListView)view.findViewById(R.id.list_inventory);
        // для формування структури списку використовуємо відповідний адаптер (див.нижче п.9)
        adapter = new InventoryAdapter(this.getContext(), inventories);
        listInventories.setAdapter(adapter);
        listInventories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // тут будуть функції обробки при натисканні на елемент списку
                Intent intent = new Intent(getActivity(), InventoryActivity.class);
                Bundle inventory = new Bundle();
                inventory.putParcelable("inventory",inventories.get(position));
                intent.putExtra("inventory", inventory);
                intent.putExtra("position", position);
                startActivityForResult(intent, 0);
            }
        });
        // формування інтенту та запуск активності при натисканні на кнопку додавання
        View fab = getActivity().findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), InventoryActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
        return view;
    }
    // обробка зворотнього інтенту та додавання нового елементу до inventories
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK ) {

            int position = data.getIntExtra("position",-1);

            if(position == -1){
                Inventory inventory = new Inventory();
                inventory.setmTitle(data.getStringExtra("title"));
                inventory.setmDate((Date) data.getSerializableExtra("date"));
                inventory.setmSolved(data.getBooleanExtra("status",false));
                inventories.add(inventory);
            } else {
                switch (data.getStringExtra("action")) {
                    case "save": {
                        Inventory inventory = new Inventory();
                        inventory.setmTitle(data.getStringExtra("title"));
                        inventory.setmDate((Date) data.getSerializableExtra("date"));
                        inventory.setmSolved(data.getBooleanExtra("status",false));
                        inventories.set(position,inventory);
                    }
                    case "delete": {
                        inventories.remove(position);
                    }

                }
            }
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(getActivity(), "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}
