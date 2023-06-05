package top.javap.hermes.remoting.message;

import top.javap.hermes.constant.MessageConstant;

/**
 * 4byte
 *
 * @author: pch
 * @description:
 * @date: 2023/5/24
 **/
public abstract class BaseMessage {

    protected short magic = MessageConstant.MAGIC;
    protected short version = MessageConstant.VERSION_V1;

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }
}