package bs.pi.gateway.client.zigbee;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import bs.pi.gateway.assist.Tool;

public class ZigbeeClientCfg {
	
	public final static String K_CHANNEL = "channel";
	public final static String K_PANID = "PANID";
	public final static String K_DEVICE_TYPE = "deviceType";
	public final static String K_DST_ENDPOINT= "dstEndpoint";
	public final static String K_SRC_ENDPOINT= "srcEndpoint";
	public final static String K_APP_COUNT = "appCount";
	public final static String K_CLUSTER_ID = "clusterID";
	public final static String K_OPTIONS = "options";
	public final static String K_RADIUS = "radius";
	
	public final static byte DEVICE_TYPE_COORDINATOR = 0x00;
	public final static byte DEVICE_TYPE_ROUTE = 0x01;
	public final static byte DEVICE_TYPE_END_DEVICE = 0x02;
	
	public final static byte[] DEFAULT_CHANNEL = {0x00, 0x08, 0x00, 0x00};
	public final static byte[] DEFAULT_PANID = {0x34, 0x12};
	public final static byte DEFAULT_DEVICE_TYPE = DEVICE_TYPE_ROUTE;
	public final static byte DEFAULT_DST_ENDPOINT = 0x08;
	public final static byte DEFAULT_SRC_ENDPOINT = 0x08;
	public final static byte[] DEFAULT_CLUSTER_ID = {0x00, 0x00};
	public final static byte DEFAULT_OPTIONS = 0x00;
	public final static byte DEFAULT_RADIUS = 0x00;
	
	private byte[] channel;	//�ŵ�
	private byte[] panID;	//�����
	private byte deviceType;	//zigbee�豸����
	private ArrayList<ZigbeeAppReg> appRegList;
	
	private byte dstEndpoint;
	private byte srcEndpoint;
	private byte[] clusterID;
	private byte options;
	private byte radius;

	public ZigbeeClientCfg(){
		channel = DEFAULT_CHANNEL;
		panID = DEFAULT_PANID;
		deviceType = DEFAULT_DEVICE_TYPE;
		dstEndpoint = DEFAULT_DST_ENDPOINT;
		srcEndpoint = DEFAULT_SRC_ENDPOINT;
		clusterID = DEFAULT_CLUSTER_ID;
		options = DEFAULT_OPTIONS;
		radius = DEFAULT_RADIUS;
	}
	
	public ZigbeeClientCfg(String cfgPath) throws Exception{
		loadCfg(cfgPath);
	}
	
	public void loadCfg(String cfgPath) throws Exception{
		Properties properties = new Properties();
		properties.load(new FileInputStream(cfgPath));
		channel = Tool.strToBytes(properties.getProperty(K_CHANNEL));
		panID = Tool.strToBytes(properties.getProperty(K_PANID));
		deviceType = Tool.strToBytes(properties.getProperty(K_DEVICE_TYPE))[0];
		
		if(properties.containsKey(K_APP_COUNT)){
			int appCount = Integer.parseInt(properties.getProperty(K_APP_COUNT));
			if(appCount>0){
				appRegList = new ArrayList<>();
				for(int i=1;i<(appCount+1);i++){
					ZigbeeAppReg reg = new ZigbeeAppReg(properties, i);
					appRegList.add(reg);
				}
			}
		}
		
		dstEndpoint = Tool.strToBytes(properties.getProperty(K_DST_ENDPOINT))[0];
		srcEndpoint = Tool.strToBytes(properties.getProperty(K_SRC_ENDPOINT))[0];
		clusterID = Tool.strToBytes(properties.getProperty(K_CLUSTER_ID));
		options = Tool.strToBytes(properties.getProperty(K_OPTIONS))[0];
		radius = Tool.strToBytes(properties.getProperty(K_RADIUS))[0];
	}
	
	public byte[] getChannel() {
		return channel;
	}

	public void setChannel(byte[] channel) {
		this.channel = channel;
	}

	public byte[] getPanID() {
		return panID;
	}

	public void setPanID(byte[] panID) {
		this.panID = panID;
	}

	public byte getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(byte deviceType) {
		this.deviceType = deviceType;
	}
	
	public ArrayList<ZigbeeAppReg> getAppRegList() {
		return appRegList;
	}

	public void setAppRegList(ArrayList<ZigbeeAppReg> appRegList) {
		this.appRegList = appRegList;
	}
	
	public void addAppReg(ZigbeeAppReg appReg){
		if(appReg == null)
			return;
		if(this.appRegList == null){
			this.appRegList = new ArrayList<>();
		}
		this.appRegList.add(appReg);
	}

	public byte getDstEndpoint() {
		return dstEndpoint;
	}

	public void setDstEndpoint(byte dstEndpoint) {
		this.dstEndpoint = dstEndpoint;
	}

	public byte getSrcEndpoint() {
		return srcEndpoint;
	}

	public void setSrcEndpoint(byte srcEndpoint) {
		this.srcEndpoint = srcEndpoint;
	}

	public byte[] getClusterID() {
		return clusterID;
	}

	public void setClusterID(byte[] clusterID) {
		this.clusterID = clusterID;
	}

	public byte getOptions() {
		return options;
	}

	public void setOptions(byte options) {
		this.options = options;
	}

	public byte getRadius() {
		return radius;
	}

	public void setRadius(byte radius) {
		this.radius = radius;
	}
}