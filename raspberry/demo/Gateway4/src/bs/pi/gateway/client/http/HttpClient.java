package bs.pi.gateway.client.http;

import bs.pi.gateway.main.IClient;
import bs.pi.gateway.main.IConverter;
import bs.pi.gateway.main.IReceiver;
import bs.pi.gateway.main.ISender;

public class HttpClient implements IClient{

	public static final String DEFAULT_CFG_PATH = System.getProperty("user.dir")+System.getProperty("file.separator")+"httpClientCfg.properties";
	private HttpClientCfg cfg;
	private String cfgPath;
	private IConverter converter;
	private HttpSender sender;
	private HttpReceiver receiver;
	
	public HttpClient(String path){
		this.cfgPath = path;
	}
	private void loadCfg() throws Exception{
		if(cfgPath == null){
			throw new Exception("cfgPath is null");
		}else{
			cfg = new HttpClientCfg(cfgPath);
		}
	}
	
	public void init() throws Exception{
		loadCfg();
	}
	
	@Override
	public void start()throws Exception{
		sender = new HttpSender(cfg, converter);
		receiver = new HttpReceiver(cfg, converter);
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConverter(IConverter converter) {
		// TODO Auto-generated method stub
		this.converter = converter;
	}

	@Override
	public ISender getSender() {
		// TODO Auto-generated method stub
		return sender;
	}

	@Override
	public IReceiver getReceiver() {
		// TODO Auto-generated method stub
		return receiver;
	}
	
	public HttpClientCfg getHttpClientCfg(){
		return cfg;
	}
}
