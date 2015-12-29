package com.didi.business;

import android.app.Activity;
import android.os.Bundle;

import com.didi.business.api.BusinessLogApi;
import com.didi.business.model.BusinessProductChannel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huhuajun on 2015/10/19.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BusinessLogApi.init(this, BusinessProductChannel.PRODUCT_DIDI_CUSTOMER);
//        Button t = new Button(this);
//        t.setText("upload");
//        t.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BusinessLogApi.addBusinessAdLog("123", "1", "2", "show", "123.12", "123.23");
//            }
//        });
//        setContentView(t,new ViewGroup.LayoutParams(-1,500));

//        String json1 = "{\"msgId\":1156032955446040205,\"timestamp\":1450839245508,\"command\":11,\"topic\":\"PAD\",\"data\":\"{\"adId\":10,\"adpId\":1,\"materialType\":0,\"startTime\":1449676800000,\"endTime\":1450281599000,\"callback\":\"\",\"material\":{\"materialId\":1,\"name\":\"测试待机视频素材\",\"url\":\"http://test1.mp4\",\"size\":0,\"md5\":\"0\"}}\",\"dataUrl\":\"\",\"cbUrl\":\"\"}";
//
//        try {
//            JSONObject jsonObject = new JSONObject(json1);
//            Log.i("test json", "----->" + jsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        String json2 = "{\"adId\":10,\"adpId\":1,\"materialType\":0,\"startTime\":1449676800000,\"endTime\":1450281599000,\"callback\":\"\",\"material\":{\"materialId\":1,\"name\":\"测试待机视频素材\",\"url\":\"http://test1.mp4\",\"size\":0,\"md5\":\"0\"}}";

        try {
            JSONObject jsonObject2 = new JSONObject(json2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BusinessLogApi.exit();
    }
}
