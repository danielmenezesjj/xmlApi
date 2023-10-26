package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityNotExistException;
import com.xml.xmlApi.Infrastructure.Repository.CodigoFornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoCodigoFornecedorRepository;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.ProdutoCodigoFornecedor.ProdutoCodigoFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CodigoFornecedorBusiness {

    @Autowired
    private CodigoFornecedorRepository codigoFornecedorRepository;


    @Autowired
    private FornecedorEnderecoBusiness fornecedorEnderecoBusiness;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ProdutoCodigoFornecedorRepository produtoCodigoFornecedorRepository;


    public CodigoDoFornecedor postCodigoFornecedor(CodigoDoFornecedor codigoDoFornecedor, Integer id) throws EntityAlreadyExistExceptionCdFornecedor {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        codigoDoFornecedor.setFornecedor(fornecedor.get());
      return codigoFornecedorRepository.save(codigoDoFornecedor);
    }

    public Page<CodigoDoFornecedor> getAll(Pageable pageable){
        return codigoFornecedorRepository.findAll(pageable);
    }

    public CodigoDoFornecedor getOne(String cdfornecedor) throws EntityNotExistExceptionCdFornecedor {
        Optional<CodigoDoFornecedor> optionalCodigoDoFornecedor = codigoFornecedorRepository.findBycdfornecedor(cdfornecedor);
        if(!optionalCodigoDoFornecedor.isPresent()){
            throw new EntityNotExistExceptionCdFornecedor(cdfornecedor);
        }else{
            return optionalCodigoDoFornecedor.get();
        }
    }

    public List<CodigoDoFornecedor> getAllCodigosEmpresa(Integer cdEmpresa) throws EntityNotExistExceptionCdFornecedor {
        List<CodigoDoFornecedor> codigoDoFornecedorList = codigoFornecedorRepository.findByCdEmpresa(cdEmpresa);
        if (codigoDoFornecedorList.isEmpty()) {
            throw new EntityNotExistExceptionCdFornecedor("Códigos não encontrados para a empresa com o ID: " + cdEmpresa);
        } else {
            return codigoDoFornecedorList;
        }
    }

    public List<ProdutoCodigoFornecedor> getAllCodigosRelacionado(String cdEmpresa) throws EntityNotExistExceptionCdFornecedor {
        List<ProdutoCodigoFornecedor> codigoDoFornecedorList = produtoCodigoFornecedorRepository.findByCodigoFornecedorList(cdEmpresa);
        if (codigoDoFornecedorList.isEmpty()) {
            throw new EntityNotExistExceptionCdFornecedor("Códigos não encontrados para a empresa com o ID: " + cdEmpresa);
        } else {
            return codigoDoFornecedorList;
        }
    }



    public void updateCodigoFornecedor(String cdFornecedor, CodigoFornecedorDTO data) {
        try {
            Optional<CodigoDoFornecedor> optionalCodigoDoFornecedor = codigoFornecedorRepository.findBycdfornecedor(cdFornecedor);
            if (optionalCodigoDoFornecedor.isPresent()) {
                CodigoDoFornecedor codigoDoFornecedor = optionalCodigoDoFornecedor.get();
                codigoDoFornecedor.update(data);
                codigoFornecedorRepository.save(codigoDoFornecedor);
            } else {
                throw new EntityNotExistExceptionCdFornecedor(cdFornecedor);
            }
        } catch (EntityNotExistExceptionCdFornecedor ex) {
            String mensagem = ex.getMessage();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, mensagem);
        }
    }

    public void deleteCodigoFornecedor(String cdfornecedor) throws EntityNotExistExceptionCdFornecedor{

        Optional<ProdutoCodigoFornecedor> optionalProdutoCodigoFornecedor = produtoCodigoFornecedorRepository.findByCodigoFornecedor(cdfornecedor);
        ProdutoCodigoFornecedor produtoCodigoFornecedor = optionalProdutoCodigoFornecedor.get();
        produtoCodigoFornecedorRepository.delete(produtoCodigoFornecedor);
        Optional<CodigoDoFornecedor> optionalCodigoDoFornecedor = codigoFornecedorRepository.findBycdfornecedor(cdfornecedor);
        if(optionalCodigoDoFornecedor.isPresent()){
            CodigoDoFornecedor codigoDoFornecedor = optionalCodigoDoFornecedor.get();
            codigoFornecedorRepository.delete(codigoDoFornecedor);
        }else{
            throw new EntityNotExistExceptionCdFornecedor(cdfornecedor);
        }
    }



//    public Fornecedor convertMapToFornecedor(Map<String, Object> ide) {
//        Fornecedor fornecedor = new Fornecedor();
//        fornecedor.setCnpj((String) ide.get("CNPJ"));
//        fornecedor.setXNome((String) ide.get("xNome"));
//        fornecedor.setIE((String) ide.get("IE"));
//        fornecedor.setCRT((String) ide.get("CRT"));
//        return fornecedor;
//    }

}
