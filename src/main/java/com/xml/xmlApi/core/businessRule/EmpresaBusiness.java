package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.EmpresaDTO;
import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.EmpresaRepository;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.domain.Empresa.Empresa;
import com.xml.xmlApi.core.domain.Empresa.EnderEmpresa;
import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class EmpresaBusiness {

    @Autowired
    private EmpresaRepository empresaRepository;


    @Autowired
    private EmpresaEnderecoBusiness empresaEnderecoBusiness;

    public void postEmpresaXML(Map<String, Object> ide) throws EntityAlreadyExistException {

        // Convert Map to Fornecedor
        Empresa empresa = convertMapToEmpresa(ide);
        Optional<Empresa> optionalEmpresa = empresaRepository.findBycnpj(empresa.getCnpj());

        // Extract Endereco details from the XML map
        Map<String, Object> enderecoMap = (Map<String, Object>) ide.get("enderEmit");
        EnderEmpresa enderEmpresa = empresaEnderecoBusiness.convertMapToEmit(enderecoMap);
        if(optionalEmpresa.isPresent()){
            throw new EntityAlreadyExistException(empresa.getCnpj());
        }else{
            // Associating Endereco with Fornecedor
            empresa.setEnderEmpresa(enderEmpresa);

            // Saving Fornecedor with associated Endereco
            empresaRepository.save(empresa);
        }
    }

    public Empresa postEmpresa(Empresa empresa) throws EntityAlreadyExistException {
        Optional<Empresa> optionalEmpresa = empresaRepository.findBycnpj(empresa.getCnpj());
        if(optionalEmpresa.isPresent()){
            throw new EntityAlreadyExistException(empresa.getCnpj());
        }else{
            return empresaRepository.save(empresa);
        }
    }

    public Page<Empresa> getAll(Pageable pageable){
        return empresaRepository.findAll(pageable);
    }

    public Empresa getOne(String cnpj) throws EntityNotExistException {
        Optional<Empresa> optionalEmpresa = empresaRepository.findBycnpj(cnpj);
        if(!optionalEmpresa.isPresent()){
            throw new EntityNotExistException(cnpj);
        }else{
            return optionalEmpresa.get();
        }
    }

    public void updateEmpresa(String cnpj, EmpresaDTO data) {
        try {
            Optional<Empresa> optionalEmpresa = empresaRepository.findBycnpj(cnpj);
            if (optionalEmpresa.isPresent()) {
                Empresa empresa = optionalEmpresa.get();
                empresa.update(data);
                empresaRepository.save(empresa);
            } else {
                throw new EntityNotExistException(cnpj);
            }
        } catch (EntityNotExistException ex) {
            String mensagem = ex.getMessage();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, mensagem);
        }
    }

    public void deleteEmpresa(String cnpj) throws EntityNotExistException{
        Optional<Empresa> optionalEmpresa = empresaRepository.findBycnpj(cnpj);
        if(optionalEmpresa.isPresent()){
            Empresa empresa = optionalEmpresa.get();
            empresaRepository.delete(empresa);
        }else{
            throw new EntityNotExistException(cnpj);
        }
    }



private String generateRandomCdEmpresa() {
    // Gera um número aleatório
    Random random = new Random();
    int randomNumber = random.nextInt(100); // Você pode ajustar o intervalo conforme necessário

    // Formata o número aleatório como "LBVT01", "LBVT02", etc.
    String randomCdEmpresa = "LBVT" + String.format("%02d", randomNumber);

    return randomCdEmpresa;
}
    public Empresa convertMapToEmpresa(Map<String, Object> ide) {
        Empresa empresa = new Empresa();
        empresa.setCnpj((String) ide.get("CNPJ"));
        empresa.setXNome((String) ide.get("xNome"));
        empresa.setIE((String) ide.get("IE"));
        empresa.setCRT((String) ide.get("CRT"));
        empresa.setCdEmpresa((String) generateRandomCdEmpresa());

        return empresa;
    }

}
