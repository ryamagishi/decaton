/*
 * Copyright 2020 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.linecorp.decaton.benchmark;

import com.linecorp.decaton.testing.KafkaAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Accessors(fluent = true)
@AllArgsConstructor
public class TemporaryTopic implements AutoCloseable {
    private final KafkaAdmin admin;
    @Getter
    private final String topic;

    public static TemporaryTopic create(String bootstrapServers, String topic) {
        KafkaAdmin admin = new KafkaAdmin(bootstrapServers);
        log.info("Creating topic {} on {}", topic, bootstrapServers);
        admin.createTopic(topic, 3, 3);
        return new TemporaryTopic(admin, topic);
    }

    @Override
    public void close() throws Exception {
        log.info("Cleaning up topic {}", topic);
        admin.deleteTopics(topic);
        admin.close();
    }

    @Override
    public String toString() {
        return topic;
    }
}
