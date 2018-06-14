package com.cq.controller;

import com.cq.common.JsonData;
import com.cq.dto.DeptLevelDto;
import com.cq.param.DeptParam;
import com.cq.service.SysDeptService;
import com.cq.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: caoqsq
 * @Date: 2018/6/4 09:24
 * @Description:
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData saveDept(DeptParam deptParam) {
        sysDeptService.saveDept(deptParam);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/tree.json")
    public JsonData tree() {
        List<DeptLevelDto> dtoList= sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData updateDept(DeptParam deptParam) {
        sysDeptService.updateDept(deptParam);
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") Integer id) {
        sysDeptService.delete(id);
        return JsonData.success();
    }

}
