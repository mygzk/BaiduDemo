package com.example.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<ClassEntity> classEntityList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classEntityList.add(new ClassEntity("基础地图", BaseMapActivity.class));
        classEntityList.add(new ClassEntity("mapFragment基础地图", MapFragmentActivity.class));
        classEntityList.add(new ClassEntity("LayersDemoActivity 普通地图 卫星地图 交通流量 城市热力", LayersDemoActivity.class));
        classEntityList.add(new ClassEntity("基础控制", MapControlActivity.class));
        classEntityList.add(new ClassEntity("定位", LocationActivity.class));
        ListView list = (ListView)findViewById(R.id.lisv_map);

        list.setAdapter(new MyAdapter());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this,
                        classEntityList.get(position).aClass);
                startActivity(i);
            }
        });

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return classEntityList.size();
        }

        @Override
        public Object getItem(int position) {
            return classEntityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HolderView holder;
            if(convertView ==null){
                holder = new HolderView();
                convertView = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.list_item_map, null);
                holder.tvDecription = (TextView)convertView.findViewById(R.id.tv_decription);
                convertView.setTag(holder);
            }else{
                holder = (HolderView)convertView.getTag();
            }
            holder.tvDecription.setText(classEntityList.get(position).name);
            return convertView;
        }
    }

    class HolderView{
        TextView tvDecription;
    }

    class ClassEntity{
        public ClassEntity(String name, Class aClass) {
            this.name = name;
            this.aClass = aClass;
        }

        public String name;
        public Class aClass;
    }
}
