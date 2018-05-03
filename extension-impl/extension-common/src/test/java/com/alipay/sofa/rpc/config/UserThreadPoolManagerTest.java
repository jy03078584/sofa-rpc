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
package com.alipay.sofa.rpc.config;

import org.junit.Assert;
import org.junit.Test;

import com.alipay.sofa.rpc.server.UserThreadPool;

/**
 *
 */
public class UserThreadPoolManagerTest {
    @Test
    public void testAll() throws Exception {
        String service = "xx";
        UserThreadPool threadPool = new UserThreadPool();
        try {
            UserThreadPoolManager.registerUserThread(service, threadPool);
            Assert.assertTrue(UserThreadPoolManager.hasUserThread());

            Assert.assertNotNull(UserThreadPoolManager.getUserThread(service));
            Assert.assertTrue(threadPool.equals(UserThreadPoolManager.getUserThread(service)));

            UserThreadPoolManager.unRegisterUserThread(service);
            Assert.assertNull(UserThreadPoolManager.getUserThread(service));

        } finally {
            UserThreadPoolManager.unRegisterUserThread(service);
        }
    }
}