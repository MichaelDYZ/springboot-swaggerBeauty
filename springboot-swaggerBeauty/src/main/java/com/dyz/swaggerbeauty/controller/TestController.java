package com.dyz.swaggerbeauty.controller;

import com.battcn.boot.swagger.model.DataType;
import com.battcn.boot.swagger.model.ParamType;
import com.dyz.swaggerbeauty.commom.ApiResponse;
import com.dyz.swaggerbeauty.data.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dyz
 * @version 1.0
 * @date 2020/5/13 16:00
 */
@RestController
@RequestMapping("/test")
@Api(tags = "1.0", description = "用户管理", value = "用户管理")
public class TestController {
    @GetMapping
    @ApiOperation(value = "条件查询", notes = "备注：条件查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "用户名", dataType = DataType.STRING, paramType = ParamType.QUERY, defaultValue = "dyz",required = true)})
    public ApiResponse<User> getByUserName(String name) {
        System.out.println("多个参数用  @ApiImplicitParams");
        return ApiResponse.<User>builder().code(200)
                .message("操作成功")
                .data(new User(1, name, "男"))
                .build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "备注：删除用户")
    @ApiImplicitParam(name = "id", value = "用户编号",  paramType = ParamType.PATH)
    public void delete(@PathVariable Integer id) {
        System.out.println("删除用户");
    }


    @PostMapping
    @ApiOperation(value = "添加用户",notes = "备注：添加用户")
    public User post(@RequestBody User user) {
        System.out.println("添加用户");
        return user;
    }

    @PostMapping("/{id}/file")
    @ApiOperation(value = "上传文件",notes = "备注：上传文件")
    public String file(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        return file.getOriginalFilename();
    }




}
