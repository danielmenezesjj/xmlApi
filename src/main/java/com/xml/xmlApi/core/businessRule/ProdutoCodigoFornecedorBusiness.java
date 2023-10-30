package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.Dtos.CodigoFornecedorDTO;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityAlreadyExistExceptionCdFornecedor;
import com.xml.xmlApi.Adapters.exceptions.exceptionsCodigoFornecedor.EntityNotExistExceptionCdFornecedor;
import com.xml.xmlApi.Infrastructure.Repository.CodigoFornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoCodigoFornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.ProdutoRepository;
import com.xml.xmlApi.core.domain.CodigoFornecedor.CodigoDoFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.Produto.Produto;
import com.xml.xmlApi.core.domain.ProdutoCodigoFornecedor.ProdutoCodigoFornecedor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoCodigoFornecedorBusiness {

    @Autowired
    private ProdutoCodigoFornecedorRepository produtoCodigoFornecedorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    public CodigoDoFornecedor associarProduto(CodigoDoFornecedor codigoDoFornecedor, String cdproduto, String cdfornecedor) throws EntityAlreadyExistExceptionCdFornecedor {
        // Obtenha o Produto pelo ID
        Optional<Produto> produtoOptional = produtoRepository.findBycdproduto(cdproduto);

        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();

            // Crie uma instância de ProdutoCodigoFornecedor
            ProdutoCodigoFornecedor produtoCodigoFornecedor = new ProdutoCodigoFornecedor();
            produtoCodigoFornecedor.setProduto(produto);
            produtoCodigoFornecedor.setCodigoFornecedor(codigoDoFornecedor);
            produtoCodigoFornecedor.setCdProduto(cdproduto);
            produtoCodigoFornecedor.setCdFornecedor(cdfornecedor);


            // Salve a associação na tabela de junção
            produtoCodigoFornecedorRepository.save(produtoCodigoFornecedor);

            // Você pode retornar o código do fornecedor se necessário
            return codigoDoFornecedor;
        } else {
            // Lida com o caso em que o produto com o ID especificado não foi encontrado
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + cdproduto);
        }
    }

    public List<ProdutoCodigoFornecedor> compararCodigosFornecedor(List<String> cProdList) {
        // Consulta os registros na tabela intermediária que correspondem aos códigos de produtos do fornecedor
        List<ProdutoCodigoFornecedor> codigosFornecedor = produtoCodigoFornecedorRepository.findByCdFornecedorIn(cProdList);
        return codigosFornecedor;
    }



    public Page<ProdutoCodigoFornecedor> getAll(Pageable pageable){
        return produtoCodigoFornecedorRepository.findAll(pageable);
    }

    public List<ProdutoCodigoFornecedor> produtosFornecedorAssociado(String cdProduto) throws EntityNotExistExceptionCdFornecedor {
        List<ProdutoCodigoFornecedor> produtoCodigoFornecedorList = produtoCodigoFornecedorRepository.findByCdProduto(cdProduto);
        if(produtoCodigoFornecedorList.isEmpty()){
            throw new EntityNotExistExceptionCdFornecedor(cdProduto);
        }else{
            return produtoCodigoFornecedorList;
        }
    }


    public List<ProdutoCodigoFornecedor> getAllComParametro(String cdProduto) throws EntityNotExistExceptionCdFornecedor {
        List<ProdutoCodigoFornecedor> produtoCodigoFornecedorList = produtoCodigoFornecedorRepository.findByCdProduto(cdProduto);
        if(produtoCodigoFornecedorList.isEmpty()){
            throw new EntityNotExistExceptionCdFornecedor(cdProduto);
        }else{
            return produtoCodigoFornecedorList;
        }
    }
//
//    public List<CodigoDoFornecedor> getAllCodigosEmpresa(Integer cdEmpresa) throws EntityNotExistExceptionCdFornecedor {
//        List<CodigoDoFornecedor> codigoDoFornecedorList = codigoFornecedorRepository.findByCdEmpresa(cdEmpresa);
//        if (codigoDoFornecedorList.isEmpty()) {
//            throw new EntityNotExistExceptionCdFornecedor("Códigos não encontrados para a empresa com o ID: " + cdEmpresa);
//        } else {
//            return codigoDoFornecedorList;
//        }
//    }
//
//
//    public void updateCodigoFornecedor(String cdFornecedor, CodigoFornecedorDTO data) {
//        try {
//            Optional<CodigoDoFornecedor> optionalCodigoDoFornecedor = codigoFornecedorRepository.findBycdfornecedor(cdFornecedor);
//            if (optionalCodigoDoFornecedor.isPresent()) {
//                CodigoDoFornecedor codigoDoFornecedor = optionalCodigoDoFornecedor.get();
//                codigoDoFornecedor.update(data);
//                codigoFornecedorRepository.save(codigoDoFornecedor);
//            } else {
//                throw new EntityNotExistExceptionCdFornecedor(cdFornecedor);
//            }
//        } catch (EntityNotExistExceptionCdFornecedor ex) {
//            String mensagem = ex.getMessage();
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, mensagem);
//        }
//    }
//
//    public void deleteCodigoFornecedor(String cdfornecedor) throws EntityNotExistExceptionCdFornecedor{
//        Optional<CodigoDoFornecedor> optionalCodigoDoFornecedor = codigoFornecedorRepository.findBycdfornecedor(cdfornecedor);
//        if(optionalCodigoDoFornecedor.isPresent()){
//            CodigoDoFornecedor codigoDoFornecedor = optionalCodigoDoFornecedor.get();
//            codigoFornecedorRepository.delete(codigoDoFornecedor);
//        }else{
//            throw new EntityNotExistExceptionCdFornecedor(cdfornecedor);
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
