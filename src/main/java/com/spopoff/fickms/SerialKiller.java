/*
Copyright Stéphane Georges Popoff, (juillet 2009 - mai 2020)

spopoff@rocketmail.com

Ce logiciel est un programme informatique servant à gérer des habilitations.

Ce logiciel est régi par la licence [CeCILL|CeCILL-B|CeCILL-C] soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence [CeCILL|CeCILL-B|CeCILL-C] telle que diffusée par le CEA, le CNRS et l'INRIA
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant
donné sa spécificité de logiciel libre, qui peut le rendre complexe à
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement,
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
pris connaissance de la licence [CeCILL|CeCILL-B|CeCILL-C], et que vous en avez accepté les
termes.
 */
package com.spopoff.fickms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author spopoff@rocketmail.com
 */
public class SerialKiller implements Serializable{
   Map<String, Object> kmsProviders;
    final byte[] localMasterKey = new byte[96];
    private Logger log = LogManager.getLogger(SerialKiller.class);
    
    public void newKms(String filePath, String lot) throws Exception{
        ObjectOutputStream oos = null;
        kmsProviders = new HashMap<>();
        //il faut vérifier si pas déja présent
        Map<String, Object> histoKms = null;
        try{
            histoKms = getKms(filePath);
        }catch(Exception ex){
            System.err.println("Pas trouvé historique");
        }
        if(histoKms != null){
            if(!histoKms.isEmpty()){
                for(Map.Entry<String, Object> entK : histoKms.entrySet()){
                    if(!kmsProviders.containsKey(entK.getKey())){
                        kmsProviders.put(entK.getKey(), entK.getValue());
                        log.info("histo "+entK.getKey());
                    }
                }
            }
        }
        new SecureRandom().nextBytes(localMasterKey);
        if(!kmsProviders.containsKey(lot+"idnt")){
            kmsProviders.put(lot+"idnt",  localMasterKey);
            log.info(lot+"idnt");
        }
        new SecureRandom().nextBytes(localMasterKey);
        if(!kmsProviders.containsKey(lot+"acct")){
            kmsProviders.put(lot+"acct",  localMasterKey);
            log.info(lot+"acct");
        }
        new SecureRandom().nextBytes(localMasterKey);
        if(!kmsProviders.containsKey(lot+"srvr")){
            kmsProviders.put(lot+"srvr",  localMasterKey);
            log.info(lot+"srvr");
        }
        new SecureRandom().nextBytes(localMasterKey);
        if(!kmsProviders.containsKey(lot+"priv")){
            kmsProviders.put(lot+"priv",  localMasterKey);
            log.info(lot+"priv");
        }
        new SecureRandom().nextBytes(localMasterKey);
        if(!kmsProviders.containsKey(lot+"orga")){
            kmsProviders.put(lot+"orga",  localMasterKey);
            log.info(lot+"orga");
        }
        new SecureRandom().nextBytes(localMasterKey);
        if(!kmsProviders.containsKey(lot+"frst")){
            kmsProviders.put(lot+"frst",  localMasterKey);
            log.info(lot+"frst");
        }
        try {
            final FileOutputStream fichier = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(kmsProviders);
            oos.flush();
        } catch (final java.io.IOException e) {
            System.err.println(e);
            throw new Exception(e);
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException ex) {
                System.err.println(ex);
                throw new Exception(ex);
            }
        }
    }
    public Map<String, Object> getKms(String filePath) throws Exception{
        ObjectInputStream ois = null;
        Map<String, Object> kmsProvSerial = null;
        try{
            final FileInputStream fichier = new FileInputStream(filePath);
            ois = new ObjectInputStream(fichier);
            kmsProvSerial = (Map<String, Object>) ois.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.err.println(e);
            throw new Exception(e);
        }
        return kmsProvSerial;
    }
    private String encB64(byte[] text){
        if(text == null) return "";
        if(text.length == 0) return "";
        byte[] enc = Base64.getEncoder().encode(text);
        return new String(enc);
    } 
}
