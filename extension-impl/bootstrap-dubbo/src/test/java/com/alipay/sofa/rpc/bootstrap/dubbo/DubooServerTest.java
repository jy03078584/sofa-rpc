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
package com.alipay.sofa.rpc.bootstrap.dubbo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alipay.sofa.rpc.bootstrap.dubbo.demo.DemoService;
import com.alipay.sofa.rpc.bootstrap.dubbo.demo.DemoServiceImpl;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.MethodConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.context.RpcInternalContext;
import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.context.RpcRunningState;
import com.alipay.sofa.rpc.context.RpcRuntimeContext;

/**
 * @author bystander
 * @version $Id: DubooServerTest.java, v 0.1 2017年10月30日 下午9:23 bystander Exp $
 */
public class DubooServerTest {

    ProviderConfig<DemoService> providerConfig;

    ConsumerConfig<DemoService> consumerConfig;

    @BeforeClass
    public static void adBeforeClass() {
        RpcRunningState.setUnitTestMode(true);
    }

    @Test
    //同步调用,直连
    public void testSync() {
        // 只有1个线程 执行
        ServerConfig serverConfig = new ServerConfig()
                .setStopTimeout(60000)
                .setPort(20880)
                .setProtocol("dubbo")
                .setQueues(100).setCoreThreads(1).setMaxThreads(2);

        // 发布一个服务，每个请求要执行1秒
        ApplicationConfig serverApplacation = new ApplicationConfig();
        serverApplacation.setAppName("server");
        providerConfig = new ProviderConfig<DemoService>()
                .setInterfaceId(DemoService.class.getName())
                .setRef(new DemoServiceImpl())
                .setBootstrap("dubbo")
                .setServer(serverConfig)
                // .setParameter(RpcConstants.CONFIG_HIDDEN_KEY_WARNING, "false")
                .setRegister(false).setApplication(serverApplacation);
        providerConfig.export();

        ApplicationConfig clientApplication = new ApplicationConfig();
        clientApplication.setAppName("client");
        consumerConfig = new ConsumerConfig<DemoService>()
                .setInterfaceId(DemoService.class.getName())
                .setDirectUrl("dubbo://127.0.0.1:20880")
                .setBootstrap("dubbo")
                .setTimeout(30000)
                .setRegister(false).setProtocol("dubbo").setApplication(clientApplication);
        final DemoService demoService = consumerConfig.refer();

        String result = demoService.sayHello("xxx");
        Assert.assertTrue(result.equalsIgnoreCase("hello xxx"));

    }

    @Test
    //单向调用
    public void testOneWay() {
        // 只有1个线程 执行
        ServerConfig serverConfig = new ServerConfig()
                .setStopTimeout(60000)
                .setPort(20880)
                .setProtocol("dubbo")
                .setQueues(100).setCoreThreads(1).setMaxThreads(2);

        // 发布一个服务，每个请求要执行1秒
        ApplicationConfig serverApplacation = new ApplicationConfig();
        serverApplacation.setAppName("server");
        providerConfig = new ProviderConfig<DemoService>()
                .setInterfaceId(DemoService.class.getName())
                .setRef(new DemoServiceImpl())
                .setServer(serverConfig)
                .setBootstrap("dubbo")
                // .setParameter(RpcConstants.CONFIG_HIDDEN_KEY_WARNING, "false")
                .setRegister(false).setApplication(serverApplacation);
        providerConfig.export();

        ApplicationConfig clientApplication = new ApplicationConfig();
        clientApplication.setAppName("client");

        List<MethodConfig> methodConfigs = new ArrayList<MethodConfig>();

        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setInvokeType(RpcConstants.INVOKER_TYPE_ONEWAY);
        methodConfig.setName("sayHello");

        methodConfigs.add(methodConfig);
        consumerConfig = new ConsumerConfig<DemoService>()
                .setInterfaceId(DemoService.class.getName())
                .setDirectUrl("dubbo://127.0.0.1:20880")
                .setTimeout(30000)
                .setRegister(false)
                .setProtocol("dubbo")
                .setBootstrap("dubbo")
                .setApplication(clientApplication)
                .setInvokeType(RpcConstants.INVOKER_TYPE_ONEWAY)
                .setMethods(methodConfigs);
        final DemoService demoService = consumerConfig.refer();
        String tmp = demoService.sayHello("xxx");
        Assert.assertEquals(null, tmp);

    }

    @Test
    //future调用,从future中取值.
    public void testFuture() {
        // 只有1个线程 执行
        ServerConfig serverConfig = new ServerConfig()
                .setStopTimeout(60000)
                .setPort(20880)
                .setProtocol("dubbo")
                .setQueues(100).setCoreThreads(1).setMaxThreads(2);

        // 发布一个服务，每个请求要执行1秒
        ApplicationConfig serverApplacation = new ApplicationConfig();
        serverApplacation.setAppName("server");
        providerConfig = new ProviderConfig<DemoService>()
                .setInterfaceId(DemoService.class.getName())
                .setRef(new DemoServiceImpl())
                .setServer(serverConfig)
                .setBootstrap("dubbo")
                // .setParameter(RpcConstants.CONFIG_HIDDEN_KEY_WARNING, "false")
                .setRegister(false).setApplication(serverApplacation);
        providerConfig.export();

        ApplicationConfig clientApplication = new ApplicationConfig();
        clientApplication.setAppName("client");
        List<MethodConfig> methodConfigs = new ArrayList<MethodConfig>();

        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setInvokeType(RpcConstants.INVOKER_TYPE_FUTURE);
        methodConfig.setName("sayHello");
        consumerConfig = new ConsumerConfig<DemoService>()
                .setInterfaceId(DemoService.class.getName())
                .setDirectUrl("dubbo://127.0.0.1:20880")
                .setTimeout(30000)
                .setRegister(false).setProtocol("dubbo")
                .setBootstrap("dubbo")
                .setApplication(clientApplication)
                .setInvokeType(RpcConstants.INVOKER_TYPE_FUTURE)
                .setMethods(methodConfigs);
        final DemoService demoService = consumerConfig.refer();

        String result = demoService.sayHello("xxx");
        Assert.assertEquals(null, result);

        Future<Object> future = RpcContext.getContext().getFuture();

        String futureResult = null;
        try {
            futureResult = (String) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("Hello xxx", futureResult);

    }

    @After
    public void afterMethod() {
        DubboSingleton.destroyAll();
        RpcRuntimeContext.destroy();
        RpcInternalContext.removeAllContext();
        RpcInvokeContext.removeContext();
    }
}