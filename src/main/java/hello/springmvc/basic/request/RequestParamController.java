package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={},age={}",username,age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ){
        log.info("username={},age={}",memberName,memberAge);

        return "ok";
    }

    /**
     *
     * @param username  파라미터 이름같으면 (name) 생략가능
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ){
        log.info("username={},age={}",username,age);

        return "ok";
    }

    /**
     *
     * @param username 단순 타입이면 @requestParam도 생략가능,int,String,Integer 등
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(
           String username,
           int age){
        log.info("username={},age={}",username,age);

        return "ok";
    }


    /**
     * @RequestParam(required = true) String username, true면 유저네임 필수
     * @RequestParam(required = false) int age  false면 잇어도 없어도
     * 근데 int 는 required=false해도 에러남 why?
     * int 기본형은 null이 올수없어, 그래서 Integer 객체형은 가능
     * int a =null; x
     * Integer a = null; o
     */

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age){
        Optional<Integer> opt = Optional.ofNullable(age);


        log.info("username={},age={}",username,opt);

        return "ok";
    }
    /**
     *defaultValue="기본값"  (사실상 required가 필요없음)
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true , defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age){

        log.info("username={},age={}",username,age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String,Object> paramMap){
        log.info("username={},age={}",paramMap.get("username"),paramMap.get("age"));

        return "ok";
    }
}
