package com.stylefeng.guns.modular.main.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.BaseEntityWrapper.BaseEntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.MemberCard;
import com.stylefeng.guns.modular.main.service.IMemberCardService;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2018-08-10 15:39:46
 */
@Controller
@RequestMapping("/memberCard")
public class MemberCardController extends BaseController {

    private String PREFIX = "/main/memberCard/";

    @Autowired
    private IMemberCardService memberCardService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "memberCard.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/memberCard_add")
    public String memberCardAdd() {
        return PREFIX + "memberCard_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/memberCard_update/{memberCardId}")
    public String memberCardUpdate(@PathVariable Integer memberCardId, Model model) {
        MemberCard memberCard = memberCardService.selectById(memberCardId);
        model.addAttribute("item",memberCard);
        LogObjectHolder.me().set(memberCard);
        return PREFIX + "memberCard_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return memberCardService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MemberCard memberCard) {
        memberCardService.insert(memberCard);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer memberCardId) {
        memberCardService.deleteById(memberCardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MemberCard memberCard) {
        memberCardService.updateById(memberCard);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{memberCardId}")
    @ResponseBody
    public Object detail(@PathVariable("memberCardId") Integer memberCardId) {
        return memberCardService.selectById(memberCardId);
    }
}
