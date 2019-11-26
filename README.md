# oauth2
## AuthorizationServer,认证服务器，模拟授权码方式获取token,tokenStore采用RedisTokenStore。测试时需要本机正常启动redis.需要与下面的ResourceServer项目一同启动。
## 整个授权码方式获取code请求token后，并且通过token来访问资源服务器的资源,验证是否有效的流程如下：
### 1.请求code
#### 认证服务器需要开放这个端点，要不然不能正常访问，AuthorizationEndpoint 中 /oauth/authorize 这个路径   
### 2.根据code来获取token
#### 通过配置的redirectUris("http://127.0.0.1:8001/csdn/token")，可以在程序内部获取到通过code请求到的token，TokenEndpoint中/oauth/token 这个路径
### 3.验证token
#### 这个是在ResourceServer中每次在访问受保护的资源时，会根据配置文件中的tokenInfoUri: http://127.0.0.1:8000/oauth/check_token 每次会去自动验证token .CheckTokenEndpoint 中的/oauth/check_token的这个路径。userInfoUri: http://127.0.0.1:8000/me 这个端点是需要自己实现的，主要是返回用户的相关信息，整体的验证流程在/oauth/check_token 这个端点我感觉已经完成。


