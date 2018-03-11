import java.util.HashMap;

import net.sf.json.JSONObject;
import bs.pi.gateway.client.http.HttpClient;
import bs.pi.gateway.client.port.PortClient;
import bs.pi.gateway.client.port.PortConverter;
import bs.pi.gateway.client.zigbee.ZigbeeClient;
import bs.pi.gateway.main.Gateway;
import bs.pi.gateway.processor.MyProcessor;


public class Test {

	public static void main(String[] args) throws Exception {
		//test2();
		//test3();
		test4();
	}
	
	public static void test4(){
		//String str = "{\"success\":true,\"msg\":\"{cmd:'openLight', appID:0xf1}\"}";
		String str = "{\"key\":{\"key1\":\"value1\"}}";
		JSONObject json = JSONObject.fromObject(str);
		
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		json2.put("key1", "value1");
		json1.put("key", json2);
		System.out.println(json1.toString());
	}
	public static void test3() throws Exception{
		
		PortClient portClient = new PortClient(PortClient.DEFAULT_CFG_PATH);
		portClient.setConverter(new PortConverter());
		portClient.init();
		portClient.start();
		
		MyProcessor processor = new MyProcessor();
		
		Gateway gateway = new Gateway();
	//	gateway.installSender(zigbeeClient.getSender());
		gateway.installReceiver(portClient.getReceiver());
	//	gateway.installSender(httpClient.getSender());
	//	gateway.installReceiver(httpClient.getReceiver());
		gateway.setProcessor(processor);
		gateway.start();
	}
	
	public static void test2(){
		
		float f = (float) -123.45;
		
		// ��floatת��Ϊbyte[]  
	    int fbit = Float.floatToIntBits(f);  
	      
	    byte[] b = new byte[4];    
	    for (int i = 0; i < 4; i++) {    
	        b[i] = (byte) (fbit >> (24 - i * 8));    
	    }   
	      
	    // ��ת����  
	    int len = b.length;  
	    // ����һ����Դ����Ԫ��������ͬ������  
	    byte[] dest = new byte[len];  
	    // Ϊ�˷�ֹ�޸�Դ���飬��Դ���鿽��һ�ݸ���  
	    System.arraycopy(b, 0, dest, 0, len);  
	    byte temp;  
	    // ��˳λ��i���뵹����i������  
	    for (int i = 0; i < len / 2; ++i) {  
	        temp = dest[i];  
	        dest[i] = dest[len - i - 1];  
	        dest[len - i - 1] = temp;  
	    }
	    
	    for(byte bb: dest)
	    	System.out.printf("%x,", bb);
	}
	
	public static void test1() throws InterruptedException{
		Gateway gateway = new Gateway();
		
		MyProcessor processor = new MyProcessor();
		
		TestReceiver testReceiver = new TestReceiver();
		testReceiver.start();
		gateway.installReceiver(testReceiver);
		gateway.setProcessor(processor);
		
		gateway.start();
		
		Thread.sleep(6000);
		testReceiver.stop();
	}

}