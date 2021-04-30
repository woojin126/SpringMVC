package hello.springmvc.basic.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello","age":20}
 * content-type:application/json
 */

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     ❄  Http API 메시지 바디 : 단순 텍스트
     ex> 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자.
     HTTP메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있는데,
     ServletInputStream inputStream = request.getInputStream(); 이렇게 읽으면 Byte코드로 읽어오기 때문에,
     String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); 이렇게 변환시켜주어야 한다.

     ❄️ Http API 메시지 바디 : JSON
     JSON형식 같은 경우는 파싱할 수 있게 객체를 하나 생성해야 한다.
     content-type 같은 경우에 application/json으로 지정해야 한다.
     JSON 결과를 파싱해서 사용하려면 Jackson, Gson 같은 JSON변환 라이브러리를 추가해서 사용해야 하는데,
     스프링 부트로 Spring MVC를 선택하면 기본으로 Jackson라이브러리 ObjectMapper를 제공한다.

     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}", data.getUsername(),data.getAge());
        response.getWriter().write("ok");
    }

    /**
     1.
     ServletInputStream inputStream = request.getInputStream();
     String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

     바이트코드로 받고, 스트링형태로바꾸고, UTF-8 형식으로 바꿔주고 이 과정을
      @RequestBody로 한방에 해결(문자로 가져오는 과정)


      response.getWriter().write("ok");
      는 @ResponseBody로 대체
     2.
     readValue(arg,type)
     arg : 지정된 타입으로 변환할 대상
     type:대상을 어떤 타입으로 변환할 것인지 클래스를 명시한다,Class객체, TypeReference 객체가 올수있따.
     **/
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}",messageBody);
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}", data.getUsername(),data.getAge());

        return "ok";
    }


    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data){
        log.info("username={},age={}", data.getUsername(),data.getAge());
        return "ok";
    }

    /**
     * 위아래 같은데 다른방식
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> helloData){
        HelloData data = helloData.getBody();
        log.info("username={},age={}", data.getUsername(),data.getAge());
        return "ok";
    }


    /**
     * Json을 받아서 -> 객체로 사용했다가 -> 응답보낼떄 다시 Json 으로 바뀌어짐
     * 
     * @RequestBody 요청
     * JSON 요청 -> HTTP 메시지 컨버터 -> 객체
     * @ResponseBody 응답
     * 객체 -> HTTP 메시지 컨버터 -> JSON 응답
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data){
        log.info("username={},age={}", data.getUsername(),data.getAge());
        return data;
    }
}
