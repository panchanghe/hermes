package top.javap.hermes.constant;

/**
 * @Author: pch
 * @Date: 2023/5/18 16:38
 * @Description:
 */
public interface MessageConstant {

    int REQ_HEADER_LENGTH = 13;
    int REQ_BODY_LENGTH_OFFSET = 9;
    int RES_HEADER_LENGTH = 13;
    int RES_BODY_LENGTH_OFFSET = 9;

    // 协议的前2个字节 魔数
    short MAGIC = (short) 0x0430;

    short VERSION_V1 = 0x01;

    // flags
    byte FLAG_ONE_WAY = (byte) 0x80; // 10000000
    byte FLAG_RES = (byte) 0x80; // 10000000
    byte FLAG_REPLY = (byte) 0x40; // 01000000
    // flags > eventType
    byte FLAG_EVENT_BIZ = (byte) 0x10; // 00010000
    byte FLAG_EVENT_HEARTBEAT = (byte) 0x20; // 00100000
    // flags > serializationID
    byte FLAG_SERIALIZE_KRYO = (byte) 0x04; // 00000100

    // 掩码
    byte FLAG_EVENT_MASK = 0x70; // 01110000

    // 响应码
    byte RES_STATUS_OK = 0x01;
    byte RES_STATUS_FAILED = 0x02;

    // 最大载荷 8M
    int MAX_PAYLOAD = 8 * 1024 * 1024;
}