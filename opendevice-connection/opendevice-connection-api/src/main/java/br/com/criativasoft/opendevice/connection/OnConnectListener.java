/*
 * *****************************************************************************
 * Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Ricardo JL Rufino - Initial API and Implementation
 * *****************************************************************************
 */

package br.com.criativasoft.opendevice.connection;

import br.com.criativasoft.opendevice.connection.message.Message;

/**
 * @author Ricardo JL Rufino
 * @date 23/08/15.
 */
public abstract class OnConnectListener implements ConnectionListener {

    @Override
    public void connectionStateChanged(DeviceConnection connection, ConnectionStatus status){
        if(status == ConnectionStatus.CONNECTED){
            onConnect(connection);
        }
    }

    public void onMessageReceived(Message message, DeviceConnection connection){
        // Ignore..
    }

    public abstract void onConnect(DeviceConnection connection);
}
