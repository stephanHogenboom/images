package io.hogenboom.familyfoto.webcontroller.group;

import io.hogenboom.familyfoto.mapper.entity.GroupMapper;
import io.hogenboom.familyfoto.repository.GroupRepository;
import io.hogenboom.familyfoto.service.group.GroupService;
import io.hogenboom.familyfoto.webcontroller.MissingInformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/manage-groups/")
public class ManageGroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupService groupService;
    @GetMapping("new")
    public ModelAndView createNewGroup() {
        var modelAndView = new ModelAndView("group/new-group");

        modelAndView.addObject("newGroup", new GroupView());

        return modelAndView;
    }
    @GetMapping("edit/{id}")
    public ModelAndView editgroup(@PathVariable("id") UUID id) {
        var group = groupRepository.findById(id).orElseThrow();

        var groupView = GroupMapper.toView(group);

        var mav = new ModelAndView("group/edit-group");
        mav.addObject("group", groupView);

        return mav;
    }

    @PostMapping("edit/{id}")
    public ModelAndView saveEditedgroup(@PathVariable("id") UUID id, @ModelAttribute("group") GroupView groupView) {
        var group = groupRepository.findById(id).orElseThrow();

        group.setName(groupView.name);
        groupRepository.save(group);

        return showgroupMainPage();
    }

    private ModelAndView showgroupMainPage() {
        var groups = groupRepository.findAll(Pageable.ofSize(100));

        var mav = new ModelAndView("group/view-groups");
        mav.addObject("groups", groups.stream().toList());

        return mav;
    }

    @PostMapping("new")
    public ModelAndView addGroup(@ModelAttribute("newGroup") GroupView groupView) {
        if (groupView == null) {
            throw new MissingInformationException("newGroup");
        }
        if (groupView.name == null) {
            throw new MissingInformationException("name");
        }

        var group = GroupMapper.viewToGroup(groupView);
        groupService.addGroup(group);

        return showgroupMainPage();
    }
}
