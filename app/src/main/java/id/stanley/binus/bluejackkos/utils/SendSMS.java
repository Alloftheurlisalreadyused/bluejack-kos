package id.stanley.binus.bluejackkos.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendSMS {
    public void SendSMS(Context context, String phoneNumber){
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://5at2tfk1o5.execute-api.ap-southeast-1.amazonaws.com/prod/terserah";

            JSONObject object = new JSONObject();
            try {
                object.put("sender","BluejackKos");
                object.put("receiver",phoneNumber);
                object.put("message","You have successfully registered.");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest smsRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                    response -> {
                        // response
                        Log.d("Response", response.toString());
                    },
                    error -> {
                        // error
                        String response;
                        Log.d("Error.Response", error.getMessage());
                    }
            );
//            ) {
//                @Override
//                protected Map<String, String> getParams()
//                {
//                    Map<String, String>  params = new HashMap<String, String>();
//                    params.put("sender","Bluejack Kos");
//                    params.put("receiver","+6281256057829");
//                    params.put("message","You have successfully registered.");
//
//                    return params;
//                }
//            };
            queue.add(smsRequest);
        }
    }
