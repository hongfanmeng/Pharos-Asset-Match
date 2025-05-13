package com.zhd.ws;

import com.alibaba.fastjson.JSON;
import com.zhd.entity.AssetDevice;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/device/{deviceId}")
@Component
public class WebSocketServer {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId") String deviceId) {
        sessions.put(deviceId, session);
        System.out.println("设备 " + deviceId + " 连接成功");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到客户端消息：" + message);
    }

    @OnClose
    public void onClose(@PathParam("deviceId") String deviceId) {
        sessions.remove(deviceId);
        System.out.println("设备 " + deviceId + " 断开连接");
    }

    /**
     * 在数据库device改变时向前端发送消息，表示设备位置改变
     * @param newDevice
     */
    public  void sendDeviceChange( AssetDevice newDevice)  {
        sendMessage(newDevice.getDeviceId().toString(), JSON.toJSONString(newDevice));
    }

    // 主动推送消息给指定用户
    public  void sendMessage(String deviceId, String message)  {
        Session session = sessions.get(deviceId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                // 处理发送异常
                e.printStackTrace();
            }
        }
    }
}