package com.example.mamton.testapp.utils;

import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import timber.log.Timber;

public class StreamUtils {

    public static void close(@Nullable final Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Timber.e(e, "close stream error");
            }
        }
    }

    /**
     * The method should be used when bytes of some stream are needed.
     *
     * @param source the source stream for result.
     * @return bytes of source stream
     * @throws java.io.IOException problems with read data from source stream.
     */
    public static byte[] readBytesFromStream(InputStream source) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int length;
        while ((length = source.read(buffer)) >= 0) {
            baos.write(buffer, 0, length);
        }

        return baos.toByteArray();
    }

    public static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
