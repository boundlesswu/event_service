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
import com.vorxsoft.ieye.eventservice.util.*;
import com.vorxsoft.ieye.microservice.MicroService;
import com.vorxsoft.ieye.microservice.MicroServiceImpl;
import com.vorxsoft.ieye.microservice.WatchCallerInterface;
import com.vorxsoft.ieye.proto.ReloadRequest;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.vorxsoft.ieye.eventservice.EventServerStart.ClientType.*;
import static com.vorxsoft.ieye.eventservice.process.AlarmProcess.ProcessType.*;
import static com.vorxsoft.ieye.proto.VSLogLevel.VSLogLevelInfo;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class EventServerStart implements WatchCallerInterface {

  private String alarmBellIp;
  private int alarmBellPort;
  private String alarmBellUrl;

  public String getAlarmBellIp() {
    return alarmBellIp;
  }

  public void setAlarmBellIp(String alarmBellIp) {
    this.alarmBellIp = alarmBellIp;
  }

  public int getAlarmBellPort() {
    return alarmBellPort;
  }

  public void setAlarmBellPort(int alarmBellPort) {
    this.alarmBellPort = alarmBellPort;
  }

  public String getAlarmBellUrl() {
    return alarmBellUrl;
  }

  public void setAlarmBellUrl(String alarmBellUrl) {
    this.alarmBellUrl = alarmBellUrl;
  }

  public String getAlarmUrl() {
    return "http://" + getAlarmBellIp() + ":" + getAlarmBellPort() + "/" + getAlarmBellUrl();
  }

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
    getLogger().info("watcher response  " + ret);
    for (int i = 0; i < ret.getEvents().size(); i++) {
      WatchEvent a = ret.getEvents().get(i);
      String key = a.getKeyValue().getKey().toStringUtf8();
      String[] akey = key.split("/");
      String name = new String(akey[2]);
      String address = akey[3];
      String[] aa = address.split(":");
      String ip = aa[0];
      int port = Integer.parseInt(aa[1]);
      switch (a.getEventType()) {
        case PUT:
          if (name.equals("server_cms")) {
            //cms client is not exist
            if (getCmsClient() == null || getCmsClient().getManagedChannel() == null || getCmsClient().getManagedChannel().isShutdown()) {
              getLogger().debug("re set cms client of address:" + address);
              setCmsClient(address);
            } else {
              String tmpstring = null;
              tmpstring = (getCmsClient() == null) ? "CmsClient is null" : (getCmsClient().getManagedChannel() == null) ?
                      "CmsClient ManagedChannel is null" : (!getCmsClient().getManagedChannel().isShutdown()) ?
                      "CmsClient ManagedChannel is opened" : "";
              getLogger().debug(tmpstring);
            }
            //update cms grpc client and  synchronize to process threads
          }
          if (name.equals("server_blg")) {
            //update blg grpc client and  synchronize to process threads
            if (getBlgClient() == null || getBlgClient().getManagedChannel() == null || getBlgClient().getManagedChannel().isShutdown()) {
              getLogger().debug("re set blg client of address:" + address);
              setBlgClient(address);
            } else {
              String tmpstring = null;
              tmpstring = (getBlgClient() == null) ? "blgClient is null" : (getBlgClient().getManagedChannel() == null) ?
                      "blgClient ManagedChannel is null" : (!getBlgClient().getManagedChannel().isShutdown()) ?
                      "blgClient ManagedChannel is opened" : "";
              getLogger().debug(tmpstring);
            }
          }
          if (name.equals("server_log")) {
            //update log grpc client and  synchronize to process threads
            if (getLogServiceClient() == null || getLogServiceClient().getManagedChannel() == null || getLogServiceClient().getManagedChannel().isShutdown()) {
              getLogger().debug("re set log client of address:" + address);
              setLogServiceClient(address);
            } else {
              String tmpstring = null;
              tmpstring = (getLogServiceClient() == null) ? "logClient is null" : (getLogServiceClient().getManagedChannel() == null) ?
                      "logClient ManagedChannel is null" : (!getLogServiceClient().getManagedChannel().isShutdown()) ?
                      "logClient ManagedChannel is opened" : "";
              getLogger().debug(tmpstring);
            }
          }
          if (name.equals("server_iaag")) {
            //update cms grpc client
            VsIAClient client = findIaagClient(address);
            if (client == null) {
              getLogger().debug("cannot find iaag client of address(" + address + ")");
              client = addVsIAClient(address);
            } else {
              getLogger().debug(" find iaag client of address(" + address + ") and reinit client");
              client.shut();
              client.setAddress(address);
              client.init();
            }
            //redispatch
            IaagMapItem b = getIaagMap().findIaagMapItem(address);
            if (b != null) {
              getLogger().error("find iaag of address(" + address + ") in IaagMap and redispatch ");
              b.getIaagInfo().setOnLine(true);
              b.setClient(client);
              b.redispatch(getConn());
            } else {
              getLogger().error("cannot find iaag of address(" + address + ") in IaagMap");
              //re read  iaag and
            }
          }
          break;
        case DELETE:
          if (name.equals("server_cms")) {
            //clear cms grpc client and  synchronize to process threads
            if (getCmsClient() != null) {
              if (address.equals(getCmsClient().getIP() + ":" + getCmsClient().getPORT())) {
                getCmsClient().shut();
                //setCmsClient(null);
              } else {
                getLogger().error("cannot find cms of address(" + address + ") to be deleted");
              }
            }
          }
          if (name.equals("server_blg")) {
            //clear blg grpc client and  synchronize to process threads
            if (getBlgClient() != null) {
              if (address.equals(getBlgClient().getIP() + ":" + getBlgClient().getPORT())) {
                getBlgClient().shut();
                //setBlgClient(null);
              } else {
                getLogger().error("cannot find blg of address(" + address + ") to be deleted");
              }
            }
          }
          if (name.equals("server_log")) {
            //clear log grpc client and  synchronize to process threads
            if (getLogServiceClient() != null) {
              if (address.equals(getLogServiceClient().getIP() + ":" + getLogServiceClient().getPORT())) {
                getLogServiceClient().shut();
                //setLogServiceClient(null);
              } else {
                getLogger().error("cannot find log service of address(" + address + ") to be deleted");
              }
            }
          }
          if (name.equals("server_iaag")) {
            //clear cms grpc client
            VsIAClient client = findIaagClient(address);
            if (client != null) {
              client.shut();
              getIaagClients().remove(client);
            } else {
              getLogger().error("server_iaag :" + address + " client not found in VsIAClients");
            }
            IaagMapItem b = getIaagMap().findIaagMapItem(address);
            if (b != null) {
              b.setClient(null);
              b.getIaagInfo().setOnLine(false);
              getIaagMap().getIaags().remove(b);
            } else {
              getLogger().error("server_iaag :" + address + " client not found in IaagMap");
            }
          }
          break;

//      KeyValue keyVal = a.getKeyValue();
//      String key = a.getKeyValue().getKey().toString();
//      String value = a.getKeyValue().getKey

        case UNRECOGNIZED:
          break;
      }
    }
  }

  private ConcurrentLinkedQueue< Map<String, String>> monitorCq ;
  private ConcurrentLinkedQueue< Map<String, String>> sioCq ;
  private ConcurrentLinkedQueue< Map<String, String>> iaCq ;
  private ConcurrentLinkedQueue< Map<String, String>> serverCq ;
  private ConcurrentLinkedQueue< Map<String, String>> deviceCq;

  public ConcurrentLinkedQueue<String> getcCq() {
    return cCq;
  }

  public void setcCq(ConcurrentLinkedQueue<String> cCq) {
    this.cCq = cCq;
  }

  private ConcurrentLinkedQueue<String> cCq;

  public ConcurrentLinkedQueue<Map<String, String>> getMonitorCq() {
    return monitorCq;
  }

  public void setMonitorCq(ConcurrentLinkedQueue<Map<String, String>> monitorCq) {
    this.monitorCq = monitorCq;
  }

  public ConcurrentLinkedQueue<Map<String, String>> getSioCq() {
    return sioCq;
  }

  public void setSioCq(ConcurrentLinkedQueue<Map<String, String>> sioCq) {
    this.sioCq = sioCq;
  }

  public ConcurrentLinkedQueue<Map<String, String>> getIaCq() {
    return iaCq;
  }

  public void setIaCq(ConcurrentLinkedQueue<Map<String, String>> iaCq) {
    this.iaCq = iaCq;
  }

  public ConcurrentLinkedQueue<Map<String, String>> getServerCq() {
    return serverCq;
  }

  public void setServerCq(ConcurrentLinkedQueue<Map<String, String>> serverCq) {
    this.serverCq = serverCq;
  }

  public ConcurrentLinkedQueue<Map<String, String>> getDeviceCq() {
    return deviceCq;
  }

  public void setDeviceCq(ConcurrentLinkedQueue<Map<String, String>> deviceCq) {
    this.deviceCq = deviceCq;
  }

  public EventConfig getEventConfig() {
    return eventConfig;
  }

  public void setEventConfig(EventConfig eventConfig) {
    this.eventConfig = eventConfig;
  }

  private ScheduledExecutorService executor_ = Executors.newScheduledThreadPool(6);
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
  //private Jedis jedis;
  //public RedisUtil redisUtil = null;
  private ConcurrentLinkedQueue<String> cfgcq;
  private InputStream cfgFile;
  private final String cfgFileName = "event_service.xml";

  private VsIeyeClient blgClient = new VsIeyeClient();
  private VsIeyeClient cmsClient = new VsIeyeClient();

  private List<VsIAClient> iaagClients;
  IaagMap iaagMap;
  private static Logger logger = LogManager.getLogger(EventServerStart.class.getName());
  private LogServiceClient logServiceClient = new LogServiceClient();

//  public RedisUtil getRedisUtil() {
//    return redisUtil;
//  }
//
//  public void setRedisUtil(RedisUtil redisUtil) {
//    this.redisUtil = redisUtil;
//  }

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

  public void setLogServiceClient(String address) {
    setXXXClient(LogType, address);
  }

  private ScheduledExecutorService getExecutor() {
    return executor_;
  }


  public void setBlgClient(VsIeyeClient blgClient) {
    this.blgClient = blgClient;
  }

  public void setBlgClient(String address) {
    setXXXClient(BlgType, address);
  }

  enum ClientType {
    BlgType, CmsType, LogType
  }

  public void setXXXClient(ClientType type, String address) {
    if (address == null) {
      String tmp = "cannot resolve " +
              ((type == BlgType) ? " blg " : ((type == CmsType) ? " cms " : (type == LogType) ? " log " : " "))
              + "server address";
      System.out.println(tmp);
      getLogger().warn(tmp);
      return;
    }
    switch (type) {
      case CmsType:
        String tmp = "successful resolve cms server  address:";
        System.out.println(tmp + address);
        getLogger().info(tmp + address);
        if (getCmsClient() == null) {
          setCmsClient(new VsIeyeClient("cms", address));
        } else {
          getCmsClient().shut();
          getCmsClient().setAddress(address);
          getCmsClient().init();
        }
        break;
      case BlgType:
        tmp = "successful resolve blg server  address:";
        System.out.println(tmp + address);
        getLogger().info(tmp + address);
        if (getBlgClient() == null) {
          setBlgClient(new VsIeyeClient("blg", address));
        } else {
          getBlgClient().shut();
          getBlgClient().setAddress(address);
          getBlgClient().init();
        }
        break;
      case LogType:
        System.out.println("successful resolve log server  address:" + address);
        getLogger().info("successful resolve log server  address:" + address);
        if (getLogServiceClient() == null) {
          setLogServiceClient(new LogServiceClient("log", address));
        } else {
          getLogServiceClient().shut();
          getLogServiceClient().setAddress(address);
          getLogServiceClient().init();
        }
        getLogServiceClient().setHostNameIp(hostip);
        getLogServiceClient().setpName(serviceName);
        String logContent = "successful resolve log server  address:" + address;
        if (getLogServiceClient().getStub() != null || !getLogServiceClient().getManagedChannel().isTerminated())
          getLogServiceClient().sentVSLog(logContent, VSLogLevelInfo);
        break;
    }
  }


  public void setCmsClient(VsIeyeClient cmsClient) {
    this.cmsClient = cmsClient;
  }

  public void setCmsClient(String address) {
    setXXXClient(CmsType, address);
  }


  public void cfgInit() throws FileNotFoundException {
    ConfigReadUtils configReadUtils = new ConfigReadUtils();
    configReadUtils.cfgInit();
    hostip = configReadUtils.getHostip();
    PORT = configReadUtils.getEmsPort();
    ttl = configReadUtils.getTtl();
    registerCenterAddress = configReadUtils.getRegisterCenterAddress();
    dbname = configReadUtils.getDbname();
    dbUser = configReadUtils.getDbUser();
    dbPasswd = configReadUtils.getDbPasswd();
    driverClassName = configReadUtils.getDriverClassName();
    dbAddress = configReadUtils.getDbAddress();
    redisName = configReadUtils.getRedisName();
    redisIP = configReadUtils.getRedisIP();
    redisPort = configReadUtils.getRedisPort();
    activemqName = configReadUtils.getActivemqName();
    activemqIp = configReadUtils.getActivemqIp();
    activemqPort = configReadUtils.getActivemqPort();
    alarmBellIp = configReadUtils.getAlarmBellIp();
    alarmBellPort = configReadUtils.getAlarmBellPort();
    alarmBellUrl = configReadUtils.getAlarmBellUrl();
  }

  public List<ReloadRequest> getReloadRequest() throws JsonFormat.ParseException {
    List<ReloadRequest> reloadRequestList = new ArrayList<>();
    while(cfgcq.isEmpty()){
      String a = cfgcq.poll();
      if (a == null || a.length() == 0) {
        System.out.println("wrong cfgcq");
      } else {
        try {
          ReloadRequest.Builder builder = ReloadRequest.newBuilder();
          com.google.protobuf.util.JsonFormat.parser().merge(a, builder);
          ReloadRequest req = builder.build();
          reloadRequestList.add(req);
        } catch (InvalidProtocolBufferException e) {
          e.printStackTrace();
          getLogger().error(e.getMessage(), e);
        }
      }
    }
    return reloadRequestList;

//    Set<String> set = jedis.keys("reload_config_req*");
//    Set<String> set = redisUtil.keys("reload_config_req*");
//    if (set == null || set.size() == 0) {
//      return null;
//    }
//    Iterator<String> it = set.iterator();
//    List<ReloadRequest> reloadRequestList = new ArrayList<>();
//    while (it.hasNext()) {
//      String keyStr = it.next();
//      //String a = jedis.hget(keyStr, "req");
//      //String a = redisUtil.hget(keyStr, "req");
//      String a = keyStr;
//      if (a == null || a.length() == 0) {
//        System.out.println("wrong redis hash,and key:" + keyStr);
//      } else {
//        try {
//          ReloadRequest.Builder builder = ReloadRequest.newBuilder();
//          com.google.protobuf.util.JsonFormat.parser().merge(a, builder);
//          ReloadRequest req = builder.build();
//          reloadRequestList.add(req);
//        } catch (InvalidProtocolBufferException e) {
//          e.printStackTrace();
//          getLogger().error(e.getMessage(), e);
//        }
//      }
//      redisUtil.del(keyStr);
//      //jedis.del(keyStr);
//    }
//    return reloadRequestList;
  }

  public void updateConfig(MicroService myservice) throws SQLException, JsonFormat.ParseException {
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
            case OA_ON:
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
            case OA_OFF:
              for (int j = 0; j < req.getIdListList().size(); j++) {
                getEventConfig().deleteEventInfo(req.getIdList(j));
              }
              break;
            case OA_QUR:
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
              if (req.getIdListList().size() == 0) {
                getEventConfig().getAlarmStormConfig().zero();
                getEventConfig().getAlarmStormConfig().load(conn);
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
            case OA_DEL:
              //修改时候 id无效
//              for (int j = 0; j < req.getIdListList().size(); j++) {
//              }
              iaagMap.zero();
              iaagMap.setConn(getConn());
              iaagMap.load();
              if (getIaagClients() != null && getIaagClients().size() != 0) {
                for (VsIAClient client : getIaagClients()) {
                  //getIaagClients().remove(client);
                  client.zero();
                }
                getIaagClients().clear();
              }
              List<String> iaagAdress = null;
              try {
                iaagAdress = myservice.ResolveAllAddress("server_iaag");
                if (iaagAdress == null) {
                  getLogger().error(" ResolveAllAddress failed,and server_iaag is null");
                }
              } catch (Exception e) {
                e.printStackTrace();
                getLogger().error(e.getMessage(), e);
              }
              if (iaagAdress != null) {
                setIaagClients2(iaagAdress);
              }
              try {
                getEventConfig().reLoadConfig(getConn());
              } catch (Exception e) {
                e.printStackTrace();
                getLogger().error(e.getMessage(), e);
              }
              break;
//            case OA_DEL:
//              //删除时候 id是有效的,会级联删除掉级联的事件等
//              for (int j = 0; j < req.getIdListList().size(); j++) {
//              }
//              break;
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

  /**
   * @Author boundlesswu
   * @Description
   * @Param
   * @create 2018/1/15 0015 16:54
   **/
  public void dbInit() throws SQLException, ClassNotFoundException {
    dbUrl = "jdbc:" + dbname + "://" + dbAddress;
    System.out.println("db url :" + dbUrl);
    Class.forName(driverClassName);
    conn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
    //st = conn.createStatement();
  }

//  public void redisInit() {
//    redisUtil = new RedisUtil(redisIP,redisPort);
//    //jedis = new Jedis(redisIP, redisPort);
//  }

  public void cqInit(){
    monitorCq = new ConcurrentLinkedQueue<>();
    sioCq = new ConcurrentLinkedQueue<>();
    iaCq = new ConcurrentLinkedQueue<>();
    deviceCq = new ConcurrentLinkedQueue<>();
    serverCq = new ConcurrentLinkedQueue<>();
    cCq = new ConcurrentLinkedQueue<>();
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
//            .addService(new EventServer(redisIP, redisPort).bindService())
//            .addService(new EventServer2(redisIP, redisPort).bindService())
//            .addService(new EventServer(getRedisUtil()).bindService())
//            .addService(new EventServer2(getRedisUtil()).bindService())
            .addService(new EventServer(getMonitorCq(),getSioCq(),getIaCq(),getServerCq(),getDeviceCq()).bindService())
            .addService(new EventServer2(getcCq()).bindService())
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
      //jedis.close();
      conn.close();
      server.awaitTermination(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
      getLogger().error(e.getMessage(), e);
    } catch (SQLException e) {
      e.printStackTrace();
      getLogger().error(e.getMessage(), e);
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

  public void setIaagClients2(List<String> iaagAdress) {
    try {
      if (getIaagClients() == null) {
        setIaagClients(new ArrayList<>());
      }
      for (int i = 0; i < iaagAdress.size(); i++) {
        VsIAClient a = getIaagMap().IaClinetInit(iaagAdress.get(i));
        if (a != null)
          getIaagClients().add(a);
      }
    } catch (Exception e) {
      getLogger().error(e.getMessage(), e);
      e.printStackTrace();
    }

  }

  public VsIAClient findIaagClient(String address) {
    if (getIaagClients() == null) {
      return null;
    }
    for (VsIAClient vsIAClient : getIaagClients()) {
      String tmp = new String(vsIAClient.getIP() + ":" + (vsIAClient.getPORT() - 2));
      if (address.equals(tmp)) {
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
    //simpleServerStart.redisInit();
    simpleServerStart.cqInit();
    simpleServerStart.dbInit();
    MicroService myservice = new MicroServiceImpl();
    myservice.init(registerCenterAddress, simpleServerStart);
    simpleServerStart.start();

    simpleServerStart.getEventConfig().loadConfig(simpleServerStart.getConn());
    myservice.RegisteWithHB(serviceName, hostip, PORT, ttl);

    myservice.SetWatcher("server_", true);

    //obtail cms and blg server address
    //simpleServerStart.setBlgClient(new VsIeyeClient("blg", myservice.Resolve("blg").toString()));
    //simpleServerStart.setCmsClient(new VsIeyeClient("cms", myservice.Resolve("cms").toString()));

    String logAddress = myservice.Resolve("server_log");
    simpleServerStart.setLogServiceClient(logAddress);

    String blgAddress = myservice.Resolve("server_blg");
    simpleServerStart.setBlgClient(blgAddress);


    String cmsAddress = myservice.Resolve("server_cms");
    simpleServerStart.setCmsClient(cmsAddress);

    IaagMap iaagMap = new IaagMap();
    iaagMap.setConn(simpleServerStart.getConn());
    iaagMap.load();
    simpleServerStart.setIaagMap(iaagMap);

    if (simpleServerStart.getIaagClients() != null && simpleServerStart.getIaagClients().size() != 0) {
      for (VsIAClient client : simpleServerStart.getIaagClients()) {
        simpleServerStart.getIaagClients().remove(client);
        client.zero();
      }
    }
    List<String> iaagAdress = new ArrayList<>();
    try {
      iaagAdress = myservice.ResolveAllAddress("server_iaag");
    } catch (Exception e) {
      getLogger().error(e.getMessage(), e);
      e.printStackTrace();
    }
    simpleServerStart.setIaagClients2(iaagAdress);

    simpleServerStart.getExecutor().scheduleWithFixedDelay(() -> {
      try {
        getLogger().info("updateConfig  thread : " + Thread.currentThread().getName() + " " + Thread.currentThread().getId() + "  is running");
        System.out.println("updateConfig  thread : " + Thread.currentThread().getName() + " " + Thread.currentThread().getId() + "  is running");
        simpleServerStart.updateConfig(myservice);
        //simpleServerStart.getIaagMap().dispatch();
      } catch (SQLException e) {
        simpleServerStart.getLogger().error(e.getMessage(), e);
        e.printStackTrace();
      } catch (JsonFormat.ParseException e) {
        simpleServerStart.getLogger().error(e.getMessage(), e);
        e.printStackTrace();
      }
      simpleServerStart.getIaagMap().dispatch();
    }, 1L, 1L, TimeUnit.SECONDS);


//    //#####################################################################
//    String alarmBellUrl = "http://" + "192.168.20.145"+ ":" + 8080 + "/" + "sendMessage";
//    System.out.println("alarmBellUrl"+alarmBellUrl);
//    String message = new String("中文_abcded17");
//    Properties initProp = new Properties(System.getProperties());
//    System.out.println("当前系统编码:" + initProp.getProperty("file.encoding"));
//    System.out.println("当前系统语言:" + initProp.getProperty("user.language"));
//    int id = 2;
//    String tmp = simpleServerStart.getEventConfig().findEventInfo(id).getEvent_name();
//    HttpClientUtils.doPostHttp(alarmBellUrl, tmp, 1);
//    //#####################################################################



    //monitor process
    AlarmProcess monitorProcess = new AlarmProcess();
    monitorProcess.setLogger(simpleServerStart.getLogger());
    monitorProcess.setBlgTeyeClient(simpleServerStart.getBlgClient());
    monitorProcess.setCmsIeyeClient(simpleServerStart.getCmsClient());
    monitorProcess.setName("monitorProcess");
    monitorProcess.setEventConfig(simpleServerStart.getEventConfig());
    monitorProcess.setIaagMap(simpleServerStart.getIaagMap());
    monitorProcess.setProcessType(ProcessMonitorType);
    monitorProcess.dbInit(dbname, dbAddress, driverClassName, dbUser, dbPasswd);
    monitorProcess.setMonitorCq(simpleServerStart.getMonitorCq());
    //monitorProcess.setRedisUtil(simpleServerStart.getRedisUtil());
    //monitorProcess.redisInit(redisIP, redisPort);
    monitorProcess.mqInit(activemqIp, activemqPort);
    monitorProcess.setAlarmBellUrl(simpleServerStart.getAlarmUrl());

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
    sioProcess.setSioCq(simpleServerStart.getSioCq());
    //sioProcess.setRedisUtil(simpleServerStart.getRedisUtil());
    //sioProcess.redisInit(redisIP, redisPort);
    sioProcess.mqInit(activemqIp, activemqPort);
    sioProcess.setAlarmBellUrl(simpleServerStart.getAlarmUrl());

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
    iaProcess.setIaCq(simpleServerStart.getIaCq());
    //iaProcess.setRedisUtil(simpleServerStart.getRedisUtil());
    //iaProcess.redisInit(redisIP, redisPort);
    iaProcess.mqInit(activemqIp, activemqPort);
    iaProcess.setAlarmBellUrl(simpleServerStart.getAlarmUrl());

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
//    serverProcess.setRedisUtil(simpleServerStart.getRedisUtil());
//    serverProcess.mqInit(activemqIp, activemqPort);
    //serverProcess.setAlarmBellUrl(simpleServerStart.getAlarmUrl());
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
//    deviceProcess.setRedisUtil(simpleServerStart.getRedisUtil());
//    deviceProcess.redisInit(redisIP, redisPort);
//    deviceProcess.mqInit(activemqIp, activemqPort);
    // deviceProcess.setAlarmBellUrl(simpleServerStart.getAlarmUrl());

    new Thread(monitorProcess).start();
    new Thread(sioProcess).start();
    new Thread(iaProcess).start();
//    new Thread(serverProcess).start();
//    new Thread(deviceProcess).start();

//    simpleServerStart.getExecutor().scheduleWithFixedDelay(() -> {
//      try {
//        simpleServerStart.updateConfig(myservice);
//      } catch (SQLException e) {
//        simpleServerStart.getLogger().error(e.getMessage(), e);
//        e.printStackTrace();
//      } catch (JsonFormat.ParseException e) {
//        simpleServerStart.getLogger().error(e.getMessage(), e);
//        e.printStackTrace();
//      }
//    }, 1l, 1L, TimeUnit.SECONDS);


    //TimeUnit.DAYS.sleep(365 * 2000);
  }
}
