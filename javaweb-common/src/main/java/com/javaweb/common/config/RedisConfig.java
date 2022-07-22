// +----------------------------------------------------------------------
// | JavaWeb_EleVue_Pro前后端分离旗舰版框架 [ JavaWeb ]
// +----------------------------------------------------------------------
// | 版权所有 2021 上海JavaWeb研发中心
// +----------------------------------------------------------------------
// | 官方网站: http://www.javaweb.vip/
// +----------------------------------------------------------------------
// | 作者: 鲲鹏 <javaweb520@gmail.com>
// +----------------------------------------------------------------------
// | 免责声明:
// | 本软件框架禁止任何单位和个人用于任何违法、侵害他人合法利益等恶意的行为，禁止用于任何违
// | 反我国法律法规的一切平台研发，任何单位和个人使用本软件框架用于产品研发而产生的任何意外
// | 、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、附带
// | 或衍生的损失等)，本团队不承担任何法律责任。本软件框架只能用于公司和个人内部的法律所允
// | 许的合法合规的软件产品研发，详细声明内容请阅读《框架免责声明》附件；
// +----------------------------------------------------------------------

package com.javaweb.common.config;

import com.javaweb.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//@PropertySource("classpath:redis.properties")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    /*
    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;
    */
    /**
     * JedisPoolConfig 连接池
     * @return

     @Bean public JedisPoolConfig jedisPoolConfig() {
     JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
     // 最大空闲数
     jedisPoolConfig.setMaxIdle(maxIdle);
     // 连接池的最大数据库连接数
     jedisPoolConfig.setMaxTotal(maxTotal);
     // 最大建立连接等待时间
     jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
     // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
     jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
     // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
     jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
     // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
     jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
     // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
     jedisPoolConfig.setTestOnBorrow(testOnBorrow);
     // 在空闲时检查有效性, 默认false
     jedisPoolConfig.setTestWhileIdle(testWhileIdle);
     return jedisPoolConfig;
     }

     /**
      * 单机版配置
      * @Title: JedisConnectionFactory
     * @param @param jedisPoolConfig
     * @param @return
     * @return JedisConnectionFactory
     * @autor lpl
     * @date 2018年2月24日
     * @throws

     @Bean public JedisConnectionFactory jedisConnectionFactory(){
     RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
     redisStandaloneConfiguration.setHostName(hostName);
     redisStandaloneConfiguration.setPort(port);
     redisStandaloneConfiguration.setPassword(password);

     JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
     jedisClientConfigurationBuilder.connectTimeout(Duration.ofMillis(timeout));

     JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfigurationBuilder.build());

     return factory;
     }
     */
    /**
     * 实例化 RedisTemplate 对象 jredis实现方式，springboot2.x以后使用下面的方法
     *
     * @return
     @Bean public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
     RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
     initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
     return redisTemplate;
     }
     */
    /**
     * lettuce实现redis方式
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     *
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }

    /**
     * 注入封装RedisTemplate
     *
     * @return RedisUtil
     * @throws
     * @Title: redisUtil
     * @autor lpl
     * @date 2017年12月21日
     */
    @Bean(name = "redisUtils")
    public RedisUtils redisUtils(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils redisUtil = new RedisUtils();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
}
