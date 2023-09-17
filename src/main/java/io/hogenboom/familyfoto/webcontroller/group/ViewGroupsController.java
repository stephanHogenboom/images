package io.hogenboom.familyfoto.webcontroller.group;

import io.hogenboom.familyfoto.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view-groups")
public class ViewGroupsController {
    @Autowired
    GroupRepository groupRepository;

    @GetMapping("")
    public ModelAndView viewGroupsPage() {
        var groups = groupRepository.findAll(Pageable.ofSize(100));

        var mav = new ModelAndView("group/view-groups");
        mav.addObject("groups", groups.stream().toList());

        return mav;
    }
}
