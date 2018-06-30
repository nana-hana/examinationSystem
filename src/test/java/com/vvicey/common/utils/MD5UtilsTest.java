package com.vvicey.common.utils;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * MD5Utils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 28, 2018</pre>
 */
public class MD5UtilsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: encryptPassword(String password)
     */
    @Test
    public void testEncryptPassword() throws Exception {
        MD5Utils.encryptPassword("test");
    }


} 
