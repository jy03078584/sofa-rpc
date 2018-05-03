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
package com.alipay.sofa.rpc.client.aft;

import org.junit.Assert;
import org.junit.Test;

import com.alipay.sofa.rpc.client.ProviderInfo;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">liangen</a>
 */
public class InvocationStatDimensionTest extends FaultBaseTest {

    @Test
    public void testInvocation() {
        InvocationStatDimension invocationA = new InvocationStatDimension(ProviderInfo.valueOf("ipA"), consumerConfig);
        InvocationStatDimension invocationB = new InvocationStatDimension(ProviderInfo.valueOf("ipA"), consumerConfig);
        InvocationStatDimension invocationC = new InvocationStatDimension(ProviderInfo.valueOf("ipC"), consumerConfig);

        Assert.assertTrue(invocationA.hashCode() == invocationB.hashCode());
        Assert.assertTrue(invocationA.equals(invocationB));

        Assert.assertTrue(invocationA.hashCode() != invocationC.hashCode());
        Assert.assertTrue(!invocationA.equals(invocationC));

    }

}