package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //롬복기능 로그
@RestController
public class LogTestController {
    /*private final Logger log = LoggerFactory.getLogger(getClass());*/


    @RequestMapping("/log-test")
    public String logTest(){
        String name="String";

        log.trace("trace log={}",name);
        log.trace("debug log={}",name);
        log.trace("info log={}",name);
        log.info("warn log={}",name);
        log.info("error log={}",name);

        return "ok";
    }
}
