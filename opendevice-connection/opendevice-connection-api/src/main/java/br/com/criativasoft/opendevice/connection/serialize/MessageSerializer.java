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

package br.com.criativasoft.opendevice.connection.serialize;

import br.com.criativasoft.opendevice.connection.message.Message;

/**
 * Base interface to make serialization and deserialization of Commands and Messages that are sent through connections. <br/>
 * You can implement your own protocol implementing this interface (ex: CommandStreamSerializer in opendevice-core).
 * @author Ricardo JL Rufino
 * @param <I> Input data type
 * @param <O> Output data type
 */
public interface MessageSerializer<I, O> {
	
	public Message parse(I data);
	
	public O serialize(Message message);

}
