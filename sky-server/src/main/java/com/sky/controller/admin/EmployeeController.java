package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api("员工相关接口实现")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */

    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     */

    @PostMapping
    @ApiOperation("新增员工")
    public Result addEmployer(@RequestBody EmployeeDTO employeeDTO){
        //根据产品原型分析需要处理的字段
        /**
         *  账号：唯一，3-20
         *  员工姓名：
         *  手机号：11位
         *  身份证：18位
         *  性别：0/1
         */
        log.info("新增员工：{}", employeeDTO);
        String idNumber = employeeDTO.getIdNumber();
        String phone = employeeDTO.getPhone();
        employeeService.save(employeeDTO);


        return Result.success();
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageEmployer(EmployeePageQueryDTO employeePageQueryDTO){

         PageResult pageResult  = employeeService.query(employeePageQueryDTO);

        return Result.success(pageResult);
    }


    /**
     * 员工状态设置
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("员工状态设置")
    public Result setEmployerStatus(@PathVariable Integer status, Long id){

//        //当前用户的id
//        Long currentId = BaseContext.getCurrentId();
//        System.out.println(currentId + "/" + id);

        employeeService.setStatus(id,status);
        return Result.success();
    }


    /**
     * 根据id获取员工数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id获取员工数据")
    public Result<Employee> getEmployerById(@PathVariable Long id){

        Employee employee = employeeService.getEmployeeById(id);

        return Result.success(employee);
    }


    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result editEmployer(@RequestBody EmployeeDTO employeeDTO){
            employeeService.updateEmployee(employeeDTO);
            return Result.success();
    }

}
