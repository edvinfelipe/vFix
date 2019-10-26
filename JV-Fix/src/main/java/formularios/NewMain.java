/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Windows 8.1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(File file) {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;

        InputStream is = null;
        OutputStream os = null;
        boolean ret = false;
        String StrMessage = "";
        String exsistingFileName = file.getAbsolutePath();

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;

        int maxBufferSize = 1 * 1024 * 1024;

        String responseFromServer = "";

        String urlString = "http://localhost:8000/api/imagenes/?codigo=PF9XIF04X2OL8CMKSCRQK3PFOZIB83";

        try {
            //------------------ CLIENT REQUEST

            FileInputStream fileInputStream = new FileInputStream(new File(exsistingFileName));

            // open a URL connection to the Servlet 
            URL url = new URL(urlString);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            dos = new DataOutputStream(conn.getOutputStream());

            
            dos.writeBytes("{\"imagen\": \""+exsistingFileName+"\", \"codigoEvento\": \"PF9XIF04X2OL8CMKSCRQK3PFOZIB83\"}");

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            System.out.println("From ServletCom CLIENT REQUEST:" + ex);
        } catch (IOException ioe) {
            System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
        }

        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream(conn.getInputStream());
            String str;
            while ((str = inStream.readLine()) != null) {
                System.out.println("Server response is: " + str);
                System.out.println("");
            }
            inStream.close();

        } catch (IOException ioex) {
            System.out.println("From (ServerResponse): " + ioex);

        }

    }
}
