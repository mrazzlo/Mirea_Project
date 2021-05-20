package ru.mirea.zlotov.mireaproject.ui.tools;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

import ru.mirea.zlotov.mireaproject.R;

public class ToolsFragment extends Fragment implements SensorEventListener {

    private SensorManager sensor_manager;
    private Sensor sensor;

    private TextView text_view_azimuth;
    private TextView text_view_pitch;
    private TextView text_view_roll;
    private ImageView image_view;

    private final static int RECORD_AUDIO = 0;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private static final int CAMERA_REQUEST = 0;

    private boolean is_work = false;
    private Uri image_uri;

    private MediaRecorder media_recorder;
    public static String file_name;
    public static String DIRECTORY_MUSIC = "Music";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        sensor_manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        text_view_azimuth = root.findViewById(R.id.text_view_azimuth);
        text_view_pitch = root.findViewById(R.id.text_view_pitch);
        text_view_roll = root.findViewById(R.id.text_view_roll);
        image_view = root.findViewById(R.id.imag_photo);
        Button button_shot = root.findViewById(R.id.button_shot);
        Button button_play = root.findViewById(R.id.button_play_record);
        Button button_start = root.findViewById(R.id.button_start_recording);
        Button button_stop = root.findViewById(R.id.button_stop_recording);
        Button button_stop_playing = root.findViewById(R.id.button_stop_playing);

        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        file_name = cw.getExternalFilesDir(DIRECTORY_MUSIC) + "/record.3gpp";

        int cameraPermissionStatus = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus
                == PackageManager.PERMISSION_GRANTED) {
            is_work = true;
        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.
                            CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }

        View.OnClickListener photoClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (is_work) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String authorities = getContext().getPackageName() + ".fileprovider";
                    image_uri = FileProvider.getUriForFile(getContext(), authorities, photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        };

        button_shot.setOnClickListener(photoClickListener);

        View.OnClickListener startClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);
                } else {
                    try {
                        releaseRecorder();

                        File outFile = new File(file_name);
                        if (outFile.exists()) {
                            outFile.delete();
                        }

                        media_recorder = new MediaRecorder();
                        media_recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        media_recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        media_recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        media_recorder.setOutputFile(file_name);
                        media_recorder.prepare();
                        media_recorder.start();
                        Toast.makeText(getContext(), "Recording is started", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        button_start.setOnClickListener(startClickListener);

        View.OnClickListener stopClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media_recorder != null) {
                    media_recorder.stop();
                }
                Toast.makeText(getContext(), "Recording is stopped", Toast.LENGTH_SHORT).show();
            }
        };

        button_stop.setOnClickListener(stopClickListener);

        View.OnClickListener playClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getContext(), RecordPlayService.class));
            }
        };

        button_play.setOnClickListener(playClickListener);

        View.OnClickListener unplayClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getContext(), RecordPlayService.class));
            }
        };

        button_stop_playing.setOnClickListener(unplayClickListener);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensor_manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensor_manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth = event.values[0];
        float pitch = event.values[1];
        float roll = event.values[2];

        text_view_azimuth.setText(String.valueOf(azimuth));
        text_view_pitch.setText(String.valueOf(pitch));
        text_view_roll.setText(String.valueOf(roll));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            image_view.setImageURI(image_uri);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                is_work = true;
            } else {
                is_work = false;
            }
        }
    }

    private void releaseRecorder() {
        if (media_recorder != null) {
            media_recorder.release();
            media_recorder = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseRecorder();
    }
}