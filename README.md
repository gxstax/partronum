# partronum (哈利波特召唤守护神咒语)
partronum:限流框架



## 配置示例

```yaml
spring:
  partronum:
    enable: true
    configs:              # <!--对应RuleConfig-->
      - appId: app-1      # <!--对应AppRuleConfig-->
        limits:
          - api: /v1/user # <!--对应ApiLimit-->
            limit: 100
            unit: 60
          - api: /v1/order
            limit: 50
      - appId: app-2
        limits:
          - api: /v1/user
            limit: 50
          - api: /v1/order
            limit: 50
```

> enable: 配置开关，spring-boot自动配置
>
> -appId： 指定服务名称
>
> ​	- api：指定的api接口
>
> ​		limit：单位时间限流次数
>
> ​		unit: 时间（毫秒）
