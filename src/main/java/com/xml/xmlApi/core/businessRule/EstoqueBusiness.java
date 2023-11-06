package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.ProdutoDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Infrastructure.Repository.EstoqueRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoRepository;
import com.xml.xmlApi.core.domain.Estoque.Estoque;
import com.xml.xmlApi.core.domain.Produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueBusiness {

    @Autowired
    private EstoqueRepository estoqueRepository;



    public Estoque postProduto(Estoque estoque) throws EntityAlreadyExistExceptionCdFornecedor {
        // Verifique se já existe um estoque com base em cdProduto, nlote, dfab, dval e empresa_id
        Estoque existingEstoque = estoqueRepository.findEstoqueByChaveComposta(
                estoque.getCdProduto(),
                estoque.getNlote(),
                estoque.getDtfab(),
                estoque.getDtval()

        );
        if (existingEstoque != null) {
            // O estoque já existe, atualize-o ou realize alguma outra ação necessária
            existingEstoque.setQlote(existingEstoque.getQlote() + estoque.getQlote()); // Exemplo: Adicionar quantidade ao estoque existente
            return estoqueRepository.save(existingEstoque);
        } else {
            // O estoque não existe, crie um novo
            return estoqueRepository.save(estoque);
        }
    }

    public Page<Estoque> getAll(Pageable pageable){
        return estoqueRepository.findAll(pageable);
    }

    public List<Estoque> getEstoqueCdEmpresa(Integer cdEmpresa) throws EntityNotExistExceptionCdFornecedor, ChangeSetPersister.NotFoundException {
        List<Estoque> estoqueList = estoqueRepository.findAllByEmpresaId(cdEmpresa);
        if(!estoqueList.isEmpty()){
            return estoqueList;
        }else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }


}
