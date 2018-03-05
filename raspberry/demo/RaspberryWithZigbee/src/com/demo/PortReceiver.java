package com.demo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/*
 * port�ͻ�����Ϣ������/
 */

public class PortReceiver implements MessageReceiver {

	private MessageReceiveCallBack callBack;
	private Thread thread;
	private boolean isStart;
//	private InputStream iStream;
	
	public PortReceiver(final InputStream inputStream){
		isStart = false;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(true){
						if(callBack != null){
							Message msg = reveiveMsg(inputStream);
							if(msg != null)
								callBack.received(msg);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void setReceiveCallBack(MessageReceiveCallBack callBack) {
		// TODO Auto-generated method stub
		this.callBack = callBack;
	}

	@Override
	public void start() {
		isStart = true;
		thread.start();
	}

	@Override
	public void stop() {
		isStart = false;
	}
	
	public Byte readByte(InputStream iStream) throws IOException
	{
		int time1 = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(true){
			int time2 = Calendar.getInstance().get(Calendar.MILLISECOND);
			if((time2-time1)>12)
				return null;
			int temp = iStream.read();
			if(temp != -1){
				Byte result = new Byte((byte) temp);
				return result;
			}
		}
	}
	
	public byte[] readBytes(int len, InputStream iStream) throws IOException
	{
		byte[] result = new byte[len];
		for(int i=0;i<len;i++){
			Byte temp = readByte(iStream);
			if(temp == null)
				return null;
			result[i] = temp;
		}
		
		return result;
	}
	
	public Message reveiveMsg(InputStream iStream)throws IOException
	{
		
		//�ж���Ϣ���Ƿ�ʼ
		
		byte b;
		boolean isBegin = false;
		while(iStream.available()>0){
			if(iStream.read() == 0xfe){
				isBegin = true;
				break;
			}
		}
		if( ! isBegin)
			return null;
		
		Byte len = readByte(iStream);
		if(len == null)
			return null;

		Message msg = new Message();
		Byte cmd0 = readByte(iStream);
		if(cmd0 == null)
			return null;
		msg.setCmd0(cmd0);
		
		Byte cmd1 = readByte(iStream);
		if(cmd1 == null)
			return null;
		msg.setCmd1(cmd1);

		byte[] data = readBytes(len, iStream);
		if(data == null)
			return null;
		msg.setData(data);
		
		Byte fcs = readByte(iStream);
		if(fcs == null)
			return null;
		
		if(msg.validate(fcs))
			return msg;
		else
			return null;
		
	}
}