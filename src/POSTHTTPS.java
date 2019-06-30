import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.US_ASCII;


public class POSTHTTPS {
    public static void main(String[] args) throws Exception{

        SSLContext sc = SSLContext.getDefault();
        SSLSocketFactory ssf = sc.getSocketFactory();

        SSLSocket socket = (SSLSocket) ssf.createSocket("hotspot.um.ac.ir", 443);
        socket.startHandshake();

//        Socket socket = new Socket("hotspot.um.ac.ir", 80);

        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.US_ASCII));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = scanner.next();
        System.out.println("Enter your password:");
        String password = scanner.next();

        String data = URLEncoder.encode("username", "ASCII") + "=" + URLEncoder.encode(username, "ASCII") + "&" +
                URLEncoder.encode("password", "ASCII") + "=" + URLEncoder.encode(MD5.getMd5(password), "ASCII") + "&" +
                URLEncoder.encode("dst", "ASCII") + "=&" +
                URLEncoder.encode("popup", "ASCII") + "=" + URLEncoder.encode("true", "ASCII");

        wr.write("POST /login HTTP/1.1\r\n");
        wr.write("Host: hotspot.um.ac.ir\r\n");
        wr.write("Content-Length: " + data.length() + "\r\n");
        wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
        wr.write("\r\n");
        wr.write(data);

        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        wr.close();
        rd.close();
        socket.close();
    }
}