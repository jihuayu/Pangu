package cn.mccraft.pangu.core.util.data;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SimpleByteSerializer<T> implements ByteSerializer<T> {

    private Serializer<T> serializer;
    private Deserializer<T> deserializer;

    public SimpleByteSerializer(Serializer<T> serializer, Deserializer<T> deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public void serialize(DataOutputStream stream, T object) throws IOException {
        if (serializer != null) serializer.accept(stream, object);
    }

    @Override
    public T deserialize(DataInputStream in) throws IOException {
        return deserializer != null? deserializer.accept(in) : null;
    }

    public interface Serializer<T> {
        void accept(DataOutputStream out, T object) throws IOException;
    }

    public interface Deserializer<T> {
        T accept(DataInputStream in) throws IOException;
    }

}