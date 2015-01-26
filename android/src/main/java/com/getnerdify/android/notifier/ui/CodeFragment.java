package com.getnerdify.android.notifier.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getnerdify.android.notifier.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CodeFragment extends Fragment {

    public static final String TAG = CodeFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_code, container, false);
        TextView labelView = (TextView) root.findViewById(R.id.label);
        TextView codeView = (TextView) root.findViewById(R.id.code);

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras != null) {
            if (extras.containsKey("com.parse.Data")) {
                try {
                    JSONObject pushData = new JSONObject(extras.getString("com.parse.Data"));
                    String code = pushData.getString("code");
                    String company = pushData.getString("company");

                    labelView.setText(String.format(getString(R.string.company_code), company));
                    codeView.setText(code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return root;
    }

}
