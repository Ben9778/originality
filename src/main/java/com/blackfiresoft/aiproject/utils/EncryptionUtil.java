package com.blackfiresoft.aiproject.utils;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义加密
 */
@Configuration
public class EncryptionUtil {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        return new DESEncrypt();
    }

    public static class DESEncrypt implements StringEncryptor {

        /**
         * SimpleStringPBEConfig是Jasypt中的一个实现类，用于配置PBE算法的参数。
         * 它提供了一些方法，可以设置密码、密钥迭代次数、算法、盐和IV生成器等。您可以使用它来配置PBE算法的参数，
         * 以便与StringEncryptor一起使用。
         */
        private PBEConfig config(){
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            //设置秘钥
            config.setPassword("blackFireSoft");
            //设置加密算法
            config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
            //这行代码设置了PBE加密算法从密码中推导密钥的迭代次数。
            config.setKeyObtentionIterations("1000");
            config.setPoolSize("1");
            config.setProviderName("SunJCE");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
            config.setStringOutputType("base64");
            return config;
        }

        /**
         * 加密
         */
        @Override
        public String encrypt(String s) {
            PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
            encryptor.setConfig(config());
            return encryptor.encrypt(s);
        }

        /**
         * 解密
         */
        @Override
        public String decrypt(String s) {
            PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
            encryptor.setConfig(config());
            return encryptor.decrypt(s);
        }
    }
}

