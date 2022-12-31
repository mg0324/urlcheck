package com.mgang.util.db;

/**
 * @Author: mango
 * @Date: 2022/12/31 2:01 PM
 */
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public abstract class MgDataSourceFactory {
    private static String name;
    private static String clazz;
    private static String destory;
    private static Map<String, Object> contants = new HashMap();
    private static MgDataSource ds;
    private static Map<String, MgDataSource> factory = new HashMap();

    public MgDataSourceFactory() {
    }

    public static void build() {
        try {
            initMgds4jFormXml();
            ds.initConnectionPool();
        } catch (DocumentException var1) {
            var1.printStackTrace();
        }

    }

    private static void initMgds4jFormXml() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(MgDataSourceFactory.class.getClassLoader().getResourceAsStream("mgds4j.xml"));
        Element rootElement = doc.getRootElement();
        Iterator constantElements = rootElement.elementIterator("constant");

        while(constantElements.hasNext()) {
            Element constantElement = (Element)constantElements.next();
            contants.put(constantElement.attributeValue("key"), constantElement.attributeValue("value"));
        }

        Iterator dsElements = rootElement.elementIterator("dataSource");

        while(dsElements.hasNext()) {
            Element dsElement = (Element)dsElements.next();
            name = dsElement.attributeValue("name");
            clazz = dsElement.attributeValue("class");
            destory = dsElement.attributeValue("destory");

            try {
                Class dsClass = Class.forName(clazz);
                ds = (MgDataSource)dsClass.getMethod("getInstance", (Class[])null).invoke(dsClass, (Object[])null);
                factory.put(name, ds);
                Iterator ps = dsElement.elementIterator("property");

                while(ps.hasNext()) {
                    Element propertyElement = (Element)ps.next();
                    String key = propertyElement.attributeValue("key");
                    String value = propertyElement.attributeValue("value");
                    String type = propertyElement.attributeValue("type");
                    Method m;
                    if (type.equals("string")) {
                        m = MgDataSource.class.getMethod("set" + UpperFirst(key), String.class);
                        m.invoke(ds, value);
                    } else if (type.equals("int")) {
                        m = MgDataSource.class.getMethod("set" + UpperFirst(key), Integer.TYPE);
                        m.invoke(ds, Integer.parseInt(value.trim()));
                    }
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }
        }

    }

    public static MgDataSource getMgDataSource(String name) {
        return (MgDataSource)factory.get(name);
    }

    private static String UpperFirst(String str) {
        return String.valueOf(str.charAt(0)).toString().toUpperCase() + str.substring(1, str.length());
    }

    public static Object getContant(String key) {
        return contants.get(key);
    }
}
