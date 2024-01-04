package com.wind.anuroopjodi.Helper;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class PermissionManager {

    // Constants for different groups of permissions
    public static final int PERMISSION_CAMERA_AND_MEDIA_TERAMASU = 1;
    public static final int PERMISSION_CAMERA_ONLY_AV_10 = 2;

    // Callback interface for permission results
    public interface PermissionCallback {
        void onPermissionGranted();
        void onPermissionDenied();
    }

    // Function to request permissions based on the permissionType
    public static void requestPermissions(Activity activity, int permissionType, PermissionCallback callback) {
        Dexter.withActivity(activity)
                .withPermissions(getPermissions(permissionType))
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            callback.onPermissionGranted();
                        } else {
                            callback.onPermissionDenied();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog(activity);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity, "Error occurred!", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    // Function to show settings dialog
    private static void showSettingsDialog(Activity activity) {
        // Show alert dialog navigating to Settings
        // Implement this based on your requirements
    }

    // Function to get the array of permissions based on permissionType
    private static String[] getPermissions(int permissionType) {
        switch (permissionType) {
            case PERMISSION_CAMERA_AND_MEDIA_TERAMASU:
                return new String[]{
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_AUDIO,
                        Manifest.permission.CAMERA
                };
            case PERMISSION_CAMERA_ONLY_AV_10:
                return new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
            default:
                return new String[0];
        }
    }
}

