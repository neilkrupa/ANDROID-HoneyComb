package com.nakrupadevelopment.nakrupa.honeycomb;

/**
 * Created by NAKRUPA on 2/20/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ConnectionPost extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        String post_user_url = "http://scc-honeycomb.lancaster.ac.uk/post_user.php";
        String post_project_url = "http://scc-honeycomb.lancaster.ac.uk/post_project.php";
        String post_puser_url = "http://scc-honeycomb.lancaster.ac.uk/post_puser.php";
        String post_commitment_url = "http://scc-honeycomb.lancaster.ac.uk/post_commitment.php";
        String post_commitment_member_url = "http://scc-honeycomb.lancaster.ac.uk/post_commitment_member.php";
        String post_todo_url = "http://scc-honeycomb.lancaster.ac.uk/post_todo.php";
        String update_project_url = "http://scc-honeycomb.lancaster.ac.uk/update_project.php";
        String update_commitment_url = "http://scc-honeycomb.lancaster.ac.uk/update_commitment.php";
        String refactor_req_url = "http://scc-honeycomb.lancaster.ac.uk/refactor_req.php";
        String post_completion_url = "http://scc-honeycomb.lancaster.ac.uk/post_completion.php";
        String post_start_url = "http://scc-honeycomb.lancaster.ac.uk/post_start.php";
        String post_assist_url = "http://scc-honeycomb.lancaster.ac.uk/post_assist.php";
        String post_response_url = "http://scc-honeycomb.lancaster.ac.uk/post_response.php";
        String post_settings_url = "http://scc-honeycomb.lancaster.ac.uk/post_settings.php";

        String method = params[0];

        if(method.equals("post_user")) {
            String username = params[1];
            String password = params[2];
            String email = params[3];
            try {
                URL url = new URL(post_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                                URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(method.equals("post_project")){
            String uid = params[1];
            String title = params[2];
            String desc = params[3];
            String dead = params[4];
            try {
                URL url = new URL(post_project_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                                URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(title,"UTF-8")+"&"+
                                URLEncoder.encode("desc","UTF-8")+"="+URLEncoder.encode(desc,"UTF-8")+"&"+
                                URLEncoder.encode("dead","UTF-8")+"="+URLEncoder.encode(dead,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_puser")){
            String uid = params[1];
            String pid = params[2];
            try {
                URL url = new URL(post_puser_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                                URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_commitment")){
            String pid = params[1];
            String name = params[2];
            String desc = params[3];
            String cpoints = params[4];
            String csdeadln = params[5];
            String cfeadline = params[6];
            try {
                URL url = new URL(post_commitment_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8")+"&"+
                                URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                                URLEncoder.encode("desc","UTF-8")+"="+URLEncoder.encode(desc,"UTF-8")+"&"+
                                URLEncoder.encode("cpoints","UTF-8")+"="+URLEncoder.encode(cpoints,"UTF-8")+"&"+
                                URLEncoder.encode("csdeadln","UTF-8")+"="+URLEncoder.encode(csdeadln,"UTF-8")+"&"+
                                URLEncoder.encode("cfeadline","UTF-8")+"="+URLEncoder.encode(cfeadline,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_commitment_member")){
            String uid = params[1];
            String cid = params[2];
            String pid = params[3];

            try {
                URL url = new URL(post_commitment_member_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                                URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&"+
                                URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_todo")){
            String pid = params[1];
            String title = params[2];
            String cid = params[3];

            try {
                URL url = new URL(post_todo_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8")+"&"+
                                URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(title,"UTF-8")+"&"+
                                URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("update_project")){
            String pid = params[1];
            String name = params[2];
            String description = params[3];
            String deadline = params[4];

            try {
                URL url = new URL(update_project_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8")+"&"+
                                URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                                URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(description,"UTF-8")+"&"+
                                URLEncoder.encode("deadline","UTF-8")+"="+URLEncoder.encode(deadline,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("update_commitment")){
            String cid = params[1];
            String name = params[2];
            String description = params[3];
            String points = params[4];
            String fdeadline = params[5];
            String sdeadline = params[6];
            String pid = params[7];

            try {
                URL url = new URL(update_commitment_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&"+
                                URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                                URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(description,"UTF-8")+"&"+
                                URLEncoder.encode("points","UTF-8")+"="+URLEncoder.encode(points,"UTF-8")+"&"+
                                URLEncoder.encode("fdeadline","UTF-8")+"="+URLEncoder.encode(fdeadline,"UTF-8")+"&"+
                                URLEncoder.encode("sdeadline","UTF-8")+"="+URLEncoder.encode(sdeadline,"UTF-8")+"&"+
                                URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("refactor_req")){
            String lid = params[2];
            String cid = params[1];
            String pid = params[3];

            try {
                URL url = new URL(refactor_req_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8")+"&"+
                                URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&"+
                                URLEncoder.encode("lid","UTF-8")+"="+URLEncoder.encode(lid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                System.out.println(response);
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_completion")){
            String cid = params[1];
            String pid = params[2];
            String uid = params[3];

            try {
                URL url = new URL(post_completion_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&"+
                                URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8")+"&"+
                                URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_start")){
            String cid = params[1];
            String lid = params[2];
            String pid = params[3];

            try {
                URL url = new URL(post_start_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&"+
                                URLEncoder.encode("lid","UTF-8")+"="+URLEncoder.encode(lid,"UTF-8")+"&"+
                                URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_assist")){
            String cid = params[1];
            String pid = params[2];
            String desc = params[3];
            String code = params[4];

            try {
                URL url = new URL(post_assist_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&"+
                                URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8")+"&"+
                                URLEncoder.encode("desc","UTF-8")+"="+URLEncoder.encode(desc,"UTF-8")+"&"+
                                URLEncoder.encode("code","UTF-8")+"="+URLEncoder.encode(code,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_response")){
            String cid = params[1];
            String pid = params[2];
            String rtitle = params[3];
            String rdescrip = params[4];

            System.out.println(":::::::::::::::::::::" + rtitle);
            System.out.println(":::::::::::::::::::::" + rdescrip);

            try {
                URL url = new URL(post_response_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&"+
                                URLEncoder.encode("pid","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8")+"&"+
                                URLEncoder.encode("rtitle","UTF-8")+"="+URLEncoder.encode(rtitle,"UTF-8")+"&"+
                                URLEncoder.encode("rdescrip","UTF-8")+"="+URLEncoder.encode(rdescrip,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                System.out.println(response);
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("post_settings")){
            String pwd = params[1];
            String email = params[2];
            String uid = params[3];
            try {
                URL url = new URL(post_settings_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data =
                        URLEncoder.encode("pwd","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8")+"&"+
                                URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                                URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}