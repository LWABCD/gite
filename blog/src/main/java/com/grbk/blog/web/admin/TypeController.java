package com.grbk.blog.web.admin;

import com.grbk.blog.pojo.Type;
import com.grbk.blog.service.TypeService;
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
 * 有关分类的controller
 */

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 查询分类列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    //设置分页规则
    public String types(@PageableDefault(size=3,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model){
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 点击新增按钮
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 添加分类
     * @param type   接收前端传递过来的数据，@Valid会校验这个数据
     * @param result   //BindingResult会拿到校验过后的数据
     * @param attributes   //存入要向重定向后的页面传递的数据
     * @return
     */
    @PostMapping("/types/add")
    public String addType(@Valid Type type, BindingResult result,RedirectAttributes attributes){
        //判断是否已存在该分类
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        Type t=typeService.saveType(type);
        //判断是否保存成功，并向前端返回信息
        if(t==null){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 编辑分类
     * @param type
     * @param result   BindingResult一定要放在@Valid后面，不然会失效
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/types/edit/{id}")
    public String editPost(@Valid Type type,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        //判断是否已存在该分类
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            //向前端传递提示信息
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.updateType(id, type);
        if(t==null){
            attributes.addFlashAttribute("message", "更新失败");
        }else{
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 点击编辑按钮
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type", typeService.getType(id));
        return "/admin/types-input";
    }

    /**
     * 删除分类
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
