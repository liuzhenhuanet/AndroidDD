package com.androiddd.utils.java;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by liuzhenhua on 2017/11/13.
 */

public class JavaSoftKeyboardDetector {
    private static final int KEYBOARD_VISIBLE_THRESHOLD_DIP = 100;

    public static DefaultUnRegister registerKeyboardEventListener(Activity activity, final OnKeyboardEventListener listener) {
        if (activity == null || listener == null) {
            return null;
        }

        if (activity.getWindow() != null) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            if (attributes != null) {
                int softInputMode = attributes.softInputMode;
                if (softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
                        || softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) {
                    return null;
                }
            }
        }

        final View activityRoot = getActivityRoot(activity);

        if (activityRoot == null) {
            return null;
        }

        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

            private final Rect visibleFrame = new Rect();
            private final int threshold = JavaUnitUtil.dip2px(activityRoot.getContext(), KEYBOARD_VISIBLE_THRESHOLD_DIP);
            private boolean wasKeyboardOpened = false;
            private int visibleHeight = -1;

            @Override
            public void onGlobalLayout() {
                activityRoot.getWindowVisibleDisplayFrame(visibleFrame);
                int heightDiff = activityRoot.getRootView().getHeight() - visibleFrame.height();
                boolean isOpen = heightDiff > threshold;
                if (visibleFrame.height() == this.visibleHeight) {
                    return;
                }
                this.visibleHeight = visibleFrame.height();

                wasKeyboardOpened = isOpen;
                listener.onKeyboardEvent(isOpen, heightDiff, visibleFrame);
            }
        };

        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        return new DefaultUnRegister(activity, layoutListener);
    }

    public static boolean isKeyboardVisible(Activity activity) {
        Rect windowFrame = new Rect();
        View root = getActivityRoot(activity);

        if (root != null) {
            root.getWindowVisibleDisplayFrame(windowFrame);
            int heightDiff = root.getRootView().getHeight() - windowFrame.height();
            return heightDiff > JavaUnitUtil.dip2px(activity, KEYBOARD_VISIBLE_THRESHOLD_DIP);
        }
        return false;
    }

    public static int getKeyboardHeight(Activity activity) {
        Rect windowFrame = new Rect();
        View root = getActivityRoot(activity);

        if (root != null) {
            root.getWindowVisibleDisplayFrame(windowFrame);
            int heightDiff = root.getRootView().getHeight() - windowFrame.height();
            if (heightDiff > JavaUnitUtil.dip2px(activity, KEYBOARD_VISIBLE_THRESHOLD_DIP)) {
                return heightDiff;
            }
        }
        return 0;
    }

    public static
    @Nullable
    View getActivityRoot(Activity activity) {
        if (activity != null) {
            return activity.findViewById(android.R.id.content);
        }
        return null;
    }

    public interface OnKeyboardEventListener {
        void onKeyboardEvent(boolean isShown, int softKeyboardHeight, Rect visibleFrame);
    }

    public static final class DefaultUnRegister {

        private WeakReference<Activity> activityRef;
        private WeakReference<ViewTreeObserver.OnGlobalLayoutListener> listenerRef;

        public DefaultUnRegister(Activity activity, ViewTreeObserver.OnGlobalLayoutListener listener) {
            this.activityRef = new WeakReference<>(activity);
            this.listenerRef = new WeakReference<>(listener);
        }

        @SuppressLint("ObsoleteSdkInt")
        public void execute() {
            Activity activity = activityRef.get();
            ViewTreeObserver.OnGlobalLayoutListener listener = listenerRef.get();

            if (activity != null && listener != null) {
                View root = getActivityRoot(activity);
                if (root != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        root.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
                    } else {
                        root.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
                    }
                }
            }

            activityRef.clear();
            listenerRef.clear();
        }
    }
}
