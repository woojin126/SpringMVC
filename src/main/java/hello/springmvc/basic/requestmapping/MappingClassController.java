package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    /**
     * 회원 목록 조회 :GET
     * 회원 등록:POST
     * 회원 조회:GET
     * 회원 수정:PATCH
     * 회원 삭제:DELETE
     * @return
     */
    @GetMapping
    public String user(){
        return "get users";
    }
    @PostMapping
    public String addUser(){
        return "post user";
    }
    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId){
        return "get userId";
    }
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId){
        return "update userId=" + userId;
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return "Delete userId=" + userId;
    }
}
