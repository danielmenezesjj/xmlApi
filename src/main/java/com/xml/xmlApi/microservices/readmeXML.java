package com.xml.xmlApi.microservices;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class readmeXML {


    @PostMapping("/teste")
    public ResponseEntity<Map<String, Object>> receberXML(@RequestParam("file") MultipartFile file) {
        try {
            // Converta o arquivo XML para um Map usando Jackson XML
            XmlMapper xmlMapper = new XmlMapper();
            Map<String, Object> xmlMap = xmlMapper.readValue(file.getInputStream(), Map.class);

            // Acesse diretamente a parte desejada, por exemplo, a parte 'ide'
            Map<String, Object> ide = (Map<String, Object>) xmlMap.get("NFe");
            if (ide != null) {
                ide = (Map<String, Object>) ide.get("infNFe");
                if (ide != null) {
                    ide = (Map<String, Object>) ide.get("emit");
                    if (ide != null) {
                        // Aqui você pode usar 'ide' para acessar os valores específicos dentro de 'ide'
                        String cUF = (String) ide.get("cUF");
                        String cNF = (String) ide.get("cNF");
                        // e assim por diante...
                        System.out.println(ide);
                        return new ResponseEntity<>(ide, HttpStatus.OK);
                    }
                }
            }

            // Se chegou aqui, a estrutura não foi encontrada
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
