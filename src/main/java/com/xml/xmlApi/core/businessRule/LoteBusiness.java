package com.xml.xmlApi.core.businessRule;

import com.xml.xmlApi.Adapters.exceptions.exceptionsFornecedor.EntityAlreadyExistException;
import com.xml.xmlApi.Infrastructure.Repository.FornecedorRepository;
import com.xml.xmlApi.Infrastructure.Repository.LoteRepository;
import com.xml.xmlApi.core.domain.Fornecedor.EnderFornecedor;
import com.xml.xmlApi.core.domain.Fornecedor.Fornecedor;
import com.xml.xmlApi.core.domain.Lote.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoteBusiness {

    @Autowired
    private LoteRepository loteRepository;

    @Transactional
    public void postloteXML(Map<String, Object> rastro) throws EntityAlreadyExistException {
        // Verifica se um lote com as mesmas informações já existe
        if (loteAlreadyExists(rastro)) {
            throw new EntityAlreadyExistException("Lote já existe no banco de dados");
        }


        Lote lote = convertMapToLote(rastro);
        loteRepository.save(lote);
    }

    public boolean loteAlreadyExists(Map<String, Object> rastro) {
        // Realize uma consulta no banco de dados para verificar se um lote com as mesmas informações já existe.
        String nLote = (String) rastro.get("nLote");
        String dFab = (String) rastro.get("dFab");
        String dVal = (String) rastro.get("dVal");

        // Exemplo de consulta (dependendo da sua entidade Lote e do repositório):
        List<Lote> existingLotes = loteRepository.findLoteByNLoteDFabDVal(nLote, dFab, dVal);

        return !existingLotes.isEmpty();
    }

    public Lote convertMapToLote(Map<String, Object> ide) {
        Lote lote = new Lote();
        lote.setNLote((String) ide.get("nLote"));
        lote.setDFab((String) ide.get("dFab"));
        lote.setDVal((String) ide.get("dVal"));
        return lote;
    }

}
