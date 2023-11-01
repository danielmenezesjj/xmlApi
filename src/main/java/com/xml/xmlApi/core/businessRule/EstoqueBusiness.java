package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.ProdutoDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Infrastructure.Repository.EstoqueRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoRepository;
import com.xml.xmlApi.core.domain.Estoque.Estoque;
import com.xml.xmlApi.core.domain.Produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EstoqueBusiness {

    @Autowired
    private EstoqueRepository estoqueRepository;



    public Estoque postProduto(Estoque estoque) throws EntityAlreadyExistExceptionCdFornecedor {
      return estoqueRepository.save(estoque);
    }

    public Page<Estoque> getAll(Pageable pageable){
        return estoqueRepository.findAll(pageable);
    }


}
