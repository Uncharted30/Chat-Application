#  Chatroom Application

## Introduction
Users can use this program to chat with each other in the chat room.

## Specification
- [x] logoff command
- [x] who command
- [x] @user command
- [x] logoff command
- [x] @all command
- [x] !user command
- [x] message deserialize and serialize through given protocol


## Usage
Run the server first
`$ -p $port`, if `-p` is not specified the server will run on the random port.

Run the client
`$ -i $ip_address -p $port -u $username` if `-i` is not specified the client will try to connect the `localhost`

Command Description
- logoff: sends a DISCONNECT_MESSAGE to the server
- who: sends a QUERY_CONNECTED_USERS to the server
- @user: sends a DIRECT_MESSAGE to the specified user to the server
- @all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected
- !user: sends a SEND_INSULT message to the server, to be sent to the specified user



## Core Classes Description

### Common
#### ChatRoomProtocol
It is a interface which defines the serialize and deserialize method. All the message should implement this interface.
#### MessageProcessor
It is a abstract class which use to process the data from the socket input stream.

### Client Side:
#### MessageReader
It is a thread which use to get the data from server.
#### MessageSender
It is a thread which use to send the data to server.
#### ClientMessageProcessor
It is extends from `MessageProcessor`, and use to process the data from server.

### Server Side:
#### ClientHandler
It is a thread which use to handle the client request.
#### ServerMessageAgent
It use to send the message to the client
#### ServerMessageProcessor
It is extends from `MessageProcessor`, and use to process the data from client.

## Dependencies 
1. `junit` `4.12`
2. `commons-cli` `1.4`
3. `json-simple` `1.1.1`