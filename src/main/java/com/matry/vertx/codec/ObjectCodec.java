package com.matry.vertx.codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.*;

public class ObjectCodec implements MessageCodec<Object, Object> {
    @Override
    public void encodeToWire(Buffer buffer, Object o) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            ObjectOutputStream stream = new ObjectOutputStream(b);
            stream.writeObject(o);
            stream.close();

            buffer.appendBytes(b.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object decodeFromWire(int pos, Buffer buffer) {
        final ByteArrayInputStream b = new ByteArrayInputStream(buffer.getBytes());
        ObjectInputStream o = null;
        Object msg = null;
        try {
            o = new ObjectInputStream(b);
            msg = o.readObject();
            o.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    @Override
    public Object transform(Object o) {
        return o;
    }

    @Override
    public String name() {
        return "ObjectCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
