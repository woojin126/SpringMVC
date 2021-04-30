package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     *요청 파라미터 vs Http 메시지 바디
     * 요청 파라미터를 조회하는 기능: @RequestParam, @ModelAttribute
     * Http 메시지 바디를 직접 조회하는 기능은:@RequestBody
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response
    )throws IOException{
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        response.getWriter().write("ok");

    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter
                                    )throws IOException{
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);

        responseWriter.write("ok");

    }

    /**
     *요청 파라미터는 get에 쿼리스트링 or post 방식 html 폼데이터 전송방식
     * (www.x-www url encoded )이경우에만사용 (@RequestParam,@ModelAttribute)
     *
     * 아래 HttpEntity랑은 무관한거
     * HttpEntity는 메시지 바디 정보를 직접조회,
     * 위에 요청파라미터 조회하는 기능이랑은 아예 상관없음.
     * 메시지 바디 정보 직접 반환도가능
     * 헤더 정보 포함 가능
     * view 조회 X
     * (그냥 에는 HTTP 바디만 딱! 찝어서 요청받고 응답하는것)
     *
     *
     * 스프링 MVC 내부에서 메시지 바디를 읽어서 문자나 객체로 반환해서 전달해주는데
     * 이때 HTTP 메시지 컨버터터 HttpMessageConverter 라는 기능을 사용.(에가 자동으로 변환해주는것)
     *
     * 아래두개것이  = @RequestBody 임
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity
                                    )throws IOException{
       String messageBody=httpEntity.getBody();
       log.info("messageBody={}",messageBody);


       return new HttpEntity<>("ok");

    }


    /**
     * 위랑같은데 http 상태코드를 넣을수있음
     */
    @PostMapping("/request-body-string-v31")
    public HttpEntity<String> requestBodyStringV31(RequestEntity<String> httpEntity
                                                   )throws IOException{
        String messageBody=httpEntity.getBody();
        log.info("messageBody={}",messageBody);

        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }


    /**
     * 최고 간단
     *
     * 요청오는건 RequestBody
     * 응답하는건 ResponseBody로
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody
    )throws IOException{

        log.info("messageBody={}",messageBody);

        return "ok";
    }

}
