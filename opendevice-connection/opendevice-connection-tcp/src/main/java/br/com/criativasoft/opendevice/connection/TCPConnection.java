/*
 * ******************************************************************************
 *  Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Ricardo JL Rufino - Initial API and Implementation
 * *****************************************************************************
 */

package br.com.criativasoft.opendevice.connection;

import br.com.criativasoft.opendevice.connection.discovery.DiscoveryService;
import br.com.criativasoft.opendevice.connection.discovery.NetworkDeviceInfo;
import br.com.criativasoft.opendevice.connection.exception.ConnectionException;
import br.com.criativasoft.opendevice.connection.serialize.DefaultSteamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.util.Set;

/**
 * TCP Socket bases connection
 */
public class TCPConnection extends AbstractStreamConnection implements ITcpConnection{
	
	private static final Logger log = LoggerFactory.getLogger(TCPConnection.class);

	public static final long DISCOVERY_TIMEOUT = 5000;
	public static final long DEFAULT_PORT = 8182;

    private Socket connection;

    private DiscoveryService discoveryService;

    public TCPConnection() {
        super();
    }

    /**
     * @param deviceURI - (Ex.: 192.168.0.101:1234)
     */
	public TCPConnection(String deviceURI) {
		super();
        setConnectionURI(deviceURI);
	}

	@Override
	public void connect() throws ConnectionException {
		
		try {

			if(!isConnected()){
				
				initConnection(); // Setup

				// open the streams
				setInput(connection.getInputStream());
				setOutput(connection.getOutputStream());
				
				getStreamReader().setInput(input);
				
				if(reader instanceof DefaultSteamReader){
					((DefaultSteamReader)reader).startReading();
				}
				
				setStatus(ConnectionStatus.CONNECTED);
			}
			
		} catch (IOException e) {
			connection = null;
            setStatus(ConnectionStatus.FAIL);
			throw new ConnectionException(e.getMessage(), e);
		}
		
	}
	
	private void initConnection() throws IOException{
		if(connection == null){

            String fdeviceURI = this.deviceURI;

            // Automatic discovery
			if(fdeviceURI.endsWith("local.opendevice")){
				int indexOf = fdeviceURI.indexOf(".local.opendevice");
				String name = fdeviceURI.substring(0, indexOf);
				Set<NetworkDeviceInfo> devices = discoveryService.scan(DISCOVERY_TIMEOUT, name);
                if(devices.size() > 0){
                    NetworkDeviceInfo info = devices.iterator().next();
                    fdeviceURI = info.getIp() + ":" + info.getPort();
                }else{
					throw new NoRouteToHostException("Can't find device");
				}
			}

            int index = fdeviceURI.lastIndexOf(":");
            String port = (index > 0 ?  fdeviceURI.substring(index + 1, fdeviceURI.length()) : "80");
            String host = fdeviceURI.substring(0, index);

            log.debug("Connecting to: " + fdeviceURI + ", port:" + port);

            connection = new Socket();
			connection.setTcpNoDelay(true);
            connection.connect(new InetSocketAddress(host, Integer.parseInt(port)), 5000);

		}
	}

    @Override
    public void setDiscoveryService(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    public String getDeviceURI() {
		return deviceURI;
	}

	@Override
	public void disconnect() throws ConnectionException {
		if(isConnected()){
			try {
				super.disconnect();
				connection.close();
				connection = null;
			} catch (IOException e) {
				throw new ConnectionException(e);
			}
			
			log.debug("Disconnected !");
		}else{
			log.info("disconnect :: not connected !");
		}
	}


	@Override
	public String toString() {
		return "TCPConnection["+getConnectionURI().replaceAll("\\.local\\.opendevice","")+"]";
	}
}
