package io.hogenboom.familyfoto.service.group;

import io.hogenboom.familyfoto.entity.Group;
import io.hogenboom.familyfoto.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    GroupRepository repository;

    public void addGroup(Group group) {
        repository.save(group);
    }
}
