package com.zby.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zby.pojo.Tag;
import com.zby.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String tags(Model model,@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "4") Integer size) throws Exception {
        List<Tag> tags = tagService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(tags);
         model.addAttribute("pageInfo",pageInfo);
        return "admin/tag/tags";
    }

    @GetMapping("/tags/input")
    public String toSaveTag() throws Exception {

        return "admin/tag/tags-input";
    }

    @PostMapping("/tags/saveTags")
    public String saveTags(Tag tag,RedirectAttributes attributes)throws Exception{
        Integer count = tagService.getTagByName(tag.getName());
        if (count!=0){
            attributes.addFlashAttribute("message", "该类已存在,不可以重复添加");
            return "redirect:/admin/tags/input";
        }

        Integer i = tagService.saveTag(tag);
        if (i != 1) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";

    }

    @GetMapping("/tags/edit")
    public String toUpdateTag(@RequestParam(name = "id") Long id,Model model) throws Exception {

        Tag t = tagService.getTag(id);

        model.addAttribute("tag",t);

        return "admin/tag/tags-edit";
    }

    @PostMapping("/tags/editTags")
    public String editTags(Tag tag,RedirectAttributes attributes)throws Exception{

        Integer i = tagService.updateTag(tag.getName(),tag.getId());
        if (i != 1) {
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/delete")
    public String delete(@RequestParam(name = "id")Long id,RedirectAttributes attributes) throws Exception {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }


}
