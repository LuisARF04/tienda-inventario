package com.tienda.inventario;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Guarda un archivo de texto donde el usuario elija, con la pantalla
 * "Guardar como" de Android (Storage Access Framework). No pide permisos.
 *
 * JS: Capacitor.Plugins.FileSaver.save({fileName, mimeType, data})
 *     -> {saved:true, uri} o {saved:false} si el usuario canceló
 */
@CapacitorPlugin(name = "FileSaver")
public class FileSaverPlugin extends Plugin {

    @PluginMethod
    public void save(PluginCall call) {
        if (call.getString("data") == null) {
            call.reject("No hay datos para guardar");
            return;
        }
        Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(call.getString("mimeType", "application/json"));
        i.putExtra(Intent.EXTRA_TITLE, call.getString("fileName", "respaldo.json"));
        startActivityForResult(call, i, "onSaveResult");
    }

    @ActivityCallback
    private void onSaveResult(PluginCall call, ActivityResult result) {
        if (call == null) return;
        Intent data = result.getData();
        if (result.getResultCode() != Activity.RESULT_OK || data == null || data.getData() == null) {
            JSObject r = new JSObject();
            r.put("saved", false);
            call.resolve(r);
            return;
        }
        Uri uri = data.getData();
        byte[] bytes = call.getString("data", "").getBytes(StandardCharsets.UTF_8);
        try {
            OutputStream os;
            try {
                os = getContext().getContentResolver().openOutputStream(uri, "wt");
            } catch (Exception e) {
                os = getContext().getContentResolver().openOutputStream(uri, "w");
            }
            if (os == null) throw new Exception("no se pudo abrir el archivo");
            try {
                os.write(bytes);
                os.flush();
            } finally {
                os.close();
            }
        } catch (Exception e) {
            String m = e.getMessage();
            call.reject("No se pudo guardar el archivo" + (m != null ? ": " + m : ""));
            return;
        }
        JSObject r = new JSObject();
        r.put("saved", true);
        r.put("uri", uri.toString());
        call.resolve(r);
    }
}
