package com.vorxsoft.ieye.eventservice;

import com.coreos.jetcd.Watch;
import com.coreos.jetcd.watch.WatchEvent;
import com.coreos.jetcd.watch.WatchResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.vorxsoft.ieye.eventservice.config.EventConfig;
import com.vorxsoft.ieye.eventservice.grpc.LogServiceClient;
import com.vorxsoft.ieye.eventservice.grpc.VsIAClient;
import com.vorxsoft.ieye.eventservice.grpc.VsIeyeClient;
import com.vorxsoft.ieye.eventservice.process.AlarmProcess;
import com.vorxsoft.ieye.eventservice.util.IaagMap;
import com.vorxsoft.ieye.microservice.MicroService;
import com.vorxsoft.ieye.microservice.MicroServiceImpl;
import com.vorxsoft.ieye.microservice.WatchCallerInterface;
import com.vorxsoft.ieye.proto.ReloadRequest;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.vorxsoft.ieye.eventservice.process.AlarmProcess.ProcessType.*;
import static com.vorxsoft.ieye.proto.VSLogLevel.VSLogLevelInfo;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class EventServerStart implements WatchCallerInterface {

  public VsIAClient addVsIAClient(String address) {
    VsIAClient a = iaagMap.IaClinetInit(address);
    if (a != null)
      getIaagClients().add(a);
    return a;
  }
  @Override
  public void WatchCaller(Watch.Watcher watch) {
    WatchResponse ret = watch.listen();
    System.out.println("watcher response  " + ret);
    for (int i = 0; i < ret.getEvents().size(); i++) {
      WatchEvent a = ret.getEvents().get(i);
      String key = a.getKeyValue().getKey().toString();
      String[] akey = key.split("/");
      String name = akey[1];
      String address = akey[3];
      String[] aa = address.split(":");
      String ip = aa[0];
      int port = Integer.parseInt(aa[1]);
      switch (a.getEventType()) {
        case PUT:
          if (name.equals("server_cms")) {
            //update cms grpc client and  synchronize to process threads
          }
          if (name.equals("server_blg")) {
            //update blg grpc client and  synchronize to process threads
          }
          if (name.equals("server_log")) {
            //update log grpc client and  synchronize to process threads
          }
          if (name.equals("server_iaag")) {
            //update cms grpc client
            VsIAClient client = findIaagClient(address);
            if(client == null){
              client  = addVsIAClient(address);
            }else{
              client.shut();
              client.setIP(ip);
              client.setPORT(port);
              client.init();
            }
            //redispatch
            getIaagMap().getIaags().
          }
          break;
        case DELETE:
          if (name.equals("server_cms")) {
            //clear cms grpc client and  synchronize to process threads
          }
          if (name.equals("server_blg")) {
            //clear blg grpc client and  synchronize to process threads
          }
          if (name.equals("server_log")) {
            //clear log grpc client and  synchronize to process threads
          }
          if (name.equals("server_iaag")) {
            //clear cms grpc client
            VsIAClient client = findIaagClient(address);
            if(client != null){
              client.shut();
              getIaagClients().remove(client);
              //
              getIaagMap().getIaags().
            }else{
              getLogger().error("server_iaag :" + address +" client not found in VsIAClients");
            }

          }
          break;
        case UNRECOGNIZED:
          break;
      }
//      KeyValue keyVal = a.getKeyValue();
//      String key = a.getKeyValue().getKey().toString();
//      String value = a.getKeyValue().getKey

    }
  }

  public EventConfig getEventConfig() {
    return eventConfig;
  }

  public void setEventConfig(EventConfig eventConfig) {
    this.eventConfig = eventConfig;
  }

  private ScheduledExecutorService executor_ = Executors.newScheduledThreadPool(3);
  ;
  public long count = 0;

  public Connection getConn() {
    return conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  private EventConfig eventConfig = new EventConfig();
  private String redisName;
  private String activemqName;
  private static String activemqIp;
  private static int activemqPort;
  private Connection conn;
  private static int PORT = 9999;
  private Server server;
  private static String hostip;
  private static int ttl = 30;
  private static String dbname;
  private static String dbAddress;
  private static String dbUrl = null;
  private static String dbUser = null;
  private static String dbPasswd = null;
  private static String driverClassName = null;
  private static String serviceName = "server_ems";
  private static String registerCenterName;
  private static String registerCenterAddress = "http://192.168.20.251:2379";
  private static String mqName;
  private static String redisIP;
  private static int redisPort;
  private Jedis jedis;
  private InputStream cfgFile;
  private final String cfgFileName = "event_service.xml";

  private VsIeyeClient blgClient;
  private VsIeyeClient cmsClient;

  private List<VsIAClient> iaagClients;
  IaagMap iaagMap;
  private static Logger logger = LogManager.getLogger(EventServerStart.class.getName());
  private LogServiceClient logServiceClient;

  public static Logger getLogger() {
    return logger;
  }

  public IaagMap getIaagMap() {
    return iaagMap;
  }

  public void setIaagMap(IaagMap iaagMap) {
    this.iaagMap = iaagMap;
  }

  public LogServiceClient getLogServiceClient() {
    return logServiceClient;
  }

  public void setLogServiceClient(LogServiceClient logServiceClient) {
    this.logServiceClient = logServiceClient;
  }

  private ScheduledExecutorService getExecutor() {
    return executor_;
  }


  public void setBlgClient(VsIeyeClient blgClient) {
    this.blgClient = blgClient;
  }

  public void setCmsClient(VsIeyeClient cmsClient) {
    this.cmsClient = cmsClient;
  }


  public void getConfigPath() throws FileNotFoundException {
    String tmp = String.valueOf(this.getClass().getClassLoader().getResource(cfgFileName));
    System.out.println("tmp:" + tmp);
    if (tmp.startsWith("jar"))
      cfgFile = new FileInputStream(new File(System.getProperty("user.dir") + File.separator + cfgFileName));
    else
      cfgFile = this.getClass().getClassLoader().getResourceAsStream(cfgFileName);
  }

  public void cfgInit() throws FileNotFoundException {
    // 解析books.xml文件
    // 创建SAXReader的对象reader
    getConfigPath();
    SAXReader reader = new SAXReader();
    try {
      System.out.println("cfg file is:" + cfgFile);
      // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
      //Document document = reader.read(new File(cfgFile));
      Document document = reader.read(cfgFile);
      // 通过document对象获取根节点bookstore
      Element bookStore = document.getRootElement();
      // 通过element对象的elementIterator方法获取迭代器
      Iterator it = bookStore.elementIterator();
      // 遍历迭代器，获取根节点中的信息（书籍）
      while (it.hasNext()) {
        //System.out.println("=====开始遍历某一本书=====");
        Element cfg = (Element) it.next();
        // 获取book的属性名以及 属性值
        List<Attribute> bookAttrs = cfg.attributes();
        System.out.println("cfgname :" + cfg.getName());
        for (Attribute attr : bookAttrs) {
          //System.out.println("属性名：" + attr.getName() + "--属性值：" + attr.getValue());
        }
        String tname = cfg.getName();
        //解析子节点的信息
        Iterator itt = cfg.elementIterator();
        while (itt.hasNext()) {
          Element bookChild = (Element) itt.next();
          String lname = bookChild.getName();
          String lvalue = bookChild.getStringValue();
          //System.out.println("节点名：" + bookChild.getName() + "--节点值：" + bookChild.getStringValue());
          if (tname.equals("info")) {
            if (lname.equals("hostip"))
              hostip = lvalue;
            else if (lname.equals("port"))
              PORT = Integer.parseInt(lvalue);
            else if (lname.equals("name"))
              serviceName = lvalue;
            else if (lname.equals("ttl"))
              ttl = Integer.parseInt(lvalue);
          }
          if (tname.equals("database")) {
            if (lname.equals("name"))
              dbname = lvalue;
            else if (lname.equals("address"))
              dbAddress = lvalue;
            else if (lname.equals("user"))
              dbUser = lvalue;
            else if (lname.equals("passwd"))
              dbPasswd = lvalue;
            else if (lname.equals("driverClassName"))
              driverClassName = lvalue;
          }
          if (tname.equals("registerCenter")) {
            if (lname.equals("name"))
              registerCenterName = lvalue;
            else if (lname.equals("address"))
              registerCenterAddress = lvalue;
          }
          if (tname.equals("redis")) {
            if (lname.equals("name"))
              redisName = lvalue;
            else if (lname.equals("ip"))
              redisIP = lvalue;
            else if (lname.equals("port"))
              redisPort = Integer.parseInt(lvalue);
          }
          if (tname.equals("activemq")) {
            if (lname.equals("name"))
              activemqName = lvalue;
            else if (lname.equals("ip"))
              activemqIp = lvalue;
            else if (lname.equals("port"))
              activemqPort = Integer.parseInt(lvalue);
          }
        }
        //System.out.println("=====结束遍历某一本书=====");
      }
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }

  public List<ReloadRequest> getReloadRequest() throws JsonFormat.ParseException {
    Set<String> set = jedis.keys("reload_config_req*");
    if (set == null || set.size() == 0) {
      return null;
    }
    Iterator<String> it = set.iterator();
    List<ReloadRequest> reloadRequestList = new ArrayList<>();
    while (it.hasNext()) {
      String keyStr = it.next();
      String a = jedis.hget(keyStr, "req");
      if (a == null || a.length() == 0) {
        System.out.println("wrong redis hash,and key:" + keyStr);
      } else {
        try {
          ReloadRequest.Builder builder = ReloadRequest.newBuilder();
          com.google.protobuf.util.JsonFormat.parser().merge(a, builder);
          ReloadRequest req = builder.build();
          reloadRequestList.add(req);
        } catch (InvalidProtocolBufferException e) {
          e.printStackTrace();
        }
      }
      jedis.del(keyStr);
    }
    return reloadRequestList;
  }

  public void updateConfig() throws SQLException, JsonFormat.ParseException {
    List<ReloadRequest> reqList = getReloadRequest();
    if (reqList == null) {
      return;
    }
    for (ReloadRequest req : reqList) {
      getLogger().info("config reload req:" + req);
      System.out.println("config reload req:" + req);
      switch (req.getLoadType()) {
        case REL_DEV_INFO:
          break;
        case REL_RES_INFO:
          break;
        case REL_REC_PLAN:
          break;
        case REL_REC_TIME:
          break;
        case REL_REC_CAM:
          break;
        case REL_EVENT_INFO:
          switch (req.getEmAct()) {
            case OA_ADD:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().addEventInfo(conn, req.getIdList(j));
              }
              break;
            case OA_MOD:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().updateEventInfo(conn, req.getIdList(j));
              }
              break;
            case OA_DEL:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().deleteEventInfo(req.getIdList(j));
              }
              break;
            case OA_QUR:
            case OA_ON:
            case OA_OFF:
            case OA_OTHER:
            case OA_ALL_ISSUE:
            case UNRECOGNIZED:
            default:
              break;
          }
          break;
        case REL_EVENT_GUARD:
          switch (req.getEmAct()) {
            case OA_ADD:
              //guard play add ,and event is or not add or modify?
              break;
            case OA_MOD:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().updateGuardPlan(conn, req.getIdList(j));
              }
              break;
            case OA_DEL:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().deleteGuardPlan(conn, req.getIdList(j));
              }
              break;
            case OA_QUR:
            case OA_ON:
            case OA_OFF:
            case OA_OTHER:
            case OA_ALL_ISSUE:
            case UNRECOGNIZED:
              break;
          }
          break;
        case REL_EVENT_CONTACTS:
          break;
        case REL_EVENT_STORM:
          switch (req.getEmAct()) {
            case OA_ADD:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().addAlarmStorm(conn, req.getIdList(j));
              }
              break;
            case OA_MOD:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().updateAlarmStorm(conn, req.getIdList(j));
              }
              break;
            case OA_DEL:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().deleteAlarmStorm(req.getIdList(j));
              }
              break;
            case OA_QUR:
            case OA_ON:
            case OA_OFF:
            case OA_OTHER:
            case OA_ALL_ISSUE:
            case UNRECOGNIZED:
            default:
              break;
          }
          break;
        case REL_EVENT_LINKAGE:
          switch (req.getEmAct()) {
            case OA_ADD:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                //getEventConfig().addAlarmStorm(conn, req.getIdList(j));
              }
              break;
            case OA_MOD:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().updateLinkage(conn, req.getIdList(j));
              }
              break;
            case OA_DEL:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().deleteLinkage(req.getIdList(j));
              }
              break;
            case OA_QUR:
            case OA_ON:
            case OA_OFF:
            case OA_OTHER:
            case OA_ALL_ISSUE:
            case UNRECOGNIZED:
              break;
          }
          break;
        //  需要判断级联删除后，事件配置是否修改
        case REL_EVENT_CAM:
          switch (req.getEmAct()) {
            case OA_ADD:
              //无增加情况出现
              for (int j = 0; j < req.getIdListList().size(); j++) {
              }
              break;
            case OA_MOD:
              //修改时候 id无效
              for (int j = 0; j < req.getIdListList().size(); j++) {
              }
              break;
            case OA_DEL:
              //删除时候 id是有效的
              for (int j = 0; j < req.getIdListList().size(); j++) {
              }
              break;
            case OA_QUR:
            case OA_ON:
            case OA_OFF:
            case OA_OTHER:
            case OA_ALL_ISSUE:
            case UNRECOGNIZED:
              break;
          }
          break;
      }
    }
  }

  public void dbInit() throws SQLException, ClassNotFoundException {
    dbUrl = "jdbc:" + dbname + "://" + dbAddress;
    System.out.println("db url :" + dbUrl);
    Class.forName(driverClassName);
    conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
    //st = conn.createStatement();
  }

  public void redisInit() {
    jedis = new Jedis(redisIP, redisPort);
  }

  public String evenType2string(int type) {
    if ((type == 1) || ((type > 100) && (type < 200))) { //VSEventTypeMonitor
      return "event_monitor";
    } else if ((type == 2) || ((type > 200) && (type < 300))) { //VSEventTypeDigitalIO
      return "event_sio";
    } else {
      return null;
    }
  }

  private void start() throws Exception {
    //server = NettyServerBuilder.forPort(PORT).addService(new EventServer(mqIP,mqPort).bindService()).build();
    server = NettyServerBuilder.forPort(PORT)
            .addService(new EventServer(redisIP, redisPort).bindService())
            .addService(new EventServer2(redisIP, redisPort).bindService())
            .build();
    server.start();

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        getLogger().error("*** shutting down gRPC server since JVM is shutting down");
        EventServerStart.this.stop();
        System.err.println("*** server shut down");
        getLogger().error("*** server shut down");
      }
    });
  }

  private void stop() {
    try {
      jedis.close();
      conn.close();
      server.awaitTermination(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
      getLogger().error(e);
    } catch (SQLException e) {
      e.printStackTrace();
      getLogger().error(e);
    }
  }

  public VsIeyeClient getBlgClient() {
    return blgClient;
  }

  public VsIeyeClient getCmsClient() {
    return cmsClient;
  }

  public List<VsIAClient> getIaagClients() {
    return iaagClients;
  }

  public void setIaagClients(List<VsIAClient> iaagClients) {
    this.iaagClients = iaagClients;
  }

  public VsIAClient findIaagClient(String address){
    if(getIaagClients() == null){
      return null;
    }
    for(VsIAClient vsIAClient : getIaagClients()){
      if(address.equals(vsIAClient.getIP()+":"+vsIAClient.getPORT()){
        return vsIAClient;
      }
    }
    return null;
  }

  public static void main(String[] args) throws Exception {
    int cpuNums = Runtime.getRuntime().availableProcessors();
    int threadPoolNum = cpuNums;
//    for (int i = 0; i < threadPoolNum; i++) {
//      new Thread (new AlarmProcess("thread"+ (i+1))).start();
//    }
    System.out.println("cpuNums :" + cpuNums);
    final EventServerStart simpleServerStart = new EventServerStart();
    simpleServerStart.cfgInit();
    MicroService myservice = new MicroServiceImpl();
    myservice.init(registerCenterAddress, simpleServerStart);
    simpleServerStart.start();
    simpleServerStart.redisInit();
    simpleServerStart.dbInit();
    simpleServerStart.getEventConfig().loadConfig(simpleServerStart.getConn());
    myservice.RegisteWithHB(serviceName, hostip, PORT, ttl);

    myservice.SetWatcher("server_", true);

    //obtail cms and blg server address
    //simpleServerStart.setBlgClient(new VsIeyeClient("blg", myservice.Resolve("blg").toString()));
    //simpleServerStart.setCmsClient(new VsIeyeClient("cms", myservice.Resolve("cms").toString()));

    String logAddress = myservice.Resolve("server_log");
    if(logAddress == null){
      System.out.println("cannot resolve log server  address");
      simpleServerStart.getLogger().warn("cannot resolve log server  address");
    }else{
      System.out.println("successful resolve log server  address:" + logAddress);
      simpleServerStart.getLogger().info("successful resolve log server  address:" + logAddress);
      simpleServerStart.setLogServiceClient(new LogServiceClient("log",logAddress));
      simpleServerStart.getLogServiceClient().setHostNameIp(hostip);
      simpleServerStart.getLogServiceClient().setpName(serviceName);
      String logContent = "successful resolve log server  address:" + logAddress;
      simpleServerStart.getLogServiceClient().sentVSLog(logContent,VSLogLevelInfo);
    }

    String blgAddress = myservice.Resolve("server_blg");
    if (blgAddress == null) {
      System.out.println("cannot resolve blg server  address");
      simpleServerStart.getLogger().warn("cannot resolve blg server  address");
    } else {
      System.out.println("successful resolve blg server  address:" + blgAddress);
      simpleServerStart.getLogger().info("successful resolve blg server  address:" + blgAddress);
      simpleServerStart.setBlgClient(new VsIeyeClient("blg", blgAddress));
    }
    String cmsAddress = myservice.Resolve("server_cms");
    if (cmsAddress == null) {
      System.out.println("cannot resolve cms server  address");
      simpleServerStart.getLogger().warn("cannot resolve cms server  address");
    } else {
      System.out.println("successful resolve cms server  address:" + cmsAddress);
      simpleServerStart.getLogger().info("successful resolve cms server  address:" + cmsAddress);
      simpleServerStart.setCmsClient(new VsIeyeClient("cms", cmsAddress));
    }

    IaagMap iaagMap = new IaagMap();
    iaagMap.setConn(simpleServerStart.getConn());
    iaagMap.load();
    simpleServerStart.setIaagMap(iaagMap);

    List<String> iaagAdress = null;
    try {
      iaagAdress = myservice.ResolveAllAddress("server_iaag");
      simpleServerStart.getLogger().info("resolve all iaag address :" + iaagAdress);
      if (simpleServerStart.getIaagClients() == null) {
        simpleServerStart.setIaagClients(new ArrayList<>());
      }
      for (int i = 0; i < iaagAdress.size(); i++) {
        VsIAClient a = iaagMap.IaClinetInit(iaagAdress.get(i));
        if (a != null)
          simpleServerStart.getIaagClients().add(a);
      }
    } catch (Exception e) {
      simpleServerStart.getLogger().error(e);
      e.printStackTrace();
    }


    //monitor process
    AlarmProcess monitorProcess = new AlarmProcess();
    monitorProcess.setLogger(simpleServerStart.getLogger());
    monitorProcess.setBlgTeyeClient(simpleServerStart.getBlgClient());
    monitorProcess.setCmsIeyeClient(simpleServerStart.getCmsClient());
    monitorProcess.setName("monitorProcess");
    monitorProcess.setEventConfig(simpleServerStart.getEventConfig());
    monitorProcess.setIaagMap(simpleServerStart.getIaagMap());
    monitorProcess.setProcessType(ProcessServerType);
    monitorProcess.dbInit(dbname, dbAddress, driverClassName, dbUser, dbPasswd);
    monitorProcess.redisInit(redisIP, redisPort);
    monitorProcess.mqInit(activemqIp, activemqPort);

    //sio process
    AlarmProcess sioProcess = new AlarmProcess();
    sioProcess.setLogger(simpleServerStart.getLogger());
    sioProcess.setBlgTeyeClient(simpleServerStart.getBlgClient());
    sioProcess.setCmsIeyeClient(simpleServerStart.getCmsClient());
    sioProcess.setName("sioProcess");
    sioProcess.setEventConfig(simpleServerStart.getEventConfig());
    sioProcess.setIaagMap(simpleServerStart.getIaagMap());
    sioProcess.setProcessType(ProcessSioType);
    sioProcess.dbInit(dbname, dbAddress, driverClassName, dbUser, dbPasswd);
    sioProcess.redisInit(redisIP, redisPort);
    sioProcess.mqInit(activemqIp, activemqPort);

    //ia process
    AlarmProcess iaProcess = new AlarmProcess();
    iaProcess.setLogger(simpleServerStart.getLogger());
    iaProcess.setBlgTeyeClient(simpleServerStart.getBlgClient());
    iaProcess.setCmsIeyeClient(simpleServerStart.getCmsClient());
    iaProcess.setName("iaProcess");
    iaProcess.setEventConfig(simpleServerStart.getEventConfig());
    iaProcess.setIaagMap(simpleServerStart.getIaagMap());
    iaProcess.setProcessType(ProcessIaType);
    iaProcess.dbInit(dbname, dbAddress, driverClassName, dbUser, dbPasswd);
    iaProcess.redisInit(redisIP, redisPort);
    iaProcess.mqInit(activemqIp, activemqPort);

//    //server process
//    AlarmProcess serverProcess = new AlarmProcess();
//    serverProcess.setLogger(simpleServerStart.getLogger());
//    serverProcess.setBlgTeyeClient(simpleServerStart.getBlgClient());
//    serverProcess.setCmsIeyeClient(simpleServerStart.getCmsClient());
//    serverProcess.setName("serverProcess");
//    serverProcess.setEventConfig(simpleServerStart.getEventConfig());
//    serverProcess.setIaagMap(simpleServerStart.getIaagMap());
//    serverProcess.setProcessType(ProcessServerType);
//    serverProcess.dbInit(dbname, dbAddress, driverClassName, dbUser, dbPasswd);
//    serverProcess.redisInit(redisIP, redisPort);
//serverProcess.mqInit(activemqIp, activemqPort);
//    //device process
//    AlarmProcess deviceProcess = new AlarmProcess();
//    deviceProcess.setLogger(simpleServerStart.getLogger());
//    deviceProcess.setBlgTeyeClient(simpleServerStart.getBlgClient());
//    deviceProcess.setCmsIeyeClient(simpleServerStart.getCmsClient());
//    deviceProcess.setName("deviceProcess");
//    deviceProcess.setEventConfig(simpleServerStart.getEventConfig());
//    deviceProcess.setIaagMap(simpleServerStart.getIaagMap());
//    deviceProcess.setProcessType(ProcessDeviceType);
//    deviceProcess.dbInit(dbname, dbAddress, driverClassName, dbUser, dbPasswd);
//    deviceProcess.redisInit(redisIP, redisPort);
//    deviceProcess.mqInit(activemqIp, activemqPort);

    new Thread(monitorProcess).start();
    new Thread(sioProcess).start();
    new Thread(iaProcess).start();
//    new Thread(serverProcess).start();
//    new Thread(deviceProcess).start();

    simpleServerStart.getExecutor().scheduleWithFixedDelay(() -> {
      try {
        simpleServerStart.updateConfig();
      } catch (SQLException e) {
        simpleServerStart.getLogger().error(e);
        e.printStackTrace();
      } catch (JsonFormat.ParseException e) {
        simpleServerStart.getLogger().error(e);
        e.printStackTrace();
      }
    }, 1l, 1L, TimeUnit.SECONDS);

    simpleServerStart.getExecutor().scheduleWithFixedDelay(() -> {
      simpleServerStart.getIaagMap().dispatch();
    }, 1l, 1L, TimeUnit.SECONDS);

    TimeUnit.DAYS.sleep(365 * 2000);
  }
}
