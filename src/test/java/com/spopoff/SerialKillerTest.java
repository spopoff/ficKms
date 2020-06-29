/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spopoff;

import com.spopoff.fickms.SerialKiller;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author stephane.popoff
 */
public class SerialKillerTest {
    String filePath = "c:/t0File/kms.ser";
    public SerialKillerTest() {
    }
    @Test
    public void newTest(){
        SerialKiller sk = new SerialKiller();
        Map<String, Object> ret = null;
        try{
            sk.newKms(filePath, "lot jour 08/05/2020");
        }catch(Exception e){
            fail("Erreur serial "+e);
        }
        try{
            ret = sk.getKms(filePath);
            Object un = ret.get("lot jour 08/05/2020idnt");
        }catch(Exception ex){
            fail("retour deserial "+ex);
        }
    }
    @Test
    public void readKms(){
        SerialKiller sk = new SerialKiller();
        Map<String, Object> ret = null;
        try{
            ret = sk.getKms(filePath);
        }catch(Exception e){
            fail("Erreur deserial "+e);
        }
        try{
            Object un = ret.get("lot jour 08/05/2020idnt");
        }catch(Exception ex){
            fail("retour deserial "+ex);
        }
    }
}
