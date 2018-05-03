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
package com.alipay.sofa.rpc.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 *
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
public class ProviderHelperTest {
    @Test
    public void isEmpty() throws Exception {
        ProviderGroup pg = null;
        Assert.assertTrue(ProviderHelper.isEmpty(pg));

        pg = new ProviderGroup("xxx", null);
        Assert.assertTrue(ProviderHelper.isEmpty(pg));

        pg = new ProviderGroup("xxx", new ArrayList<ProviderInfo>());
        Assert.assertTrue(ProviderHelper.isEmpty(pg));

        pg.add(ProviderInfo.valueOf("127.0.0.1:12200"));
        Assert.assertFalse(ProviderHelper.isEmpty(pg));
    }

    @Test
    public void compareProviders() throws Exception {

        ProviderGroup group1 = new ProviderGroup("a");
        ProviderGroup group2 = new ProviderGroup("a");

        List<ProviderInfo> oldList = new ArrayList<ProviderInfo>();
        List<ProviderInfo> newList = new ArrayList<ProviderInfo>();
        List<ProviderInfo> add = new ArrayList<ProviderInfo>();
        List<ProviderInfo> remove = new ArrayList<ProviderInfo>();

        group1.setProviderInfos(oldList);
        group2.setProviderInfos(newList);

        {
            ProviderHelper.compareGroup(group1, group2, add, remove);
            Assert.assertEquals(add.size(), 0);
            Assert.assertEquals(remove.size(), 0);

        }

        {
            oldList.clear();
            newList.clear();
            add.clear();
            remove.clear();
            oldList.add(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"));
            oldList.add(ProviderInfo.valueOf("127.0.0.2:12200?p=11&v=4.0"));
            oldList.add(ProviderInfo.valueOf("127.0.0.3:12200?p=11&v=4.0"));

            ProviderHelper.compareGroup(group1, group2, add, remove);
            Assert.assertEquals(add.size(), 0);
            Assert.assertEquals(remove.size(), 3);
        }

        {
            oldList.clear();
            newList.clear();
            add.clear();
            remove.clear();

            newList.add(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"));
            newList.add(ProviderInfo.valueOf("127.0.0.2:12200?p=11&v=4.0"));
            newList.add(ProviderInfo.valueOf("127.0.0.3:12200?p=11&v=4.0"));

            ProviderHelper.compareGroup(group1, group2, add, remove);
            Assert.assertEquals(add.size(), 3);
            Assert.assertEquals(remove.size(), 0);
        }

        {
            oldList.clear();
            newList.clear();
            add.clear();
            remove.clear();
            oldList.add(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"));
            oldList.add(ProviderInfo.valueOf("127.0.0.2:12200?p=11&v=4.0"));
            oldList.add(ProviderInfo.valueOf("127.0.0.3:12200?p=11&v=4.0"));

            newList.add(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"));
            newList.add(ProviderInfo.valueOf("127.0.0.4:12200?p=11&v=4.0"));
            newList.add(ProviderInfo.valueOf("127.0.0.5:12200?p=11&v=4.0"));

            ProviderHelper.compareGroup(group1, group2, add, remove);
            Assert.assertEquals(add.size(), 2);
            Assert.assertEquals(remove.size(), 2);
        }
    }

    @Test
    public void compareAddressMap() throws Exception {

        List<ProviderGroup> oldMap = new ArrayList<ProviderGroup>();
        List<ProviderGroup> newMap = new ArrayList<ProviderGroup>();

        List<ProviderInfo> add = new ArrayList<ProviderInfo>();
        List<ProviderInfo> remove = new ArrayList<ProviderInfo>();

        {
            ProviderHelper.compareGroups(oldMap, newMap, add, remove);
            Assert.assertEquals(add.size(), 0);
            Assert.assertEquals(remove.size(), 0);
        }

        {
            oldMap.clear();
            newMap.clear();
            add.clear();
            remove.clear();
            oldMap.add(new ProviderGroup("zone1",
                    Arrays.asList(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"))));
            oldMap.add(new ProviderGroup("zone2", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.2:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.3:12200?p=11&v=4.0"))));
            ProviderHelper.compareGroups(oldMap, newMap, add, remove);
            Assert.assertEquals(add.size(), 0);
            Assert.assertEquals(remove.size(), 3);
        }

        {
            oldMap.clear();
            newMap.clear();
            add.clear();
            remove.clear();
            newMap.add(new ProviderGroup("zone1",
                    Arrays.asList(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"))));
            newMap.add(new ProviderGroup("zone2", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.2:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.3:12200?p=11&v=4.0"))));
            ProviderHelper.compareGroups(oldMap, newMap, add, remove);
            Assert.assertEquals(add.size(), 3);
            Assert.assertEquals(remove.size(), 0);
        }

        {
            oldMap.clear();
            newMap.clear();
            add.clear();
            remove.clear();
            oldMap.add(new ProviderGroup("zone1",
                    Arrays.asList(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"))));
            oldMap.add(new ProviderGroup("zone2", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.2:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.3:12200?p=11&v=4.0"))));
            oldMap.add(new ProviderGroup("zone3", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.4:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.5:12200?p=11&v=4.0"))));
            oldMap.add(new ProviderGroup("zone4", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.6:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.7:12200?p=11&v=4.0"))));
            oldMap.add(new ProviderGroup("zone5", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.8:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.9:12200?p=11&v=4.0"))));

            newMap.add(new ProviderGroup("zone1",
                    Arrays.asList(ProviderInfo.valueOf("127.0.0.1:12200?p=11&v=4.0"))));
            newMap.add(new ProviderGroup("zone2", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.2:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.3:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.31:12200?p=11&v=4.0")))); // +1
            newMap.add(new ProviderGroup("zone3", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.4:12200?p=11&v=4.0")))); // -1
            newMap.add(new ProviderGroup("zone4", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.61:12200?p=11&v=4.0"),
                    ProviderInfo.valueOf("127.0.0.71:12200?p=11&v=4.0")))); // +2 -2
            newMap.add(new ProviderGroup("zone6", Arrays.asList(
                    ProviderInfo.valueOf("127.0.0.81:12200?p=11&v=4.0")))); // +1 -2

            ProviderHelper.compareGroups(oldMap, newMap, add, remove);
            Assert.assertEquals(add.size(), 4);
            Assert.assertEquals(remove.size(), 5);

            Assert.assertTrue(add.contains(ProviderInfo.valueOf("127.0.0.31:12200?p=11&v=4.0")));
            Assert.assertTrue(add.contains(ProviderInfo.valueOf("127.0.0.61:12200?p=11&v=4.0")));
            Assert.assertTrue(add.contains(ProviderInfo.valueOf("127.0.0.71:12200?p=11&v=4.0")));
            Assert.assertTrue(add.contains(ProviderInfo.valueOf("127.0.0.81:12200?p=11&v=4.0")));

            Assert.assertTrue(remove.contains(ProviderInfo.valueOf("127.0.0.5:12200?p=11&v=4.0")));
            Assert.assertTrue(remove.contains(ProviderInfo.valueOf("127.0.0.6:12200?p=11&v=4.0")));
            Assert.assertTrue(remove.contains(ProviderInfo.valueOf("127.0.0.7:12200?p=11&v=4.0")));
            Assert.assertTrue(remove.contains(ProviderInfo.valueOf("127.0.0.8:12200?p=11&v=4.0")));
            Assert.assertTrue(remove.contains(ProviderInfo.valueOf("127.0.0.9:12200?p=11&v=4.0")));
        }
    }

}