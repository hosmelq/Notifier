package com.getnerdify.android.notifier.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getnerdify.android.notifier.R;
import com.getnerdify.android.notifier.model.RetrofitNotification;
import com.getnerdify.android.notifier.sync.NotifierService;
import com.getnerdify.android.notifier.ui.widget.CollectionView;
import com.getnerdify.android.notifier.ui.widget.CollectionViewCallbacks;
import com.getnerdify.android.notifier.util.PrefUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NotificationsFragment extends Fragment implements CollectionViewCallbacks {

    public static final String TAG = NotificationsFragment.class.getSimpleName();

    private Callbacks mCallbacks;

    private CollectionView mCollectionView;
    private List<RetrofitNotification> mCursor = null;
    private TextView mEmptyView;

    private static final int GROUP_ID_NORMAL = 123;

    public interface Callbacks {
        public void onNotificationSelected(String notificationId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCursor = null;

        updateCollectionView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallbacks = (Callbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement fragment's callbacks");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notifications, container, false);
        mCollectionView = (CollectionView) root.findViewById(R.id.notifications_collection_view);
        mEmptyView = (TextView) root.findViewById(R.id.empty_text);

        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    @Override
    public View newCollectionHeaderView(Context context, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.list_item_notification_header, parent, false);
    }

    @Override
    public void bindCollectionHeaderView(Context context, View view, int groupId, String headerLabel) {
        ( (TextView) view.findViewById(android.R.id.text1)).setText(headerLabel);
    }

    @Override
    public View newCollectionItemView(Context context, int groupId, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.list_item_notification, parent, false);
    }

    @Override
    public void bindCollectionItemView(Context context, View view, int groupId, int indexInGroup, int dataIndex, Object tag) {
        RetrofitNotification notification = mCursor.get(dataIndex);

        if (notification == null) {
            return;
        }

        TextView titleView = (TextView) view.findViewById(R.id.notification_title);
        TextView descriptionView = (TextView) view.findViewById(R.id.notification_description);
        TextView dateView = (TextView) view.findViewById(R.id.notification_date);

        titleView.setText(notification.getCompany());
        descriptionView.setText(notification.getTitle());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat shortTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        String createdAt = notification.getCreatedAt();

        try {
            Date formatedDate = dateFormat.parse(createdAt);
            dateView.setText(shortTimeFormat.format(formatedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setContentTopClearance(int topClearance) {
        if (mCollectionView != null) {
            mCollectionView.setContentTopClearance(topClearance);
        }
    }

    private void updateCollectionView() {
        NotifierService service = new NotifierService();
        int userId = PreferenceManager
            .getDefaultSharedPreferences(getActivity())
            .getInt(PrefUtils.PREF_USER_ID, 0);

        service.notifications(userId, new Callback<List<RetrofitNotification>>() {
            @Override
            public void success(List<RetrofitNotification> notifications, Response response) {
                if (notifications != null) {
                    mCursor = notifications;

                    CollectionView.Inventory inv = prepareInventory();

                    mCollectionView.setCollectionAdapter(NotificationsFragment.this);
                    mCollectionView.updateInventory(inv);

                    mEmptyView.setVisibility((mCursor.size() == 0) ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private CollectionView.Inventory prepareInventory() {
        CollectionView.Inventory inventory = new CollectionView.Inventory();
        CollectionView.InventoryGroup curGroup = null;
        int dataIndex = -1;
        int normalColumns = getResources().getInteger(R.integer.notifications_columns);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat monthOfYearFormat = new SimpleDateFormat("EEEE, MMMM d");

        for (RetrofitNotification notification : mCursor) {
            ++dataIndex;
            String createdAt = notification.getCreatedAt();
            Date formatedDate;
            String groupName = "";

            try {
                formatedDate = dateFormat.parse(createdAt);
                groupName = monthOfYearFormat.format(formatedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (curGroup == null || ! curGroup.getHeaderLabel().equals(groupName)) {
                if (curGroup != null) {
                    inventory.addGroup(curGroup);
                }

                curGroup = new CollectionView.InventoryGroup(GROUP_ID_NORMAL)
                    .setDataIndexStart(dataIndex)
                    .setHeaderLabel(groupName)
                    .setShowHeader(true)
                    .setDisplayCols(normalColumns)
                    .setItemCount(1);
            } else {
                curGroup.incrementItemCount();
            }
        }

        if (curGroup != null) {
            inventory.addGroup(curGroup);
        }

        return inventory;
    }

}
