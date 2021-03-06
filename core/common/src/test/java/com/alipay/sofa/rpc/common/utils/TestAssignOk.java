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
package com.alipay.sofa.rpc.common.utils;

/**
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
interface TestAssignOk {
}

interface TestAssignOk1 extends TestAssignOk {
}

interface TestAssignOk5 extends TestAssignOk1 {
}

interface TestAssignNotOk {
}

interface TestAssignNotOk3 extends TestAssignNotOk {
}

abstract class TestAssignOk0 implements TestAssignOk {
}

class TestAssignOk2 implements TestAssignOk {
}

class TestAssignOk3 implements TestAssignOk1 {
}

class TestAssignOk4 extends TestAssignOk0 {
}

abstract class TestAssignNotOk0 implements TestAssignNotOk {
}

class TestAssignNotOk1 extends TestAssignNotOk0 {
}

class TestAssignNotOk2 implements TestAssignNotOk {
}

class TestAssignOk6 implements TestAssignOk, TestAssignNotOk {
}

class TestAssignOk7 implements TestAssignOk1, TestAssignNotOk3 {
}

class TestAssignOk8 extends TestAssignNotOk1 implements TestAssignOk5, TestAssignNotOk3 {
}

class TestAssignOk9 extends TestAssignOk6 {
}