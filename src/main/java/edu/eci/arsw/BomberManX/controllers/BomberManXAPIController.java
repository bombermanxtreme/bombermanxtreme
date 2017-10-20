/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.controllers;

import edu.eci.arsw.BomberManX.services.BomberManXServices;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kvn CF <ECI>
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BomberManXAPIController {
    @Autowired
    BomberManXServices gc=null;    
    
    /**
     * Responde a a una petición get todos los Blueprints
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        try {            
            
            Set<Blueprint> data = gc.getAllBlueprints();
            if(data.isEmpty())throw new Exception("NOT FOUND");
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("NOT FOUND BluePrinters", HttpStatus.NOT_FOUND);
        }

    }
    
    /**
     * Responde a a una petición get todos los Blueprints del author
     * @param author
     * @param model
     * @return
     */
    @RequestMapping(path = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getForAuthor(@PathVariable String author, Model model){
        try {     
            Set<Blueprint> data = gc.getBlueprintsByAuthor(author);
            if(data.isEmpty())throw new Exception("NOT FOUND");
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Not Found User:" +author, HttpStatus.NOT_FOUND);

        }

    }
    
    /**
     * Responde a a una petición get todos los Blueprints del author y nombre
     * @param author
     * @param bpname
     * @param model
     * @return
     */
    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> getForAuthor_bpname(@PathVariable String author, @PathVariable String bpname, Model model) {
        try {     
            Set<Blueprint> data = gc.getBlueprintsByAuthor_bpname(author, bpname);
            if(data.isEmpty())throw new Exception("NOT FOUND");
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Not Found Blueprint for name: "+bpname +" User: "+author, HttpStatus.NOT_FOUND);
        }

    }
    
    @RequestMapping(method = RequestMethod.POST)    
    public ResponseEntity<?> manejadorPostBluePrint(@RequestBody Blueprint bp){
        
        try {
           
            //curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints -d "{\"author\":\"kvn\",\"points\":[{\"x\":1,\"y\":1},{\"x\":2,\"y\":2}],\"name\":\"PRueba\"}"
            
            gc.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No se pudo insertar el Blueprint por el error: "+ex.getMessage(),HttpStatus.FORBIDDEN);            
        }        
    }
    
    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.PUT)  
    public ResponseEntity<?> manejadorPutBluePrint(@PathVariable String author, @PathVariable String bpname,@RequestBody Blueprint bp, Model model) {
        try {     
            
            //curl -i -X PUT -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints/kvn/PRueba -d "{\"author\":\"kvn\",\"points\":[{\"x\":100,\"y\":100},{\"x\":200,\"y\":200}],\"name\":\"PRueba\"}"
           
            gc.putBlueprintsByAuthor_bpname(author, bpname, bp);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Not UPDATED Blueprint for name: "+bpname +" User: "+author+" "+ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}