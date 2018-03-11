package bs.pi.gateway.client.zigbee;

import java.util.HashMap;

import bs.pi.gateway.main.IConverter;
import bs.pi.gateway.main.ISender;
import bs.pi.gateway.msg.IMsg;
import bs.pi.gateway.msg.PortSendResponseMsg;
import bs.pi.gateway.msg.SendPortMsgMsg;
import bs.pi.gateway.msg.ZigbeeSendReponseMsg;

public class ZigbeeSender implements ISender {

	private ZigbeeClientCfg cfg;
	private ISender portSender;
	private IConverter converter;
	
	public ZigbeeSender(ISender portSender, IConverter converter, ZigbeeClientCfg cfg){
		this.portSender = portSender;
		this.converter = converter;
		this.cfg = cfg;
	}
	
	@Override
	public String getName() {
		return ISender.V_SEND_NAME_ZIGBEE_SNEDER;
	}

	@Override
	public IMsg send(IMsg msg) {
		Object obj = converter.convertMsgSend(msg);
		ZigbeeSendReponseMsg zigbeeSendReponseMsg = new ZigbeeSendReponseMsg();
		if(obj != null){
			ZigbeeMsgSend zigbeeMsg = (ZigbeeMsgSend)obj;
			ZigbeeFrameOut frame = new ZigbeeFrameOut();
			frame.setDstAddr(zigbeeMsg.getDstAddr());
			frame.setDstEndpoint(cfg.getDstEndpoint());
			frame.setClusterID(cfg.getClusterID());
			frame.setData(zigbeeMsg.getData());
			frame.setOptions(cfg.getOptions());
			frame.setRadius(cfg.getRadius());
			frame.setSrcEndpoint(cfg.getSrcEndpoint());
			frame.setTransID((byte) 0x00);

			SendPortMsgMsg portSendMsg = new SendPortMsgMsg();
			portSendMsg.setData(CodeGenerator.packetSend(frame));
			PortSendResponseMsg portSendResponseMsg = (PortSendResponseMsg) portSender.send(portSendMsg);
			//���ͳɹ�
			zigbeeSendReponseMsg.setSendSuccess(portSendResponseMsg.getSendSuccess());
			System.out.println("zigbee ���ͳɹ�");
		}else{
			zigbeeSendReponseMsg.setSendSuccess(false);
		}
		return zigbeeSendReponseMsg;
	}

}