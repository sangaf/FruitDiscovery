package com.fruitdiscovery.service;

import com.fruitdiscovery.domain.ProductInfo;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sanjidagafur on 10/04/2016.
 */
public class ProductInfoConverter {

    public Map<String, Object> createMapForOutput(List<ProductInfo> productInfos) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        BigDecimal total = productInfos.stream().map(ProductInfo::getUnitPrice).reduce(new BigDecimal(0), (a, b)-> a.add(b));

        resultMap.put("results", productInfos);
        resultMap.put("total", total);

        return resultMap;
    }

    public String getJSONString(List<ProductInfo> productInfos) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(createMapForOutput(productInfos));
    }
}
