package com.grbk.blog.web.admin;

import com.grbk.blog.pojo.Tag;
import com.grbk.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 标签管理
 */

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询标签列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    //设置分页规则
    public String types(@PageableDefault(size=3,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 点击新增按钮
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * 添加标签
     * @param tag   接收前端传递过来的数据，@Valid会校验这个数据
     * @param result   //BindingResult会拿到校验过后的数据
     * @param attributes   //存入要向重定向后的页面传递的数据
     * @return
     */
    @PostMapping("/tags/add")
    public String addType(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //判断是否已存在该标签
        Tag tag1=tagService.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        Tag t = tagService.saveTag(tag);
        //判断是否保存成功，并向前端返回信息
        if(t==null){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 编辑标签
     * @param tag
     * @param result   BindingResult一定要放在@Valid后面，不然会失效
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/tags/edit/{id}")
    public String editPost(@Valid Tag tag,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        //判断是否已存在该分类
        Tag tag1=tagService.getTagByName(tag.getName());
        if(tag1!=null){
            //向前端传递提示信息
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if(t==null){
            attributes.addFlashAttribute("message", "更新失败");
        }else{
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 点击编辑按钮
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "/admin/tags-input";
    }

    /**
     * 删除标签
     */
    @GetMapping("/tags/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
