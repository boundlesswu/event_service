package com.vorxsoft.ieye.eventservice.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import com.alibaba.fastjson.JSON;

/**
 * http请求工具类
 *
 * @author 申志峰<br/>
 * @使用方法<br/> 在使用时必须严格命名参数map：<br/>
 * queryMap：一般的查询参数<br/>
 * headerMap：需要放在header中的参数，比如accessToken<br/>
 * bodyMap：post请求中的参数一般放在body中<br/>
 */

public class HttpClientUtils {
  private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

  private static final long serialVersionUID = 3954337501397850688L;
  private static final int OK = 200;// OK: Success!
  private static final int NOT_MODIFIED = 304;// Not Modified: There was no
  // new data to return.
  private static final int BAD_REQUEST = 400;// Bad Request: The request was
  // invalid. An accompanying
  // error message will explain
  // why. This is the status code
  // will be returned during rate
  // limiting.
  private static final int NOT_AUTHORIZED = 401;// Not Authorized:
  // Authentication
  // credentials were missing
  // or incorrect.
  private static final int FORBIDDEN = 403;// Forbidden: The request is
  // understood, but it has been
  // refused. An accompanying
  // error message will explain
  // why.
  private static final int NOT_FOUND = 404;// Not Found: The URI requested is
  // invalid or the resource
  // requested, such as a user,
  // does not exists.
  private static final int NOT_ACCEPTABLE = 406;// Not Acceptable: Returned by
  // the Search API when an
  // invalid format is
  // specified in the request.
  private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server
  // Error: Something
  // is broken. Please
  // post to the group
  // so the Weibo team
  // can investigate.
  private static final int BAD_GATEWAY = 502;// Bad Gateway: Weibo is down or
  // being upgraded.
  private static final int SERVICE_UNAVAILABLE = 503;// Service Unavailable:
  // The Weibo servers are
  // up, but overloaded
  // with requests. Try
  // again later. The
  // search and trend
  // methods use this to
  // indicate when you are
  // being rate limited.
  private static final String CHARSET = "UTF-8";

  public static String doPostHttp(String url, String message, int level) {
    Map<String, Object> params = new HashMap<>();
    params.put("message", message);
    params.put("level", String.valueOf(level));
    //return doPostHttp(url,params);
    return sendPost(url, params);
  }

  public static String sendPost(String targetURL, Map<String, Object> urlParameters) {
    HttpURLConnection connection = null;
    String param = JSON.toJSONString(urlParameters);
    String result = "";
    PrintWriter pw = null;
    BufferedReader br = null;
    try {
      // Create connection
      URL url = new URL(targetURL);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Length", Integer.toString(param.getBytes().length));
      connection.setRequestProperty("Accept", "*/*");
      connection.setRequestProperty("Content-type", "application/json");
      connection.setRequestProperty("Connection", "Keep-Alive");
      connection.setConnectTimeout(5 * 1000);
      connection.setReadTimeout(3 * 1000);
      connection.setUseCaches(false);
      connection.setDoOutput(true);
      connection.setDoInput(true);

      // Send request
      if (param != null) {
        pw = new PrintWriter(connection.getOutputStream());
        pw.write(param);
        pw.flush();
      }
      // Get Response
      StringBuilder response = new StringBuilder(); // or StringBuffer if
      if (connection.getResponseCode() == 200) {
        InputStream is = connection.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
          response.append(line);
          response.append('\r');
        }
      }

      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      try {
        if (pw != null) {
          pw.close();
        }
        if (br != null) {
          br.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

//  public static String doPostHttp(String targetURL, Map<String, Object> params){
//    HttpURLConnection connection = null;
//    String param = JSON.toJSONString(params);
//    String result = "";
//    PrintWriter pw = null;
//    BufferedReader br = null;
//    try {
//      // Create connection
//      // Create connection
//      URL url = new URL(targetURL);
//      connection = (HttpURLConnection) url.openConnection();
//      connection.setRequestMethod("POST");
//      connection.setRequestProperty("Content-Length", Integer.toString(param.getBytes().length));
//      connection.setRequestProperty("Accept", "*/*");
//      connection.setRequestProperty("Content-type", "application/json");
//      connection.setRequestProperty("Connection", "Keep-Alive");
//      connection.setConnectTimeout(5 * 1000);
//      connection.setReadTimeout(3 * 1000);
//      connection.setUseCaches(false);
//      connection.setDoOutput(true);
//      connection.setDoInput(true);
//
//      // Send request
//      if (param != null) {
//        pw = new PrintWriter(connection.getOutputStream());
//        pw.write(param);
//        pw.flush();
//      }
//      // Get Response
//      StringBuilder response = new StringBuilder(); // or StringBuffer if
//      if (connection.getResponseCode() == 200) {
//        InputStream is = connection.getInputStream();
//        br = new BufferedReader(new InputStreamReader(is));
//        String line;
//        while ((line = br.readLine()) != null) {
//          response.append(line);
//          response.append('\r');
//        }
//      }
//      result = "ok";
//      return result;
//    } catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    } finally {
//      try {
//        if (pw != null) {
//          pw.close();
//        }
//        if (br != null) {
//          br.close();
//        }
////        if(connection!=null){
////          connection.disconnect();
////        }
//      } catch (IOException ex) {
//        ex.printStackTrace();
//      }
//    }
//  }
  /**
   * 以post方式发送http请求
   *
   * @param params
   * @param url
   * @return
   */
//  public static String doPostHttp(String url, Map<String, Object> params) {
//    CloseableHttpClient httpclient = HttpClients.createDefault();
//
//    String fullUrl = new String(url);
//    HttpPost httpPost = new HttpPost(fullUrl);
//
//    String message = "";
//    String level = "";
//    String bodyContent = "";
//    try {
//      if (null != params.get("message")) {
//        message = (String) params.get("message");
//      }
//      if (null != params.get("level")) {
//        level = (String) params.get("level");
//      }
//      if (message.length() == 0  ||  level.length() == 0) {
//        logger.error("message.isEmpty() or level.isEmpty()");
//        return null;
//      }
//      bodyContent = JSON.toJSONString(message) + JSON.toJSONString(level);
//      System.out.println("bodyContent : " + bodyContent);
//
//
//      StringEntity entity = new StringEntity(bodyContent);
//      entity.setContentEncoding(CHARSET);
//      entity.setContentType("application/x-www-form-urlencoded");// 发送json数据需要设置contentType
//
//      httpPost.setEntity(entity);
//
//      // 创建响应控制器
//      ResponseHandler<HttpEntity> handler = createResponseHandler(url);
//      HttpEntity httpEntity = httpclient.execute(httpPost, handler);
//
//      // 将结果转化为json返回
//      String result = EntityUtils.toString(httpEntity, "UTF-8");
//      EntityUtils.consume(httpEntity);
//      return result;
//    } catch (Exception e) {
//      logger.error("post请求失败! error: " + e.getMessage());
//    } finally {
//      try {
//        httpclient.close();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//    return null;
//  }

  /**
   * 以post方式发送http请求
   *
   * @param params
   * @param url
   * @return
   */
  public static String doPostHttps(String url, Map<String, Object> params) {
    try {
      HttpClient httpclient = new DefaultHttpClient();
      // Secure Protocol implementation.
      SSLContext ctx = SSLContext.getInstance("TLS");
      // Implementation of a trust manager for X509 certificates
      X509TrustManager tm = new X509TrustManager() {

        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

        }

        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx);
      ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = httpclient.getConnectionManager();
      // register https protocol in httpclient's scheme registry
      SchemeRegistry sr = ccm.getSchemeRegistry();
      sr.register(new Scheme("https", 443, ssf));
      Map<String, String> queryMap = null;
      if (null != params.get("queryMap")) {
        queryMap = (Map<String, String>) params.get("queryMap");
      }

      String fullUrl = new String(url);
      if (null != queryMap && queryMap.size() > 0) {
        List<NameValuePair> pairs = mapToList(queryMap);
        if (pairs != null && queryMap.size() > 0) {
          String label = "?";
          if (url.contains("?"))
            label = "&";
          try {
            fullUrl += label + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
          } catch (ParseException | IOException e) {
            e.printStackTrace();
          }
        }
      }
      HttpPost httpPost = new HttpPost(fullUrl);

      Map<String, String> headerMap = null;
      if (null != params.get("headerMap")) {
        headerMap = (Map<String, String>) params.get("headerMap");
      }
      if (null != headerMap && headerMap.size() > 0) {
        Set<String> set = headerMap.keySet();
        if (null != set && set.size() > 0) {
          for (String key : set) {
            httpPost.addHeader(key, headerMap.get(key));
          }
        }
      }
      Map<String, String> bodyMap = null;
      if (null != params.get("bodyMap")) {
        bodyMap = (Map<String, String>) params.get("bodyMap");
      }

      String bodyContent = "";
      if (null != bodyMap && bodyMap.size() > 0) {
                /*
                 * Set<String> set = bodyMap.keySet(); if (null != set &&
				 * set.size() > 0) { for (String key : set) { bodyContent =
				 * bodyMap.get(key); } }
				 */
        bodyContent = JSON.toJSONString(bodyMap);
      }

      StringEntity entity = new StringEntity(bodyContent);
      entity.setContentEncoding(CHARSET);
      entity.setContentType("application/json");// 发送json数据需要设置contentType

      httpPost.setEntity(entity);

      // 创建响应控制器
      ResponseHandler<HttpEntity> handler = createResponseHandler(url);

      HttpEntity httpEntity = httpclient.execute(httpPost, handler);
      // 将结果转化为json返回
      String result = EntityUtils.toString(httpEntity, "UTF-8");
      EntityUtils.consume(httpEntity);
      return result;
    } catch (Exception e) {
      logger.error("post请求失败! error: " + e.getMessage());
    }
    return null;
  }

  /**
   * 以get方式发送http请求
   *
   * @param url
   * @param params
   * @return
   */
  public static String doGetHttp(final String url, Map<String, Object> params) {

    CloseableHttpClient httpclient = HttpClients.createDefault();
    String fullUrl = new String(url);
    Map<String, String> queryMap = null;
    if (null != params.get("queryMap")) {
      queryMap = (Map<String, String>) params.get("queryMap");
    }
    try {
      if (null != queryMap && queryMap.size() > 0) {
        List<NameValuePair> pairs = mapToList(queryMap);
        if (pairs != null && queryMap.size() > 0) {
          String label = "?";
          if (url.contains("?"))
            label = "&";
          fullUrl += label + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
        }
      }

      HttpGet httpGet = new HttpGet(fullUrl);
      Map<String, String> headerMap = null;
      if (null != params.get("headerMap")) {
        headerMap = (Map<String, String>) params.get("headerMap");
      }
      if (null != headerMap && headerMap.size() > 0) {
        Set<String> set = headerMap.keySet();
        if (null != set && set.size() > 0) {
          for (String key : set) {
            httpGet.addHeader(key, headerMap.get(key));
          }
        }
      }

      // 创建响应控制器
      ResponseHandler<HttpEntity> handler = createResponseHandler(url);

      HttpEntity httpEntity = httpclient.execute(httpGet, handler);
      // 以json格式返回结果
      String result = EntityUtils.toString(httpEntity, "UTF-8");
      EntityUtils.consume(httpEntity);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("get请求失败! error: " + e.getMessage());
    } finally {
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * 以get方式发送http请求
   *
   * @param url
   * @param
   * @return
   */
  public static String doGetHttps(final String url, Map<String, Object> param) {

    try {

      HttpClient httpclient = new DefaultHttpClient();
      // Secure Protocol implementation.
      SSLContext ctx = SSLContext.getInstance("TLS");
      // Implementation of a trust manager for X509 certificates
      X509TrustManager tm = new X509TrustManager() {

        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {

        }

        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx);
      ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = httpclient.getConnectionManager();
      // register https protocol in httpclient's scheme registry
      SchemeRegistry sr = ccm.getSchemeRegistry();
      sr.register(new Scheme("https", 443, ssf));
      String fullUrl = new String(url);
      Map<String, String> queryMap = null;
      if (null != param.get("queryMap")) {
        queryMap = (Map<String, String>) param.get("queryMap");
      }

      if (null != queryMap && queryMap.size() > 0) {
        List<NameValuePair> pairs = mapToList(queryMap);
        if (pairs != null && queryMap.size() > 0) {
          String label = "?";
          if (url.contains("?"))
            label = "&";
          fullUrl += label + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
        }
      }
      HttpGet httpget = new HttpGet(fullUrl);
      HttpParams params = httpclient.getParams();
      params.setParameter("queryMap", param.get("queryMap"));
      params.setParameter("headerMap", param.get("headerMap"));
      // httpget.setParams(params);
      Map<String, String> headerMap = (Map<String, String>) param.get("headerMap");
      if (null != headerMap && headerMap.size() > 0) {
        Set<String> set = headerMap.keySet();
        if (null != set && set.size() > 0) {
          for (String key : set) {
            httpget.addHeader(key, headerMap.get(key));
          }
        }
      }
      ResponseHandler responseHandler = new BasicResponseHandler();
      String responseBody;

      responseBody = httpclient.execute(httpget, responseHandler).toString();


      return responseBody;

    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();

    }
    return null;
  }

  public static List<NameValuePair> mapToList(Map<String, String> params) {
    List<NameValuePair> pairs = null;

    if (params != null && !params.isEmpty()) {
      pairs = new ArrayList<NameValuePair>(params.size());
      for (Map.Entry<String, String> entry : params.entrySet()) {
        String value = entry.getValue();
        if (value != null && value.length() > 0) {
          pairs.add(new BasicNameValuePair(entry.getKey(), value));
        }
      }
    }
    return pairs;
  }

  /**
   * 创建一个响应控制器
   *
   * @return 返回一个请求响应控制实例
   */
  private static ResponseHandler<HttpEntity> createResponseHandler(final String url) {
    return new ResponseHandler<HttpEntity>() {
      public HttpEntity handleResponse(HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK) {
          try {
            throw new Exception("request [" + url + "] failed, detail: " + getCause(status));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        HttpEntity entity = response.getEntity();
        if (entity != null) {
          return new BufferedHttpEntity(entity);
        } else {
          return null;
        }
      }
    };
  }

  /**
   * 获取状态描述
   *
   * @param statusCode
   * @return
   */
  private static String getCause(int statusCode) {
    String cause = null;
    switch (statusCode) {
      case NOT_MODIFIED:
        break;
      case BAD_REQUEST:
        cause = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
        break;
      case NOT_AUTHORIZED:
        cause = "Authentication credentials were missing or incorrect.";
        break;
      case FORBIDDEN:
        cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
        break;
      case NOT_FOUND:
        cause = "The URI requested is invalid or the resource requested.";
        break;
      case NOT_ACCEPTABLE:
        cause = "Returned by the Search API when an invalid format is specified in the request.";
        break;
      case INTERNAL_SERVER_ERROR:
        cause = "Something is broken. Please post to the group so the Toon-Auth team can investigate.";
        break;
      case BAD_GATEWAY:
        cause = "Toon-Auth is down or being upgraded.";
        break;
      case SERVICE_UNAVAILABLE:
        cause = "Service Unavailable: The Toon-Auth servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
        break;
      default:
        cause = "";
    }
    return statusCode + ":" + cause;
  }

}
