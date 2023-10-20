package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.Dtos.ProdutoDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Infrastructure.Repository.CodigoFornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoRepository;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.Produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoBusiness {

    @Autowired
    private ProdutoRepository produtoRepository;



    public Produto postProduto(Produto produto) throws EntityAlreadyExistExceptionCdFornecedor {
      return produtoRepository.save(produto);
    }

    public Page<Produto> getAll(Pageable pageable){
        return produtoRepository.findAll(pageable);
    }

    public Produto getOne(String cdproduto) throws EntityNotExistExceptionCdFornecedor {
        Optional<Produto> optionalProduto = produtoRepository.findBycdproduto(cdproduto);
        if(!optionalProduto.isPresent()){
            throw new EntityNotExistExceptionCdFornecedor(cdproduto);
        }else{
            return optionalProduto.get();
        }
    }


    public void updateProduto(String cdproduto, ProdutoDTO data) {
        try {
            Optional<Produto> optionalProduto = produtoRepository.findBycdproduto(cdproduto);
            if (optionalProduto.isPresent()) {
                Produto produto = optionalProduto.get();
                produto.update(data);
                produtoRepository.save(produto);
            } else {
                throw new EntityNotExistExceptionCdFornecedor(cdproduto);
            }
        } catch (EntityNotExistExceptionCdFornecedor ex) {
            String mensagem = ex.getMessage();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, mensagem);
        }
    }

    public void deleteProduto(String cdproduto) throws EntityNotExistExceptionCdFornecedor{
        Optional<Produto> optionalProduto = produtoRepository.findBycdproduto(cdproduto);
        if(optionalProduto.isPresent()){
            Produto produto = optionalProduto.get();
            produtoRepository.delete(produto);
        }else{
            throw new EntityNotExistExceptionCdFornecedor(cdproduto);
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
