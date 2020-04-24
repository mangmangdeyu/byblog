package com.zby.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zby.pojo.Type;
import com.zby.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String types(Model model,@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "4") Integer size) throws Exception {
        List<Type> types = typeService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(types);
         model.addAttribute("pageInfo",pageInfo);
        return "admin/type/types";
    }

    @GetMapping("/types/input")
    public String toSaveType() throws Exception {

        return "admin/type/types-input";
    }

    @PostMapping("/types/saveTypes")
    public String saveTypes(Type type,RedirectAttributes attributes)throws Exception{
        System.out.println(type.getId());
        Integer count = typeService.getTypeByName(type.getName());
        if (count!=0){
            attributes.addFlashAttribute("message", "该类已存在,不可以重复添加");
            return "redirect:/admin/types/input";
        }

        Integer i = typeService.saveType(type);
        if (i != 1) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";

    }

    @GetMapping("/types/edit")
    public String toUpdateType(@RequestParam(name = "id") Long id,Model model) throws Exception {

        Type t = typeService.getType(id);

        model.addAttribute("type",t);

        return "admin/type/types-edit";
    }

    @PostMapping("/types/editTypes")
    public String editTypes(Type type,RedirectAttributes attributes)throws Exception{

        Integer i = typeService.updateType(type.getName(),type.getId());
        if (i != 1) {
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/delete")
    public String delete(@RequestParam(name = "id")Long id,RedirectAttributes attributes) throws Exception {
        typeService.deleteType(id);

        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
