//package com.bryan.example.net.rest;
//
//import java.io.BufferedWriter;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.lang.ref.WeakReference;
//import java.net.HttpURLConnection;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpResponseException;
//
//
//import android.os.AsyncTask;
//import android.util.Log;
//
////TODO 方法过期
///**
// * 三个参数Params, Progress, Result
// *  -Params     传进来的数据类型  不需要就写Void
// *  -Progress   进度 Integer
// *  -Result     返回的数据类型  Object
// * @author bryan
// *
// * HTTP信息：头信息+正文（POST参数写在正文里,GET写在URL字符串里面）
// *
// */
//public class RestTask extends AsyncTask<Void, Integer, Object>
//{
//
//    private static final String TAG = "RestTask";
//
//    public interface ResponseCallback{
//        public void onRequestSucess(String response);
//
//        public void onRequestError(Exception error);
//    }
//
//    public interface ProgressCallback{
//        public void onProgressUpdate(int progress);//我在想两个回掉可以弄成一个么
//    }
//
//    private HttpURLConnection mConn;
//    private String mFromBody;
//    private File mUploadFile;
//    private String mUploadFileName;
//
//    //Activity回掉，使用WeakReference来避免阻塞操作导致链接对象保留在内存中
//    private WeakReference<ResponseCallback> mResponseCallback;
//    private WeakReference<ProgressCallback> mProgressCallback;
//
//    public RestTask(HttpURLConnection conn)
//    {
//        mConn = conn;
//    }
//
//     public void setmFormBody(List<NameValuePair> fromData)
//    {
//        if (fromData == null)
//        {
//            mFromBody = null;
//            return;
//        }
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < fromData.size(); i++)
//        {
//            NameValuePair item = fromData.get(i)    ;
//            sb.append(URLEncoder.encode(item.getName()));
//            sb.append("=");
//            sb.append(URLEncoder.encode(item.getValue()));
//            if (i!= (fromData.size() - 1))
//            {
//                sb.append("&");
//            }
//        }
//        mFromBody = sb.toString();
//    }
//
//     public void setUploadFile(File file, String fileName){
//         mUploadFile = file;
//         mUploadFileName = fileName;
//     }
//
//     public void setResponseCallback(ResponseCallback callback){
//         mResponseCallback = new WeakReference<RestTask.ResponseCallback>(callback);
//     }
//
//     public void setmProgressCallback(ProgressCallback callback)
//     {
//        mProgressCallback = new WeakReference<RestTask.ProgressCallback>(callback);
//     }
//
//     private void writeMultipart(String boundary, String charset, OutputStream output, boolean writeContent) throws IOException{
//         BufferedWriter writer = null;
//        try
//        {
//            writer = new BufferedWriter(new OutputStreamWriter(output,Charset.forName(charset)),8192);
//            //发送表单数据
//            if (mFromBody != null)
//            {
//                writer.write("--"+boundary);
//                writer.write("\r\n");
//                writer.write("Content-Disposition:form-data;name=\"parameters\"");
//                writer.write("\r\n");
//                writer.write("\r\n");
//                if (writeContent)
//                {
//                    writer.write(mFromBody);
//                }
//                writer.write("\r\n");
//                writer.flush();
//            }
//
//            //发送二进制文件
//            writer.write("--"+boundary);
//            writer.write("\r\n");
//            writer.write("Content-Disposition:form-data;name=\""+mUploadFileName+"\"; filename = \""+mUploadFile.getName() +"\"");
//            writer.write("\r\n");
//            writer.write("Content-Transfer-Encoding:binary");
//            writer.write("\r\n");
//            writer.write("\r\n");
//            writer.flush();
//            if (writeContent)
//            {
//                InputStream input = null;
//                try
//                {
//                    input = new FileInputStream(mUploadFile);
//                    byte[] buffer = new byte[1024];
//                    for (int length = 0; (length = input.read(buffer)) > 0;)
//                    {
//                        output.write(buffer,0,length);
//                    }
//                    //不要关闭OutputStream，finally中进行
//                    output.flush();
//                }
//                catch (Exception e)
//                {
//                    Log.w(TAG, e);
//                }finally{
//                    if (input != null)
//                    {
//                        input.close();
//                    }
//                }
//            }
//
//            //这个回车换行标志着二进制数据块的结束
//            writer.write("\r\n");
//            writer.flush();
//
//            //multipart/form-data的结束
//            writer.write("--"+boundary+"--");
//            writer.write("\r\n");
//            writer.flush();
//
//        }finally{
//            if (writer != null)
//            {
//                writer.close();
//            }
//        }
//     }
//
//    private void writeFormData(String charset, OutputStream output) throws Exception
//    {
//        try
//        {
//            output.write(mFromBody.getBytes(charset));
//            output.flush();
//        }
//        finally
//        {
//            if (output != null)
//            {
//                output.close();
//            }
//        }
//    }
//
//
//
//
//    @Override
//    protected Object doInBackground(Void... params)
//    {
//        //生成用来标识界限的随机字符串
//        String boundary = Long.toHexString(System.currentTimeMillis());
//        String charset = Charset.defaultCharset().displayName();
//
//        try
//        {
//            //如果可以的话创建输出流
//            if (mUploadFile != null)
//            {
//                //我们必须做一次复合请求
//                mConn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
//                //计算extra元数据的大小
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                writeMultipart(boundary, charset, bos, false);
//                byte[] extra = bos.toByteArray();
//                int contentLength = extra.length;
//                //将文件的大小加到length上
//                contentLength += mUploadFile.length();
//                //如果存在表单主体，把它也加到length上
//                if (mFromBody != null)
//                {
//                    contentLength += mFromBody.length();
//                }
//
//                mConn.setFixedLengthStreamingMode(contentLength);
//            }else if (mFromBody != null)
//            {
//                //这种情况只是发送表单数据
//                mConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="+charset);
//                mConn.setFixedLengthStreamingMode(mFromBody.length());
//            }
//
//            //建立与服务器端的链接，生成了HTTP的头信息
//            //openConnection的执行还是本地操作
//            mConn.connect();
//
//            //接下来组装数据，写HTTP数据的正文
//            //正文通过outputStream写入
//            //outputStream是一个字符串流，往里面写入的东西会保存在缓存中。当流关闭的时候会自动生成HTTP的正文
//            //在getInputStream()函数调用的时候，就会把准备好的http请求 正式发送到服务器了，
//            //然后返回一个输入流，用于读取服务器对于此次http请求的返回信息。
//
//            //如果可以的话（对于POST）,创建输出流
//            if (mUploadFile != null)
//            {
//                OutputStream out = mConn.getOutputStream();//其实这句话包含了  mConn.connect();，上面可以不调用
//                writeMultipart(boundary, charset, out, true);
//            }else if (mFromBody != null) {
//                OutputStream out = mConn.getOutputStream();
//                writeFormData(charset, out);
//            }
//
//            //获取响应数据
//            //先检查响应代码的值，以保证没有服务器端的错误
//            int status = mConn.getResponseCode();
//            if (status >= 300)
//            {
//                String message = mConn.getResponseMessage();
//                return new HttpResponseException(status, message);
//            }
//
//            //将组装好的HTTP数据发送到服务端。然后获得输入流
//            InputStream in = mConn.getInputStream();
//            String encoding = mConn.getContentEncoding();
//            int contentLength = mConn.getContentLength();
//
//            if (encoding == null)
//            {
//                encoding = "UTF-8";
//            }
//
//            byte[] buffer = new byte[1024];
//
//            int length = contentLength >0? contentLength:0;
//            ByteArrayOutputStream out = new ByteArrayOutputStream(length);
//
//            int downloadBytes = 0;
//            int read ;
//            while ((read = in.read(buffer)) != -1)
//            {
//                downloadBytes += read;
//                publishProgress((downloadBytes * 100)/contentLength);//发送进度
//                out.write(buffer,0,read);
//            }
//            return new String(out.toByteArray(), encoding);
//        }
//        catch (Exception e)
//        {
//            Log.w(TAG, e);
//            return e;
//        }finally{
//            if (mConn != null)
//            {
//                mConn.disconnect();
//            }
//        }
//    }
//
//    @Override
//    protected void onProgressUpdate(Integer... values)
//    {
//       //更新进度UI
//        if (mProgressCallback != null && mProgressCallback.get() != null)
//        {
//            mProgressCallback.get().onProgressUpdate(values[0]);
//        }
//    }
//
//    @Override
//    protected void onPostExecute(Object result)
//    {
//        if (mResponseCallback != null && mResponseCallback.get()!= null)
//        {
//            if (result instanceof String)
//            {
//                mResponseCallback.get().onRequestSucess((String) result);
//            }else if (result instanceof Exception) {
//                mResponseCallback.get().onRequestError((Exception) result);
//            }else {
//                mResponseCallback.get().onRequestError(new IOException("Unknown Error Contacting Host"));
//            }
//        }
//    }
//
//}
