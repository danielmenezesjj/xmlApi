package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
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

@Service
public class FornecedorBusiness {

    @Autowired
    private FornecedorRepository fornecedorRepository;


    @Autowired
    private FornecedorEnderecoBusiness fornecedorEnderecoBusiness;

    public void postFornecedorXML(Map<String, Object> ide) throws EntityAlreadyExistException {

        // Convert Map to Fornecedor
        Fornecedor fornecedor = convertMapToFornecedor(ide);
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(fornecedor.getCnpj());

        // Extract Endereco details from the XML map
        Map<String, Object> enderecoMap = (Map<String, Object>) ide.get("enderEmit");
        EnderFornecedor endereco = fornecedorEnderecoBusiness.convertMapToEmit(enderecoMap);
        if(optionalFornecedor.isPresent()){
            throw new EntityAlreadyExistException(fornecedor.getCnpj());
        }else{
            // Associating Endereco with Fornecedor
            fornecedor.setEnderFornecedor(endereco);

            // Saving Fornecedor with associated Endereco
            fornecedorRepository.save(fornecedor);
        }
    }

    public Fornecedor postFornecedor(Fornecedor fornecedor) throws EntityAlreadyExistException {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(fornecedor.getCnpj());
        if(optionalFornecedor.isPresent()){
            throw new EntityAlreadyExistException(fornecedor.getCnpj());
        }else{
            return fornecedorRepository.save(fornecedor);
        }
    }

    public Page<Fornecedor> getAll(Pageable pageable){
        return fornecedorRepository.findAll(pageable);
    }

    public Fornecedor getOne(String cnpj) throws EntityNotExistException {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(cnpj);
        if(!optionalFornecedor.isPresent()){
            throw new EntityNotExistException(cnpj);
        }else{
            return optionalFornecedor.get();
        }
    }

    public void updateFornecedor(String cnpj, FornecedorDTO data) {
        try {
            Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(cnpj);
            if (optionalFornecedor.isPresent()) {
                Fornecedor fornecedor = optionalFornecedor.get();
                fornecedor.update(data);
                fornecedorRepository.save(fornecedor);
            } else {
                throw new EntityNotExistException(cnpj);
            }
        } catch (EntityNotExistException ex) {
            String mensagem = ex.getMessage();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, mensagem);
        }
    }

    public void deleteFornecedor(String cnpj) throws EntityNotExistException{
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(cnpj);
        if(optionalFornecedor.isPresent()){
            Fornecedor fornecedor = optionalFornecedor.get();
            fornecedorRepository.delete(fornecedor);
        }else{
            throw new EntityNotExistException(cnpj);
        }
    }



    public Fornecedor convertMapToFornecedor(Map<String, Object> ide) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj((String) ide.get("CNPJ"));
        fornecedor.setXNome((String) ide.get("xNome"));
        fornecedor.setIE((String) ide.get("IE"));
        fornecedor.setCRT((String) ide.get("CRT"));
        return fornecedor;
    }

}
