package kr.co.tjeit.bluetoothpractice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjeit.bluetoothpractice.adapter.BtAdapter;
import kr.co.tjeit.bluetoothpractice.data.BtDevice;

public class DeviceListActivity extends BaseActivity {

    private BluetoothAdapter mBtAdapter;
    private android.widget.ListView newDeviceListView;
    private android.widget.ListView pairedDeviceListView;
    private android.widget.Button scanBtn;

    List<BtDevice> newDeviceList = new ArrayList<>();
    BtAdapter mBtListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        bindViews();
        setupEvents();
        setValuse();
    }

    @Override
    public void setupEvents() {

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                탐색을 시작.
                doDiscovery();

//                탐색이 진행줄일땐 다시 탐색을 시작할 수 없도록.
                scanBtn.setVisibility(View.GONE);

            }
        });

    }
//    주변의 블루투스 기기를 탐색.

    void doDiscovery () {

//        1. 새 기기 목록 리스트뷰를 표시. 페어링 된 목록 리스트 숨김.

        newDeviceListView.setVisibility(View.VISIBLE);
        pairedDeviceListView.setVisibility(View.GONE);

//        만약, 이미 기기 탐색을 진행중이라면, 진행중이던 요청은 취소.

        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        mBtAdapter.startDiscovery();
    }

    @Override
    public void setValuse() {

//        탐색된 블루투스 기기를 보여줄 리스트뷰 세팅
        mBtListAdapter = new BtAdapter(mContext, newDeviceList);
        newDeviceListView.setAdapter(mBtListAdapter);

//        브로드 캐스트 리시버를 등록.

//        수신하고 싶은 방송의 종류를 설정.
//        1. 블루투스 기기를 찾았다! 라는 방송을 받겠다.
        IntentFilter foundfiFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

//        수신하고 싶은 방송을 등록
//        재료 2가지.
//        1) 방송이 수신되었을 때 진행할 행동을 담은 Receiver => 맨 밑에 따로 작성
//        2) 어던 방송을 수신할 지 설정해둔 IntentFilter
        registerReceiver(mReceiver, foundfiFilter);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            블루투스 기기를 찾았을 때 수신되는 신호를 가지고
//            이벤트 처리를 해준다.

//            어떤 방송이 수신되었나?
            String actionName = intent.getAction();

//            1. 기기 탐색(Discovering) 결과 어떤 기기를 발견했을 때.

            if (actionName.equals(BluetoothDevice.ACTION_FOUND)) {

//                방송 데이터 안에 들어있는 블루투스기기 클래스를 받아오기.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

//                찾아낸 기기가. 이미 페어링 된 적이 있다면 무시.
//                새 장비가 아니니, 굳이 페어링 가능 목록에 띄워줄 필요가 없다.

                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                    아직 연결된 적이 없는 기기.

                    newDeviceList.add(new BtDevice(device.getName(), device.getAddress()));
                    mBtListAdapter.notifyDataSetChanged();
                    
                }
            }
        }
    };

    @Override
    public void bindViews() {
        this.pairedDeviceListView = (ListView) findViewById(R.id.pairedDeviceListView);
        this.newDeviceListView = (ListView) findViewById(R.id.newDeviceListView);
        this.scanBtn = (Button) findViewById(R.id.scanBtn);
    }
}
