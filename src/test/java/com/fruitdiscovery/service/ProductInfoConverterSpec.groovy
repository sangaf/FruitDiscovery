package com.fruitdiscovery.service

import com.fruitdiscovery.domain.ProductInfo
import spock.lang.Specification

/**
 * Created by sanjidagafur on 10/04/2016.
 */
class ProductInfoConverterSpec extends Specification {

    //list of productInfo to convert to putput Map
    private List<ProductInfo> productInfos = new ArrayList<>()

    //Object in test
    private ProductInfoConverter testObj

    def setup(){
        //initializing the test object
        testObj = new ProductInfoConverter()

        //building up the productInfo array
        productInfos.add(new ProductInfo("Sainsbury's Avocado, Ripe & Ready x2","3kb", 1.80,"Avocados"))
        productInfos.add(new ProductInfo("Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)","3kb", 1.50,"Conference"))
        productInfos.add(new ProductInfo("Sainsbury's Kiwi Fruit, Ripe & Ready x4","3kb", 1.50,"Kiwi"))
    }

    def "createMapForOutput returns a map for final JSON output"(){
        given:
            //expected MAP
            Map<String, Object> expected = [results:productInfos, total:4.80]

        when:
            Map<String, Object> actual = testObj.createMapForOutput(productInfos)

        then:
            actual == expected
    }

    def "getJSONString converts a Map to a JSON string"(){
        given:
            //expected JSON output
            final String EXPECTED_JSON_STRING = """{"results":[{"title":"Sainsbury's Avocado, Ripe & Ready x2","size":"3kb","description":"Avocados","unit_price":1.80},{"title":"Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)","size":"3kb","description":"Conference","unit_price":1.50},{"title":"Sainsbury's Kiwi Fruit, Ripe & Ready x4","size":"3kb","description":"Kiwi","unit_price":1.50}],"total":4.80}"""

        when:
            String actual = testObj.getJSONString(productInfos)

        then:
            actual == EXPECTED_JSON_STRING
    }

}
