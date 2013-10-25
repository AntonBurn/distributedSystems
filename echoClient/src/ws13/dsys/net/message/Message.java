package ws13.dsys.net.message;

public class Message {
	
	public static byte[] StringToByteMsg(String stringMsg){
		char charMsg[] = stringMsg.toCharArray();
		byte byteMsg[] = new byte[charMsg.length];

		for(int i = 0; i < charMsg.length; i++){
			byteMsg[i] = (byte)charMsg[i];
		}

		return byteMsg;
	}
	
	public static String ByteToStringMsg(byte byteMsg[]){
		return new String(byteMsg);
	}

}
