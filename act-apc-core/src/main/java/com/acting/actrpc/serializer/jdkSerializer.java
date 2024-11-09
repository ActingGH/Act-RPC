package com.acting.actrpc.serializer;

import com.acting.actrpc.registry.LocalRegistry;

import java.io.*;

public class jdkSerializer implements Serializer{
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ObjectOutput objectOutput=new ObjectOutputStream(outputStream);
        objectOutput.writeObject(object);
        objectOutput.close();
        return outputStream.toByteArray();
    }

    /**
     * 反序列化
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
        try {
            return (T) objectInputStream.readObject();
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }finally{
            objectInputStream.close();
        }
    }
}
