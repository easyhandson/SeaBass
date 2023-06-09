package cn.com.seabase.framework.web.core.handler;

import cn.com.seabase.framework.common.pojo.CommonResult;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 全局统一返回信息处理 (项目必备)
 *
 * 统一拦截对返回的数据进行处理, 必备
 * 作用: 返回数据内容加密,签名,记录,转换等...
 */
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {

    private static final List<String> EXCLUDE = Lists.newArrayList(
            "Swagger2Controller",
            "Swagger2ControllerWebMvc",
            "ApiResourceController");

    /**
     * 是否支持advice功能 (我这里是都进行拦截, 可根据事情情况来进行改动)
     *
     * @param methodParameter 方法相关信息
     * @param aClass 选择的转换器类型
     * @return true-支持,beforeBodyWrite()调用 ，false-不支持,beforeBodyWrite()不调用
     *
     * 方法贴了 NotResponseBodyHandler 注解表示不需要封装成 CommonResult 返回
     */
    @Override
    @SuppressWarnings("NullableProblems") // 避免 IDEA 警告
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod() != null
                || methodParameter.hasMethodAnnotation(NotResponseBodyHandler.class);
    }

    /**
     * 统一拦截对返回的数据进行处理
     * @param body controller返回的数据
     * @return 处理后的数据 ( ResultData 或 ResultData结构体的json类型[返回数据为String类型])
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 项目统一返回体无需处理
        if (body instanceof CommonResult) {
            return body;
        }

        // 解决全局统一返回信息处理对swagger消息体的封装
        if (EXCLUDE.contains(returnType.getDeclaringClass().getSimpleName())) {
            return body;
        }

        // 注意: 返回体类型为String类型的数据时, 需要先转换为json格式的字符串类型, 否则会报错如下:
        //  java.lang.ClassCastException: com.gbk.example.framework.common.pojo.CommonResult cannot be cast to java.lang.String
        if (body instanceof String) {
            return JSONUtil.toJsonStr(CommonResult.success(body));
        }

        // 避免返回null, 而是返回空集合,空字符等等...
        if (Objects.isNull(body)) {
            Class<?> returnClass = this.getRealClassType(returnType);
            if (String.class.equals(returnClass)) {
                // String 类型只能返回字符串...
                return JSONUtil.toJsonStr(CommonResult.success(""));
            }

            if (Collection.class.isAssignableFrom(returnClass)) {
                body = Collections.emptyList();
            } else {
                body = Collections.emptyMap();
            }
        }

        return CommonResult.success(body);
    }
    
    /**
     * 获取返回的类型
     *
     * @param returnType 返回体类型
     * @return 返回体的类型
     */
    @SneakyThrows // 是lombok的注解, 作用是就是为了消除try-catch这样的模板代码。(但是不知道会不会存在性能问题, 不知道try的范围大不大...)
    private Class<?> getRealClassType(MethodParameter returnType) {
        Type genericParameterType = returnType.getGenericParameterType();
        String typeName = genericParameterType.getTypeName();
        typeName = typeName.replaceAll("<.*>", "");
        return Class.forName(typeName);
    }
    
}
