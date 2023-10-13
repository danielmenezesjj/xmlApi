package com.xml.xmlApi.microservices;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xml.xmlApi.Infrastructure.Repository.EmitRepository;
import com.xml.xmlApi.domain.Emitente.Emit;
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

    private final EmitRepository emitRepository;

    // Injeção de dependência do repositório por meio do construtor
    public readmeXML(EmitRepository emitRepository) {
        this.emitRepository = emitRepository;
    }

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
                ide = (Map<String, Object>) ide.get("emit");

                // Converta o Map para uma instância de Emit
                Emit emit = convertMapToEmit(ide);

                // Salvar a instância no repositório
                emitRepository.save(emit);

                // Retorna o mapa salvo como resposta
                return new ResponseEntity<>(ide, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Emit convertMapToEmit(Map<String, Object> ide) {
        // Implemente a lógica para converter o Map em uma instância de Emit
        // Exemplo:
        Emit emit = new Emit();
        emit.setCNPJ((String) ide.get("CNPJ"));
        emit.setXNome((String) ide.get("xNome"));
        emit.setIE((String) ide.get("IE"));
        emit.setCRT((String) ide.get("CRT"));

        // Configurar outros campos...
        return emit;
    }
}
