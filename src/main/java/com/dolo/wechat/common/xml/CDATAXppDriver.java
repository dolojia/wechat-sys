package com.dolo.wechat.common.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;
import java.lang.reflect.Field;

public class CDATAXppDriver extends XppDriver
{
    public HierarchicalStreamWriter createWriter(Writer out)
    {
        return new CDATAPrettyPrintWriter(out);
    }


    public static XStream createXstream()
    {
        return new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out)
            {
                return new PrettyPrintWriter(out) {
                    boolean cdata = false;
                    Class<?> targetClass = null;


                    @Override
                    public void startNode(String name, @SuppressWarnings("rawtypes")
                    Class clazz)
                    {
                        super.startNode(name, clazz);
                        // 业务处理，对于用XStreamCDATA标记的Field，需要加上CDATA标签
                        if (!name.equals("xml") && !name.equals("Articles") && !name.equals("item"))
                        {
                            cdata = needCDATA(targetClass, name);
                        }
                        else
                        {
                            targetClass = clazz;
                        }
                    }


                    @Override
                    protected void writeText(QuickWriter writer, String text)
                    {
                        if (cdata)
                        {
                            writer.write("<![CDATA[" + text + "]]>");
                        }
                        else
                        {
                            writer.write(text);
                        }
                    }
                };
            }
        });
    }


    private static boolean needCDATA(Class<?> targetClass, String fieldAlias)
    {
        boolean cdata = false;
        cdata = existsCDATA(targetClass, fieldAlias);
        if (cdata)
        {
            return cdata;
        }
        Class<?> superClass = targetClass.getSuperclass();
        while (!superClass.equals(Object.class))
        {
            cdata = existsCDATA(superClass, fieldAlias);
            if (cdata)
            {
                return cdata;
            }
            superClass = superClass.getClass().getSuperclass();
        }
        return false;
    }


    /**
     * 
     * 功能描述：判断节点是否有CDATA的注解
     * @param clazz
     * @param fieldAlias
     * @return
     */
    private static boolean existsCDATA(Class<?> clazz, String fieldAlias)
    {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            if (field.getAnnotation(XStreamCDATA.class) != null)
            {
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                if (null != xStreamAlias)
                {
                    if (fieldAlias.equals(xStreamAlias.value()))
                    {
                        return true;
                    }
                }
                else
                {
                    if (fieldAlias.equals(field.getName()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
