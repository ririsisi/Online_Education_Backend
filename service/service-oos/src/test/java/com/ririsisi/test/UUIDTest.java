package com.ririsisi.test;

import org.junit.Test;

import java.util.UUID;

/**
 * @Author ririsisi
 * @Version 1.0
 */
public class UUIDTest {

    // 测试随机UUID
    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }
}
