//package com.vorxsoft.ieye.eventservice.util;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//import com.vorxsoft.ieye.proto.PTZ_ACT;
//import com.vorxsoft.ieye.proto.ReportDevStatesRequest.DevStates;
//import com.vorxsoft.ieye.proto.ReportDevStatesRequest.ResStates;
//import com.vorxsoft.vscms.common.VSDefCode;
//import com.vorxsoft.vscms.common.VsCloudInfo;
//import com.vorxsoft.vscms.common.IaagInfo;
//import com.vorxsoft.vscms.common.VsMainSvrInfo;
//import com.vorxsoft.vscms.common.VsRmsInfo;
//import com.vorxsoft.vscms.common.VsSvrInfo;
//import com.vorxsoft.vscms.common.VsSwichPlanInfo;
//import com.vorxsoft.vscms.common.VsSwitchTaskInfo;
//import com.vorxsoft.vscms.common.VsSwitchseqInfo;
//import com.vorxsoft.vscms.ieyeapi.model.VsCasCadeModel;
//import com.vorxsoft.vscms.ieyeapi.model.VsDevModel;
//import com.vorxsoft.vscms.ieyeapi.model.VsRecCamModel;
//import com.vorxsoft.vscms.ieyeapi.model.VsRecPlanModel;
//import com.vorxsoft.vscms.ieyeapi.model.VsRecTimeModel;
//import com.vorxsoft.vscms.ieyeapi.model.ResInfo;
//
///**
// * Database class for Vorx Cloud2.0 platform This class is composed by many
// * methods for database operation
// *
// * @author Nie Bo
// */
//public class DBPlatformServer {
//
//	private static final int RETURN_OK = 0;
//	private static final int RETURN_NG = -1;
//
//	private static final int WEB_CLIENT = 0;
//	private static final int IOS_CLIENT = 1;
//	private static final int ANDROID_CLIENT = 2;
//	private static final int UPPER_CLIENT = 3;
//
//	private static final Logger logger = Logger.getLogger(DBPlatformServer.class);
//
//	// cms
//	public static ResInfo getResinfo(String szResNo, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		ResInfo vsResInfo = null;
//		String cmd = "SELECT a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id ,a.res_no,a.res_name,a.res_type,a.chn_type,b.dev_id,d.svr_no FROM ti_resource a left join ti_device b "
//				+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on b.svr_id=c.svr_id LEFT JOIN ti_server d on d.svr_id=c.svr_id WHERE a.res_no =?";
//
//		// String cmd = "SELECT
//		// a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id FROM
//		// ti_resource a left join ti_device b "
//		// + "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on
//		// b.svr_id=c.svr_id WHERE a.res_no =?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, szResNo);
//
//			rs = st.executeQuery();
//			if (rs.next()) {
//				vsResInfo = new ResInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
//						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11),
//						rs.getString(12));
//				// vsResInfo = new ResInfo(rs.getInt(1), rs.getString(2),
//				// rs.getString(3), rs.getString(4), rs.getInt(5),
//				// rs.getInt(6));
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return vsResInfo;
//	}
//
//	/**
//	 * init client and device state
//	 *
//	 * @param connPool
//	 */
//	public static void initClientDevState(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		String cmd = "UPDATE ti_user_state SET mobile_user_state=0, browser_user_state=0";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.executeUpdate();
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//
//			cmd = "UPDATE ti_Dev_State SET online_state=0";
//			st = conn.prepareStatement(cmd);
//			st.executeUpdate();
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//
//			cmd = "UPDATE ti_res_state SET online_state=0";
//			st = conn.prepareStatement(cmd);
//			st.executeUpdate();
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//
//			cmd = "UPDATE ti_Server_state SET online_state=0";
//			st = conn.prepareStatement(cmd);
//			st.executeUpdate();
//
//			// cmd = "DELETE from ti_CallDev";
//			// st = conn.prepareStatement(cmd);
//			// st.executeUpdate();
//
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//			// e.printStackTrace();
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//
//	}
//
//	public static void iosLogoutPlatform(int clientId, ConnectionPool connPool) {
//
//		Connection conn = null;
//		PreparedStatement st = null;
//
//		// if (identity.equals(IOS_CLIENT_IDENTITY)) {
//		//// cmd = "UPDATE ti_UserState SET MobileUserState=0 WHERE UserID=?";
//		// return ;
//		// }
//
//		String cmd = "UPDATE ti_UserState SET MobileUserState=0,iOSToken='' WHERE UserID=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, clientId);
//			st.executeUpdate();
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//
//	}
//
//	/**
//	 * 同一用户在两个手机登陆线,找到前一个IOS
//	 *
//	 * @param clientID
//	 * @param token
//	 * @param connPool
//	 * @return
//	 */
//	public static String otherIOSToken(int clientID, String token, int iosClient, ConnectionPool connPool) {
//
//		Connection conn = null;
//		String iOSToken = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//
//		String cmd = "SELECT iOSToken FROM ti_UserState WHERE iOSToken <> ? and UserID = ? and MobileType=? and MobileUserState=1";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, token);
//			st.setInt(2, clientID);
//			st.setInt(3, iosClient);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				iOSToken = rs.getString(1);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return iOSToken;
//	}
//
//	public static String getDevNameById(int devId, ConnectionPool connPool) {
//
//		Connection conn = null;
//		String devName = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//
//		String cmd = "SELECT t.DevName FROM ti_DeviceInfo t WHERE t.DevID = ?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, devId);
//
//			rs = st.executeQuery();
//			if (rs.next()) {
//				devName = rs.getString(1);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return devName;
//	}
//
//	public static String getUserNameById(int userId, ConnectionPool connPool) {
//
//		Connection conn = null;
//		String userName = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//
//		String cmd = "SELECT t.UserName FROM ti_UserInfo t WHERE t.UserID = ?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, userId);
//
//			rs = st.executeQuery();
//			if (rs.next()) {
//				userName = rs.getString(1);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return userName;
//	}
//
//	/**
//	 * 同一设备其他用户下线
//	 *
//	 * @param clientID
//	 * @param token
//	 * @param connPool
//	 * @return
//	 */
//	public static int otherIOSclientOffByToken(int clientID, String token, ConnectionPool connPool) {
//		if (token == null || token.length() < 50) {
//			return 0;
//		}
//
//		Connection conn = null;
//		int userID = 0;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//
//		String cmd = "SELECT UserID FROM ti_UserState WHERE iOSToken =? and UserID <> ?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, token);
//			st.setInt(2, clientID);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				userID = rs.getInt(1);
//			}
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//			if (rs != null) {
//				rs.close();
//				rs = null;
//			}
//			if (userID != 0) {
//				cmd = "UPDATE ti_UserState SET MobileUserState=0, iOSToken='' WHERE UserID=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, userID);
//				st.executeUpdate();
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return userID;
//
//	}
//
//	/**
//	 * 验证登陆名密码
//	 *
//	 * @param user
//	 * @param password
//	 * @param connPool
//	 * @return
//	 */
//	public static int clientLoginPlatformCheck(String user, String password, ConnectionPool connPool) {
//		Connection conn = null;
//		int userID = 0;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//
//		// weidong 修改登录昵称为登录名
//		String cmd = "SELECT UserID FROM ti_UserInfo WHERE UserName = ? and Password =? ";
//
//		// log.
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, user);
//			st.setString(2, password);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				userID = rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return userID;
//	}
//
//	public static int clientLoginPlatform(int userID, int clientType, String token, String version,
//			ConnectionPool connPool) {
//		Connection conn = null;
//
//		PreparedStatement st = null;
//
//		String cmd = null;
//
//		// log.
//		try {
//			conn = connPool.getConnection();
//			switch (clientType) {
//			case WEB_CLIENT:
//				cmd = "UPDATE ti_UserState SET BrowserUserState=1, BrowserType=?, BrowserVersion=? WHERE UserID=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, clientType);
//				st.setString(2, version);
//				st.setInt(3, userID);
//				break;
//			case UPPER_CLIENT:
//				cmd = "UPDATE ti_UserState SET BrowserUserState=1, BrowserType=?, BrowserVersion=? WHERE UserID=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, clientType);
//				st.setString(2, version);
//				st.setInt(3, userID);
//				break;
//			case IOS_CLIENT:
//				cmd = "UPDATE ti_UserState SET MobileUserState=1, MobileType=?, MobileVersion=?, iOSToken=? WHERE UserID=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, clientType);
//				st.setString(2, version);
//				st.setString(3, token);
//				st.setInt(4, userID);
//				break;
//			case ANDROID_CLIENT:
//				cmd = "UPDATE ti_UserState SET MobileUserState=1, MobileType=?, MobileVersion=? , iOSToken='' WHERE UserID=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, clientType);
//				st.setString(2, version);
//				st.setInt(3, userID);
//				break;
//			default: {
//				return 0;
//			}
//			}
//			if (st.executeUpdate() == 0) {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				switch (clientType) {
//				case WEB_CLIENT:
//					cmd = "INSERT INTO ti_UserState(UserID, BrowserUserState, BrowserType, BrowserVersion) Values(?,1,?,?)";
//					st = conn.prepareStatement(cmd);
//					st.setInt(1, userID);
//					st.setInt(2, clientType);
//					st.setString(3, version);
//					break;
//				case UPPER_CLIENT:
//					cmd = "INSERT INTO ti_UserState(UserID, BrowserUserState, BrowserType, BrowserVersion) Values(?,1,?,?)";
//					st = conn.prepareStatement(cmd);
//					st.setInt(1, userID);
//					st.setInt(2, clientType);
//					st.setString(3, version);
//					break;
//				case IOS_CLIENT:
//					cmd = "INSERT INTO ti_UserState(UserID, MobileUserState, MobileType, MobileVersion, iOSToken) Values(?,1,?,?,?)";
//					st = conn.prepareStatement(cmd);
//					st.setInt(1, userID);
//					st.setInt(2, clientType);
//					st.setString(3, version);
//					st.setString(4, token);
//					break;
//				case ANDROID_CLIENT:
//					cmd = "INSERT INTO ti_UserState(UserID, MobileUserState, MobileType, MobileVersion) Values(?,1,?,?)";
//					st = conn.prepareStatement(cmd);
//					st.setInt(1, userID);
//					st.setInt(2, clientType);
//					st.setString(3, version);
//					break;
//				default:
//					break;
//				}
//				st.executeUpdate();
//
//			}
//
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//
//			cmd = "UPDATE ti_UserInfo SET LastLoginTime = ? WHERE UserID=?";
//			st = conn.prepareStatement(cmd);
//			st.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//			st.setInt(2, userID);
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return userID;
//
//	}
//
//	private static final String WEB_CLIENT_IDENTITY = "Web";
//	private static final String UPPER_CLIENT_IDENTITY = "Uper";
//	private static final String IOS_CLIENT_IDENTITY = "iOS";
//	private static final String ANDROID_CLIENT_IDENTITY = "And";
//	private static final String DEV_IDENTITY = "Dev";
//	private static final String STREAM_SERVER_IDENTITY = "Str";
//
//	public static void setOffline(int ID, String identity, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		String cmd = null;
//		if (identity.equals(WEB_CLIENT_IDENTITY)) {
//			cmd = "UPDATE ti_UserState SET BrowserUserState=0 WHERE UserID=?";
//		} else if (identity.equals(UPPER_CLIENT_IDENTITY)) {
//			cmd = "UPDATE ti_UserState SET BrowserUserState=0 WHERE UserID=?";
//		} else if (identity.equals(IOS_CLIENT_IDENTITY)) {
//			// cmd = "UPDATE ti_UserState SET MobileUserState=0 WHERE UserID=?";
//			return;
//		} else if (identity.equals(ANDROID_CLIENT_IDENTITY)) {
//			cmd = "UPDATE ti_UserState SET MobileUserState=0 WHERE UserID=?";
//		} else if (identity.equals(DEV_IDENTITY)) {
//			cmd = "UPDATE ti_DevState SET state=0 WHERE DevID=?";
//		} else if (identity.equals(STREAM_SERVER_IDENTITY)) {
//			cmd = "UPDATE ti_Servers SET state=0 WHERE ServerID=?";
//		}
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, ID);
//			st.executeUpdate();
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//
//			if (identity == DEV_IDENTITY) {
//				cmd = "UPDATE ti_CameraInfo SET CamState=0 WHERE DevID=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, ID);
//				st.executeUpdate();
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//	}
//
//	public static void setClientAsOffline(int ID, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		String cmd = "UPDATE ti_UserState SET state=0 WHERE UserID=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, ID);
//			st.executeUpdate();
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//
//	}
//
//	public static void setDevAsOffline(String sIp, int port, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		// String cmd = "SELECT a.dev_id from ti_device a left join
//		// ti_server_media_ex b on a.svr_id=b.svr_id WHERE b.ip_intranet=? and
//		// b.port_manage=?";
//		String cmd = "UPDATE ti_dev_state SET online_state=0 WHERE dev_id in(SELECT a.dev_id from ti_device a left join ti_server_media_ex b on a.svr_id=b.svr_id WHERE b.ip_intranet=? and b.port_manage=?)";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sIp);
//			st.setInt(2, port);
//			st.executeUpdate();
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//			cmd = "UPDATE ti_res_state SET online_state=0 WHERE res_id in(SELECT c.res_id from ti_resource c left join ti_device a on c.dev_id=a.dev_id left join ti_server_media_ex b on a.svr_id=b.svr_id WHERE b.ip_intranet=? and b.port_manage=?)";
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sIp);
//			st.setInt(2, port);
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//	}
//
//	public static void setDevAsOnline(String sIp, int nPort, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		// String cmd = "SELECT a.dev_id from ti_device a left join
//		// ti_server_media_ex b on a.svr_id=b.svr_id WHERE b.ip_intranet=? and
//		// b.port_manage=?";
//		String cmd = "UPDATE ti_dev_state SET online_state=0 WHERE dev_id in(SELECT a.dev_id from ti_device a left join ti_server_media_ex b on a.svr_id=b.svr_id WHERE b.ip_intranet=? and b.port_manage=?)";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sIp);
//			st.setInt(2, nPort);
//			st.executeUpdate();
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//			cmd = "UPDATE ti_res_state SET online_state=0 WHERE res_id in(SELECT c.res_id from ti_resource c left join ti_device a on c.dev_id=a.dev_id left join ti_server_media_ex b on a.svr_id=b.svr_id WHERE b.ip_intranet=? and b.port_manage=?)";
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sIp);
//			st.setInt(2, nPort);
//			st.executeUpdate();
//
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//
//	}
//
//	public static List<VsDevModel> getDevbySvrIP(String sIp, int nPort, String svrType, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsDevModel> devlist = new ArrayList<VsDevModel>();
//		String cmd = null;
//		switch (svrType) {
//		case VSDefCode.vagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_video,b.chn_alarmin,b.chn_alarmout FROM ti_device a INNER join ti_device_nvs_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_manage=?";
//			break;
//		case VSDefCode.vmsname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_dec,b.dec_mode FROM ti_device a INNER join ti_device_dec_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_manage=?";
//			break;
//		case VSDefCode.amagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_videoin,b.chn_videoout,b.com,b.addr FROM ti_device a INNER join ti_device_matrix_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_manage=?";
//			break;
//		case  VSDefCode.sioagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_alarmin,b.chn_alarmout,b.com,b.addr FROM ti_device a INNER join ti_device_sio_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_main_ex c on a.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_intranet=?";
//			break;
//		case VSDefCode.iaagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_video FROM ti_device a INNER join ti_device_iau_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_main_ex c on a.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_intranet=?";
//			break;
//		}
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sIp);
//			st.setInt(2, nPort);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsDevModel vsDevInfo = new VsDevModel();
//				vsDevInfo.setDev_no(rs.getString(1));
//				vsDevInfo.setSvr_id(rs.getInt(2));
//				vsDevInfo.setDev_name(rs.getString(3));
//				vsDevInfo.setDev_type(rs.getString(4));
//				vsDevInfo.setProtocol_type(rs.getString(5));
//				vsDevInfo.setIp(rs.getString(6));
//				vsDevInfo.setPort(rs.getInt(7));
//				vsDevInfo.setUsername(rs.getString(8));
//				vsDevInfo.setPassword(rs.getString(9));
//				vsDevInfo.setDev_id(rs.getInt(10));
//				switch (svrType) {
//				case VSDefCode.vagname:
//					vsDevInfo.setChn_video(rs.getInt(11));
//					vsDevInfo.setChn_alarmin(rs.getInt(12));
//					vsDevInfo.setChn_alarmout(rs.getInt(13));
//					devlist.add(vsDevInfo);
//					break;
//				case VSDefCode.vmsname:
//					vsDevInfo.setChn_dec(rs.getInt(11));
//					vsDevInfo.setDec_mode(rs.getInt(12));
//					devlist.add(vsDevInfo);
//					break;
//				case VSDefCode.amagname:
//					vsDevInfo.setChn_videoin(rs.getInt(11));
//					vsDevInfo.setChn_videoout(rs.getInt(12));
//					vsDevInfo.setCom(rs.getInt(13));
//					vsDevInfo.setAddr(rs.getInt(14));
//					devlist.add(vsDevInfo);
//					break;
//				case  VSDefCode.sioagname:
//					vsDevInfo.setChn_alarmin(rs.getInt(11));
//					vsDevInfo.setChn_alarmout(rs.getInt(12));
//					vsDevInfo.setCom(rs.getInt(13));
//					vsDevInfo.setAddr(rs.getInt(14));
//					devlist.add(vsDevInfo);
//					break;
//				case VSDefCode.iaagname:
//					vsDevInfo.setChn_video(rs.getInt(11));
//					devlist.add(vsDevInfo);
//					break;
//				}
//
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return devlist;
//	}
//
//	public static List<ResInfo> getResbySvrIP(String sIp, int nPort, String svrType, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<ResInfo> reslist = new ArrayList<ResInfo>();
//		String cmd = "SELECT a.res_id,a.res_uid,a.res_no,a.res_name,a.res_type,a.chn_type,c.ip_intranet,c.port_manage,c.svr_id,b.dev_id ,b.dev_no FROM ti_resource a left join ti_device b "
//				+ "on a.dev_id=b.dev_id inner JOIN ti_server_media_ex c on b.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_manage=?";
//		if (svrType == VSDefCode.sioagname) {
//			cmd = "SELECT a.res_id,a.res_uid,a.res_no,a.res_name,a.res_type,a.chn_type,c.ip_intranet,c.port_intranet,c.svr_id FROM ti_resource a left join tr_iaag_cam b "
//					+ "on a.res_id=b.res_id inner JOIN ti_server_main_ex c on b.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_intranet=?";
//			// cmd = "SELECT
//			// a.res_id,a.res_uid,a.res_no,a.res_name,a.res_type,a.chn_type,c.ip_intranet,c.port_manage,c.svr_id
//			// FROM ti_resource a left join tr_iaag_cam b "
//			// + "on a.res_id=b.res_id inner JOIN ti_server_media_ex c on
//			// b.svr_id=c.svr_id WHERE c.ip_intranet =? and c.port_manage=?";
//		}
//		if(svrType.equals(VSDefCode.sioagname)){
//			cmd = "SELECT a.res_id,a.res_uid,a.res_no,a.res_name,a.res_type,a.chn_type,c.ip_intranet,c.port_intranet,c.svr_id,b.dev_id ,b.dev_no,d.sio_mode FROM ti_resource a left join ti_device b "
//					+ "on a.dev_id=b.dev_id inner JOIN ti_server_main_ex c on b.svr_id=c.svr_id inner join ti_res_sio_ex d on d.res_id=a.res_id WHERE c.ip_intranet =? and c.port_intranet=?";
//		}
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sIp);
//			st.setInt(2, nPort);
//
//			rs = st.executeQuery();
//			while (rs.next()) {
//				ResInfo vsResInfo = new ResInfo();
//				vsResInfo.setRes_id(rs.getInt(1));
//				vsResInfo.setRes_uid(rs.getString(2));
//				vsResInfo.setRes_no(rs.getString(3));
//				vsResInfo.setRes_name(rs.getString(4));
//				vsResInfo.setRes_type(rs.getString(5));
//				vsResInfo.setChn_type(rs.getInt(6));
//				vsResInfo.setIp_intranet(rs.getString(7));
//				vsResInfo.setPort_manage(rs.getInt(8));
//				vsResInfo.setSvr_id(rs.getInt(9));
//				if (!svrType.equals(VSDefCode.iaagname)) {
//					vsResInfo.setDev_id(rs.getInt(10));// iaag此字段
//					vsResInfo.setDev_no(rs.getString(11));// iaag 此字段不解析无用
//				}
//				if(svrType.equals(VSDefCode.sioagname)){
//					vsResInfo.setSio_mode(rs.getInt(12));
//				}
//				reslist.add(vsResInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return reslist;
//
//	}
//
//	public static List<VsRecTimeModel> getRecTime(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsRecTimeModel> recTimelist = new ArrayList<VsRecTimeModel>();
//		String cmd = "SELECT rec_timeplan_id,timeplan_name,time_schedule  FROM ti_record_timeplan ";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsRecTimeModel vsRecTime = new VsRecTimeModel(rs.getInt(1), rs.getString(2), rs.getString(3));
//				recTimelist.add(vsRecTime);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return recTimelist;
//	}
//
//	public static List<VsRecPlanModel> getRecPlan(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsRecPlanModel> recPlanlist = new ArrayList<VsRecPlanModel>();
//		String cmd = "SELECT *  FROM ti_record_plan ";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsRecPlanModel vsRecPlan = new VsRecPlanModel(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4),
//						rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
//						rs.getInt(11), rs.getInt(12), rs.getString(13), rs.getInt(14));
//				// vsRecTime.setRec_timeplan_id(rs.getInt(1));
//				// vsRecTime.setTimeplan_name(rs.getString(2));
//				// vsRecTime.setTime_schedule(rs.getString(3));
//				recPlanlist.add(vsRecPlan);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return recPlanlist;
//	}
//
//	public static List<VsRecCamModel> getRecCam(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsRecCamModel> recCamlist = new ArrayList<VsRecCamModel>();
//		String cmd = "SELECT a.rec_plan_id,b.dev_no,a.res_uid,b.protocol_type,a.dev_id,a.res_id FROM tr_record_plan_cam a left join ti_device b on a.dev_id=b.dev_id";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsRecCamModel vsRecCam = new VsRecCamModel(rs.getInt(1), rs.getString(2), rs.getString(3),
//						rs.getString(4), rs.getInt(5), rs.getInt(6));
//				recCamlist.add(vsRecCam);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return recCamlist;
//	}
//
//	public static void updateResState(ResStates restemp, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String cmd = "UPDATE ti_res_state SET online_state=? WHERE res_id=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, restemp.getNState());
//			st.setInt(2, restemp.getNResID());
//			st.execute();
//			if (st.executeUpdate() == 0) {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				cmd = "select * from ti_resource where res_id=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, restemp.getNResID());
//				rs = st.executeQuery();
//				if (rs.next()) {
//					if (st != null) {
//						st.close();
//						st = null;
//					}
//					cmd = "INSERT INTO ti_res_state(res_id, online_state) Values(?,?)";
//					st = conn.prepareStatement(cmd);
//					st.setInt(1, restemp.getNResID());
//					st.setInt(2, restemp.getNState());
//					st.executeUpdate();
//				}
//			}
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//	}
//
//	public static int getSvrbyIP(String svrType, String sIp, int nPort, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		int svrid = 0;
//		String cmd = null;
//		if (svrType.equals(VSDefCode.sioagname) || svrType.equals(VSDefCode.iaagname) || svrType.equals(VSDefCode.emsname)) {
//			cmd = "SELECT svr_id  FROM ti_server_main_ex WHERE ip_intranet=? and port_intranet=?";
//		} else {
//			cmd = "SELECT svr_id  FROM ti_server_media_ex WHERE ip_intranet=? and port_manage=?";
//		}
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sIp);
//			st.setInt(2, nPort);
//
//			rs = st.executeQuery();
//			if (rs.next()) {
//				svrid = rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return svrid;
//	}
//
//	public static void SetPreset(int nPresetNo, String szName, int res_id, String token, PTZ_ACT emAct,
//			ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		String cmd = "delete from ti_preset  where preset_no=? and res_id=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, nPresetNo);
//			// st.setString(2, szName);
//			// st.setString(3, token);
//			st.setInt(2, res_id);
//			st.execute();
//			if (st != null) {
//				st.close();
//				st = null;
//			}
//			if (emAct == PTZ_ACT.PTZ_SET_PRESET) {
//				cmd = "INSERT INFO ti_preset(preset_no, preset_name,preset_token,res_id)  Values(?,?,?,?)";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, nPresetNo);
//				st.setString(2, szName);
//				st.setString(3, token);
//				st.setInt(4, res_id);
//				st.execute();
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			}
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//
//	}
//
//	public static ResInfo getResinfobyId(int res_id, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		ResInfo vsResInfo = null;
//		String cmd = "SELECT a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id ,a.res_no,a.res_name,a.res_type,a.chn_type,b.dev_id,d.svr_no FROM ti_resource a left join ti_device b "
//				+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on b.svr_id=c.svr_id LEFT JOIN ti_server d on d.svr_id=c.svr_id WHERE a.res_id =?";
//
//		// String cmd = "SELECT
//		// a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id FROM
//		// ti_resource a left join ti_device b "
//		// + "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on
//		// b.svr_id=c.svr_id WHERE a.res_id =?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, res_id);
//
//			rs = st.executeQuery();
//			if (rs.next()) {
//				vsResInfo = new ResInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
//						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11),
//						rs.getString(12));
//				// vsResInfo = new ResInfo(rs.getInt(1), rs.getString(2),
//				// rs.getString(3), rs.getString(4), rs.getInt(5),
//				// rs.getInt(6));
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return vsResInfo;
//	}
//
//	public static VsRmsInfo getrmsinfo(String resno, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		VsRmsInfo vsRmsInfo = null;
//		String cmd = "SELECT a.svr_id,a.svr_no,a.svr_name,b.ip_extranet,b.ip_intranet,b.port_manage,a.enable_state,b.http_port FROM "
//				+ "ti_server a  LEFT JOIN ti_server_media_ex b on a.svr_id=b.svr_id WHERE a.svr_type ='server_rms' and a.enable_state=1 "
//				+ "and a.machine_id=(SELECT c.machine_id  FROM ti_resource a left join ti_device b on a.dev_id=b.dev_id "
//				+ "LEFT JOIN ti_server c on b.svr_id=c.svr_id  WHERE a.res_no =?)";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, resno);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				vsRmsInfo = new VsRmsInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
//						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return vsRmsInfo;
//	}
//
//	public static VsMainSvrInfo getmainsvr(String svrtype, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		VsMainSvrInfo vsMainInfo = null;
//		String cmd = "SELECT a.svr_id,a.svr_no,a.svr_name,a.svr_type,a.machine_id,a.enable_state,b.ip_extranet,b.ip_intranet,b.port_extranet,b.port_intranet FROM "
//				+ "ti_server a  LEFT JOIN ti_server_main_ex b on a.svr_id=b.svr_id WHERE a.svr_type =? ";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, svrtype);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				vsMainInfo = new VsMainSvrInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
//						rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10));
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return vsMainInfo;
//	}
//
//	public static List<ResInfo> getAllCamRes(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<ResInfo> reslist = new ArrayList<ResInfo>();
//		String cmd = "SELECT a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id ,a.res_no,a.res_name,a.res_type,a.chn_type,b.dev_id,d.svr_no FROM ti_resource a left join ti_device b "
//				+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on b.svr_id=c.svr_id LEFT JOIN ti_server d on d.svr_id=c.svr_id where a.chn_type=1";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//
//			rs = st.executeQuery();
//			while (rs.next()) {
//				ResInfo vsResInfo = new ResInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
//						rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10),
//						rs.getInt(11), rs.getString(12));
//				reslist.add(vsResInfo);
//				// CMSServer.resinfoMap.put(resno, vsResInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return reslist;
//	}
//
//	public static void updateDevState(DevStates devstate, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String cmd = "UPDATE ti_dev_state SET online_state=? WHERE dev_id=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, devstate.getNState());
//			st.setInt(2, devstate.getNDevID());
//
//			if (st.executeUpdate() == 0) {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				cmd = "select * from ti_device where dev_id=?";
//				st = conn.prepareStatement(cmd);
//				st.setInt(1, devstate.getNDevID());
//				rs = st.executeQuery();
//				if (rs.next()) {
//					if (st != null) {
//						st.close();
//						st = null;
//					}
//					cmd = "INSERT INTO ti_dev_state(dev_id, online_state) Values(?,?)";
//					st = conn.prepareStatement(cmd);
//					st.setInt(1, devstate.getNDevID());
//					st.setInt(2, devstate.getNState());
//					st.execute();
//				}
//			}
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//	}
//
//	public static String getPwdbyUser(String strUser, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String password = null;
//		String cmd = "SELECT password from ti_user where username=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, strUser);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				password = rs.getString(1);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return password;
//	}
//
//	public static List<VsDevModel> getDevbyDevIDs(List<Integer> idlist, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsDevModel> devlist = new ArrayList<VsDevModel>();
//		String ids = idlist.toString();
//		ids = ids.substring(1, ids.length() - 1);
//		String cmd = null;
//		cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,a.dev_id FROM ti_device a WHERE a.dev_id in ("
//				+ ids + ")";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsDevModel vsDevInfo = new VsDevModel();
//				vsDevInfo.setDev_no(rs.getString(1));
//				vsDevInfo.setSvr_id(rs.getInt(2));
//				vsDevInfo.setDev_name(rs.getString(3));
//				vsDevInfo.setDev_type(rs.getString(4));
//				vsDevInfo.setProtocol_type(rs.getString(5));
//				vsDevInfo.setDev_id(rs.getInt(6));
//				devlist.add(vsDevInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return devlist;
//	}
//
//	public static VsDevModel getDevbyDevNo(String dev_no, String svrType, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String cmd = null;
//		VsDevModel vsDevInfo = new VsDevModel();
//		switch (svrType) {
//		case VSDefCode.vagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_video,b.chn_alarmin,b.chn_alarmout FROM ti_device a INNER join ti_device_nvs_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE a.dev_no=?";
//			break;
//		case VSDefCode.vmsname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_dec,b.dec_mode FROM ti_device a INNER join ti_device_dec_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE a.dev_no=?";
//			break;
//		case VSDefCode.amagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_videoin,b.chn_videoout,b.com,b.addr FROM ti_device a INNER join ti_device_matrix_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE a.dev_no=?";
//			break;
//		case VSDefCode.sioagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_alarmin,b.chn_alarmout,b.com,b.addr FROM ti_device a INNER join ti_device_sio_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_main_ex c on a.svr_id=c.svr_id WHERE a.dev_no=?";
//			break;
//		case VSDefCode.iaagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_video FROM ti_device a INNER join ti_device_ia_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_main_ex c on a.svr_id=c.svr_id WHERE a.dev_no=?";
//			break;
//		}
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				vsDevInfo.setDev_no(rs.getString(1));
//				vsDevInfo.setSvr_id(rs.getInt(2));
//				vsDevInfo.setDev_name(rs.getString(3));
//				vsDevInfo.setDev_type(rs.getString(4));
//				vsDevInfo.setProtocol_type(rs.getString(5));
//				vsDevInfo.setIp(rs.getString(6));
//				vsDevInfo.setPort(rs.getInt(7));
//				vsDevInfo.setUsername(rs.getString(8));
//				vsDevInfo.setPassword(rs.getString(9));
//				vsDevInfo.setDev_id(rs.getInt(10));
//				switch (svrType) {
//				case VSDefCode.vagname:
//					vsDevInfo.setChn_video(rs.getInt(11));
//					vsDevInfo.setChn_alarmin(rs.getInt(12));
//					vsDevInfo.setChn_alarmout(rs.getInt(13));
//					break;
//				case VSDefCode.vmsname:
//					vsDevInfo.setChn_dec(rs.getInt(11));
//					vsDevInfo.setDec_mode(rs.getInt(12));
//					break;
//				case VSDefCode.amagname:
//					vsDevInfo.setChn_videoin(rs.getInt(11));
//					vsDevInfo.setChn_videoout(rs.getInt(12));
//					vsDevInfo.setCom(rs.getInt(13));
//					vsDevInfo.setAddr(rs.getInt(14));
//					break;
//				case VSDefCode.sioagname:
//					vsDevInfo.setChn_alarmin(rs.getInt(11));
//					vsDevInfo.setChn_alarmout(rs.getInt(12));
//					vsDevInfo.setCom(rs.getInt(13));
//					vsDevInfo.setAddr(rs.getInt(14));
//					break;
//				case VSDefCode.iaagname:
//					vsDevInfo.setChn_video(rs.getInt(11));
//					break;
//				}
//
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return vsDevInfo;
//	}
//
//	public static List<VsDevModel> getDevbyDevIDs(List<Integer> idlist, String svrType, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsDevModel> devlist = new ArrayList<VsDevModel>();
//		String ids = idlist.toString();
//		ids = ids.substring(1, ids.length() - 1);
//		String cmd = null;
//
//		switch (svrType) {
//		case VSDefCode.vagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_video,b.chn_alarmin,b.chn_alarmout FROM ti_device a INNER join ti_device_nvs_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE a.dev_id in ("
//					+ ids + " )";
//			break;
//		case VSDefCode.vmsname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_dec,b.dec_mode FROM ti_device a INNER join ti_device_dec_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE a.dev_id in ("
//					+ ids + " )";
//			break;
//		case VSDefCode.amagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_videoin,b.chn_videoout,b.com,b.addr FROM ti_device a INNER join ti_device_matrix_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_media_ex c on a.svr_id=c.svr_id WHERE a.dev_id in ("
//					+ ids + " )";
//			break;
//		case  VSDefCode.sioagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_alarmin,b.chn_alarmout,b.com,b.addr FROM ti_device a INNER join ti_device_sio_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_main_ex c on a.svr_id=c.svr_id WHERE a.dev_id in ("
//					+ ids + " )";
//			break;
//		case VSDefCode.iaagname:
//			cmd = "SELECT a.dev_no, a.svr_id,a.dev_name,a.dev_type,a.protocol_type,b.ip,b.port,b.username,b.password,a.dev_id,b.chn_video FROM ti_device a INNER join ti_device_ia_ex b "
//					+ "on a.dev_id=b.dev_id LEFT JOIN ti_server_main_ex c on a.svr_id=c.svr_id WHERE a.dev_id in ("
//					+ ids + " )";
//			break;
//		}
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsDevModel vsDevInfo = new VsDevModel();
//				vsDevInfo.setDev_no(rs.getString(1));
//				vsDevInfo.setSvr_id(rs.getInt(2));
//				vsDevInfo.setDev_name(rs.getString(3));
//				vsDevInfo.setDev_type(rs.getString(4));
//				vsDevInfo.setProtocol_type(rs.getString(5));
//				vsDevInfo.setIp(rs.getString(6));
//				vsDevInfo.setPort(rs.getInt(7));
//				vsDevInfo.setUsername(rs.getString(8));
//				vsDevInfo.setPassword(rs.getString(9));
//				vsDevInfo.setDev_id(rs.getInt(10));
//				switch (svrType) {
//				case VSDefCode.vagname:
//					vsDevInfo.setChn_video(rs.getInt(11));
//					vsDevInfo.setChn_alarmin(rs.getInt(12));
//					vsDevInfo.setChn_alarmout(rs.getInt(13));
//					devlist.add(vsDevInfo);
//					break;
//				case VSDefCode.vmsname:
//					vsDevInfo.setChn_dec(rs.getInt(11));
//					vsDevInfo.setDec_mode(rs.getInt(12));
//					devlist.add(vsDevInfo);
//					break;
//				case VSDefCode.amagname:
//					vsDevInfo.setChn_videoin(rs.getInt(11));
//					vsDevInfo.setChn_videoout(rs.getInt(12));
//					vsDevInfo.setCom(rs.getInt(13));
//					vsDevInfo.setAddr(rs.getInt(14));
//					devlist.add(vsDevInfo);
//					break;
//				case  VSDefCode.sioagname:
//					vsDevInfo.setChn_alarmin(rs.getInt(11));
//					vsDevInfo.setChn_alarmout(rs.getInt(12));
//					vsDevInfo.setCom(rs.getInt(13));
//					vsDevInfo.setAddr(rs.getInt(14));
//					;
//					devlist.add(vsDevInfo);
//					break;
//				case VSDefCode.iaagname:
//					vsDevInfo.setChn_video(rs.getInt(11));
//					devlist.add(vsDevInfo);
//					break;
//				}
//
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return devlist;
//	}
//
//	public static List<ResInfo> getResbyIDs(List<Integer> idlist, int seachtype, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<ResInfo> reslist = new ArrayList<ResInfo>();
//		String cmd = null;
//		String ids = idlist.toString();
//		ids = ids.substring(1, ids.length() - 1);
//		if (seachtype == 1) {
//			cmd = "SELECT a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id ,a.res_no,a.res_name,a.res_type,a.chn_type,b.dev_id  FROM ti_resource a left join ti_device b "
//					+ "on a.dev_id=b.dev_id inner JOIN ti_server_media_ex c on b.svr_id=c.svr_id WHERE a.res_id in ("
//					+ ids + " )";
//		} else {
//			cmd = "SELECT a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id ,a.res_no,a.res_name,a.res_type,a.chn_type,b.dev_id  FROM ti_resource a left join ti_device b "
//					+ "on a.dev_id=b.dev_id inner JOIN ti_server_media_ex c on b.svr_id=c.svr_id WHERE a.dev_id in ("
//					+ ids + " )";
//		}
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				ResInfo vsResInfo = new ResInfo();
//				vsResInfo.setRes_id(rs.getInt(1));
//				vsResInfo.setRes_uid(rs.getString(2));
//				vsResInfo.setDev_no(rs.getString(3));
//				vsResInfo.setIp_intranet(rs.getString(4));
//				vsResInfo.setPort_manage(rs.getInt(5));
//				vsResInfo.setSvr_id(rs.getInt(6));
//				vsResInfo.setRes_no(rs.getString(7));
//				vsResInfo.setRes_name(rs.getString(8));
//				vsResInfo.setRes_type(rs.getString(9));
//				vsResInfo.setChn_type(rs.getInt(10));
//				vsResInfo.setDev_id(rs.getInt(11));
//				reslist.add(vsResInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return reslist;
//	}
//
//	public static List<VsSvrInfo> getAllSvr(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsSvrInfo> svrlist = new ArrayList<VsSvrInfo>();
//		String cmd = "SELECT a.*,b.ip_extranet,b.ip_intranet,b.port_manage FROM ti_server a inner JOIN ti_server_media_ex b on b.svr_id=a.svr_id ";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsSvrInfo vsSvrInfo = new VsSvrInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
//						rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10));
//				svrlist.add(vsSvrInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return svrlist;
//	}
//
//	public static List<VsSwitchseqInfo> getswichseq(int seq_id, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsSwitchseqInfo> seqlist = new ArrayList<VsSwitchseqInfo>();
//		String cmd = "SELECT a.switch_seq_id,a.seq_name,a.seq_no,a.keep_time,b.res_cam_id,b.stream_type,b.preset_no,b.exec_index FROM ti_switch_seq a inner JOIN ti_switch_seq_ex b on b.switch_seq_id=a.switch_seq_id where a.switch_seq_id = ? order by exec_index asc";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, seq_id);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsSwitchseqInfo vsseqInfo = new VsSwitchseqInfo(rs.getInt(1), rs.getString(2), rs.getString(3),
//						rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8));
//				seqlist.add(vsseqInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return seqlist;
//
//	}
//
//	public static List<VsSwitchTaskInfo> GetSwichTask(String task_no, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsSwitchTaskInfo> tasklist = new ArrayList<VsSwitchTaskInfo>();
//		String cmd = "SELECT a.switch_task_id,a.task_name,a.task_no,a.wall_id,b.res_screen_no,b.window_no,b.switch_seq_id,c.seq_no FROM ti_switch_task a inner JOIN ti_switch_task_ex b on b.switch_task_id=a.switch_task_id left join ti_switch_seq c on c.switch_seq_id=b.switch_seq_id where a.task_no = ? ";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, task_no);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsSwitchTaskInfo vstaskInfo = new VsSwitchTaskInfo(rs.getInt(1), rs.getString(2), rs.getString(3),
//						rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8));
//				tasklist.add(vstaskInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return tasklist;
//	}
//
//	public static List<VsSwichPlanInfo> GetSwichPlan(String plan_no, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsSwichPlanInfo> planlist = new ArrayList<VsSwichPlanInfo>();
//		String cmd = "SELECT a.switch_plan_id,a.plan_name,a.plan_no,a.plan_type,a.enable_state,b.switch_task_id,b.start_time,b.end_time,b.keep_time,b.exec_index,c.task_no FROM ti_switch_plan a inner JOIN ti_switch_plan_ex b on b.switch_plan_id=a.switch_plan_id left join ti_switch_task c on c.switch_task_id=b.switch_task_id where a.plan_no = ? and a.enable_state=1 order by exec_index asc";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, plan_no);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsSwichPlanInfo vsplanInfo = new VsSwichPlanInfo(rs.getInt(1), rs.getString(2), rs.getString(3),
//						rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9),
//						rs.getInt(10), rs.getString(11));
//				planlist.add(vsplanInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return planlist;
//	}
//
//	public static void setCruiseToken(int cruise_id, String sCruiseToken, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String cmd = "UPDATE ti_cruise SET cruise_token=? WHERE cruise_id=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sCruiseToken);
//			st.setInt(2, cruise_id);
//			st.executeUpdate();
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//	}
//
//	public static void setPresetToken(int res_id, String preset_no, String sPresetToken, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String cmd = "UPDATE ti_preset SET preset_token=? WHERE res_id=? AND preset_no=?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, sPresetToken);
//			st.setInt(2, res_id);
//			st.setString(3, preset_no);
//			rs = st.executeQuery();
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//
//	}
//
//	public static VsCasCadeModel getCasCadeinfo(int cascadeid, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		VsCasCadeModel CasCadeinfo = null;
//		String cmd = "SELECT * from ti_cascade where cascade_id = ?";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, cascadeid);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				CasCadeinfo = new VsCasCadeModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
//						rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10),
//						rs.getInt(11));
//			}
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return CasCadeinfo;
//	}
//
//	public static List<VsCasCadeModel> getCasCadelist(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<VsCasCadeModel> CasCadelist = new ArrayList<VsCasCadeModel>();
//		String cmd = "SELECT * from ti_cascade";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				VsCasCadeModel CasCadeinfo = new VsCasCadeModel(rs.getInt(1), rs.getString(2), rs.getInt(3),
//						rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9),
//						rs.getString(10), rs.getInt(11));
//				CasCadelist.add(CasCadeinfo);
//			}
//		} catch (SQLException e) {
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return CasCadelist;
//	}
//
//	public static List<ResInfo> getResByrole(int role_id, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<ResInfo> reslist = new ArrayList<ResInfo>();
//		String cmd = "SELECT a.res_id,a.res_no,a.res_name,a.chn_type,a.res_type,a.res_uid,b.online_state,c.dev_id,c.dev_no FROM ti_resource a inner join ti_res_state b on a.res_id =b.res_id left join ti_device c on a.dev_id=c.dev_id where a.chn_type=1 and a.res_id in (SELECT c.res_id from tr_role_group b"
//				+ "	LEFT JOIN tr_group_res c on b.group_id =c.group_id Where b.role_id=?) ";
//		// a.res_id,a.res_uid,b.dev_no,c.ip_intranet,c.port_manage,c.svr_id
//		// ,a.res_no,a.res_name,a.res_type,a.chn_type,b.dev_id,d.svr_no
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setInt(1, role_id);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				ResInfo vsResInfo = new ResInfo();
//				vsResInfo.setRes_id(rs.getInt(1));
//				vsResInfo.setRes_no(rs.getString(2));
//				vsResInfo.setRes_name(rs.getString(3));
//				vsResInfo.setChn_type(rs.getInt(4));
//				vsResInfo.setRes_type(rs.getString(5));
//				vsResInfo.setRes_uid(rs.getString(6));
//				vsResInfo.setOnline_state(rs.getInt(7));
//				vsResInfo.setDev_id(rs.getInt(8));
//				vsResInfo.setDev_no(rs.getString(9));
//				reslist.add(vsResInfo);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return reslist;
//	}
//
//	public static VsCloudInfo getCloudinfo(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		VsCloudInfo cloudinfo = new VsCloudInfo();
//		String cmd = "SELECT cloud_id,cloud_type,enable_state,role_id FROM ti_cloud";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				cloudinfo.setCloud_id(rs.getInt(1));
//				cloudinfo.setCloud_type(rs.getInt(2));
//				cloudinfo.setEnable_state(rs.getInt(3));
//				cloudinfo.setRole_id(rs.getInt(4));
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return cloudinfo;
//	}
//
//	public static IaagInfo getiaagbyResno(String resno, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		IaagInfo iaagInfo = new IaagInfo();
//		String cmd = "select a.svr_id,a.preset_no,c.ip_intranet,c.port_manage  from tr_iaag_cam a LEFT JOIN ti_resource b on a.res_id =b.res_id LEFT JOIN ti_server_media_ex c ON a.svr_id =c.svr_id WHERE b.res_no =?";
//
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, resno);
//			rs = st.executeQuery();
//			if (rs.next()) {
//				iaagInfo.setSvr_id(rs.getInt(1));
//				iaagInfo.setPreset_no(rs.getString(2));
//				iaagInfo.setIp_intranet(rs.getString(3));
//				iaagInfo.setPort_manage(rs.getInt(4));
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return iaagInfo;
//	}

//	public static int getResState(String resno, ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		int online_state = 0;
//		String cmd = "select a.online_state from ti_res_state a left join ti_resource b on a.res_id=b.res_id WHERE res_no =?";
//
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			st.setString(1, resno);
//			rs = st.executeQuery();
//
//			if (rs.next()) {
//				online_state = rs.getInt(1);
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return online_state;
//	}
//
//	public static List<String> GetSwichPlanlist(ConnectionPool connPool) {
//		Connection conn = null;
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		List<String> planlist = new ArrayList<String>();
//		String cmd = "SELECT a.switch_plan_id,a.plan_name,a.plan_no,a.plan_type,a.enable_state FROM ti_switch_plan a where a.enable_state=1";
//		try {
//			conn = connPool.getConnection();
//			st = conn.prepareStatement(cmd);
//			rs = st.executeQuery();
//			while (rs.next()) {
//				planlist.add(rs.getString(3));
//			}
//		} catch (SQLException e) {
//			// e.printStackTrace();
//			logger.error("DBPlatformServer error!", e);
//		} finally {
//			connPool.returnConnection(conn);
//			try {
//				if (st != null) {
//					st.close();
//					st = null;
//				}
//				if (rs != null) {
//					rs.close();
//					rs = null;
//				}
//			} catch (SQLException ignore) {
//			}
//		}
//		return planlist;
//	}
//
//}