package com.demo;

public class PortOption {
	
	private String portName;
	private int baudRate;
	private int dataBits;
	private int stopBits;
	private int parity;
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public int getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
	public int getDataBits() {
		return dataBits;
	}
	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}
	public int getStopBits() {
		return stopBits;
	}
	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}
	public int getParity() {
		return parity;
	}
	public void setParity(int parity) {
		this.parity = parity;
	}
	
	
}
