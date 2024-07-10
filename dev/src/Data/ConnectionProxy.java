package dev.src.Data;

import java.lang.reflect.*;
import java.sql.*;

public class ConnectionProxy implements InvocationHandler {

    private Connection realConnection;

    private ConnectionProxy(Connection realConnection) {
        this.realConnection = realConnection;
    }

    public static Connection newInstance(Connection realConnection) {
        return (Connection) Proxy.newProxyInstance(
                ConnectionProxy.class.getClassLoader(),
                new Class[] { Connection.class },
                new ConnectionProxy(realConnection));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if ("prepareStatement".equals(method.getName()) && args != null && args.length > 0) {
                String sql = (String) args[0];
                System.out.println("Executing SQL query: " + sql);
            }
            return method.invoke(realConnection, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
}
