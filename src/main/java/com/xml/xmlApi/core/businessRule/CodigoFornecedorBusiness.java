package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.FornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.CodigoFornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
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
public class CodigoFornecedorBusiness {

    @Autowired
    private CodigoFornecedorRepository codigoFornecedorRepository;


    @Autowired
    private FornecedorEnderecoBusiness fornecedorEnderecoBusiness;

    @Autowired
    private FornecedorRepository fornecedorRepository;



    public CodigoDoFornecedor postCodigoFornecedor(CodigoDoFornecedor codigoDoFornecedor, Integer id) throws EntityAlreadyExistException {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        codigoDoFornecedor.setFornecedor(fornecedor.get());
      return codigoFornecedorRepository.save(codigoDoFornecedor);
    }

    public Page<CodigoDoFornecedor> getAll(Pageable pageable){
        return codigoFornecedorRepository.findAll(pageable);
    }

    public CodigoDoFornecedor getOne(String cdfornecedor) throws EntityNotExistException {
        Optional<CodigoDoFornecedor> optionalCodigoDoFornecedor = codigoFornecedorRepository.findBycdfornecedor(cdfornecedor);
        if(!optionalCodigoDoFornecedor.isPresent()){
            throw new EntityNotExistException(cdfornecedor);
        }else{
            return optionalCodigoDoFornecedor.get();
        }
    }

//    public void updateCodigo(String cnpj, FornecedorDTO data) {
//        try {
//            Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(cnpj);
//            if (optionalFornecedor.isPresent()) {
//                Fornecedor fornecedor = optionalFornecedor.get();
//                fornecedor.update(data);
//                fornecedorRepository.save(fornecedor);
//            } else {
//                throw new EntityNotExistException(cnpj);
//            }
//        } catch (EntityNotExistException ex) {
//            String mensagem = ex.getMessage();
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, mensagem);
//        }
//    }
//
//    public void deleteFornecedor(String cnpj) throws EntityNotExistException{
//        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findBycnpj(cnpj);
//        if(optionalFornecedor.isPresent()){
//            Fornecedor fornecedor = optionalFornecedor.get();
//            fornecedorRepository.delete(fornecedor);
//        }else{
//            throw new EntityNotExistException(cnpj);
//        }
//    }



//    public Fornecedor convertMapToFornecedor(Map<String, Object> ide) {
//        Fornecedor fornecedor = new Fornecedor();
//        fornecedor.setCnpj((String) ide.get("CNPJ"));
//        fornecedor.setXNome((String) ide.get("xNome"));
//        fornecedor.setIE((String) ide.get("IE"));
//        fornecedor.setCRT((String) ide.get("CRT"));
//        return fornecedor;
//    }

}
