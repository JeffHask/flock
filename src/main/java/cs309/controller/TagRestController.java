package cs309.controller;

import cs309.data.Tag;
import cs309.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagRestController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/api/tag/get")
    public List<Tag> getEnabledTags() {
        return tagService.getTagsEnabled();
    }

    @RequestMapping(value = "/api/tag/get/all")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @RequestMapping(value = "/api/tag/add")
    public Tag addTag(@RequestBody Tag tag) {
        return tagService.saveTag(tag);
    }

    @RequestMapping(value = "/api/tag/delete")
    public void deleteTag(@RequestBody Tag tag) {
        tagService.deleteTag(tag);
    }

    @RequestMapping(value = "/api/tag/update/{tagId}")
    public void updateTag(@PathVariable Integer tagId, @RequestBody String tagName) {
        Tag savedTag = tagService.getTagById(tagId);
        savedTag.setTagName(tagName);
        tagService.saveTag(savedTag);
    }

    @RequestMapping(value = "/api/tag/toggle/{tagId}")
    public void toggleTag(@PathVariable Integer tagId) {
        Tag savedTag = tagService.getTagById(tagId);
        savedTag.setEnabled(!savedTag.getEnabled());
        tagService.saveTag(savedTag);
    }
}
