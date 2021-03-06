/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.rpc.message;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.alipay.sofa.rpc.common.utils.StringUtils;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;

/**
 *
 *
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
public class MessageBuilderTest {
    @Test
    public void buildSofaRequest() throws Exception {
        SofaRequest request = MessageBuilder.buildSofaRequest(Number.class, "intValue",
                new Class[0], new Object[0]);
        Assert.assertEquals(request.getInterfaceName(), Number.class.getName());
        Assert.assertEquals(request.getMethodName(), "intValue");
        Assert.assertArrayEquals(request.getMethodArgs(), new Object[0]);
        Assert.assertArrayEquals(request.getMethodArgSigs(), StringUtils.EMPTY_STRING_ARRAY);

        request = MessageBuilder.buildSofaRequest(Comparable.class, "compareTo",
                new Class[] { Object.class }, new Object[] { null });
        Assert.assertEquals(request.getInterfaceName(), Comparable.class.getName());
        Assert.assertEquals(request.getMethodName(), "compareTo");
        Assert.assertArrayEquals(request.getMethodArgs(), new Object[] { null });
        Assert.assertArrayEquals(request.getMethodArgSigs(), new String[] { "java.lang.Object" });
    }

    @Test
    public void buildSofaRequest1() throws Exception {
        Method method = Number.class.getMethod("intValue");
        SofaRequest request = MessageBuilder.buildSofaRequest(Number.class, method,
                new Class[0], new Object[0]);
        Assert.assertEquals(request.getInterfaceName(), Number.class.getName());
        Assert.assertEquals(request.getMethodName(), "intValue");
        Assert.assertArrayEquals(request.getMethodArgs(), new Object[0]);
        Assert.assertArrayEquals(request.getMethodArgSigs(), StringUtils.EMPTY_STRING_ARRAY);

        method = Comparable.class.getMethod("compareTo", Object.class);
        request = MessageBuilder.buildSofaRequest(Comparable.class, method,
                new Class[] { Object.class }, new Object[] { null });
        Assert.assertEquals(request.getInterfaceName(), Comparable.class.getName());
        Assert.assertEquals(request.getMethodName(), "compareTo");
        Assert.assertArrayEquals(request.getMethodArgs(), new Object[] { null });
        Assert.assertArrayEquals(request.getMethodArgSigs(), new String[] { "java.lang.Object" });
    }

    @Test
    public void buildSofaErrorResponse() throws Exception {
        SofaResponse response = MessageBuilder.buildSofaErrorResponse("xxx");
        Assert.assertTrue(response.isError());
        Assert.assertEquals("xxx", response.getErrorMsg());
    }

}