package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping({"id"})
    public Session get(@PathVariable Long id){
        return  sessionRepository.getOne(id);
    }

    //create-method
    @PostMapping
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);

    }

    //delete-method
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        sessionRepository.deleteById(id);
    }

    //updating a session record:
    @RequestMapping(value= "{id}", method = RequestMethod.PUT) // PUT will update all of the contents, PATCH is just a part of all.
    public Session update(@PathVariable Long id, @RequestBody Session session){
        Session existingSession = sessionRepository.getOne(id);
        //copy the contents from session onto the existingSession, ignore the session_id;
        BeanUtils.copyProperties(session, existingSession,"session_id");
        return sessionRepository.saveAndFlush(existingSession);


    }


}
