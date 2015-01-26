package com.getnerdify.android.notifier.util;

import android.util.SparseArray;

public class Lists {

    /** Clones a SparseArray. */
    public static <E> SparseArray<E> cloneSparseArray(SparseArray<E> orig) {
        SparseArray<E> result = new SparseArray<E>();

        for (int i = 0; i < orig.size(); i++) {
            result.put(orig.keyAt(i), orig.valueAt(i));
        }

        return result;
    }

}
