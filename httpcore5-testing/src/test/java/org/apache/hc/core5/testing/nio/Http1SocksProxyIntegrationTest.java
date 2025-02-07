/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.hc.core5.testing.nio;

import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.testing.SocksProxy;
import org.apache.hc.core5.util.TimeValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.junit.jupiter.migrationsupport.rules.ExternalResourceSupport;
@Extensions({@ExtendWith({ExternalResourceSupport.class})})
public class Http1SocksProxyIntegrationTest extends Http1IntegrationTest {

    protected static SocksProxy PROXY;

    @BeforeAll
    public static void before() throws Throwable {
        PROXY = new SocksProxy();
        PROXY.start();
    }

    @AfterAll
    public static void after() {
        if (PROXY != null) {
            try {
                PROXY.shutdown(TimeValue.ofSeconds(5));
            } catch (final Exception ignore) {
            }
            PROXY = null;
        }
    }

    public Http1SocksProxyIntegrationTest(final URIScheme scheme) {
        super(scheme);
    }

    @Override
    protected IOReactorConfig buildReactorConfig() {
        return IOReactorConfig.custom().setSocksProxyAddress(PROXY.getProxyAddress()).build();
    }

}
