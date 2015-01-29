package com.getnerdify.android.notifier.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getnerdify.android.notifier.R;
import com.getnerdify.android.notifier.provider.NotifierContact.NotificationsColumns;
import com.getnerdify.android.notifier.ui.widget.CollectionView;
import com.getnerdify.android.notifier.ui.widget.CollectionViewCallbacks;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationsFragment extends Fragment implements CollectionViewCallbacks {

    public static final String TAG = NotificationsFragment.class.getSimpleName();

    private Callbacks mCallbacks;

    private CollectionView mCollectionView;
    private List<ParseObject> mCursor = null;
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
    public void onResume() {
        super.onResume();

//        updateCollectionView();
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
        ParseObject event = mCursor.get(dataIndex);

        if (event == null) {
            return;
        }

//        ImageView photoView = (ImageView) view.findViewById(R.id.notification_photo_colored);
        TextView titleView = (TextView) view.findViewById(R.id.notification_title);
        TextView descriptionView = (TextView) view.findViewById(R.id.notification_description);
        TextView dateView = (TextView) view.findViewById(R.id.notification_date);

//        String imageUrl = event.getString(NotificationsColumns.NOTIFICATION_IMAGE_URL) + "?index=" + dataIndex;

//        if ( ! imageUrl.isEmpty()) {
//            Picasso.with(context)
//                .load(imageUrl)
//                .into(photoView);
//        } else {
//            photoView.setImageDrawable(null);
//        }

        titleView.setText(event.getString(NotificationsColumns.NOTIFICATION_TITLE));
        descriptionView.setText(event.getString(NotificationsColumns.NOTIFICATION_DESCRIPTION));

        DateFormat shortTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        Date startDate = event.getCreatedAt();

        dateView.setText(shortTimeFormat.format(startDate));
    }

    public void setContentTopClearance(int topClearance) {
        if (mCollectionView != null) {
            mCollectionView.setContentTopClearance(topClearance);
        }
    }

    private void updateCollectionView() {
        ParseQuery<ParseObject> query = new ParseQuery<>(NotificationsColumns.NOTIFICATION__CLASS);
        query.orderByAscending(NotificationsColumns.NOTIFICATION_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    mCursor = parseObjects;

                    CollectionView.Inventory inv = prepareInventory();

                    mCollectionView.setCollectionAdapter(NotificationsFragment.this);
                    mCollectionView.updateInventory(inv);

                    mEmptyView.setVisibility((mCursor.size() == 0) ? View.VISIBLE : View.GONE);
                } else {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    private CollectionView.Inventory prepareInventory() {
        CollectionView.Inventory inventory = new CollectionView.Inventory();
        CollectionView.InventoryGroup curGroup = null;
        int dataIndex = -1;
        int normalColumns = getResources().getInteger(R.integer.notifications_columns);
        SimpleDateFormat monthOfYearFormat = new SimpleDateFormat("EEEE, MMMM d");

        for (ParseObject notification : mCursor) {
            ++dataIndex;
            Date startDate = notification.getCreatedAt();
            String groupName = monthOfYearFormat.format(startDate);

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
