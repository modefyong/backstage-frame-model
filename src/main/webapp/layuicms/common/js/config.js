/**
 * 通用配置
 */
/*开发环境*/
var runEnv = 'dev';

// $config不建议直接引用，除了用于指定模块基目录。可以通过$tool模块中的方法获取
var $config = {
    apiContext: runEnv === 'product' ? 'http://2xm4883207.qicp.vip:35499/ordinary/' : 'http://localhost:8080/ordinary/', // api请求地址
    resUrl: runEnv === 'product' ? 'http://2xm4883207.qicp.vip:35499/ordinary/' : 'http://localhost:8080/ordinary/' // 前端资源访问根目录,生产环境请设置为托管前端资源的位置
};

