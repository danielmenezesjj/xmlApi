package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Infrastructure.Repository.DocumentoRepository;
import com.xml.xmlApi.Infrastructure.Repository.EmpresaRepository;
import com.xml.xmlApi.core.domain.Documento.Documento;
import com.xml.xmlApi.core.domain.Empresa.Empresa;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class DocumentoBusiness {


    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public Documento postFornecedor(Documento documento) throws EntityAlreadyExistException {
        Optional<Documento> optionalDocumento = documentoRepository.findBychaveacesso(documento.getChaveacesso());
        if(optionalDocumento.isPresent()){
            throw new EntityAlreadyExistException(documento.getFornecedor());
        }else{
            return documentoRepository.save(documento);
        }
    }

    public Page<Documento> getAll(Pageable pageable){
        return documentoRepository.findAll(pageable);
    }


    public Exception postDocumentoXML(Map<String, Object> ide) throws EntityAlreadyExistException {
        // Convert Map to Fornecedor
        Documento documento = convertMapToDocumento(ide);
        Optional<Documento> optionalDocumento = documentoRepository.findBychaveacesso(documento.getChaveacesso());
        if(!optionalDocumento.isPresent()){
            documentoRepository.save(documento);
        }else{
            return new Exception();
        }

        return null;
    }

    public Date createAtautomatico(){
        Date createdDate = new Date();
        return createdDate;
    }

    public Documento convertMapToDocumento(Map<String, Object> ide) {
        Documento documento = new Documento();
        Integer empresaId = (Integer) ide.get("empresa_id");
        Optional<Empresa> empresaOptional = empresaRepository.findById(empresaId);
        Empresa empresa = empresaOptional.get();
        documento.setFornecedor((String) ide.get("fornecedor"));
        documento.setChaveacesso((String) ide.get("chaveacesso"));
        documento.setNmoperacao((String) ide.get("nmoperacao"));
        documento.setNrdocumento((String) ide.get("nrdocumento"));
        documento.setDtemissao((String) ide.get("dtemissao"));
        documento.setCreatedDate(createAtautomatico());  // Corrigido para chamar o método createAtautomatico
        documento.setUsuario((String) ide.get("usuario"));
        documento.setEmpresa(empresa);


        return documento;
    }





}
