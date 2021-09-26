package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/speakers")
public class SpeakerController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping({"id"})
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getOne(id);
    }

    //create-method
    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    //delete-method
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        speakerRepository.deleteById(id);
    }

    //updating a speaker record:
    @RequestMapping(value= "{id}", method = RequestMethod.PUT) // PUT will update all of the contents, PATCH is just a part of all.
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        Speaker existingSpeaker = speakerRepository.getOne(id);
        //copy the contents from speaker onto the existingSpeaker, ignore the speaker_id;
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }


}
