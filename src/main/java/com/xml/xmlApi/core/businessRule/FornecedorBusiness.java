package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.exceptions.EntityAlreadyExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Fornecedor getOne(String cnpj) throws EntityAlreadyExistException {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(cnpj);
        if(!optionalFornecedor.isPresent()){
            throw new EntityAlreadyExistException(cnpj);
        }else{
            return optionalFornecedor.get();
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
